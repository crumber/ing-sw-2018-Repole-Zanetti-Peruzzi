package repolezanettiperuzzi.controller;

import repolezanettiperuzzi.model.GameBoard;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

//questa e' l'implementazione effettiva del controller che riesede nel server e che chiamera' i metodi della classe controller
//e sua insaputa che sia una comunicazione RMI o Socket
public class HandlerControllerRMI extends UnicastRemoteObject implements HandlerStubRMI {

    private ControllerContext controller;

    public HandlerControllerRMI(ControllerContext controller) throws RemoteException {
        this.controller = controller;
    }

    @Override
    public void faiQualcosa(String description) throws RemoteException {
        //manipolo i dati...
        controller.currentAction();
    }
}
