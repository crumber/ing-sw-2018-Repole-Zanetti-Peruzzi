package repolezanettiperuzzi.controller;

import repolezanettiperuzzi.model.GameBoard;
import repolezanettiperuzzi.model.RealPlayer;

import java.util.ArrayList;

public class ControllerContext {

    //view
    private ArrayList<RealPlayer> view;
    //model
    private GameBoard board;

    private ControllerState currentState;

    public ControllerContext(ArrayList<RealPlayer> view, GameBoard board){

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
