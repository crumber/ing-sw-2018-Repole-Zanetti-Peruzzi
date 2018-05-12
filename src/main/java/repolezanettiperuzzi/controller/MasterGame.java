package repolezanettiperuzzi.controller;

import repolezanettiperuzzi.model.GameBoard;

import repolezanettiperuzzi.model.actions.CreateGame;


import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;


public class MasterGame {

    private int nPlayer;
    private static GameBoard board;

    public static void main(String[] args) throws AlreadyBoundException, RemoteException {

        board= new GameBoard();

        CreateGame.doAction(board);




    }




}
