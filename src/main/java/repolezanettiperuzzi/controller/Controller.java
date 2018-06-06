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

    private boolean isTimerOn;

    public Controller(List<Player> view, GameBoard board){

       this.currentState = null;
       this.view = (ArrayList<Player>)view;
       this.board = board;
       this.isTimerOn = false;

    }

    public void setState(ControllerState nextState) throws IOException, ParseException {

        this.currentState=nextState;
        this.currentAction();
    }

    public ControllerState getState(){

        return this.currentState;

    }

    public boolean isTimerOn(){
        return this.isTimerOn;
    }

    public void setTimer(){
        //TODO creare il timer su un altro thread
        this.isTimerOn = true;
    }

    public void cancelTimer(){
        //TODO cancello il timer
        this.isTimerOn = false;
    }

    public int getCurrentTime(){
        if(isTimerOn){
            return 100;//TODO chiamo la getCurrentTime() dal Timer creato
        } else {
            return -1;
        }
    }

    //this method do the action of the current state
    private void currentAction() throws IOException, ParseException {

        this.currentState.doAction(this);

    }


}
