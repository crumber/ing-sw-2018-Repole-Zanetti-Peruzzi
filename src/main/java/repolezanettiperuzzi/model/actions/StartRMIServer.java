package repolezanettiperuzzi.model.actions;

import repolezanettiperuzzi.controller.Controller;
import repolezanettiperuzzi.controller.ControllerStub;
import repolezanettiperuzzi.model.GameBoard;
import repolezanettiperuzzi.view.GameViewSkeleton;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;

//all'inizio del gioco va lanciato il server RMI che imposta tutto
public class StartRMIServer extends Action {
    public void doAction(ArrayList<GameViewSkeleton> view, GameBoard board) throws RemoteException, AlreadyBoundException {
        System.setProperty("java.security.policy", "RMI/stupid.policy");
        System.setSecurityManager(new SecurityManager());

        System.out.println("Creating new controller");
        ControllerStub cStub = new Controller(view, board);

        System.out.println("Binding");
        Registry registry = LocateRegistry.getRegistry();
        registry.rebind("controller", cStub);

        System.out.println("Waiting for invocations...");
    }
}
