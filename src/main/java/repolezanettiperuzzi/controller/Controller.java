package repolezanettiperuzzi.controller;

import repolezanettiperuzzi.model.GameBoard;
import repolezanettiperuzzi.model.Player;

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


}
