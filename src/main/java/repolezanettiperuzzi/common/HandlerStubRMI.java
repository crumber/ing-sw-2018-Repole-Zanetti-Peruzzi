package repolezanettiperuzzi.common;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

//questa e' l'interfaccia a cui potra' accedere la view dal lato client
public interface HandlerStubRMI extends Remote {
    void faiQualcosa(String description) throws RemoteException;
}
