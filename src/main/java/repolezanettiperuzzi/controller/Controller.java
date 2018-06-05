package repolezanettiperuzzi.controller;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import repolezanettiperuzzi.model.GameBoard;
import repolezanettiperuzzi.model.Player;

import java.io.*;
import java.net.InetAddress;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public class Controller {

    //view
    protected ArrayList<Player> view;
    //model
    protected GameBoard board;

    protected ControllerState currentState;

    public Controller(List<Player> view, GameBoard board){

       this.currentState = null;
       this.view = (ArrayList<Player>)view;
       this.board = board;

    }

    public void setState(ControllerState nextState) throws IOException, ParseException {

        this.currentState=nextState;
        this.currentAction();
    }

    public ControllerState getState(){

        return this.currentState;

    }

    //this method do the action of the current state
    private void currentAction() throws IOException, ParseException {

        this.currentState.doAction(this);

    }

    /*public boolean setupPlayer(String playerID, String connection, String UI, InetAddress addr, int port) throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        FileReader jsonIn = new FileReader("gamedata/playersinfo.json");
        JSONArray jsonArr = (JSONArray) parser.parse(jsonIn);

        try {

            boolean registered = false;

            //json file structure: {playerID, pwd, RMI/Socket, CLI/GUI, addr, port}
            for (Object o : jsonArr) {
                JSONObject player = (JSONObject) o;

                String name = (String) player.get("playerID");

                String passwd = (String) player.get("pwd");

                if (name.equals(playerID)) {
                    player.put("connection", connection);
                    player.put("UI", UI);
                    player.put("address", addr);
                    player.put("port", port);
                    registered = true;
                    break;
                }
            }

            if(registered) {

                try (FileWriter file = new FileWriter("gamedata/playersinfo.json")) {
                    file.write(jsonArr.toJSONString());
                    file.close();
                } catch (IOException e) {
                    System.out.println("Cannot write on file");
                }

                return true;
            } else {
                //non ti sei registrato. Registrati prima!
                return false;
            }

        } finally {
            jsonIn.close();
        }
    }*/


}
