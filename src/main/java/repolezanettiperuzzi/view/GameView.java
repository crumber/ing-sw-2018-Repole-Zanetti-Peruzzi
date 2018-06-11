package repolezanettiperuzzi.view;

//  import repolezanettiperuzzi.controller.ControllerStub;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import repolezanettiperuzzi.controller.HandlerSkeletonRMI;
import repolezanettiperuzzi.controller.HandlerStubRMI;
import repolezanettiperuzzi.controller.HandlerStubSocket;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.lang.management.PlatformManagedObject;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
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
        Application.launch(GameViewGUI.class);
    }

    public void onLogin(Stage stage, String username, String pwd, String conn, String UI) throws IOException, InterruptedException {
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
                    Thread serverThread = new Thread(gvSocketServer);
                    serverThread.setDaemon(true);
                    serverThread.start();
                    Thread.sleep(500);

                    this.localPort = gvSocketServer.getLocalServerPort();
                }
                gvSocket.init(username, pwd, conn, UI, this.localPort);


            } else if (connection.equals("RMI")) {
                gvRMI = new GameViewRMI();
            }

            if (UI.equals("CLI")) {
                gvCLI = new GameViewCLI();
            } else if (UI.equals("GUI")) {

            }
        }
    }

    public void showPlayerAlreadyOnlineAlert(){
        this.login = false;
        if(this.UI.equals("GUI")){
            ((LoginFXMLController) fxmlController).showPlayerAlreadyOnlineAlert();
        } else if(this.UI.equals("CLI")){

        }
    }

    public void showWrongPwdAlert(){
        this.login = false;
        if(this.UI.equals("GUI")){
            ((LoginFXMLController) fxmlController).showWrongPwdAlert();
        } else if(this.UI.equals("CLI")){

        }
    }

    public void notifyOnExit(String typeView) throws IOException {
        if (connection.equals("Socket")) {
            gvSocket = new GameViewSocket(this);
            gvSocket.notifyOnExit(username, typeView);
        } else if (connection.equals("RMI")) {

        }
    }

    public void refreshWaitingRoom(int setTimer, String[] players){
        if(this.UI.equals("GUI")){
            ((WaitingRoomFXMLController) fxmlController).refreshPlayers(setTimer, players);
        } else if(this.UI.equals("CLI")){

        }
    }

    public void enterWaitingRoom(){
        if(this.UI.equals("GUI")){
            ((LoginFXMLController) fxmlController).setWaitingRoomScene();
        } else if(this.UI.equals("CLI")){

        }
    }

    public void enterChooseWIndow(){

        if(this.UI.equals("GUI")){
            ((WaitingRoomFXMLController) fxmlController).setChooseWindowScene();
        }else if(this.UI.equals("CLI")){

        }
    }

    public void waitingRoomLoaded() throws IOException {
        if(connection.equals("Socket")){
            gvSocket = new GameViewSocket(this);
            gvSocket.waitingRoomLoaded(username);
        } else if(connection.equals("RMI")){

        }
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

    public void updateView() {
        //aggiorno View
    }
}
