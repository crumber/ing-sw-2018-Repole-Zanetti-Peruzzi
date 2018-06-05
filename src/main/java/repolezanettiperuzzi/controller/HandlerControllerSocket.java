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
    public HandlerControllerSocket(Controller controller, Socket socket) throws IOException, ParseException {
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
                initializePlayer();
                break;
            case "waitingok": //il client ha avviato la sua view della waiting room
                controller.setState(new SetConnectionState());
                ((SetConnectionState)controller.getState()).waitingRoomLoaded(playerID);
                ((SetConnectionState)controller.getState()).notifyOnNewPlayer();
                break;
            case "setup":
                setupPlayer();
                break;


        }
    }

    private void initializePlayer() throws IOException, ParseException {
        String pwd = param[0];
        String connection = param[1];
        String UI = param[2];
        this.port = Integer.parseInt(param[3]);

        PrintWriter out = new PrintWriter(this.socket.getOutputStream(), true);
        out.println("registered "+connection+" "+UI);
        out.close();
        this.in.close();
        this.socket.close();
        ((SetConnectionState)controller.getState()).initializePlayer(playerID, pwd, addr, port, connection, UI);
    }

    public void notifyOnRegister(String connection, String UI) throws IOException {
        PrintWriter out = new PrintWriter(this.socket.getOutputStream(), true);
        out.println("registered "+connection+" "+UI);
        out.close();
    }

    private void setupPlayer() throws IOException, ParseException {
        String connection = param[0];
        String UI = param[1];
        //controller.setupPlayer(playerID, connection, UI, this.addr, this.port);
    }

    public void notifyOnNewPlayer() throws IOException {
        String playersID = "";
        System.out.println( "Ciclo sui giocatori\n" );
        int i;
        for(i = 0; i<controller.board.getNPlayers(); i++){
            System.out.println("Stampo giocatore "+i+"\n");
            Player player = controller.board.getPlayers().get(i);
            playersID = playersID+player.getName()+" ";
        }
        int timer = 0;
        if(i==2){
            timer = 100;
        }

        PrintWriter out = new PrintWriter(this.socket.getOutputStream(), true);
        System.out.println("Invio messaggio\n");
        out.println("newplayers "+timer+" "+playersID);
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
