package repolezanettiperuzzi.controller;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import repolezanettiperuzzi.model.GameBoard;
import repolezanettiperuzzi.model.Player;

import java.io.*;
import java.net.InetAddress;
import java.util.ArrayList;

public class Controller {

    //view
    private ArrayList<Player> view;
    //model
    private GameBoard board;

    private ControllerState currentState;

    public Controller(ArrayList<Player> view, GameBoard board){

       this.currentState = null;
       this.view = view;
       this.board = board;

    }

    public void setState(ControllerState nextState){

        this.currentState=nextState;

    }

    public ControllerState getState(){

        return this.currentState;

    }

    public void currentAction(){

        this.currentState.doAction();
    }

    //metodo utile per il loop della classe MultiEchoSocketServer
    public boolean isListening(){
        return this.currentState instanceof EndGameState;
    }

    //metodo chiamato dal giocatore appena si connette al server
    public void initializePlayer(String playerID, String pwd, InetAddress addr, int port) throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        FileReader jsonIn = new FileReader("gamedata/playersinfo.json");
        JSONArray jsonArr = (JSONArray) parser.parse(jsonIn);

        try {

            boolean registered = false;

            //playerID, pwd, RMI/Socket, CLI/GUI, addr, port
            for (Object o : jsonArr) {
                JSONObject player = (JSONObject) o;

                String name = (String) player.get("playerID");

                String passwd = (String) player.get("pwd");

                if (name.equals(playerID) && passwd.equals(pwd)) {
                    //riconnetto il giocatore
                    registered = true;
                    break;
                }
            }

            if(!registered) {

                JSONObject obj = new JSONObject();
                obj.put("playerID", playerID);
                obj.put("pwd", pwd);
                jsonArr.add(obj);

                try (FileWriter file = new FileWriter("gamedata/playersinfo.json")) {
                    file.write(obj.toJSONString());
                    System.out.println("Successfully Copied JSON Object to File...");
                    System.out.println("\nJSON Object: " + obj);
                    file.close();
                } catch (IOException e) {
                    System.out.println("Cannot write on file");
                }
            }

        } finally {
            jsonIn.close();
        }

    }

    public boolean setupPlayer(String playerID, String connection, String UI, InetAddress addr, int port) throws IOException, ParseException {
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
    }


}
