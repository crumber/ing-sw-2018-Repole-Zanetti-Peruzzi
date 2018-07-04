package repolezanettiperuzzi.common;

import org.json.simple.parser.ParseException;
import repolezanettiperuzzi.model.Player;

import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

/**
 * Classe che rappresenta il controlle per RMI
 * @author Andrea Zanetti
 */
//questa e' l'interfaccia a cui potra' accedere la view dal lato client
public interface ControllerStubRMI extends Remote {

    /**
     * Inizializza le informazioni
     * @param callbackClient Riferimento all'oggetto remoto del client
     * @param username Nome client
     * @param pwd Password client
     * @param conn Connessione
     * @param UI Grafica scelta
     * @return Stringa con le informazioni
     * @throws IOException  Fallimento o interruzione delle operazioni I/O
     * @throws ParseException Errore durante l'analisi
     */
    String init(ClientStubRMI callbackClient, String username, String pwd, String conn, String UI) throws IOException, ParseException;

    /**
     * Caricamento waiting room
     * @param playerName Nome del Player
     * @throws RemoteException Eccezione RMI
     */
    void waitingRoomLoaded(String playerName) throws RemoteException ;

    /**
     *
     * @param playerName Nome del player
     * @param typeView Tipo di view
     * @return Booleano
     * @throws IOException Fallimento o interruzione delle operazioni I/O
     * @throws ParseException Errore durante l'analisi
     * @throws InterruptedException Interruzione thread
     */
    boolean notifyOnExit(String playerName, String typeView) throws IOException, ParseException, InterruptedException;

    /**
     * Caricamento scelta delle window
     * @param playerName Nome del player
     * @throws RemoteException Eccezione RMI
     */
    void chooseWindowSceneLoaded(String playerName) throws RemoteException;

    /**
     *
     * @param playerName Nome del player
     * @throws RemoteException Eccezione RMI
     */
    void readyToPlay(String playerName) throws RemoteException;

    /**
     * Invia la window scelta
     * @param playerName Player che ha scelto la window
     * @param windowName Nome della window scelta
     * @throws RemoteException Eccezione di RMI
     */
    void sendChosenWindow(String playerName, String windowName) throws RemoteException;
}
