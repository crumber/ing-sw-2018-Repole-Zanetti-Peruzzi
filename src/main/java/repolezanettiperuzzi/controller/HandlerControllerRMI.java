package repolezanettiperuzzi.controller;

import org.json.simple.parser.ParseException;
import repolezanettiperuzzi.common.ClientStubRMI;
import repolezanettiperuzzi.common.ControllerStubRMI;

import java.io.IOException;
import java.net.InetAddress;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

//questa e' l'implementazione effettiva del controller che riesede nel server e che chiamera' i metodi della classe controller
//e sua insaputa che sia una comunicazione RMI o Socket
public class HandlerControllerRMI implements ControllerStubRMI {

    private Controller controller;

    /* lista dei client registrati */
    private HashMap<String, ClientStubRMI> clients;

    public HandlerControllerRMI(Controller controller) throws RemoteException {
        this.controller = controller;
        clients = new HashMap<>( );
    }

    public synchronized boolean init(ClientStubRMI callbackClient, String username, String pwd, String conn, String UI) throws IOException, ParseException {
        controller.setState(new SetConnectionState());
        InetAddress ipAddr = InetAddress.getByName("0.0.0.0");
        boolean isRegistered = ((SetConnectionState)controller.getState()).initializePlayer(username, pwd, ipAddr, 0,  conn, UI);
        System.out.println("isRegistered: "+isRegistered);
        System.out.println("!clients.containsKey(username): "+!clients.containsKey(username));
        if (!clients.containsKey(username) && isRegistered) {
            clients.put(username, callbackClient);
            System.out.println(" New client registered." );
            return true;
        }
        return false;
    }

    public synchronized void notifyOnRegister(String playerName) throws RemoteException {
        System.out.println("Starting callbacks: "+playerName);
        System.out.println("Exists: "+clients.containsKey(playerName));
        ClientStubRMI client = clients.get(playerName);
        client.enterWaitingRoom();
        System.out.println("Callbacks complete.");
    }

    /* annulla registrazione per il callback */
    public synchronized void unregisterForCallback( String username, ClientStubRMI callbackClient) throws RemoteException {

        if (clients.remove(username, callbackClient)) {
            System.out.println("Client unregistered");
        } else {
            System.out.println("Unable to unregister client.");
        }
    }

    @Override
    public void faiQualcosa(String description) throws RemoteException {
        /*
        manipolo i dati...
        controller.currentAction();
        */
    }
}
