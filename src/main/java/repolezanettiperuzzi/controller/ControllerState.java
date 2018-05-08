package repolezanettiperuzzi.controller;

import repolezanettiperuzzi.model.GameBoard;

public abstract class ControllerState {

    GameBoard board;
    // classe astratta view lato server GameView view;

    public ControllerState(GameBoard board /*,GameView view*/){

        this.board=board;
        //this.view = view;

    }
    public abstract void doAction();

}
