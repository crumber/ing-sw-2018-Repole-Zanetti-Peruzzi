package repolezanettiperuzzi.controller;

import org.json.simple.parser.ParseException;
import repolezanettiperuzzi.model.GameBoard;
import repolezanettiperuzzi.model.Player;
import repolezanettiperuzzi.model.Window;
import repolezanettiperuzzi.model.actions.InitializeGame;
import repolezanettiperuzzi.model.actions.SetWindowAction;
import repolezanettiperuzzi.model.actions.TakeTwoCardWindowAction;

import java.io.IOException;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;



public class FetchState extends ControllerState {

    private GameBoard board;
    private HashMap<Player,ArrayList<Window>> playersWindowsChoices;
    private ArrayList<Window> windows;
    private Controller controller;
    private int readyPlayers = 0;
    private int playersToCheck;

    @Override
    public void doAction(Controller controller) throws IOException, ParseException {

        this.controller = controller;
        this.board=controller.board;
        playersWindowsChoices = new HashMap<>();
        InitializeGame init = new InitializeGame();
        init.doAction(board);
        this.windows = (ArrayList<Window>) init.getWindows();

    }

    public void sendWindows(Player player) throws IOException {

        player.setLastScene("chooseWindowRoom");

        if(!controller.isTimerOn()){

            controller.setTimer("chooseWindow");

        }

        if (player.getConnection().equals("Socket")) {

            playersWindowsChoices.put(player, (ArrayList<Window>) new TakeTwoCardWindowAction().doAction(windows));
            String message = this.windowsToString(playersWindowsChoices.get(player));
            Socket socket = new Socket(player.getAddress(), player.getPort());
            HandlerControllerSocket handler = new HandlerControllerSocket(this.controller, socket);
            //System.out.println(controller.getCurrentTime());
            handler.askWindow(message,controller.getCurrentTime());

        } else if (player.getConnection().equals("RMI")) {

        }

    }

    //create a string that contains the windows selectable from the player
    private String windowsToString(ArrayList<Window> windows) throws IOException {

        StringBuilder message = new StringBuilder();

        for (Window window: windows) {

            ArrayList<String> lines = (ArrayList<String>) Files.readAllLines(Paths.get("cards/gamemaps/" + window.fileName + ".txt" ));

            for(String line: lines){

                message.append(line.replace(" ","-"));
                message.append(" ");

            }

            message.append("_ ");
        }

        return String.valueOf(message);

    }


    public void setChosenWindow(Player player, String choose) throws IOException, ParseException {

        for (Window window : this.playersWindowsChoices.get(player) ) {

            if(window.getName().equals(choose)){

                new SetWindowAction().doAction(player,window);
                break;

            }

        }

        //controllo se hanno impostato una window tutti gli utenti ammessi alla scelta delle window
        for(int i = 0; i<controller.board.getNPlayers(); i++){
            //esiste almeno un giocatore (online o offline) che non ha scelto una window.
            // Allora non posso ancora passare alla fase successiva finche non scade il timer
            if(board.getPlayer(i).getWindow()==null){
                return;
            }

        }

        //tutti i giocatori hanno scelto una window prima dello scadere del timer
        checkConnectedPlayers();

    }

    public void checkConnectedPlayers() throws IOException, ParseException {

        if(controller.board.getPlayersOnline()>=2){

            this.playersToCheck = controller.board.getPlayersOnline();

            for(int i = 0; i<board.getNPlayers(); i++){

                Player player = board.getPlayer(i);

                if(player.getWindow()!=null) {

                    if (player.checkLastScene("chooseWindowRoom") && player.getLiveStatus()) {

                        if (player.getConnection().equals("Socket")) {

                            try (Socket socket = new Socket(player.getAddress(), player.getPort())) {

                                HandlerControllerSocket handlerControllerSocket = new HandlerControllerSocket(controller, socket);
                                handlerControllerSocket.notifyOnStartGame();

                            } catch (IOException e) {

                                e.printStackTrace();

                            }

                        } else if (controller.board.getPlayers().get(i).getConnection().equals("RMI")) {

                        }

                    }

                }else{

                    if(player.getLastScene().equals("chooseWindowRoom")&& player.getLiveStatus()){


                        this.setChosenWindow(player,playersWindowsChoices.get(player).get(0).getName());

                        if (player.getConnection().equals("Socket")) {

                            try (Socket socket = new Socket(player.getAddress(), player.getPort())) {

                                HandlerControllerSocket handlerControllerSocket = new HandlerControllerSocket(controller, socket);
                                handlerControllerSocket.notifyOnStartGame();

                            } catch (IOException e) {

                                e.printStackTrace();

                            }

                        } else if (controller.board.getPlayers().get(i).getConnection().equals("RMI")) {

                        }

                    }else if(player.getLastScene().equals("chooseWindowRoom")&& !player.getLiveStatus()){

                        this.setChosenWindow(player,playersWindowsChoices.get(player).get(0).getName());

                    }
                }
            }

        }else if(controller.board.getPlayersOnline()==1){

            for(int i = 0; i<board.getNPlayers(); i++){

                Player player = board.getPlayer(i);

                if(player.checkLastScene("chooseWindowRoom") && player.getLiveStatus()) {

                    if (player.getConnection().equals("Socket")) {

                        try (Socket socket = new Socket(player.getAddress(), player.getPort())) {

                            HandlerControllerSocket handlerControllerSocket = new HandlerControllerSocket(controller, socket);
                            handlerControllerSocket.notifyWinOnChooseWindow();

                        } catch (IOException e) {

                            e.printStackTrace();

                        }

                        controller.cancelTimer();
                        System.exit(0);//esce senza errori

                    } else if (controller.board.getPlayers().get(i).getConnection().equals("RMI")) {

                    }
                }
            }

        }else{

            controller.cancelTimer();
            System.exit(0); //esce senza errori

        }
    }

    public void readyToPlay() throws IOException, ParseException {

        this.readyPlayers++;

        if(this.readyPlayers==this.playersToCheck){
            System.out.println("arrivato");
            controller.setState(new BeginRoundState());

        }

    }


}
