package repolezanettiperuzzi.controller;

import repolezanettiperuzzi.common.DynamicPath;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class ControllerRMIServer {

    private Controller controller;

    public ControllerRMIServer(Controller controller){
        this.controller = controller;
    }

    public void startServer() throws RemoteException {

        Registry registry = LocateRegistry.createRegistry(1099);

        System.setProperty("java.rmi.server.useCodebaseOnly", "false");
        System.setProperty("java.rmi.server.logCalls", "true");

        System.setProperty("java.rmi.server.codebase", new DynamicPath("controller/").getPath()+" "+new DynamicPath("common/").getPath()+" "+new DynamicPath("model/").getPath());
        System.setProperty("java.security.policy", new DynamicPath("RMI/stupid.policy").getPath());

        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }



        System.out.println("Creating new RMI Handler");
        HandlerControllerRMI handlerRMI = new HandlerControllerRMI(controller);
        System.out.println("Exporting HandlerRMI");
        HandlerStubRMI stub = (HandlerStubRMI) UnicastRemoteObject.exportObject (handlerRMI, 39000);

        System.out.println("Binding");
        registry.rebind("controller", stub);

        System.out.println("Waiting for invocations...");

        //UnicastRemoteObject.unexportObject(ctrlRMI, true);
        //UnicastRemoteObject.unexportObject(registry, true);
    }
}
