package repolezanettiperuzzi.controller;

import org.json.simple.parser.ParseException;
import repolezanettiperuzzi.model.GameBoard;


import java.io.IOException;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;


public class MasterGame {

    private int nPlayer;
    private static GameBoard board;

    public static void main(String[] args) throws IOException, ParseException {

        board = new GameBoard();

        Controller controller = new Controller(board.getPlayers(),board);

        controller.setState(new SetConnectionState());



    }




}
