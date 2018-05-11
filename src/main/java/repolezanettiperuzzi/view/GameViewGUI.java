package repolezanettiperuzzi.view;

import repolezanettiperuzzi.controller.HandlerSkeletonRMI;
import repolezanettiperuzzi.controller.HandlerStubRMI;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class GameViewGUI implements HandlerSkeletonRMI {
    public void setupRMI() throws RemoteException, NotBoundException {
        System.setProperty("java.security.policy", "RMI/stupid.policy");
        System.setSecurityManager(new SecurityManager());

        Registry registry = LocateRegistry.getRegistry();

        System.out.println("RMI registry bindings:");
        for (String binding: registry.list()) {
            System.out.println(">>> " + binding);
        }


        HandlerStubRMI cStub = (HandlerStubRMI) registry.lookup("controller");
        cStub.faiQualcosa("ciao");
    }

    @Override
    public void updateView() {
        //aggiorno View
    }
}
