package repolezanettiperuzzi.controller;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ControllerRMIServer {

    private Controller controller;

    public ControllerRMIServer(Controller controller){
        this.controller = controller;
    }

    public void startServer() throws RemoteException {
        System.setProperty("java.security.policy", "RMI/stupid.policy");

        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }

        System.out.println("Creating new controller");
        HandlerControllerRMI handlerRMI = new HandlerControllerRMI(controller);

        System.out.println("Binding");
        Registry registry = LocateRegistry.getRegistry();
        registry.rebind("controller", handlerRMI);

        System.out.println("Waiting for invocations...");
    }
}
