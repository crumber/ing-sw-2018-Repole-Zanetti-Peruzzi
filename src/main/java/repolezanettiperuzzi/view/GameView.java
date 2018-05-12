package repolezanettiperuzzi.view;

//  import repolezanettiperuzzi.controller.ControllerStub;
import repolezanettiperuzzi.controller.HandlerSkeletonRMI;
import repolezanettiperuzzi.controller.HandlerStubRMI;
import repolezanettiperuzzi.controller.HandlerStubSocket;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

//lato client della view che chiama i metodi in remoto del controller
//prendo i dati gia' elaborati da RMI o Socket e li passo a GameViewCLI o GameViewGUI
public class GameView  {

    public void updateView() {
        //aggiorno View
    }
}
