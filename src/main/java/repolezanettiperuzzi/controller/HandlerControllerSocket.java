package repolezanettiperuzzi.controller;

import org.json.simple.parser.ParseException;
import repolezanettiperuzzi.model.Player;
import repolezanettiperuzzi.model.Window;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Classe che rappresenta il server socket che risiede sul server
 * @author Giampiero Repole
 * @author Andrea Zanetti
 */
//questo e' il nostro server socket che risiede sul server
public class HandlerControllerSocket implements Runnable{
    private Socket socket;
    private BufferedReader in;
    private InetAddress addr;
    private int port;
    private Controller controller;
    private String playerID;
    private String action;
    private String[] param;

    /**
     * Costruttore
     * @param controller Controller
     * @param socket Socket
     * @throws IOException Fallimento o interruzione delle operazioni I/O
     */
    //struttura messaggo: playerID action parameters
    public HandlerControllerSocket(Controller controller, Socket socket) throws IOException {
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.socket = socket;
        this.addr = socket.getInetAddress();
        this.controller = controller;
    }

    /**
     * Invoca il costruttore della classe
     */
    @Override
    public void run() {
        try {
            synchronized (controller) {

                handleMessage();

            }
        } catch (IOException | ParseException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Prende messaggio in arrivo e chiama il metodo giusto
     * @throws IOException Fallimento o interruzione delle operazioni I/O
     * @throws ParseException Errore durante l'analisi
     * @throws InterruptedException Interruzione thread
     */
    public void handleMessage() throws IOException, ParseException, InterruptedException {

        String[] line = in.readLine().split(" ");

        this.playerID = line[0];
        this.action = line[1];
        this.param = new String[line.length-2];

        for(int i = 0; i<param.length; i++){
            param[i] = line[i+2];
        }

        switch(action) {
            case "init":
                controller.setState(new SetConnectionState());
                String result = ((SetConnectionState)controller.getState()).initializePlayer(playerID, param[0], addr, Integer.parseInt(param[3]), param[1], param[2]);

                switch (result){
                    case "registered": {
                        Player player = controller.board.getPlayerByName(playerID);
                        ((SetConnectionState) controller.getState()).notifyOnRegister(controller, param[1], param[2], player.getAddress(), player.getPort());
                        break;
                    }
                    case "stealAccount": {
                        Player player = controller.board.getPlayerByName(playerID);
                        ((SetConnectionState) controller.getState()).notifyOnStealAccount(controller, player.getConnection(), player.getUI(), addr.toString().substring(1), Integer.parseInt(param[3])); //non uso i dati dall'oggetto player perche' non sono stati registrati nell'oggetto dato che il login e' invalido
                        break;
                    }
                    case "wrongPassword": {
                        Player player = controller.board.getPlayerByName(playerID);
                        ((SetConnectionState) controller.getState()).notifyOnWrongPassword(controller, player.getConnection(), player.getUI(), addr.toString().substring(1), Integer.parseInt(param[3])); //non uso i dati dall'oggetto player perche' non sono stati registrati nell'oggetto dato che il login e' invalido
                        break;
                    }
                    case "reconnect": {
                        Player player = controller.board.getPlayerByName(playerID);
                        ((SetConnectionState) controller.getState()).notifyOnReconnect(controller, player.getConnection(), player.getUI(), player.getAddress(), player.getPort(), player.getName());
                        break;
                    }
                    case "gameAlreadyStarted": {
                        ((SetConnectionState) controller.getState()).notifyOnGameAlreadyStarted(controller, param[1], param[2], addr.toString().substring(1), Integer.parseInt(param[3]));
                        break;
                    }
                    case "already4Players": {
                        ((SetConnectionState) controller.getState()).notifyOnAlready4Players(controller, param[1], param[2], addr.toString().substring(1), Integer.parseInt(param[3]));
                        break;
                    }
                }
                break;

            case "waitingOk": //il client ha avviato la sua view della waiting room
                controller.setState(new SetConnectionState());
                ((SetConnectionState)controller.getState()).waitingRoomLoaded(playerID);
                ((SetConnectionState)controller.getState()).notifyOnUpdatedPlayer();
                break;
            case "chooseWindowOk":
                controller.setState(new FetchState());
                Player playerName = controller.board.getPlayerByName(playerID);
                ((FetchState)controller.getState()).sendWindows(playerName);
                break;
            case "chosenWindow":
                controller.setState(new FetchState());
                ((FetchState)controller.getState()).setChosenWindow(controller.board.getPlayerByName(playerID), param[0].replace("-"," "));
                break;
            case "gameOk":
                controller.setState(new FetchState());
                ((FetchState)controller.getState()).readyToPlay(playerID);
                break;
            case "insertDie":
                controller.setState(new TurnState());
                ((TurnState)controller.getState()).insertDie(controller.board.getPlayerByName(playerID) , param[0]+" "+param[1]+" "+param[2]);
                break;
            case "responseToolCard":
                controller.setState(new TurnState());
                ((TurnState)controller.getState()).useCard(controller.board.getPlayerByName(playerID), Integer.parseInt(param[0]), param[1].replace("-", " "));
                break;
            case "chooseCard":
                controller.setState(new TurnState());
                ((TurnState)controller.getState()).useCardRequest(controller.board.getPlayerByName(playerID), Integer.parseInt(param[0]));
                break;
            case "endTurn":
                controller.cancelTimer();
                controller.setState(new TurnState());
                ((TurnState)controller.getState()).passToNextTurn(controller.board.getPlayerByName(playerID));
                break;
            case "exit":
                controller.notifyExitToClient(playerID);
                switch(param[0]){
                    case "waitingRoom":
                        controller.setState(new SetConnectionState());
                        controller.setLiveStatusOffline(playerID);
                        ((SetConnectionState)controller.getState()).notifyOnUpdatedPlayer();
                        break;
                    case "chooseWindow":
                        controller.setLiveStatusOffline(playerID);
                        break;
                    case "game":
                        controller.setState(new TurnState());
                        controller.setLiveStatusOffline(playerID);
                        ((TurnState)controller.getState()).notifyStatusToPlayers();
                        break;
                }
                break;


        }
    }

    /**
     * Notica la registrazione
     * @param connection Connessione
     * @param UI Rappresenta l'interfaccia grafica
     * @throws IOException Fallimento o interruzione delle operazioni I/O
     */
    public void notifyOnRegister(String connection, String UI) throws IOException {
        PrintWriter out = new PrintWriter(this.socket.getOutputStream(), true);
        out.println("registered "+connection+" "+UI);
        out.close();
        this.socket.close();
    }

    /**
     * Notifica l'update del player
     * @param timer Timer
     * @throws IOException Fallimento o interruzione delle operazioni I/O
     */
    public void notifyOnUpdatedPlayer(int timer) throws IOException {
        String playersID = "";

        for(int i = 0; i<controller.board.getNPlayers(); i++){
            if(controller.board.getPlayer(i).getLiveStatus()) {
                Player player = controller.board.getPlayers().get(i);
                playersID = playersID + player.getName() + " ";
            }
        }

        System.out.println("Giocatori stampati: "+playersID);

        PrintWriter out = new PrintWriter(this.socket.getOutputStream(), true);
        System.out.println("Invio messaggio\n");
        out.println("updatedplayers "+timer+" "+playersID);
        out.close();
        this.socket.close();

    }

    /**
     * Notifica l'uscita del client
     * @throws IOException Fallimento o interruzione delle operazioni I/O
     */
    public void notifyExitToClient() throws IOException {
        PrintWriter out = new PrintWriter(this.socket.getOutputStream(), true);
        out.println("exit");
        out.close();
        this.socket.close();
    }

    /**
     * Notifica la riconnessione
     * @param playerID Nome player
     * @param connection Connessione
     * @param UI Interfaccia grafica
     * @throws IOException Fallimento o interruzione delle operazioni I/O
     */
    public void notifyOnReconnect(String playerID, String connection, String UI) throws IOException {
        switch(controller.board.getPlayerByName(playerID).getLastScene()){
            case "waitingRoom":
                notifyOnRegister(connection, UI);
                break;
            case "chooseWindowRoom":
                notifyOnChooseWindow();
                break;
            case "game":
                notifyOnStartGame();
                controller.board.getPlayerByName(playerID).setLiveStatus(true);
                try {
                    controller.setState(new TurnState());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                ((TurnState)controller.getState()).notifyStatusToPlayers();
                ((TurnState)controller.getState()).notifyPlayerTurn();
                break;
        }
    }

    /**
     * Notifica che hai sbagliato i parametri per riconnessione
     * @throws IOException Fallimento o interruzione delle operazioni I/O
     */
    public void notifyOnStealAccount() throws IOException {
        PrintWriter out = new PrintWriter(this.socket.getOutputStream(), true);
        out.println("notregistered alreadyonline");
        out.close();
        this.socket.close();
    }

    /**
     * Notifica che hai sbagliato password per la riconnessione
     * @throws IOException Fallimento o interruzione delle operazioni I/O
     */
    public void notifyOnWrongPassword() throws IOException {
        PrintWriter out = new PrintWriter(this.socket.getOutputStream(), true);
        out.println("notregistered wrongpwd");
        out.close();
        this.socket.close();
    }

    /**
     * Notifica che il gioco è gia iniziato
     * @throws IOException Fallimento o interruzione delle operazioni I/O
     */
    public void notifyOnGameAlreadyStarted() throws IOException {
        PrintWriter out = new PrintWriter(this.socket.getOutputStream(), true);
        out.println("notregistered gameAlreadyStarted");
        out.close();
        this.socket.close();
    }

    /**
     * Notifica che il numero massimo di giocatori è stato raggiunto
     * @throws IOException Fallimento o interruzione delle operazioni I/O
     */
    public void notifyOnAlready4Players() throws IOException {
        PrintWriter out = new PrintWriter(this.socket.getOutputStream(), true);
        out.println("notregistered already4Players");
        out.close();
        this.socket.close();
    }

    /**
     * Notifica il cambio di scelta della window
     * @throws IOException Fallimento o interruzione delle operazioni I/O
     */
    public void notifyOnChooseWindow() throws IOException {

        PrintWriter out = new PrintWriter(this.socket.getOutputStream(), true);
        out.println("changeView chooseWindow");
        out.close();
        this.socket.close();

    }

    /**
     * Mostra la window scelta
     * @param message Messaggio da mostrare
     * @param currentTime Timer corrente
     * @throws IOException Fallimento o interruzione delle operazioni I/O
     */
    public void showChosenWindow(String message, int currentTime) throws IOException {
        PrintWriter out = new PrintWriter(this.socket.getOutputStream(), true);
        out.println("showWindow "+ message + currentTime);
        out.close();
        this.socket.close();
    }

    /**
     * Invia le finestre da chiedere al client
     * @param message Messaggio da ritornare
     * @param currentTime Timer corrente
     * @throws IOException Fallimento o interruzione delle operazioni I/O
     */
    public void askWindow(String message,int currentTime) throws IOException {

        PrintWriter out = new PrintWriter(this.socket.getOutputStream(), true);
        out.println("chooseWindow "+ message +currentTime);
        out.close();
        this.socket.close();
    }

    /**
     * Notifica l'inizio del round
     * @throws IOException Fallimento o interruzione delle operazioni I/O
     */
    public void notifyBeginRound() throws IOException {

        PrintWriter out = new PrintWriter(this.socket.getOutputStream(), true);
        out.println("beginRound");
        out.close();
        this.socket.close();

    }

    /**
     *
     * @return Stringa che pone una domanda su cosa vuole fare al client
     * @throws IOException Fallimento o interruzione delle operazioni I/O
     */
    public String askAction() throws IOException {

        PrintWriter out = new PrintWriter(this.socket.getOutputStream(), true);
        out.println("What action would you like to do?\n");
        out.close();
        //this.in.close();
        //this.socket.close();
        return this.in.readLine();


    }

    /**
     * Notifica che è iniziato il gioco
     * @throws IOException Fallimento o interruzione delle operazioni I/O
     */
    public void notifyOnStartGame() throws IOException {

        PrintWriter out = new PrintWriter(this.socket.getOutputStream(), true);
        out.println("startGame");
        out.close();
        this.socket.close();

    }

    /**
     * Notifica che hai vinto nella scelta delle window
     * @throws IOException Fallimento o interruzione delle operazioni I/O
     */
    public void notifyWinOnChooseWindow() throws IOException {

        PrintWriter out = new PrintWriter(this.socket.getOutputStream(), true);
        out.println("winChooseWindow");
        out.close();
        this.socket.close();

    }

    public void notifyWinBeforeEndGame() throws IOException {

        PrintWriter out = new PrintWriter(this.socket.getOutputStream(), true);
        out.println("winBeforeEnd");
        out.close();
        this.socket.close();

    }

    /**
     * Notifica l'inizio del turno
     * @param actualPlayer Player attuale
     * @param currentTime Timer corrente
     * @throws IOException Fallimento o interruzione delle operazioni I/O
     */
    public void notifyOnBeginTurn(String actualPlayer, int currentTime) throws IOException {

        PrintWriter out = new PrintWriter(this.socket.getOutputStream(),true);
        out.println("turn "+actualPlayer+" "+currentTime);
        out.close();
        this.socket.close();

    }

    /**
     * Invio messaggio per parametri carte
     * @param message Messaggio per i parametri delle carte
     * @throws IOException Fallimento o interruzione delle operazioni I/O
     */
    public void sendParametersForToolCard(String message) throws IOException {

        System.out.println(controller.board.getToolCard(0).getTitle()+" "+controller.board.getToolCard(0).getId()+" "+controller.board.getToolCard(1).getTitle()+" "+controller.board.getToolCard(1).getId()+" "+controller.board.getToolCard(2).getTitle()+" "+controller.board.getToolCard(2).getId());
        PrintWriter out = new PrintWriter(this.socket.getOutputStream(),true);
        out.println(message);
        out.close();
        this.socket.close();

    }

    /**
     * Invia codice d'errore
     * @param error Codice d'errore
     * @throws IOException Fallimento o interruzione delle operazioni I/O
     */
    public void sendActionError(String error) throws IOException {

        PrintWriter out = new PrintWriter(this.socket.getOutputStream(),true);
        out.println(error);
        out.close();

        this.socket.close();

    }

    /**
     * Invia l'aggiornamento della view
     * @param update Aggiornamento
     * @throws IOException Fallimento o interruzione delle operazioni I/O
     */
    public void sendUpdateView(String update) throws IOException {

        PrintWriter out = new PrintWriter(this.socket.getOutputStream(),true);
        out.println("updateView "+update);
        out.close();
        this.socket.close();

    }

    /**
     * Invia che non è il tuo turno
     * @throws IOException Fallimento o interruzione delle operazioni I/O
     */
    public void sendNotYourTurn() throws IOException {

        PrintWriter out = new PrintWriter(this.socket.getOutputStream(), true);
        out.println("notYourTurn");
        out.close();
        this.socket.close();

    }

    /**
     * Invia che è finito il gioco
     * @throws IOException Fallimento o interruzione delle operazioni I/O
     */
    public void notifyOnEndGame(String message) throws IOException {

        PrintWriter out = new PrintWriter(this.socket.getOutputStream(),true);
        out.println("endGame "+message);
        System.out.println("endGame "+message);
        out.close();
        this.socket.close();

    }
}
