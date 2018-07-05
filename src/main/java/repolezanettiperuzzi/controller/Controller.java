package repolezanettiperuzzi.controller;

import org.json.simple.parser.ParseException;
import repolezanettiperuzzi.model.GameBoard;
import repolezanettiperuzzi.model.Player;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

/**
 * Classe che rappresenta il controller all'inizio del round
 * @author Giampiero Repole
 */
public class Controller {

    //view
    protected ArrayList<Player> view;
    //model
    protected GameBoard board;

    protected ControllerState currentState;

    private boolean isTimerOn;

    private Timer timer;
    private ControllerTimer task;

    private HandlerControllerRMI handlerRMI;

    /**
     * Costruttore
     * @param view Lista di player
     * @param board Game board
     */
    public Controller(List<Player> view, GameBoard board){

       this.currentState = null;
       this.view = (ArrayList<Player>)view;
       this.board = board;
       this.isTimerOn = false;
       this.handlerRMI = null;

    }

    /**
     * Inizializza al nuovo stato del controller
     * @param nextState Nuovo stato del controller
     * @throws IOException Fallimento o interruzione delle operazioni I/O
     * @throws ParseException Errore durante l'analisi
     */
    public synchronized void setState(ControllerState nextState) throws IOException, ParseException {

        this.currentState=nextState;
        this.currentAction();

    }

    public synchronized void setStateNoDoAction(ControllerState nextState){
        this.currentState=nextState;
        if(nextState instanceof TurnState){
            ((TurnState)this.currentState).setController(this);
        }
    }

    /**
     *
     * @return Lo stato corrente del controller
     */
    public synchronized ControllerState getState(){
        return this.currentState;

    }

    /**
     *
     * @return True se il timer è attivo sennò false
     */
    public boolean isTimerOn(){
        return this.isTimerOn;
    }

    /**
     * Inizializza il timer
     * @param timerType Tipo di timer
     * @throws IOException Fallimento o interruzione delle operazioni I/O
     */
    public synchronized void setTimer(String timerType) throws IOException {

        this.task = new ControllerTimer(timerType,this);
        this.timer = this.task.getTimer();
        timer.schedule(this.task,0,1000);
        this.isTimerOn = true;

    }

    /**
     * Cancella il Timer
     */
    public synchronized void cancelTimer(){

        this.timer.cancel();
        this.timer.purge();
        this.isTimerOn = false;
        
    }

    /**
     * Inizializza se il timer è on (true) oppure no (false)
     * @param condition True o false
     */
    public void setIsTimerOn(boolean condition){

        this.isTimerOn=condition;

    }

    /**
     *
     * @return Il timer corrente
     */
    public synchronized int getCurrentTime(){
        if(isTimerOn){
            return this.task.getCurrentTime();
        } else {
            return -1;
        }
    }

    /**
     * Fa l'azione dello stato corrente
     * @throws IOException Fallimento o interruzione delle operazioni I/O
     * @throws ParseException Errore durante l'analisi
     */
    //this method do the action of the current state
    private void currentAction() throws IOException, ParseException {

        this.currentState.doAction(this);

    }

    /**
     * inizializza l'handler RMI
     * @param handlerRMI Controller handler RMI
     */
    public void setHandlerRMI(HandlerControllerRMI handlerRMI){
        this.handlerRMI = handlerRMI;
    }

    /**
     *
     * @return L'handler controller RMI
     */
    public HandlerControllerRMI getHandlerRMI(){
        return this.handlerRMI;
    }

    /**
     * Comunicazione della disconnessione
     * @param playerID Stringa che rappresenta il nome del player
     * @throws IOException Fallimento o interruzione delle operazioni I/O
     */
    public void notifyExitToClient(String playerID) throws IOException {
        Player player = board.getPlayerByName(playerID);
        if(player.getConnection().equals("Socket")){
            Socket socket = null;
            try {
                socket = new Socket(player.getAddress(), player.getPort());
            } catch (IOException e) {
                e.printStackTrace();
            }
            HandlerControllerSocket handlerControllerSocket = new HandlerControllerSocket(this, socket);
            handlerControllerSocket.notifyExitToClient();
        } else if(board.getPlayerByName(playerID).getConnection().equals("RMI")){

        }
    }

    /**
     * inizializza a offline lo stato del player passato
     * @param playerName Stringa che rappresnta il nome del player
     */
    public void setLiveStatusOffline(String playerName){

        for(int i = 0; i<this.board.getNPlayers(); i++){
            if(this.board.getPlayer(i).getName().equals(playerName)){
                this.board.getPlayer(i).setLiveStatus(false);
                break;
            }
        }

    }


}
