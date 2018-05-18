package repolezanettiperuzzi.controller;

import repolezanettiperuzzi.model.GameBoard;
import repolezanettiperuzzi.model.Player;

import java.util.ArrayList;

public class EndGameState extends ControllerState{

    public EndGameState(ArrayList<Player> view, GameBoard board ){

        super(view, board);

    }

    @Override
    public void doAction() {

    }
}
