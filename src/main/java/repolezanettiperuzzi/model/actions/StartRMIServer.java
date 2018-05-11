package repolezanettiperuzzi.model.actions;

import repolezanettiperuzzi.controller.*;


import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

//all'inizio del gioco va lanciato il server RMI che imposta tutto
public class StartRMIServer extends Action {
    public HandlerStubRMI doAction(Controller controller) throws RemoteException, AlreadyBoundException {
        System.setProperty("java.security.policy", "RMI/stupid.policy");
        System.setSecurityManager(new SecurityManager());

        System.out.println("Creating new controller");
        HandlerStubRMI hStub = new HandlerControllerRMI(controller);

        System.out.println("Binding");
        Registry registry = LocateRegistry.getRegistry();
        registry.rebind("controller", hStub);

        System.out.println("Waiting for invocations...");

        return hStub;
    }
}
