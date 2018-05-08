package repolezanettiperuzzi.model;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
