package repolezanettiperuzzi.controller;

import org.json.simple.parser.ParseException;
import repolezanettiperuzzi.model.Player;
import repolezanettiperuzzi.model.Window;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

//questo e' il nostro server socket che risiede sul server
public class HandlerControllerSocket implements Runnable{
    private Socket socket;
    private BufferedReader in;
    private InetAddress addr;
    private int port;
    private Controller controller;
    private String playerID;
    private String action;
    private String[] param;

    //struttura messaggo: playerID action parameters
    public HandlerControllerSocket(Controller controller, Socket socket) throws IOException {
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.socket = socket;
        this.addr = socket.getInetAddress();
        this.controller = controller;
    }

    @Override
    public void run() {
        try {
            synchronized (controller) {

                handleMessage();

            }
        } catch (IOException | ParseException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void handleMessage() throws IOException, ParseException, InterruptedException {

        String[] line = in.readLine().split(" ");

        this.playerID = line[0];
        this.action = line[1];
        this.param = new String[line.length-2];

        for(int i = 0; i<param.length; i++){
            param[i] = line[i+2];
        }

        switch(action) {
            case "init":
                controller.setState(new SetConnectionState());
                ((SetConnectionState)controller.getState()).initializePlayer(playerID, param[0], addr, Integer.parseInt(param[3]), param[1], param[2]);
                break;
            case "waitingok": //il client ha avviato la sua view della waiting room
                controller.setState(new SetConnectionState());
                ((SetConnectionState)controller.getState()).waitingRoomLoaded(playerID);
                ((SetConnectionState)controller.getState()).notifyOnUpdatedPlayer();
                break;
            case "choosewindowok":
                FetchState fetch = (FetchState)controller.getState();
                Player playerName = controller.board.getPlayerByName(playerID);
                fetch.sendWindows(playerName);
                break;
            case "chosenWindow":
                ((FetchState)controller.getState()).setChosenWindow(controller.board.getPlayerByName(playerID), param[0].replace("-"," "));
                break;
            case "startWindowOk":
                ((FetchState)controller.getState()).readyToPlay();
                break;
            case "exit":
                controller.notifyExitToClient(playerID);
                switch(param[0]){
                    case "waitingRoom":
                        controller.setState(new SetConnectionState());
                        controller.setLiveStatusOffline(playerID);
                        ((SetConnectionState)controller.getState()).notifyOnUpdatedPlayer();
                        break;
                    case "chooseWindow":
                        //TODO gestire l'uscita durante la scelta delle window
                        controller.setLiveStatusOffline(playerID);
                        break;
                    case "duringGame":
                        //TODO gestire l'uscita durante il gioco
                        break;
                }
                break;


        }
    }

    public void notifyOnRegister(String connection, String UI) throws IOException {
        PrintWriter out = new PrintWriter(this.socket.getOutputStream(), true);
        out.println("registered "+connection+" "+UI);
        out.close();
        this.socket.close();
    }

    public void notifyOnUpdatedPlayer(int timer) throws IOException {
        String playersID = "";

        for(int i = 0; i<controller.board.getNPlayers(); i++){
            if(controller.board.getPlayer(i).getLiveStatus()) {
                Player player = controller.board.getPlayers().get(i);
                playersID = playersID + player.getName() + " ";
            }
        }

        System.out.println("Giocatori stampati: "+playersID);

        PrintWriter out = new PrintWriter(this.socket.getOutputStream(), true);
        System.out.println("Invio messaggio\n");
        out.println("updatedplayers "+timer+" "+playersID);
        out.close();
        this.socket.close();

    }

    public void notifyExitToClient() throws IOException {
        PrintWriter out = new PrintWriter(this.socket.getOutputStream(), true);
        out.println("exit");
        out.close();
        this.socket.close();
    }

    public void onReconnect(String playerID, String connection, String UI) throws IOException {
        switch(controller.board.getPlayerByName(playerID).getLastScene()){
            case "waitingRoom":
                notifyOnRegister(connection, UI);
                break;
            case "chooseWindow":
                //TODO riconnessione in choose window
                break;
            case "duringGame":
                //TODO riconnessione durante la partita
                break;
        }
    }

    public void notifyOnStealAccount() throws IOException {
        PrintWriter out = new PrintWriter(this.socket.getOutputStream(), true);
        out.println("notregistered alreadyonline");
        out.close();
        this.socket.close();
    }

    public void notifyOnWrongPassword() throws IOException {
        PrintWriter out = new PrintWriter(this.socket.getOutputStream(), true);
        out.println("notregistered wrongpwd");
        out.close();
        this.socket.close();
    }

    public void notifyOnChooseWindow() throws IOException {

        PrintWriter out = new PrintWriter(this.socket.getOutputStream(), true);
        out.println("changeView chooseWindow");
        out.close();
        this.socket.close();

    }

    public void askWindow(String message,int currentTime) throws IOException {

        PrintWriter out = new PrintWriter(this.socket.getOutputStream(), true);
        out.println("chooseWindow "+ message +currentTime);
        out.close();
        this.socket.close();

    }

    public void notifyBeginRound() throws IOException {

        PrintWriter out = new PrintWriter(this.socket.getOutputStream(), true);
        out.println("beginRound");
        out.close();
        this.socket.close();

    }

    public String askAction() throws IOException {

        PrintWriter out = new PrintWriter(this.socket.getOutputStream(), true);
        out.println("What action would you like to do?\n");
        out.close();
        //this.in.close();
        //this.socket.close();
        return this.in.readLine();


    }


    public void notifyOnStartGame() throws IOException {

        PrintWriter out = new PrintWriter(this.socket.getOutputStream(), true);
        out.println("startGame");
        out.close();
        this.socket.close();

    }

    public void notifyWinOnChooseWindow() throws IOException {

        PrintWriter out = new PrintWriter(this.socket.getOutputStream(), true);
        out.println("winChooseWindow");
        out.close();
        this.socket.close();

    }
}
