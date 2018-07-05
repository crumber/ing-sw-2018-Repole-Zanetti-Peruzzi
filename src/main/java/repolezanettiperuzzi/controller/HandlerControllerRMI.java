package repolezanettiperuzzi.controller;

import javafx.collections.ObservableList;
import org.json.simple.parser.ParseException;
import repolezanettiperuzzi.common.ClientStubRMI;
import repolezanettiperuzzi.common.ControllerStubRMI;
import repolezanettiperuzzi.common.modelwrapper.ColourClient;
import repolezanettiperuzzi.common.modelwrapper.DieClient;
import repolezanettiperuzzi.common.modelwrapper.GameBoardClient;
import repolezanettiperuzzi.common.modelwrapper.WindowClient;
import repolezanettiperuzzi.model.Player;
import repolezanettiperuzzi.model.Window;
import repolezanettiperuzzi.model.actions.BeginRound;
import repolezanettiperuzzi.model.actions.BeginTurn;
import repolezanettiperuzzi.model.publiccards.PublicCard;
import repolezanettiperuzzi.model.toolcards.ToolCard;

import java.io.IOException;
import java.io.ObjectInput;
import java.net.InetAddress;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * Classe che rappresenta l'implementazione effettiva del controller che risiede nel server
 * @author Andrea Zanetti
 */
//questa e' l'implementazione effettiva del controller che riesede nel server e che chiamera' i metodi della classe controller
//e sua insaputa che sia una comunicazione RMI o Socket
public class HandlerControllerRMI implements ControllerStubRMI {

    private Controller controller;

    /* lista dei client registrati */
    private HashMap<String, ClientStubRMI> clients;

    private Object lock;

    /**
     * Costruttore
     * @param controller Controller
     * @throws RemoteException Eccezione RMI
     */
    public HandlerControllerRMI(Controller controller) throws RemoteException {
        this.controller = controller;
        clients = new HashMap<>( );
        this.lock = new Object();
    }

    /**
     *
     * @param callbackClient Riferimento all'oggetto remoto del client
     * @param username Nome client
     * @param pwd Password client
     * @param conn Connessione
     * @param UI Grafica scelta
     * @return Stringa di connessione
     * @throws IOException Fallimento o interruzione delle operazioni I/O
     * @throws ParseException Errore durante l'analisi
     */
    public synchronized String init(ClientStubRMI callbackClient, String username, String pwd, String conn, String UI) throws IOException, ParseException {
        synchronized (controller) {
            controller.setState(new SetConnectionState());
            InetAddress ipAddr = InetAddress.getByName("0.0.0.0");
            String result = ((SetConnectionState) controller.getState()).initializePlayer(username, pwd, ipAddr, 0, conn, UI);
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

    /**
     * Notifica la registrazione
     * @param playerName Nome del player
     * @throws RemoteException Eccezione RMI
     */
    public synchronized void notifyOnRegister(String playerName) throws RemoteException {
        System.out.println("Starting callbacks: "+playerName);
        System.out.println("Exists: "+clients.containsKey(playerName));
        ClientStubRMI client = clients.get(playerName);
        client.enterWaitingRoom();
        System.out.println("Callbacks complete.");
    }

    /**
     * Annulla la registrazione per il callback
     * @param playerName Nome del player
     * @param typeView Tipo di view
     * @return Boolean
     * @throws IOException Fallimento o interruzione delle operazioni I/O
     * @throws ParseException Errore durante l'analisi
     * @throws InterruptedException Interruzione thread
     */
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
                    case "game":
                        controller.setState(new TurnState());
                        controller.setLiveStatusOffline(playerName);
                        ((TurnState)controller.getState()).notifyStatusToPlayers();
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

    /**
     * waiting room loaded
     * @param playerName Nome del Player
     */
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

    /**
     * Aggiorna la waiting room
     * @param playerName Nome del player
     * @param setTimer Timer
     */
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

    /**
     * Notifica l'inizio della scelta delle windows
     * @param playerName Nome del player
     */
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

    /**
     * Loaded della scelta delle window
     * @param playerName Nome del player
     */
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
            ArrayList<WindowClient> windowsClient = new ArrayList<>();
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

    /**
     * Passa alla schermata successiva
     * @param playerName Nome del player
     */
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

    /**
     * Invia la window scelta
     * @param playerName Player che ha scelto la window
     * @param windowName Nome della window scelta
     */
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

    /**
     * Mostra che sei dentro l'inizio del gioco
     * @param playerName Nome del player
     */
    public synchronized void enterGame(String playerName){
        synchronized (controller) {
            System.out.println("enterGame");
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        clients.get(playerName).enterGame();
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }

    /**
     * Mostra che sei l'ultimo in gioco
     * @param playerName Nome del player
     */
    public synchronized void showWinOnChooseWindowAlert(String playerName){
        synchronized (controller){
            try {
                clients.get(playerName).showWinOnChooseWindowAlert();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public synchronized void updateView(String playerName){
        synchronized (controller){
            GameBoardClient boardClient = new GameBoardClient();

            for (Player player: controller.board.getPlayers()){
                boardClient.addPlayer(player.getName());
                boardClient.getPlayerByName(player.getName()).setSecretColour(ColourClient.stringToColour(player.getSecretColour().toString()));
                boardClient.getPlayerByName(player.getName()).setWindow(new WindowClient(player.getWindow().getName(), player.getWindow().getFTokens(), player.getWindow().toString()));
                boardClient.getPlayerByName(player.getName()).setFavorTokens(player.getFavorTokens());
                boardClient.getPlayerByName(player.getName()).setLiveStatus(player.getLiveStatus());
            }

            boardClient.setRound(BeginRound.getRound());
            boardClient.setTurn(BeginTurn.getCurrentTurn());
            for(int i = 0; i<controller.board.getSizeDraft(); i++){
                boardClient.addDieToDraft(new DieClient(controller.board.getDieDraft(i).toString()));
            }
            for(int i = 0; i<controller.board.getRoundTrack().getSize(); i++) {
                ArrayList<DieClient> dice = new ArrayList<>();
                for(int j = 0; j<controller.board.getRoundTrack().getRound(i).size(); j++) {
                    dice.add(new DieClient(controller.board.getDieFromRoundTrack(i, j).toString()));
                }
                boardClient.getRoundTrack().addDice(dice);
            }

            for(int i = 0; i<3; i++) {
                PublicCard card = controller.board.getPublicCards(i);
                boardClient.addPublicCard(card.getTitle(), card.getDescription(), card.getValue());
            }

            for(int i = 0; i<3; i++) {
                ToolCard card = controller.board.getToolCard(i);
                boardClient.addToolCard(card.getTitle(), card.getDescription(), card.getId(), controller.board.getCostToolCard(i));
            }

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        synchronized (clients.get(playerName)) {
                            clients.get(playerName).updateView(boardClient);
                        }
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            }).start();

        }
    }

    public synchronized void insertDie(String playerName, int draftPos, int xPos, int yPos){
        synchronized (controller){
            try {
                controller.setState(new TurnState());
                ((TurnState)controller.getState()).insertDie(controller.board.getPlayerByName(playerName),draftPos+" "+xPos+" "+yPos);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    public synchronized void notifyOnBeginTurn(String playerName, String actualPlayer, int currentTime){
        synchronized (controller){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        synchronized (clients.get(playerName)) {
                            clients.get(playerName).notifyTurn(actualPlayer, currentTime);
                        }
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }

    public synchronized void chooseCard(String playerName, int numCard){
        synchronized (controller){
            try {
                controller.setState(new TurnState());
                ((TurnState)controller.getState()).useCardRequest(controller.board.getPlayerByName(playerName), numCard);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    public synchronized void endTurn(String playerName){
        synchronized (controller){
            try {
                controller.cancelTimer();
                controller.setState(new TurnState());
                ((TurnState)controller.getState()).passToNextTurn(controller.board.getPlayerByName(playerName));
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    public synchronized void responseToolCard(String playerName, int nCard, String message){
        synchronized (controller){
            try {
                controller.setState(new TurnState());
                ((TurnState)controller.getState()).useCard(controller.board.getPlayerByName(playerName), nCard, message.replace("-", " "));
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    public synchronized void sendCardParameters(String playerName, String requestParameters){
        synchronized (controller){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        synchronized (clients.get(playerName)) {
                            clients.get(playerName).receiveCardParameters(requestParameters.split(" ")[1]);
                        }
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }

    public synchronized void sendActionError(String playerName, String error){
        synchronized (controller){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        synchronized (clients.get(playerName)) {
                            clients.get(playerName).viewError(error.split(" ")[1]);
                        }
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }

    public synchronized void sendNotYourTurn(String playerName){
        synchronized (controller){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        synchronized (clients.get(playerName)) {
                            clients.get(playerName).notYourTurn();
                        }
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }

    public synchronized void notifyOnWinBeforeEndGame(String playerName){
        synchronized (controller){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        synchronized (clients.get(playerName)) {
                            clients.get(playerName).notYourTurn();
                        }
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }

    public synchronized void receiveRanking(String playerName, String score){
        synchronized (controller){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    synchronized (clients.get(playerName)) {
                        try {
                            clients.get(playerName).receiveRanking(score);
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).start();
        }
    }

}
