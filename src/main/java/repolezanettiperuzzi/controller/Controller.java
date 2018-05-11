package repolezanettiperuzzi.controller;

import repolezanettiperuzzi.model.GameBoard;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

//questo sara' il lato server del controller
public class Controller{

    //view
    private ArrayList<HandlerSkeletonRMI> view;
    //model
    private GameBoard board;

    public Controller(ArrayList<HandlerSkeletonRMI> view, GameBoard board) throws RemoteException{
        this.view = view;
        this.board = board;
    }

    public void faiQualcosa(String description) throws RemoteException {
        //fai qualcosa senza sapere se la richiesta viene da RMI o Socket
    }
}
