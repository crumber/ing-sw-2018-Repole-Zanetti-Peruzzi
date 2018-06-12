package repolezanettiperuzzi.common;

import java.rmi.Remote;
import java.rmi.RemoteException;

//interfaccia della view lato server a cui potra' accedere il controller
public interface HandlerSkeletonRMI extends Remote {
    public void updateView() throws RemoteException;
}
