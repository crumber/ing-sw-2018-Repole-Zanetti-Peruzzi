package repolezanettiperuzzi.controller;

import repolezanettiperuzzi.model.GameBoard;
import repolezanettiperuzzi.model.Player;
import repolezanettiperuzzi.model.actions.StartRMIServer;

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
        ArrayList<Player> view = new ArrayList<Player>(); //aggiungo i giocatori man mano che si collegano
        Controller controller = new Controller(view, board); // passo i player per sapere il tipo di connessione e UI

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
