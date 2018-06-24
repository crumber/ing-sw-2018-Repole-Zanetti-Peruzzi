package repolezanettiperuzzi.view;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.json.simple.parser.ParseException;
import repolezanettiperuzzi.common.ClientStubRMI;
import repolezanettiperuzzi.common.ControllerStubRMI;
import repolezanettiperuzzi.view.modelwrapper.WindowClient;

import javax.swing.*;
import java.io.*;
import java.lang.reflect.Array;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.function.Consumer;

//lato client della view che chiama i metodi in remoto del controller
//prendo i dati gia' elaborati da RMI o Socket e li passo a GameViewCLI o GameViewGUI
public class GameView implements ClientStubRMI {

    private String username;
    private String connection;
    private int localPort;
    private String UI;
    private GameViewCLI gvCLI;
    private FXMLController fxmlController;
    private GameViewRMI gvRMI;
    private GameViewSocket gvSocketServer;
    private Thread serverThread;
    private Thread startingRMIThread;
    private GameViewSocket gvSocket;
    private Consumer<String> onReceiveCallback;
    private GameViewRMIServer gvRMIServer;
    private ControllerStubRMI stub;
    private Consumer<Integer> onReceiveLocalPort;
    private boolean RMIActive;
    private boolean login;

    public GameView(){
        this.onReceiveCallback = data -> gvSocket.handleMessage(data);
        this.login = false;
        this.localPort = 0;
        this.RMIActive = false;
    }

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
            //Thread CLIThread = new Thread(gvCLI);
            //CLIThread.setDaemon(true);
            //CLIThread.start();
        } else if(uiChosen.equals("g")){
            GameView gameView = new GameView();
            GameViewGUI.gameView = gameView;
            Application.launch(GameViewGUI.class);
        }
    }

    public void onLogin(String username, String pwd, String conn, String UI) throws IOException, InterruptedException {
        int port = 0;
        if(!login) {
            this.username = username;
            this.connection = conn;
            this.UI = UI;
            this.login = true;



            if (connection.equals("Socket")) {
                //mi serve creae prima l'oggetto in caso venga chiamata la onReceiveCallback su un oggetto che non esiste
                gvSocket = new GameViewSocket(this);
                if(this.localPort==0) {
                    gvSocketServer = new GameViewSocket(onReceiveCallback);
                    this.serverThread = new Thread(gvSocketServer);
                    //serverThread.setDaemon(true);
                    serverThread.start();
                    Thread.sleep(500);

                    this.localPort = gvSocketServer.getLocalServerPort();
                }
                gvSocket.init(username, pwd, conn, UI, this.localPort);


            } else if (connection.equals("RMI")) {
                GameView gameView = this;
                if(this.UI.equals("GUI")) {
                    //creo in un thread separato per non bloccare la GUI
                    this.startingRMIThread =new Thread(new Runnable() {
                        @Override
                        public void run() {
                            ((LoginFXMLController) fxmlController).showProgressIndicator();
                            initRMI(gameView, pwd, conn);
                        }
                    });
                    startingRMIThread.start();
                } else if(this.UI.equals("CLI")){
                    System.out.println("Connecting to RMI Server... (this may take up to 20 seconds)");
                    initRMI(gameView, pwd, conn);
                }


            }
        }
    }

    /**
     * Inizializza la connessione RMI e invia il messaggio di richiesta di login.
     * Deve essere un metodo esterno al metodo onLogin perche' cosi' puo essere chiamato da dentro o fuori un Thread in caso
     * sia fatto partire da GUI o da CLI.
     * @param gameView riferimento alla classe GameView che sta chiamando il metodo
     * @param pwd Password inserita dall'utente
     * @param conn Connessione scelta dall'utente (RMI/Socket)
     */
    private void initRMI(GameView gameView, String pwd, String conn){
        if(gvRMIServer==null){
            gvRMIServer = new GameViewRMIServer(gameView);
        }
        String message = "";
        try {
            if(stub==null) {
                stub = gvRMIServer.bind();
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
            enterWaitingRoom();
        } else if(message.contains("reconnect")) {
            String lastScene = message.split(" ")[1];
            switch(lastScene){
                case "waitingRoom":
                    enterWaitingRoom();
                    break;
                case "chooseWindow":
                    break;
            }
        } else if(message.equals("stealAccount")){
            showPlayerAlreadyOnlineAlert();
        } else if(message.equals("wrongPassword")){
            showWrongPwdAlert();
        }
    }

    public void showPlayerAlreadyOnlineAlert(){
        this.login = false;
        if(this.UI.equals("GUI")){
            ((LoginFXMLController) fxmlController).showPlayerAlreadyOnlineAlert();
        } else if(this.UI.equals("CLI")){
            gvCLI.loginScene("The username you chose is already used by a Player online");
        }
    }

    public void showWrongPwdAlert(){
        this.login = false;
        if(this.UI.equals("GUI")){
            ((LoginFXMLController) fxmlController).showWrongPwdAlert();
        } else if(this.UI.equals("CLI")){
            gvCLI.loginScene("Wrong password inserted, please try again");
        }
    }

    /**
     * Viene chiamato dalle rispettive CLI o GUI del client e notifica il server della disconnesione del client.
     * Dopo una conferma del server allora il client procede a chiudere in modo appropriato le connesioni RMI/Socket.
     * In caso il client venga chiuso durante la connesione al server RMI, il metodo interrompe il thread che si
     * sta occupando di questa operazione e successivamente chiude il programma.
     * @param typeView Nome dell'ultima view che è arrivato a visualizzare il client
     * @throws IOException
     */
    public void notifyOnExit(String typeView) throws IOException {
        if(this.login) {   //se non ho fatto il login significa che ho chiuso la GUI per chiudere il gioco
            if (connection.equals("Socket")) {
                gvSocket = new GameViewSocket(this);
                gvSocket.notifyOnExit(username, typeView);
                gvSocketServer.shutdownServer();
            } else if (connection.equals("RMI")) {
                if(RMIActive) {
                    boolean response = false;
                    try {
                        response = stub.notifyOnExit(username, typeView);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (response) {
                        gvRMIServer.unexportRMI();
                    }
                } else {
                    if(UI.equals("GUI")) {
                        startingRMIThread.interrupt();
                        System.exit(0);
                    }
                }
            }
        }
    }

    public void shutdownClient(){
        if(this.login) {
            System.out.println("logout");
            System.exit(0);
        }
    }

    public void refreshWaitingRoom(int setTimer, String[] players){
        if(this.UI.equals("GUI")){
            ((WaitingRoomFXMLController) fxmlController).refreshPlayers(setTimer, players);
        } else if(this.UI.equals("CLI")){
            gvCLI.refreshWaitingRoom(setTimer, players);
        }
    }

    public void enterWaitingRoom(){
        if(this.UI.equals("GUI")){
            ((LoginFXMLController) fxmlController).setWaitingRoomScene();
        } else if(this.UI.equals("CLI")){
            gvCLI.setWaitingRoomScene();
        }
    }

    public void enterChooseWindow(){

        if(this.UI.equals("GUI")){
            ((WaitingRoomFXMLController) fxmlController).setChooseWindowScene();
        }else if(this.UI.equals("CLI")){
            gvCLI.setChooseWindowScene();
        }
    }

    public void waitingRoomLoaded() throws IOException {
        if(connection.equals("Socket")){
            gvSocket = new GameViewSocket(this);
            gvSocket.waitingRoomLoaded(username);
        } else if(connection.equals("RMI")){
            stub.waitingRoomLoaded(username);
        }
    }

    public void chooseWindowSceneLoaded() throws IOException {
        if(connection.equals("Socket")){
            gvSocket = new GameViewSocket(this);
            gvSocket.chooseWindowSceneLoaded(username);
        } else if(connection.equals("RMI")){

        }
    }

    public void viewWindows(ArrayList<WindowClient> windows, int currentTime){
        if(this.UI.equals("GUI")){
            ((ChooseWindowFXMLController) fxmlController).viewWindows(windows,currentTime);
        }else if(this.UI.equals("CLI")){
            gvCLI.viewWindows(windows);
        }
    }

    public void sendChosenWindow(String windowName) throws IOException {
        if(connection.equals("Socket")){
            gvSocket = new GameViewSocket(this);
            gvSocket.sendChosenWindow(username, windowName);
        } else if(connection.equals("RMI")){

        }
    }

    public static void printSagrada(){
        System.out.print("\033[H\033[2J");
        System.out.flush();
        System.out.println(" ____                            _       ");
        System.out.println("/ ___|  __ _  __ _ _ __ __ _  __| | __ _ ");
        System.out.println("\\___ \\ / _` |/ _` | '__/ _` |/ _` |/ _` |");
        System.out.println(" ___) | (_| | (_| | | | (_| | (_| | (_| |");
        System.out.println("|____/ \\__,_|\\__, |_|  \\__,_|\\__,_|\\__,_|     Published by Polimi Inc.");
        System.out.println("             |___/                       \n\n\n");
    }

    public String getConnection(){
        return this.connection;
    }

    public GameViewSocket getGvSocket(){
        return this.gvSocket;
    }

    public void setFXMLController(FXMLController fxmlController){
        this.fxmlController = fxmlController;
    }

    public void setGVCLI(GameViewCLI gvCLI){
        this.UI = "CLI";
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

    public void updateView() {
        //aggiorno View
    }
}
