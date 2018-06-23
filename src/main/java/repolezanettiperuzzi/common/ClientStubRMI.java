package repolezanettiperuzzi.common;

import java.rmi.Remote;
import java.rmi.RemoteException;

//interfaccia della view lato server a cui potra' accedere il controller
public interface ClientStubRMI extends Remote {
    void updateView() throws RemoteException;
    void enterWaitingRoom() throws RemoteException;
    void refreshWaitingRoom(int setTimer, String[] players) throws RemoteException;
}
