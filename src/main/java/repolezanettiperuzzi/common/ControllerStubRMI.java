package repolezanettiperuzzi.common;

import org.json.simple.parser.ParseException;
import repolezanettiperuzzi.model.Player;

import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

//questa e' l'interfaccia a cui potra' accedere la view dal lato client
public interface ControllerStubRMI extends Remote {
    void faiQualcosa(String description) throws RemoteException;
    String init(ClientStubRMI callbackClient, String username, String pwd, String conn, String UI) throws IOException, ParseException;
    void waitingRoomLoaded(String playerName) throws RemoteException ;
    boolean notifyOnExit(String playerName, String typeView) throws IOException, ParseException, InterruptedException;
    void chooseWindowSceneLoaded(String playerName) throws RemoteException;
}
