package repolezanettiperuzzi.controller;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

//questa e' l'implementazione effettiva del controller che riesede nel server e che chiamera' i metodi della classe controller
//e sua insaputa che sia una comunicazione RMI o Socket
public class HandlerControllerRMI extends UnicastRemoteObject implements HandlerStubRMI {

    private Controller controller;

    public HandlerControllerRMI(Controller controller) throws RemoteException {
        this.controller = controller;
    }

    @Override
    public void faiQualcosa(String description) throws RemoteException {
        /*
        manipolo i dati...
        controller.currentAction();
        */
    }
}
