package repolezanettiperuzzi.model.actions;

import repolezanettiperuzzi.controller.Controller;
import repolezanettiperuzzi.controller.HandlerStubRMI;
import repolezanettiperuzzi.model.GameBoard;
import repolezanettiperuzzi.model.Player;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class CreateGame extends Action{

    public static void doAction(GameBoard board){


        //inizializzo socket e RMI per l'ascolto
        //StartRMIServer startRMI = new StartRMIServer();

        //arrayList di GameViewSkeleton temporaneo
        ArrayList<Player> view = new ArrayList<Player>(); //aggiungo i giocatori man mano che si collegano
        Controller controller = new Controller(view, board); // passo i player per sapere il tipo di connessione e UI

        /*try {
            HandlerStubRMI hStub = startRMI.doAction(controller);
        } catch (RemoteException | AlreadyBoundException e) {
            e.printStackTrace();
        }*/

        //inizializzo server socket
    }

}
