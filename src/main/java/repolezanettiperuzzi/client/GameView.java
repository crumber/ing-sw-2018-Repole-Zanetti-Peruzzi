package repolezanettiperuzzi.client;

import javafx.application.Application;
import org.json.simple.parser.ParseException;
import repolezanettiperuzzi.common.ClientStubRMI;
import repolezanettiperuzzi.common.ControllerStubRMI;
import repolezanettiperuzzi.infrastructure.client.rmi.ClientRmiView;
import repolezanettiperuzzi.infrastructure.client.rmi.GameViewRMIServer;
import repolezanettiperuzzi.infrastructure.client.socket.GameViewSocket;
import repolezanettiperuzzi.infrastructure.client.socket.ClientSocketView;
import repolezanettiperuzzi.presentation.ErrorFactory;
import repolezanettiperuzzi.presentation.cli.GameViewCLI;
import repolezanettiperuzzi.presentation.gui.ChooseWindowFXMLController;
import repolezanettiperuzzi.presentation.gui.FXMLController;
import repolezanettiperuzzi.presentation.gui.GameFXMLController;
import repolezanettiperuzzi.presentation.gui.GameViewGUI;
import repolezanettiperuzzi.presentation.gui.LoginFXMLController;
import repolezanettiperuzzi.presentation.gui.WaitingRoomFXMLController;
import repolezanettiperuzzi.shared.dto.GameBoardClient;
import repolezanettiperuzzi.shared.dto.WindowClient;

import java.io.IOException;
import java.rmi.ConnectException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.UnmarshalException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.function.Consumer;

/**
 * Classe che modella la Game vie
 * @author Alessandro Peruzzi
 * @author Andrea Zanetti
 * @author Giampiero Repole
 */
//lato client della view che chiama i metodi in remoto del controller
//prendo i dati gia' elaborati da RMI o Socket e li passo a GameViewCLI o GameViewGUI
public class GameView implements ClientGuiActions, ClientStubRMI, ClientSocketView, ClientRmiView {

    private static final String CONNECTION_SOCKET = "Socket";
    private static final String CONNECTION_RMI = "RMI";
    private static final String UI_GUI = "GUI";
    private static final String UI_CLI = "CLI";

    private String username;
    private String connection;
    private int localPort;
    private String UI;
    private GameViewCLI gvCLI;
    private FXMLController fxmlController;
    private GameViewSocket gvSocketServer;
    private Thread startingRMIThread;
    private GameViewSocket gvSocket;
    private Consumer<String> onReceiveCallback;
    private GameViewRMIServer gvRMIServer;
    private ControllerStubRMI stub;
    private boolean RMIActive;
    private boolean login;
    private boolean rejectedLogin; //useful to know if login has been rejected, because server socket and RMI have been started and have to be shut down during notifyOnExit()
    private boolean win;
    private boolean alreadyExit;
    private String serverIp;

    /**
     * Costruttore
     */
    public GameView(){
        this.onReceiveCallback = data -> gvSocket.handleMessage(data);
        this.login = false;
        this.rejectedLogin = false;
        this.localPort = 0;
        this.RMIActive = false;
        this.win = false;
        this.alreadyExit = false;
    }

    /**
     * Main
     * @param args Parametri del main
     */
    public static void main(String args[]) {
        System.out.print("\033[H\033[2J");
        System.out.flush();
        printSagrada();

        Scanner scanner = new Scanner(System.in);
        String uiChosen;
        System.out.println("Che grafica vuoi usare?");
        do {
            System.out.print("Premi 'c' per CLI e 'g' per GUI: ");
            uiChosen = scanner.nextLine();
        }while(!uiChosen.equals("c") && !uiChosen.equals("g"));

        if(uiChosen.equals("c")){
            GameView gameView = new GameView();

            GameViewCLI gvCLI = new GameViewCLI(gameView);
            gameView.setGVCLI(gvCLI);
            gvCLI.loginScene("Schermata di login");
        } else if(uiChosen.equals("g")){
            GameView gameView = new GameView();
            GameViewGUI.gameView = gameView;
            Application.launch(GameViewGUI.class);
        }
    }

    /**
     * Svolge il login
     * @param username Nome player
     * @param pwd Password
     * @param conn Connessione
     * @throws IOException Fallimento o interruzione delle operazioni I/O
     * @throws InterruptedException Interruzione thread
     */
    public void onLogin(String username, String pwd, String conn, String UI, String serverIp) throws IOException, InterruptedException {
        if(!login) {
            this.username = username;
            this.connection = conn;
            this.UI = UI;
            this.serverIp = serverIp;
            this.login = true;

            if (isSocketConnection()) {
                //mi serve creae prima l'oggetto in caso venga chiamata la onReceiveCallback su un oggetto che non esiste
                gvSocket = openSocketClientConnection();
                if(this.localPort==0) {
                    gvSocketServer = new GameViewSocket(onReceiveCallback);
                    Thread serverThread = new Thread(gvSocketServer);
                    serverThread.start();
                    Thread.sleep(500);

                    this.localPort = gvSocketServer.getLocalServerPort();
                }
                gvSocket.init(username, pwd, conn, UI, this.localPort);


            } else if (isRmiConnection()) {
                if(isGui()) {
                    //creo in un thread separato per non bloccare la GUI
                    this.startingRMIThread =new Thread(new Runnable() {
                        @Override
                        public void run() {
                            ((LoginFXMLController) fxmlController).showProgressIndicator();
                            initRMI(pwd, conn, serverIp);
                        }
                    });
                    startingRMIThread.start();
                } else if(isCli()){
                    System.out.println("Connecting to RMI Server... (this may take up to 20 seconds)");
                    initRMI(pwd, conn, serverIp);
                }


            }
        }
    }

    private GameViewSocket openSocketClientConnection() throws IOException {
        return new GameViewSocket(this, serverIp);
    }

    private boolean isSocketConnection() {
        return connection.equals(CONNECTION_SOCKET);
    }

    private boolean isRmiConnection() {
        return connection.equals(CONNECTION_RMI);
    }

    private boolean isGui() {
        return UI.equals(UI_GUI);
    }

    private boolean isCli() {
        return UI.equals(UI_CLI);
    }

    private void removeLoginProgressIndicator() {
        ((LoginFXMLController) fxmlController).removeProgressIndicator();
    }

    private void removeLoginProgressIndicatorIfGui() {
        if(isGui()) {
            removeLoginProgressIndicator();
        }
    }

    private void removeLoginProgressIndicatorIfRmi() {
        if(isRmiConnection()) {
            removeLoginProgressIndicator();
        }
    }

    private interface RemoteAction {
        void execute() throws RemoteException;
    }

    private void runRmiAsync(RemoteAction action) {
        runRmiAsync(action, true);
    }

    private void runRmiAsync(RemoteAction action, boolean printFailures) {
        new Thread(() -> {
            try {
                action.execute();
            } catch (RemoteException e) {
                if (printFailures) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * Inizializza la connessione RMI e invia il messaggio di richiesta di login.
     * Deve essere un metodo esterno al metodo onLogin perche' cosi' puo essere chiamato da dentro o fuori un Thread in caso
     * sia fatto partire da GUI o da CLI.
     * @param pwd Password inserita dall'utente
     * @param conn Connessione scelta dall'utente (RMI/Socket)
     */
    private void initRMI(String pwd, String conn, String serverIp){
        if(gvRMIServer==null){
            gvRMIServer = new GameViewRMIServer(this);
        }
        String message = "";
        try {
            if(stub==null) {
                stub = gvRMIServer.bind(serverIp);
            }
            message = stub.init(gvRMIServer.getClientStub(), username, pwd, conn, UI);
        } catch (NotBoundException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(message.equals("registered")){
            rejectedLogin = false;
            enterWaitingRoom();
        } else if(message.contains("reconnect")) {
            rejectedLogin = false;
            String lastScene = message.split(" ")[1];
            switch(lastScene){
                case "waitingRoom":
                    enterWaitingRoom();
                    break;
                case "chooseWindowRoom":
                    enterChooseWindow();
                    break;
                case "game":
                    enterGame();
                    break;
            }
        } else if(message.equals("stealAccount")){
            removeLoginProgressIndicatorIfGui();
            showPlayerAlreadyOnlineAlert();
        } else if(message.equals("wrongPassword")){
            removeLoginProgressIndicatorIfGui();
            showWrongPwdAlert();
        } else if(message.equals("gameAlreadyStarted")){
            removeLoginProgressIndicatorIfGui();
            showGameAlreadyStarted();
        } else if(message.equals("already4Players")){
            removeLoginProgressIndicatorIfGui();
            showAlready4Players();
        }
    }

    /**
     * Alert che nome gia usato da un player online
     */
    public void showPlayerAlreadyOnlineAlert(){
        this.login = false;
        this.rejectedLogin = true;
        if(isGui()){
            ((LoginFXMLController) fxmlController).showPlayerAlreadyOnlineAlert();
        } else if(isCli()){
            gvCLI.loginScene("The username you chose is already used by a Player online");
        }
    }

    /**
     * Alert di password errata
     */
    public void showWrongPwdAlert(){
        this.login = false;
        this.rejectedLogin = true;
        if(isGui()){
            ((LoginFXMLController) fxmlController).showWrongPwdAlert();
        } else if(isCli()){
            gvCLI.loginScene("Wrong password inserted, please try again");
        }
    }

    /**
     * Mostra che il gioco è gia iniziato
     */
    public void showGameAlreadyStarted(){
        this.login = false;
        this.rejectedLogin = true;
        if(isGui()){
            ((LoginFXMLController) fxmlController).showGameAlreadyStartedAlert();
        } else if(isCli()){
            gvCLI.loginScene("A game already started, we're sorry. Try again after this game");
        }
    }

    /**
     * Mostra che il numero massimo di player è stato raggiunto
     */
    public void showAlready4Players(){
        this.login = false;
        this.rejectedLogin = true;
        if(isGui()){
            ((LoginFXMLController) fxmlController).showAlready4PlayersAlert();
        } else if(isCli()){
            gvCLI.loginScene("There are already 4 players online. Try later");
        }
    }

    /**
     * Alert che dice che hai vinto perchè non ci sono altri giocatori online
     */
    public void showWinOnChooseWindowAlert(){
        this.login = false;
        this.rejectedLogin = true;
        this.win = true;
        if(isGui()){
            ((ChooseWindowFXMLController) fxmlController).showWinOnChooseWindowAlert();
        } else if(isCli()){
            System.out.println("\n/// You won! You are the only player left online! ///");
            //gvSocketServer.shutdownServer();
        }
    }

    /**
     * Viene chiamato dalle rispettive CLI o GUI del client e notifica il server della disconnesione del client.
     * Dopo una conferma del server allora il client procede a chiudere in modo appropriato le connesioni RMI/Socket.
     * In caso il client venga chiuso durante la connesione al server RMI, il metodo interrompe il thread che si
     * sta occupando di questa operazione e successivamente chiude il programma.
     * @param typeView Nome dell'ultima view che è arrivato a visualizzare il client
     * @throws IOException interruzione thread o interruzione json
     */
    public synchronized void notifyOnExit(String typeView) throws IOException {
        if((this.login || rejectedLogin) && !alreadyExit) {   //se non ho fatto il login significa che ho chiuso la GUI per chiudere il gioco
            if (isSocketConnection()) {
                try {
                    if (win && isGui()) {
                        gvSocket = openSocketClientConnection();
                        gvSocket.notifyOnExit(username, typeView);
                        alreadyExit = true;
                        System.exit(0);
                    } else {
                        //System.out.println("ci sono");
                        gvSocket = openSocketClientConnection();
                        gvSocket.notifyOnExit(username, typeView);
                        alreadyExit = true;
                    }
                } catch(IOException e){
                    //System.out.println("Server disconnesso");
                }
            } else if (isRmiConnection()) {
                if(RMIActive) {
                    if(!rejectedLogin) {
                        boolean response = false;
                        try {
                            response = stub.notifyOnExit(username, typeView);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            if (!((e instanceof ConnectException) || (e instanceof UnmarshalException))) {
                                e.printStackTrace();
                            } else {
                                //System.out.println("Server disconnesso");
                            }
                        }
                    }
                    gvRMIServer.unexportRMI();
                    RMIActive = false;
                    alreadyExit = true;
                } else {
                    if(isGui()) {
                        startingRMIThread.interrupt();
                        alreadyExit = true;
                        System.exit(0);
                    }
                }
            }
        }
    }

    /**
     * Shutdown del client
     */
    public void shutdownClient(){
        if(this.login) {
            //System.out.println("logout");
            System.exit(0);
        }
    }

    /**
     * Aggiornamento waiting room
     * @param setTimer Timer
     * @param players Array di stringhe che rappresentano i player
     */
    public void refreshWaitingRoom(int setTimer, String[] players){
        if(isGui()){
            ((WaitingRoomFXMLController) fxmlController).refreshPlayers(setTimer, players);
        } else if(isCli()){
            gvCLI.refreshWaitingRoom(setTimer, players);
        }
    }

    /**
     * Entra nella waiting room
     */
    public void enterWaitingRoom(){
        rejectedLogin = false;
        if(isGui()){
            ((LoginFXMLController) fxmlController).setWaitingRoomScene();
        } else if(isCli()){
            gvCLI.setWaitingRoomScene();
        }
    }

    /**
     * Entra nella scelta delle window
     */
    public void enterChooseWindow(){
        if(isGui()){
            if(fxmlController instanceof WaitingRoomFXMLController) {
                ((WaitingRoomFXMLController) fxmlController).setChooseWindowScene();
            } else if(fxmlController instanceof LoginFXMLController){
                removeLoginProgressIndicatorIfRmi();
                ((LoginFXMLController) fxmlController).setChooseWindowScene();
            }
        }else if(isCli()){
            gvCLI.setChooseWindowScene();
        }
    }

    /**
     * Entra nel gioco
     */
    public void enterGame(){
        if(isGui()){
            if(fxmlController instanceof ChooseWindowFXMLController) {
                ((ChooseWindowFXMLController) fxmlController).setGameScene();
            } else if(fxmlController instanceof LoginFXMLController){
                removeLoginProgressIndicatorIfRmi();
                ((LoginFXMLController) fxmlController).setGameScene();
            }
        }else if(isCli()){
            gvCLI.setGameScene();
        }
    }

    /**
     * Caricamento waiting room
     * @throws IOException Fallimento o interruzione delle operazioni I/O
     */
    public void waitingRoomLoaded() throws IOException {
        if(isSocketConnection()){
            gvSocket = openSocketClientConnection();
            gvSocket.waitingRoomLoaded(username);
        } else if(isRmiConnection()){
            stub.waitingRoomLoaded(username);
        }
    }

    /**
     * Caricamento scelta delle window
     * @throws IOException Fallimento o interruzione delle operazioni I/O
     */
    public void chooseWindowSceneLoaded() throws IOException {
        if(isSocketConnection()){
            gvSocket = openSocketClientConnection();
            gvSocket.chooseWindowSceneLoaded(username);
        } else if(isRmiConnection()){
            stub.chooseWindowSceneLoaded(username);
        }
    }

    /**
     * Caricamento gioco
     * @throws IOException Fallimento o interruzione delle operazioni I/O
     */
    public void gameLoaded() throws IOException {
        if(isSocketConnection()){
            gvSocket = openSocketClientConnection();
            gvSocket.gameSceneLoaded(username);
        } else if(isRmiConnection()){
            runRmiAsync(() -> stub.readyToPlay(username));
        }
    }

    /**
     * Mostra l'errore
     * @param error Stringa codice errore
     */
    public void viewError(String error){
        if(isGui()){
            ((GameFXMLController) fxmlController).viewError(ErrorFactory.getErrorMessage(error));
        }else if(isCli()){
            gvCLI.viewError(ErrorFactory.getErrorMessage(error));
        }
    }

    /**
     * Invia l'inserimento del dado
     * @param draftPos Indica la posizione nel draft
     * @param xWindowPos Indica la coordinata x
     * @param yWindowPos Indica la coordinata y
     * @throws IOException  Fallimento o interruzione delle operazioni I/O
     */
    public void sendInsertDie(int draftPos, int xWindowPos, int yWindowPos) throws IOException {
        if(isSocketConnection()){
            gvSocket = openSocketClientConnection();
            gvSocket.sendInsertDie(username, draftPos, xWindowPos, yWindowPos);
        } else if(isRmiConnection()){
            runRmiAsync(() -> stub.insertDie(username, draftPos, xWindowPos, yWindowPos));
        }
    }

    /**
     * Invia la scelta della carta
     * @param numCard Numero della carta
     * @throws IOException Fallimento o interruzione delle operazioni I/O
     */
    public void sendChooseCard(int numCard) throws IOException {

        if(isSocketConnection()){

            gvSocket = openSocketClientConnection();
            gvSocket.sendChooseCard(username, numCard);

        }else if(isRmiConnection()){
            runRmiAsync(() -> stub.chooseCard(username, numCard));
        }
    }

    /**
     * Riceve i parametri della carta
     * @param parameters Stringa che indica i parametri
     */
    public synchronized void receiveCardParameters(String parameters){

        //System.out.println(parameters);
        String[] separatedParameters = parameters.split("-");

        if(isGui()){

            ((GameFXMLController) fxmlController).showCardParameters(separatedParameters);

        }else if(isCli()){
            gvCLI.cardQuestion(new ArrayList<>(Arrays.asList(separatedParameters)), username);
        }
    }

    /**
     * Invia risposte delle tool card
     * @param nCard Numero carta
     * @param response Risposta
     * @throws IOException Fallimento o interruzione delle operazioni I/O
     */
    public void sendResponseToolCard(int nCard, String response) throws IOException {
        if(isSocketConnection()){
            gvSocket = openSocketClientConnection();
            gvSocket.sendResponseToolCard(username, nCard, response);
        }else if(isRmiConnection()){
            runRmiAsync(() -> stub.responseToolCard(username, nCard, response));
        }
    }

    /**
     * Avvisa che non è il tuo turno
     */
    public void notYourTurn(){
        if(isGui()){
            ((GameFXMLController) fxmlController).notYourTurn();
        }else if(isCli()){

        }
    }

    /**
     * Notifica il turno
     * @param actualPlayer Player attuale
     * @param currentTime Timer corrente
     */
    public synchronized void notifyTurn(String actualPlayer, int currentTime){
        if(isGui()){
            ((GameFXMLController) fxmlController).notifyTurn(actualPlayer, currentTime);
        }else if(isCli()){
            gvCLI.notifyTurn(actualPlayer, currentTime);
        }
    }

    /**
     * Mostra le 4 window da cui sceglierne una
     * @param windows Lista di 4 window da cui scegliere
     * @param currentTime Timer
     */
    public synchronized void viewWindows(ArrayList<WindowClient> windows, int currentTime){
        if(isGui()){
            ((ChooseWindowFXMLController) fxmlController).viewWindows(windows,currentTime);
        }else if(isCli()){
            gvCLI.viewWindows(windows, currentTime);
        }
    }

    /**
     * Mostra la singola window scelta
     * @param window window scelta
     * @param currentTime Timer
     */
    public void viewOneWindow(WindowClient window, int currentTime){
        if(isGui()){
            ((ChooseWindowFXMLController) fxmlController).viewOneWindow(window,currentTime);
        }else if(isCli()){
            gvCLI.viewOneWindow(window, currentTime);
        }
    }

    /**
     * Invia la window scelta
     * @param windowName Nome della window
     * @throws IOException Fallimento o interruzione delle operazioni I/O
     */
    public void sendChosenWindow(String windowName) throws IOException {
        if(isSocketConnection()){
            gvSocket = openSocketClientConnection();
            gvSocket.sendChosenWindow(username, windowName);
        } else if(isRmiConnection()){
            stub.sendChosenWindow(username, windowName);
        }
    }

    /**
     * Invia la fine del turno
     * @throws IOException Fallimento o interruzione delle operazioni I/O
     */
    public void sendEndTurn() throws IOException {
        if(isSocketConnection()){
            gvSocket = openSocketClientConnection();
            gvSocket.sendEndTurn(username);
        }else if(isRmiConnection()){
            runRmiAsync(() -> stub.endTurn(username), false);
        }
    }

    /**
     * Stampa SAGRADA
     */


    public static void printSagrada(){

        String ANSI_RESET = "\u001B[0m";
        String ANSI_BLACK = "\u001B[30m";
        String ANSI_RED = "\u001B[31m";
        String ANSI_GREEN = "\u001B[32m";
        String ANSI_YELLOW = "\u001B[33m";
        String ANSI_BLUE = "\u001B[34m";
        String ANSI_PURPLE = "\u001B[35m";
        String ANSI_CYAN = "\u001B[36m";
        String ANSI_WHITE = "\u001B[37m";

        System.out.print("\033[H\033[2J");
        System.out.flush();
        System.out.println(ANSI_BLUE +" ____                            _       ");
        System.out.println(ANSI_CYAN +"/ ___|  __ _  __ _ _ __ __ _  __| | __ _ ");
        System.out.println(ANSI_GREEN +"\\___ \\ / _` |/ _` | '__/ _` |/ _` |/ _` |");
        System.out.println(ANSI_PURPLE +" ___) | (_| | (_| | | | (_| | (_| | (_| |");
        System.out.println(ANSI_RED +"|____/ \\__,_|\\__, |_|  \\__,_|\\__,_|\\__,_| "+ANSI_RESET +"    Published by Polimi Inc.");
        System.out.println(ANSI_YELLOW +"             |___/                       \n\n\n"+ANSI_RESET);
    }

    /**
     *
     * @return User name
     */
    public String getUsername(){
        return this.username;
    }

    /**
     * Imposta il controller FXML
     * @param fxmlController Controller FXML
     */
    public void setFXMLController(FXMLController fxmlController){
        this.fxmlController = fxmlController;
    }

    /**
     * Imposta la Game view cli
     * @param gvCLI Game view cli
     */
    public void setGVCLI(GameViewCLI gvCLI){
        this.UI = UI_CLI;
        this.gvCLI = gvCLI;
    }

    /**
     * Viene chiamato dalla procedura di connessione con il server RMI una volta che la connessione e' stabilita.
     * Utile per capire durante il login se l'utente chiude il client se la connessione RMI e' stata gia' stabilita
     * e in caso affermativo disattivare l'esportazione dello stub del client. In caso la connessione sia ancora in
     * corso e sia in uso la GUI allora interrompe il thread che si occupa di stabilire la connessione.
     */
    public void setRMIActive(){
        this.RMIActive = true;
    }

    /**
     * Aggiorna la view
     * @param board Game board
     */
    public synchronized void updateView(GameBoardClient board) {
        if(isGui()){
            ((GameFXMLController) fxmlController).updateView(board);
        } else if(isCli()){
            gvCLI.updateView(board, username);
        }
    }

    public synchronized void receiveRanking(String score) {

     String[] player = score.split("_");
     String result = "";
     String[] resultCLI = new String[player.length/2];
     int j = 0;
     for(int i = 0; i<resultCLI.length; i++){
         resultCLI[i] = player[j];
         j++;
         resultCLI[i] += " "+player[j];
         j++;
     }

     //System.out.println(Arrays.toString(player));

     for(int i = 0; i<player.length; i+=2 ){


         result+=player[i];
         result+=" ";
         result+=player[i+1];
         result+="\n";

         //System.out.println(result);

     }

     if(isGui()){
         ((GameFXMLController) fxmlController).showEndGame(result);
     }else if(isCli()){
        gvCLI.showRanking(resultCLI);
        //gvRMIServer.unexportRMI();
        //System.exit(0);
     }

    }

    public void showWinBeforeEndGameAlert() {

        this.login = true;
        this.win = true;
        System.out.println("win");
        if(isGui()){
            ((GameFXMLController) fxmlController).showWinBeforeEndGameAlert();
        } else if(isCli()){
            System.out.println("\n/// You won! You are the only player left online! ///");
            //gvSocketServer.shutdownServer();
        }
    }
}
