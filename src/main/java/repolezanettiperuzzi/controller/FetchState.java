package repolezanettiperuzzi.controller;

import org.json.simple.parser.ParseException;
import repolezanettiperuzzi.common.DynamicPath;
import repolezanettiperuzzi.model.GameBoard;
import repolezanettiperuzzi.model.Player;
import repolezanettiperuzzi.model.Window;
import repolezanettiperuzzi.model.actions.InitializeGame;
import repolezanettiperuzzi.model.actions.SetWindowAction;
import repolezanettiperuzzi.model.actions.TakeTwoCardWindowAction;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * Classe che rappresenta lo stato di inizializzazione del gioco
 * @author Giampiero Repole
 */
public class FetchState extends ControllerState {

    private GameBoard board;
    private Controller controller;
    private ArrayList<Window> windows;

    /**
     * Esegue la inizialize game
     * @param controller Controller
     * @throws IOException Fallimento o interruzione delle operazioni I/O
     */
    @Override
    public void doAction(Controller controller) throws IOException {

        this.controller = controller;
        this.board=controller.board;

        if(board.getWindowsPool()==null){

            board.initPlayersWindowsChoices();
            InitializeGame init = new InitializeGame();
            init.doAction(board);
            board.setWindowsPool((ArrayList<Window>) init.getWindows());

        }
    }

    /**
     * Invia le windows
     * @param player Player
     * @throws IOException Fallimento o interruzione delle operazioni I/O
     */
    public void sendWindows(Player player) throws IOException {

        player.setLastScene("chooseWindowRoom");

        if (!controller.isTimerOn()) {

            controller.setTimer("chooseWindow");

        }

        if (player.getConnection().equals("Socket")) {

            if (player.getWindow() == null) {
                if (board.getPlayersWindowsChoices(player) == null) {
                    board.putPlayersWindowsChoices(player, (ArrayList<Window>) new TakeTwoCardWindowAction().doAction(board.getWindowsPool()));
                }
                String message = this.windowsToString(board.getPlayersWindowsChoices(player));
                Socket socket = new Socket(player.getAddress(), player.getPort());
                HandlerControllerSocket handler = new HandlerControllerSocket(this.controller, socket);
                handler.askWindow(message, controller.getCurrentTime());
            } else {
                ArrayList<Window> oneWindow = new ArrayList<>();
                oneWindow.add(player.getWindow());
                String message = this.windowsToString(oneWindow);
                Socket socket = new Socket(player.getAddress(), player.getPort());
                HandlerControllerSocket handler = new HandlerControllerSocket(this.controller, socket);
                handler.showChosenWindow(message, controller.getCurrentTime());
            }

        } else if (player.getConnection().equals("RMI")) {
            if (player.getWindow() == null) {
                if (board.getPlayersWindowsChoices(player) == null) {
                    board.putPlayersWindowsChoices(player, (ArrayList<Window>) new TakeTwoCardWindowAction().doAction(board.getWindowsPool()));
                }
                ArrayList<Window> windows = board.getPlayersWindowsChoices(player);
                this.windows = windows;
            } else {
                ArrayList<Window> oneWindow = new ArrayList<>();
                oneWindow.add(player.getWindow());
                this.windows = oneWindow;
            }
        }

    }

    /**
     * Crea e ritorna una stringa che rappresenta le window selezionabili dal player
     * @param windows ArrayList di window
     * @return Stringa che rappresenta le window selezionabili dal player
     * @throws IOException Fallimento o interruzione delle operazioni I/O
     */
    //create a string that contains the windows selectable from the player
    private String windowsToString(ArrayList<Window> windows) throws IOException {

        DynamicPath dP = new DynamicPath("");

        if(dP.isJar()){

            StringBuilder message = new StringBuilder();

            for (Window window: windows) {
                String elemPath = "/cards/gamemaps/" + window.fileName + ".txt";
                BufferedReader bR = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(elemPath.toString())));
                String line;
                while((line=bR.readLine())!=null){
                    message.append(line.replace(" ", "-"));
                    message.append(" ");
                }
                message.append("_ ");
                bR.close();
            }

            return String.valueOf(message);
        } else {
            StringBuilder message = new StringBuilder();

            for (Window window: windows) {

                ArrayList<String> lines = (ArrayList<String>) Files.readAllLines(Paths.get(new DynamicPath("cards/gamemaps/" + window.fileName + ".txt").getPathNoFile()));

                for(String line: lines){

                    message.append(line.replace(" ","-"));
                    message.append(" ");

                }

                message.append("_ ");
            }

            return String.valueOf(message);
        }

    }

    /**
     *
     * @return ArrayList delle window
     */
    public ArrayList<Window> getWindows(){
        return this.windows;
    }

    /**
     * Assegna una window al player se non ne ha scelta una entro la scadenza del timer
     * @param player Player giocatore
     * @param choose Window scelta dal player (nome della window)
     * @throws IOException Fallimento o interruzione delle operazioni I/O
     * @throws ParseException Errore durante l'analisi
     */
    public void setChosenWindowOnTimer(Player player, String choose) throws IOException, ParseException {

        for (Window window : board.getPlayersWindowsChoices(player)) {

            if (window.getName().equals(choose)) {

                new SetWindowAction().doAction(player, window);
                break;

            }

        }
    }

    /**
     * Assegna la window al player se non l'ha scelta in tempo
     * @param player Player
     * @param choose Nome della window
     * @throws IOException Fallimento o interruzione delle operazioni I/O
     * @throws ParseException Errore durante l'analisi
     */
    public void setChosenWindow(Player player, String choose) throws IOException, ParseException {

        for (Window window : board.getPlayersWindowsChoices(player) ) {

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
        //if(isTimerOn() && giocatori con window!=null == 4) --> chiamo la check
        //tutti i giocatori hanno scelto una window prima dello scadere del timer
        checkConnectedPlayers();

    }

    /**
     * Controlla la connessione dei player
     * @throws IOException Fallimento o interruzione delle operazioni I/O
     * @throws ParseException Errore durante l'analisi
     */
    public void checkConnectedPlayers() throws IOException, ParseException {

        if(controller.board.getPlayersOnline()>=2){

            board.setFetchPlayersToCheck(board.getPlayersOnline());

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
                            controller.getHandlerRMI().enterGame(player.getName());
                        }

                    }else if(player.getLastScene().equals("chooseWindowRoom")&& !player.getLiveStatus()){
                        player.setLastScene("game");
                    }

                }else{

                    if(player.getLastScene().equals("chooseWindowRoom")&& player.getLiveStatus()){


                        this.setChosenWindowOnTimer(player,board.getPlayersWindowsChoices(player).get(0).getName());

                        if (player.getConnection().equals("Socket")) {

                            try (Socket socket = new Socket(player.getAddress(), player.getPort())) {

                                HandlerControllerSocket handlerControllerSocket = new HandlerControllerSocket(controller, socket);
                                handlerControllerSocket.notifyOnStartGame();

                            } catch (IOException e) {

                                e.printStackTrace();

                            }

                        } else if (controller.board.getPlayers().get(i).getConnection().equals("RMI")) {
                            controller.getHandlerRMI().enterGame(player.getName());
                        }

                    }else if(player.getLastScene().equals("chooseWindowRoom")&& !player.getLiveStatus()){

                        this.setChosenWindowOnTimer(player,board.getPlayersWindowsChoices(player).get(0).getName());
                        player.setLastScene("game");

                    }
                }
            }

        }else if(controller.board.getPlayersOnline()==1){       //caso in cui ci sia solo un giocatore rimasto online

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
                        //System.exit(0);//esce senza errori

                    } else if (controller.board.getPlayers().get(i).getConnection().equals("RMI")) {
                        controller.getHandlerRMI().showWinOnChooseWindowAlert(player.getName());
                        controller.cancelTimer();
                        //System.exit(0);//esce senza errori
                    }
                }
            }

        }else{

            controller.cancelTimer();
            System.exit(0); //esce senza errori

        }
    }

    /**
     * Inizializza il controller al begin round state per iniziare il round se i player sono pronti
     * @param playerName Nome del player
     * @throws IOException Fallimento o interruzione delle operazioni I/O
     * @throws ParseException Errore durante l'analisi
     */
    public void readyToPlay(String playerName) throws IOException, ParseException {

        board.incrFetchReadyPlayers();
        board.getPlayerByName(playerName).setLastScene("game");

        if(board.getFetchPlayersToCheck()==board.getFetchReadyPlayers()){
            if(controller.isTimerOn()){
                controller.cancelTimer();
            }
            controller.setState(new BeginRoundState());

        }

    }


}
