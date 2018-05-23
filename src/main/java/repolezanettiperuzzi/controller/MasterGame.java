package repolezanettiperuzzi.controller;

import org.json.simple.parser.ParseException;
import repolezanettiperuzzi.model.GameBoard;


import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;


public class MasterGame {

    private int nPlayer;
    private static GameBoard board;

    public static void main(String[] args) throws AlreadyBoundException, RemoteException, ParseException {

        board= new GameBoard();

        //la createGame dovrebbe essere uno stato del controller
        //CreateGame.doAction(board);

        Controller controller = new Controller(board.getPlayers(),board);

        controller.setState(new FetchState());

    }




}
