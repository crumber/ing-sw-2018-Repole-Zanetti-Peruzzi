package repolezanettiperuzzi.controller;

import repolezanettiperuzzi.model.GameBoard;
import repolezanettiperuzzi.view.GameViewSkeleton;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

//versione ancora super grezza del controller con il pattern State ancora da implementare
//questo sara' il lato server del controller
public class Controller extends UnicastRemoteObject implements ControllerStub{

    //view
    private ArrayList<GameViewSkeleton> view;
    //model
    private GameBoard board;

    public Controller(ArrayList<GameViewSkeleton> view, GameBoard board) throws RemoteException{
        this.view = view;
        this.board = board;
    }

    @Override
    public void faiQualcosa(String description) throws RemoteException {
        //fai qualcosa
    }
}
