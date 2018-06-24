package repolezanettiperuzzi.controller;

import org.json.simple.parser.ParseException;
import repolezanettiperuzzi.common.ClientStubRMI;
import repolezanettiperuzzi.common.ControllerStubRMI;
import repolezanettiperuzzi.model.Player;

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

    public synchronized String init(ClientStubRMI callbackClient, String username, String pwd, String conn, String UI) throws IOException, ParseException {
        synchronized (controller) {
            controller.setState(new SetConnectionState());
            InetAddress ipAddr = InetAddress.getByName("0.0.0.0");
            String result = ((SetConnectionState) controller.getState()).initializePlayer(username, pwd, ipAddr, 0, conn, UI);
            //System.out.println("isRegistered: " + result);
            //System.out.println("!clients.containsKey(username): " + !clients.containsKey(username));
            if (!clients.containsKey(username) && result.equals("registered")) {
                clients.put(username, callbackClient);
                System.out.println("New client registered.");
            } else if(!clients.containsKey(username) && result.contains("reconnect")){
                clients.put(username, callbackClient);
                System.out.println("New client registered.");
                result += " "+controller.board.getPlayerByName(username).getLastScene();
            }
            return result;
        }
    }

    public synchronized void notifyOnRegister(String playerName) throws RemoteException {
        System.out.println("Starting callbacks: "+playerName);
        System.out.println("Exists: "+clients.containsKey(playerName));
        ClientStubRMI client = clients.get(playerName);
        client.enterWaitingRoom();
        System.out.println("Callbacks complete.");
    }

    /* annulla registrazione per il callback */
    public synchronized boolean notifyOnExit(String playerName, String typeView) throws IOException, ParseException, InterruptedException {
        synchronized (controller) {
            if (clients.remove(playerName, clients.get(playerName))) {
                switch (typeView) {
                    case "waitingRoom":
                        controller.setState(new SetConnectionState());
                        controller.setLiveStatusOffline(playerName);
                        ((SetConnectionState) controller.getState()).notifyOnUpdatedPlayer();
                        break;
                    case "chooseWindow":
                        //TODO gestire l'uscita durante la scelta delle window
                        controller.setLiveStatusOffline(playerName);
                        break;
                    case "duringGame":
                        //TODO gestire l'uscita durante il gioco
                        break;
                }
                System.out.println("Client unregistered");
                return true;
            } else {
                System.out.println("Unable to unregister client.");
                return false;
            }
        }
    }

    public synchronized void waitingRoomLoaded(String playerName){
        synchronized (controller) {
            Player player = controller.board.getPlayerByName(playerName);
            player.setLastScene("waitingRoom");
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        ((SetConnectionState) controller.getState()).notifyOnUpdatedPlayer();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }

    public synchronized void refreshWaitingRoom(String playerName, int setTimer){
        synchronized (controller){
            String[] players = new String[controller.board.getPlayersOnline()];
            int j = 0;
            for(int i = 0; i<controller.board.getNPlayers(); i++){
                Player player = controller.board.getPlayer(i);
                if(player.getLastScene().equals("waitingRoom") && player.getLiveStatus()){
                    players[j] = player.getName();
                    j++;
                }
            }
            try {
                clients.get(playerName).refreshWaitingRoom(setTimer, players);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
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
