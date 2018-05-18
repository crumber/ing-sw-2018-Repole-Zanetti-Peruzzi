package repolezanettiperuzzi.controller;

import repolezanettiperuzzi.model.GameBoard;
import repolezanettiperuzzi.model.Player;

import java.util.ArrayList;

public abstract class ControllerState {

    private GameBoard board;
    private ArrayList<Player> view;

   /* public ControllerState(ArrayList<Player> view, GameBoard board ){

        this.board=board;
        this.view = view;

    }*/
    public abstract void doAction(Controller controller);

}
