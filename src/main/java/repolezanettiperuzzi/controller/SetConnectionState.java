package repolezanettiperuzzi.controller;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import repolezanettiperuzzi.model.GameBoard;
import repolezanettiperuzzi.model.Player;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.*;


public class SetConnectionState extends ControllerState{

    private final Logger LOGGER = Logger.getLogger(SetConnectionState.class.getName());
    private GameBoard board;
    private Controller controller;

    @Override
    public void doAction(Controller controller) throws IOException, ParseException {

        this.controller = controller;
        this.board = controller.board;

    }

    //metodo chiamato dal giocatore appena si connette al server
    public void initializePlayer(String playerID, String pwd, InetAddress addr, int port, String connection, String UI) throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        FileReader jsonIn = new FileReader("gamedata/playersinfo.json");
        JSONArray jsonArr = (JSONArray) parser.parse(jsonIn);

        try {

            boolean alreadyRegistered = false;

            //playerID, pwd, RMI/Socket, CLI/GUI, addr, port
            for (Object o : jsonArr) {
                JSONObject player = (JSONObject) o;

                String name = (String) player.get("playerID");

                String passwd = (String) player.get("pwd");

                //TODO controllo anche che il giocare aveva scelto una window prima di disconnettersi
                if (name.equals(playerID) && passwd.equals(pwd)) {
                    //riconnetto il giocatore
                    onReconnect();
                    alreadyRegistered = true;
                    break;
                }
            }

            if(!alreadyRegistered) {

                JSONObject player = new JSONObject();
                player.put("playerID", playerID);
                player.put("pwd", pwd);
                player.put("connection", connection);
                player.put("UI", UI);
                player.put("address", addr.toString().substring(1));
                player.put("port", port);
                jsonArr.add(player);

                try (FileWriter file = new FileWriter("gamedata/playersinfo.json")) {
                    file.write(jsonArr.toJSONString());
                    System.out.println("Successfully Copied JSON Object to File...");
                    System.out.println("\nJSON Object: " + player);
                } catch (IOException e) {
                    System.out.println("Cannot write on file");
                }

                board.addPlayer(playerID, connection, UI, addr.toString().substring(1), port);
                //dico al giocatore che e' stato registrato
                notifyOnRegister(controller, connection, UI, addr.toString().substring(1), port);
            }

        } finally {
            jsonIn.close();
        }

    }

    //TODO fare la reconnect
    public void onReconnect(){

    }

    public void notifyOnRegister(Controller controller, String connection, String UI, String address, int port) throws IOException, ParseException {
        if(connection.equals("Socket")){
            HandlerControllerSocket handleSocket = new HandlerControllerSocket(controller, new Socket(address, port));
            handleSocket.notifyOnRegister(connection, UI);
        } else if(connection.equals("RMI")){

        }
    }

    public void waitingRoomLoaded(String playerName){
        for(int i = 0; i<controller.board.getNPlayers(); i++){
            if(controller.board.getPlayer(i).getName().equals(playerName)){
                controller.board.getPlayer(i).setWaitingRoomStatus(true);
                break;
            }
        }
    }

    public void notifyOnUpdatedPlayer() throws IOException, ParseException {
        int timer = 0;
        if(controller.board.getPlayersOnline()==2 && !controller.isTimerOn()){
            timer = 100;
            controller.setTimer();
        } else if(controller.board.getPlayersOnline()==1 && controller.isTimerOn()){
            timer = -1;
            controller.cancelTimer();
        } else if(controller.isTimerOn()){
            //TODO uso metodo del controller che mi ottiene il tempo rimasto al timer gia' fatto partire prima
            //per ora gli metto un numero a caso
            timer = 100;
        }

        for(int i = 0; i<controller.board.getNPlayers() && controller.board.getPlayer(i).getWaitingRoomStatus() && controller.board.getPlayer(i).getLiveStatus(); i++){
            System.out.println("NPlayer "+i+"\n");
            Player player = controller.board.getPlayers().get(i);
            if(player.getConnection().equals("Socket")){
                System.out.println("Creo Socket indirizzo "+player.getAddress()+" porta "+ player.getPort()+"\n");
                try(Socket socket = new Socket(player.getAddress(), player.getPort())){
                    System.out.println("Creo Handler\n");
                    HandlerControllerSocket handlerControllerSocket = new HandlerControllerSocket(controller, socket);
                    handlerControllerSocket.notifyOnUpdatedPlayer(timer);
                } catch(IOException e){
                    LOGGER.log(Level.WARNING,"IOException: ",e); //da verificare
                }
            } else if(controller.board.getPlayers().get(i).getConnection().equals("RMI")){

            }
        }
    }

    public void setLiveStatusOffline(String playerName){
        for(int i = 0; i<controller.board.getNPlayers(); i++){
            if(controller.board.getPlayer(i).getName().equals(playerName)){
                controller.board.getPlayer(i).setLiveStatus(false);
                break;
            }
        }
    }

    //method to clean the json file every time the game restarts
    private void cleanJson(String jsonPath){
        JSONArray jsonArr = new JSONArray();
        try (FileWriter file = new FileWriter(jsonPath)) {
            file.write(jsonArr.toJSONString());
            file.close();
        } catch (IOException e) {
            System.out.println("Cannot write on file");
        }
    }
}
