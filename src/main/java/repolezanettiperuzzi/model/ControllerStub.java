package repolezanettiperuzzi.model;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

//questa e' l'interfaccia a cui potra' accedere la view dal lato client
public interface ControllerStub extends Remote {
    void faiQualcosa(String description) throws RemoteException;
}
