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
            handleMessage();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void handleMessage() throws IOException, ParseException {

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
            case "exit":
                switch(param[0]){
                    case "waitingRoom":
                        controller.setState(new SetConnectionState());
                        ((SetConnectionState)controller.getState()).setLiveStatusOffline(playerID);
                        ((SetConnectionState)controller.getState()).notifyOnUpdatedPlayer();
                        break;
                    case "chooseWindow":
                        //TODO gestire l'uscita durante la scelta delle window
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

    public void askWindow(String message) throws IOException {


        PrintWriter out = new PrintWriter(this.socket.getOutputStream(), true);
        out.println("choseWindow\n");
        out.println(message);//windows
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



}
