package repolezanettiperuzzi.common;

import repolezanettiperuzzi.common.modelwrapper.WindowClient;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

//interfaccia della view lato server a cui potra' accedere il controller
public interface ClientStubRMI extends Remote {
    void updateView() throws RemoteException;
    void enterWaitingRoom() throws RemoteException;
    void refreshWaitingRoom(int setTimer, String[] players) throws RemoteException;
    void enterChooseWindow() throws RemoteException;
    void viewWindows(ArrayList<WindowClient> windows, int currentTime) throws RemoteException;
}
