package repolezanettiperuzzi.view;

import repolezanettiperuzzi.controller.ControllerStub;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

//lato client della view che chiama i metodi in remoto del controller
public class GameView implements GameViewSkeleton {
    public void setupRMI() throws RemoteException, NotBoundException {
        System.setProperty("java.security.policy", "RMI/stupid.policy");
        System.setSecurityManager(new SecurityManager());

        Registry registry = LocateRegistry.getRegistry();

        System.out.println("RMI registry bindings:");
        for (String binding: registry.list()) {
            System.out.println(">>> " + binding);
        }


        ControllerStub cStub = (ControllerStub) registry.lookup("controller");
        cStub.faiQualcosa("ciao");
    }

    @Override
    public void updateView() {
        //aggiorno View
    }
}
