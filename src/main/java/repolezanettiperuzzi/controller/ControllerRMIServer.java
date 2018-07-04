package repolezanettiperuzzi.controller;

import repolezanettiperuzzi.common.ControllerStubRMI;
import repolezanettiperuzzi.common.DynamicPath;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 * Classe che rappresenta il controller di RMI (server)
 * @author Andrea Zanetti
 */
public class ControllerRMIServer {

    private Controller controller;

    /**
     * Costruttore
     * @param controller Controller
     */
    public ControllerRMIServer(Controller controller){
        this.controller = controller;
    }

    /**
     *
     * @return Il registro di RMI
     * @throws RemoteException Eccezione RMI
     */
    public Registry startServer() throws RemoteException {

        int port = 1099;

        Registry registry = LocateRegistry.createRegistry(port);

        System.setProperty("java.rmi.server.useCodebaseOnly", "false");
        System.setProperty("java.rmi.server.logCalls", "true");

        System.setProperty("java.rmi.server.codebase", new DynamicPath("controller/").getPath()+" "+new DynamicPath("common/").getPath()+" "+new DynamicPath("model/").getPath());
        System.setProperty("java.security.policy", new DynamicPath("RMI/stupid.policy").getPath());

        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }



        //System.out.println("Creating new RMI Handler");
        HandlerControllerRMI handlerRMI = new HandlerControllerRMI(controller);
        controller.setHandlerRMI(handlerRMI);
        //System.out.println("Exporting HandlerRMI");
        ControllerStubRMI stub = (ControllerStubRMI) UnicastRemoteObject.exportObject (handlerRMI, 39000);

        //System.out.println("Binding");
        registry.rebind("controller", stub);

        System.out.println("Server RMI ready on port "+port+"\n");

        //UnicastRemoteObject.unexportObject(ctrlRMI, true);
        //UnicastRemoteObject.unexportObject(registry, true);

        return registry;
    }
}
