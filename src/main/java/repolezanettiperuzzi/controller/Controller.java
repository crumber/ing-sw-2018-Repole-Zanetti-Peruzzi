package repolezanettiperuzzi.controller;

import org.json.simple.parser.ParseException;
import repolezanettiperuzzi.application.actions.BeginRound;
import repolezanettiperuzzi.application.actions.BeginTurn;
import repolezanettiperuzzi.application.actions.EndRound;
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
    private final GameSession session;

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
       this.session = new GameSession();

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
     * @return La sessione di gioco associata al controller
     */
    public GameSession getSession(){
        return this.session;
    }

    /**
     *
     * @return Azione di inizio round collegata alla sessione
     */
    public BeginRound createBeginRoundAction(){
        return this.session.createBeginRound();
    }

    /**
     *
     * @return Azione di inizio turno collegata alla sessione
     */
    public BeginTurn createBeginTurnAction(){
        return this.session.createBeginTurn();
    }

    /**
     *
     * @return Azione di fine round collegata alla sessione
     */
    public EndRound createEndRoundAction(){
        return this.session.createEndRound();
    }

    /**
     *
     * @return Round corrente della sessione
     */
    public int getCurrentRound(){
        return this.session.getRoundTracker().getRound();
    }

    /**
     *
     * @return Indice del primo player del round corrente
     */
    public int getFirstPlayerIndex(){
        return this.session.getRoundTracker().getIndex();
    }

    /**
     *
     * @return Turno corrente della sessione
     */
    public int getCurrentTurn(){
        return this.session.getTurnTracker().getCurrentTurn();
    }

    /**
     *
     * @return Indice del player corrente della sessione
     */
    public int getCurrentPlayerIndex(){
        return this.session.getTurnTracker().getCurrentPlayer();
    }

    /**
     *
     * @return Numero di player che hanno giocato nel turno corrente della sessione
     */
    public int getNumPlayedTurn(){
        return this.session.getTurnTracker().getNumPlayedTurn();
    }

    /**
     *
     * @return Player corrente della sessione
     */
    public Player getCurrentPlayer(){
        return this.board.getPlayer(getCurrentPlayerIndex());
    }

    /**
     * Controlla che il turno del player sia quello corrente nella sessione.
     * @param player Player da controllare
     * @return Vero se il player puo' giocare nel turno corrente
     */
    public boolean isCurrentPlayerTurn(Player player){
        return this.session.getTurnTracker().controlTurn(player);
    }

    /**
     * Azzera il turno corrente della sessione.
     */
    public void resetCurrentTurn(){
        this.session.getTurnTracker().resetCurrentTurn();
    }

    /**
     * Azzera il numero di player che ha giocato nel turno corrente della sessione.
     */
    public void resetNumPlayedTurn(){
        this.session.getTurnTracker().resetNumPlayedTurn();
    }

    /**
     * Imposta il player corrente all'inizio del round della sessione.
     */
    public void resetCurrentPlayer(){
        this.session.getTurnTracker().resetCurrentPlayer(getFirstPlayerIndex());
    }

    /**
     * Aggiorna i parametri per il turno successivo nella sessione.
     * @param player Player che ha appena giocato
     */
    public void nextTurnParameters(Player player){
        this.session.getTurnTracker().nextTurnParameters(this.board,player);
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
        if(player.isSocketConnection()){
            Socket socket = null;
            try {
                socket = new Socket(player.getAddress(), player.getPort());
            } catch (IOException e) {
                e.printStackTrace();
            }
            HandlerControllerSocket handlerControllerSocket = new HandlerControllerSocket(this, socket);
            handlerControllerSocket.notifyExitToClient();
        } else if(player.isRmiConnection()){

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
