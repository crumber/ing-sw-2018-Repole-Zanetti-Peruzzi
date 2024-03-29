package repolezanettiperuzzi.controller;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import repolezanettiperuzzi.common.DynamicPath;
import repolezanettiperuzzi.model.GameBoard;
import repolezanettiperuzzi.model.Player;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.logging.*;

/**
 * Classe che rappresenta lo stato di connessione
 * @author Giampiero Repole
 */
public class SetConnectionState extends ControllerState{

    private final Logger LOGGER = Logger.getLogger(SetConnectionState.class.getName());
    private GameBoard board;
    private Controller controller;

    /**
     * Inizializza il controller e la game board
     * @param controller Controller
     */
    @Override
    public void doAction(Controller controller) {

        this.controller = controller;
        this.board = controller.board;

    }

    /**
     * Metodo chiamato all'inizio dal client per impostare il player
     * @param playerID Nome player
     * @param pwd Password
     * @param addr Indirizzo
     * @param port Numero di porta
     * @param connection Tipo di connessione
     * @param UI Tipo di interfaccia grafica
     * @return Stringa con parametri
     * @throws IOException Fallimento o interruzione delle operazioni I/O
     * @throws ParseException Errore durante l'analisi
     */
    //metodo chiamato dal giocatore appena si connette al server
    public String initializePlayer(String playerID, String pwd, InetAddress addr, int port, String connection, String UI) throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        FileReader jsonIn = new FileReader(new DynamicPath("gamedata/playersinfo.json").getPathJsonFile());
        JSONArray jsonArr = (JSONArray) parser.parse(jsonIn);
        String playerAction = "login";

        try {

            //playerID, pwd, RMI/Socket, CLI/GUI, addr, port
            for (Object o : jsonArr) {
                JSONObject player = (JSONObject) o;

                String name = (String) player.get("playerID");

                String passwd = (String) player.get("pwd");

                if (name.equals(playerID) && passwd.equals(pwd) && !controller.board.getPlayerByName(playerID).getLiveStatus()) { //mi sto riconnettendo
                    player.put("connection", connection);
                    player.put("UI", UI);
                    player.put("address", addr.toString().substring(1));
                    player.put("port", port);
                    playerAction = "reconnect";
                    break;
                } else if(name.equals(playerID) && controller.board.getPlayerByName(playerID).getLiveStatus()){ //sto cercando di connettermi con il nome di qualcun'altro che e' online
                    playerAction = "stealAccount";
                    break;
                } else if(name.equals(playerID) && !passwd.equals(pwd) && !controller.board.getPlayerByName(playerID).getLiveStatus()){ //sto cercando di connettermi con il nome di qualcun'altro che e' offline ma pwd sbagliata
                    playerAction = "wrongPassword";
                    break;
                }
            }

            if(playerAction.equals("login") && !board.isGameLocked()) {

                JSONObject player = new JSONObject();
                player.put("playerID", playerID);
                player.put("pwd", pwd);
                player.put("connection", connection);
                player.put("UI", UI);
                player.put("address", addr.toString().substring(1));
                player.put("port", port);
                jsonArr.add(player);

                try (FileWriter file = new FileWriter(new DynamicPath("gamedata/playersinfo.json").getPathJsonFile())) {
                    file.write(jsonArr.toJSONString());
                    System.out.println("Successfully Copied JSON Object to File...");
                    System.out.println("\nJSON Object: " + player);
                } catch (IOException e) {
                    System.out.println("Cannot write on file");
                }

                board.addPlayer(playerID, connection, UI, addr.toString().substring(1), port);
                playerAction = "registered";
            } else if(playerAction.equals("reconnect")){
                Player p = board.getPlayerByName(playerID);
                p.setConnection(connection); //problema se mi arriva prima una waitingok
                p.setUI(UI);
                p.setAddress(addr.toString().substring(1));
                p.setPort(port);
                try (FileWriter file = new FileWriter(new DynamicPath("gamedata/playersinfo.json").getPathJsonFile())) {
                    file.write(jsonArr.toJSONString());
                } catch (IOException e) {
                    System.out.println("Cannot write on file");
                }
                controller.board.getPlayerByName(playerID).setLiveStatus(true);
                playerAction = "reconnect";
                System.out.println("reconnect");
            } else if(playerAction.equals("login") && board.isGameLocked()){ //sto tentando di fare il login a gioco iniziato
                playerAction = "gameAlreadyStarted";
            } else if(playerAction.equals("login") && !board.isGameLocked() && board.getPlayersOnline()==4){ //sto tentando di fare il login quando ci sono gia 4 giocatori
                playerAction = "already4Players";
            }

        } finally {
            jsonIn.close();
            return playerAction;
        }

    }

    /**
     * Notifica la riconnessione
     * @param controller Controller
     * @param connection Tipo connessione
     * @param UI Tipo interfaccia grafica
     * @param address Indirizzo
     * @param port Numero della porta
     * @param playerID Nome del player
     * @throws IOException Fallimento o interruzione delle operazioni I/O
     */
    public void notifyOnReconnect(Controller controller, String connection, String UI, String address, int port, String playerID) throws IOException {
        if(connection.equals("Socket")){
            HandlerControllerSocket handleSocket = new HandlerControllerSocket(controller, new Socket(address, port));
            handleSocket.notifyOnReconnect(playerID, connection, UI);
        } else if(connection.equals("RMI")){
            //TODO controllo anche che il giocare aveva scelto una window prima di disconnettersi
        }
    }

    /**
     * Notifica che il nome inserito è gia stato usato
     * @param controller Controller
     * @param connection Tipo connessione
     * @param UI Tipo interfaccia grafica
     * @param address Indirizzo
     * @param port Numero della porta
     * @throws IOException Fallimento o interruzione delle operazioni I/O
     */
    public void notifyOnStealAccount(Controller controller, String connection, String UI, String address, int port) throws IOException {
        if(connection.equals("Socket")){
            HandlerControllerSocket handleSocket = new HandlerControllerSocket(controller, new Socket(address, port));
            handleSocket.notifyOnStealAccount();
        } else if(connection.equals("RMI")){

        }
    }

    /**
     * Notifica che ha inserito la password sbagliata
     * @param controller Controller
     * @param connection Tipo connessione
     * @param UI Tipo interfaccia grafica
     * @param address Indirizzo
     * @param port Numero della porta
     * @throws IOException Fallimento o interruzione delle operazioni I/O
     */
    public void notifyOnWrongPassword(Controller controller, String connection, String UI, String address, int port) throws IOException {
        if(connection.equals("Socket")){
            HandlerControllerSocket handleSocket = new HandlerControllerSocket(controller, new Socket(address, port));
            handleSocket.notifyOnWrongPassword();
        } else if(connection.equals("RMI")){

        }
    }

    /**
     * Notifica che il gioco è gia iniziato
     * @param controller Controller
     * @param connection Tipo connessione
     * @param UI Tipo interfaccia grafica
     * @param address Indirizzo
     * @param port Numero della porta
     * @throws IOException Fallimento o interruzione delle operazioni I/O
     */
    public void notifyOnGameAlreadyStarted(Controller controller, String connection, String UI, String address, int port) throws IOException {
        if(connection.equals("Socket")){
            HandlerControllerSocket handleSocket = new HandlerControllerSocket(controller, new Socket(address, port));
            handleSocket.notifyOnGameAlreadyStarted();
        } else if(connection.equals("RMI")){

        }
    }

    /**
     * Notifica che il numero massimo di giocatori è gia stato raggiunto
     * @param controller Controller
     * @param connection Tipo connessione
     * @param UI Tipo interfaccia grafica
     * @param address Indirizzo
     * @param port Numero della porta
     * @throws IOException Fallimento o interruzione delle operazioni I/O
     */
    public void notifyOnAlready4Players(Controller controller, String connection, String UI, String address, int port) throws IOException {
        if(connection.equals("Socket")){
            HandlerControllerSocket handleSocket = new HandlerControllerSocket(controller, new Socket(address, port));
            handleSocket.notifyOnAlready4Players();
        } else if(connection.equals("RMI")){

        }
    }

    /**
     * Notifica la registrazione
     * @param controller Controller
     * @param connection Tipo connessione
     * @param UI Tipo interfaccia grafica
     * @param address Indirizzo
     * @param port Numero della porta
     * @throws IOException Fallimento o interruzione delle operazioni I/O
     */
    public void notifyOnRegister(Controller controller, String connection, String UI, String address, int port) throws IOException {
        if(connection.equals("Socket")){
            HandlerControllerSocket handleSocket = new HandlerControllerSocket(controller, new Socket(address, port));
            handleSocket.notifyOnRegister(connection, UI);
        } else if(connection.equals("RMI")){
            //System.out.println("Dentro RMI");
            //HandlerControllerRMI handlerRMI = controller.getHandlerRMI();
            //handlerRMI.notifyOnRegister(player.getName());
        }
    }

    /**
     * Caricamento waiting room
     * @param playerName Nome del player
     */
    public void waitingRoomLoaded(String playerName){
        for(int i = 0; i<controller.board.getNPlayers(); i++){
            if(controller.board.getPlayer(i).getName().equals(playerName)){
                controller.board.getPlayer(i).setLastScene("waitingRoom");
                break;
            }
        }
    }

    /**
     * Notifica l'update del player
     * @throws IOException Fallimento o interruzione delle operazioni I/O
     * @throws ParseException Errore durante l'analisi
     * @throws InterruptedException Interruzione thread
     */
    public void notifyOnUpdatedPlayer() throws IOException, ParseException, InterruptedException {
        int timer = 0;
        if(controller.board.getPlayersOnline()==2 && !controller.isTimerOn()){
            controller.setTimer("setConnection");
            timer = controller.getCurrentTime();
        } else if(controller.board.getPlayersOnline()==1 && controller.isTimerOn()){
            timer = -1;
            controller.cancelTimer();

        } else if(controller.isTimerOn()){
            //TODO uso metodo del controller che mi ottiene il tempo rimasto al timer gia' fatto partire prima
            timer = controller.getCurrentTime();
        }

        for(int i = 0; i<controller.board.getNPlayers(); i++){
            if(controller.board.getPlayer(i).checkLastScene("waitingRoom") && controller.board.getPlayer(i).getLiveStatus()) {
                System.out.println("NPlayer " + i);
                Player player = controller.board.getPlayers().get(i);
                if (player.getConnection().equals("Socket")) {
                    System.out.println("Creo Socket indirizzo " + player.getAddress() + " porta " + player.getPort());
                    try (Socket socket = new Socket(player.getAddress(), player.getPort())) {
                        HandlerControllerSocket handlerControllerSocket = new HandlerControllerSocket(controller, socket);
                        handlerControllerSocket.notifyOnUpdatedPlayer(timer);
                    } catch (IOException e) {
                        LOGGER.log(Level.WARNING, "IOException: ", e); //da verificare
                    }
                } else if (controller.board.getPlayers().get(i).getConnection().equals("RMI")) {
                    controller.getHandlerRMI().refreshWaitingRoom(player.getName(), timer);
                }
            }
        }

        if(controller.board.getPlayersOnline()==4 ){

            Thread.sleep(2000);
            controller.cancelTimer();
            this.notifyOnBeginChooseWindow();

        }
        System.out.println("");
    }


    /**
     * Metodo che pulisce il Json ogni volta che rinizia il gioco
     * @param jsonPath Percorso del Json
     */
    //method to clean the json file every time the game restarts
    private void cleanJson(String jsonPath){
        JSONArray jsonArr = new JSONArray();
        try (FileWriter file = new FileWriter(new DynamicPath(jsonPath).getPathJsonFile())) {
            file.write(jsonArr.toJSONString());
            file.close();
        } catch (IOException e) {
            System.out.println("Cannot write on file");
        }
    }

    /**
     *
     * @throws IOException Fallimento o interruzione delle operazioni I/O
     * @throws ParseException Errore durante l'analisi
     */
    //notifia i client ancora connessi che si sta passando alla fase di scelta della window e cancella quelli offline dal file json e dalla board
    public void notifyOnBeginChooseWindow() throws IOException, ParseException {

        controller.setState(new FetchState());

        board.setGameLocked();

        //System.out.println("Start send notify");

        for(int i = 0; i<board.getNPlayers(); i++){

            Player player = board.getPlayer(i);

            if(player.checkLastScene("waitingRoom") && player.getLiveStatus()) {

                if (player.getConnection().equals("Socket")) {

                    try (Socket socket = new Socket(player.getAddress(), player.getPort())) {

                        HandlerControllerSocket handlerControllerSocket = new HandlerControllerSocket(controller, socket);
                        handlerControllerSocket.notifyOnChooseWindow();

                    } catch (IOException e) {
                        LOGGER.log(Level.WARNING, "IOException: ", e); //da verificare
                    }
                } else if (controller.board.getPlayers().get(i).getConnection().equals("RMI")) {
                    controller.getHandlerRMI().notifyOnBeginChooseWindow(player.getName());
                }
            }else {

                JSONParser parser = new JSONParser();
                FileReader jsonIn = new FileReader(new DynamicPath("gamedata/playersinfo.json").getPathJsonFile());
                JSONArray jsonArr = (JSONArray) parser.parse(jsonIn);

                for (Object o : jsonArr) {

                    JSONObject playerJSon = (JSONObject) o;
                    String name = (String) playerJSon.get("playerID");

                    if (name.equals(player.getName())) {

                        jsonArr.remove(o);
                        break;

                    }

                }

                try (FileWriter file = new FileWriter(new DynamicPath("gamedata/playersinfo.json").getPathJsonFile())) {

                    file.write(jsonArr.toJSONString());

                }

                jsonIn.close();


            }
        }

    }
}
