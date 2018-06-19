package repolezanettiperuzzi.view;

//  import repolezanettiperuzzi.controller.ControllerStub;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import repolezanettiperuzzi.view.modelwrapper.WindowClient;

import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.function.Consumer;

//lato client della view che chiama i metodi in remoto del controller
//prendo i dati gia' elaborati da RMI o Socket e li passo a GameViewCLI o GameViewGUI
public class GameView {

    private String username;
    private String connection;
    private int localPort;
    private String UI;
    private GameViewCLI gvCLI;
    private FXMLController fxmlController;
    private GameViewRMI gvRMI;
    private GameViewSocket gvSocketServer;
    private Thread serverThread;
    private GameViewSocket gvSocket;
    private Consumer<String> onReceiveCallback;
    private Consumer<Integer> onReceiveLocalPort;
    private boolean login;

    public GameView(){
        this.onReceiveCallback = data -> gvSocket.handleMessage(data);
        this.login = false;
        this.localPort = 0;
    }

    public static void main(String args[]) {
        System.out.print("\033[H\033[2J");
        System.out.flush();
        printSagrada();
        System.out.println("Che grafica vuoi usare?");
        System.out.print("Premi 'c' per CLI e 'g' per GUI: ");
        Scanner scanner = new Scanner(System.in);
        String uiChosen = scanner.nextLine();

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
                gvRMI = new GameViewRMI();
            }
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

    public void notifyOnExit(String typeView) throws IOException {
        if(this.login) {   //se non ho fatto il login significa che ho chiuso la GUI per chiudere il gioco
            if (connection.equals("Socket")) {
                gvSocket = new GameViewSocket(this);
                gvSocket.notifyOnExit(username, typeView);
                System.out.println("esco");
                System.exit(1);
            } else if (connection.equals("RMI")) {

            }
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

    public void updateView() {
        //aggiorno View
    }
}
