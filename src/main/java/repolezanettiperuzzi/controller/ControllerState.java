package repolezanettiperuzzi.controller;

import org.json.simple.parser.ParseException;
import repolezanettiperuzzi.model.GameBoard;
import repolezanettiperuzzi.model.Player;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;

public abstract class ControllerState {

    private GameBoard board;
    private ArrayList<Player> view;

   /* public ControllerState(ArrayList<Player> view, GameBoard board ){

        this.board=board;
        this.view = view;

    }*/
    public abstract void doAction(Controller controller) throws ParseException, IOException;



}
