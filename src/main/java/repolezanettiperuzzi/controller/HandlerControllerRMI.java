package repolezanettiperuzzi.controller;

import org.json.simple.parser.ParseException;
import repolezanettiperuzzi.common.ClientStubRMI;
import repolezanettiperuzzi.common.ControllerStubRMI;
import repolezanettiperuzzi.common.modelwrapper.WindowClient;
import repolezanettiperuzzi.model.Player;
import repolezanettiperuzzi.model.Window;

import java.awt.*;
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
                        System.out.println("enscro");
                        ((SetConnectionState) controller.getState()).notifyOnUpdatedPlayer();
                        System.out.println("escio");
                        break;
                    case "chooseWindow":
                        //TODO gestire l'uscita durante la scelta delle window
                        controller.setLiveStatusOffline(playerName);
                        break;
                    case "game":
                        controller.setLiveStatusOffline(playerName);
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
            try {
                controller.setState(new SetConnectionState());
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }
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

    public synchronized void notifyOnBeginChooseWindow(String playerName){
        synchronized (controller){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        clients.get(playerName).enterChooseWindow();
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }

    public synchronized void chooseWindowSceneLoaded(String playerName){
        synchronized(controller){
            ArrayList<Window> windows = new ArrayList<>();
            try {
                controller.setState(new FetchState());
                ((FetchState) controller.getState()).sendWindows(controller.board.getPlayerByName(playerName));
                windows = ((FetchState) controller.getState()).getWindows();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }

            final int currentTime = controller.getCurrentTime();

            ArrayList<WindowClient> windowsClient = new ArrayList<>();

            for (Window w : windows) {
                windowsClient.add(new WindowClient(w.getName(), w.getFTokens(), w.toString()));
            }

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        if(controller.board.getPlayerByName(playerName).getWindow()==null) {
                            clients.get(playerName).viewWindows(windowsClient, currentTime);
                        } else {
                            clients.get(playerName).viewOneWindow(windowsClient.get(0), currentTime);
                        }
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }

            }).start();
        }
    }

    /**
     * Sends the windows to view to the client
     * @param playerName nome player
     * @param windows arraylist di windows
     * @param setTimer valore timer
     */
    public synchronized void viewWindows(String playerName, ArrayList<Window> windows, int setTimer){
        synchronized (controller) {
            System.out.println("dentro view");
            ArrayList<WindowClient> windowsClient = new ArrayList<>();
            if (windows == null) {
                System.out.println("e' null");
            }
            for (Window w : windows) {
                System.out.println(w.toString());
                windowsClient.add(new WindowClient(w.getName(), w.getFTokens(), w.toString()));
            }

            System.out.println("sono in viewwindows");

            System.out.println("Sending windows");
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        clients.get(playerName).viewWindows(windowsClient, setTimer);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }

    public synchronized void readyToPlay(String playerName){
        synchronized (controller){
            new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            controller.setState(new FetchState());
                            ((FetchState)controller.getState()).readyToPlay(playerName);
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
        }
    }

    public synchronized void sendChosenWindow(String playerName, String windowName){
        synchronized (controller){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        controller.setState(new FetchState());
                        ((FetchState) controller.getState()).setChosenWindow(controller.board.getPlayerByName(playerName), windowName.replace("-", " "));
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }

    public synchronized void enterGame(String playerName){
        synchronized (controller) {
            System.out.println("enterGame");
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        System.out.println("insideEnterGame");
                        clients.get(playerName).enterGame();
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }

    public synchronized void showWinOnChooseWindowAlert(String playerName){
        synchronized (controller){
            try {
                clients.get(playerName).showWinOnChooseWindowAlert();
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
