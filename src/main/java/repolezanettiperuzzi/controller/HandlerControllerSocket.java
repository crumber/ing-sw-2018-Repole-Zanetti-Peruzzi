package repolezanettiperuzzi.controller;

import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

//questo e' il nostro server socket che risiede sul server
public class HandlerControllerSocket {
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
        this.port = socket.getPort();
        this.controller = controller;
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
                initializePlayer();
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

        PrintWriter out = new PrintWriter(this.socket.getOutputStream(), true);
        out.println("registered");
        out.close();
        this.in.close();
        this.socket.close();
        controller.initializePlayer(playerID, pwd, addr, port, connection, UI);
    }

    private void setupPlayer() throws IOException, ParseException {
        String connection = param[0];
        String UI = param[1];
        //controller.setupPlayer(playerID, connection, UI, this.addr, this.port);
    }

    public void notifyOnNewPlayer(){

    }

}
