package repolezanettiperuzzi.controller;

import repolezanettiperuzzi.model.GameBoard;
import repolezanettiperuzzi.model.RealPlayer;
import repolezanettiperuzzi.model.actions.Action;
import repolezanettiperuzzi.model.actions.StartRMIServer;
import repolezanettiperuzzi.view.GameViewSkeleton;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class MasterGame {

    private int nPlayer;
    private static GameBoard board;

    public static void main(String[] args) throws AlreadyBoundException, RemoteException {

        board= new GameBoard();
       //inizializzo socket e RMI per l'ascolto
        StartRMIServer startRMI = new StartRMIServer();

        //arrayList di GameViewSkeleton temporaneo
        ArrayList<RealPlayer> view = new ArrayList<RealPlayer>(); //aggiungo i giocatori man mano che si collegano
        ControllerContext controller = new ControllerContext(view, board); // passo i player per sapere il tipo di connessione e UI

        try {
            HandlerStubRMI hStub = startRMI.doAction(controller);
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (AlreadyBoundException e) {
            e.printStackTrace();
        }

        //inizializzo server socket



    }




}
