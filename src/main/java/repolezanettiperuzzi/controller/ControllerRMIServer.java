package repolezanettiperuzzi.controller;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ControllerRMIServer {

    private Controller controller;

    public ControllerRMIServer(Controller controller){
        this.controller = controller;
    }

    public HandlerStubRMI startServer() throws RemoteException {
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
