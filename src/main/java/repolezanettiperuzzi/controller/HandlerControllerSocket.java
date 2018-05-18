package repolezanettiperuzzi.controller;

import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

//questo e' il nostro server socket che risiede sul server
public class HandlerControllerSocket {
    private String[] line;
    private InetAddress addr;
    private int port;
    private Controller controller;
    private String playerID;
    private String action;
    private String[] param;

    //struttura messaggo: playerID action parameters
    public HandlerControllerSocket(String line, InetAddress addr, int port, Controller controller){
        this.line = line.split(" ");
        this.addr = addr;
        this.port = port;
        this.controller = controller;
        this.playerID = this.line[0];
        this.action = this.line[1];

        for(int i = 0; i<line.length()-2; i++){
            param[i] = this.line[i+2];
        }
    }

    public void handleMessage() throws IOException, ParseException {
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
        controller.initializePlayer(playerID, pwd, addr, port);
    }

    private void setupPlayer() throws IOException, ParseException {
        String connection = param[0];
        String UI = param[1];
        controller.setupPlayer(playerID, connection, UI, this.addr, this.port);
    }


}
