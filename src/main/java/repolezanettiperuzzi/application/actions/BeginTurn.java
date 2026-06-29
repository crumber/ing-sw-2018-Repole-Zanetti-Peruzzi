package repolezanettiperuzzi.application.actions;

import repolezanettiperuzzi.model.GameBoard;
import repolezanettiperuzzi.model.Player;

/**
 * Classe che rappresenta l'inizio del turno
 * @author Giampiero Repole
 */
public class BeginTurn {

    private static final TurnTracker DEFAULT_TURN_TRACKER = new TurnTracker();

    private final TurnTracker turnTracker;

    private GameBoard board;

    private Player player;

    public BeginTurn(){
        this(DEFAULT_TURN_TRACKER);
    }

    public BeginTurn(TurnTracker turnTracker){
        if(turnTracker==null){
            throw new IllegalArgumentException("turnTracker cannot be null");
        }

        this.turnTracker=turnTracker;
    }

    /**
     * inizializza i parametri
     * @param player player
     * @param board game board
     */

    public void doAction(Player player, GameBoard board){

        this.board=board;

        this.player=player;

    }

    /**
     * incrementa il turno del player passato
     * @param player player a cui bisogna incrementare il turno
     */
    public static void incrTurn(Player player){

        player.incrTurn();

    }

    /**
     * controlla che il turno del player sia uguale al turno corrente
     * @param player player a cui bisogna controllare il turno con il turno corrente
     * @return vero se turno corrente uguale a quello del player passato
     */
    public static boolean controlTurn(Player player){

        return DEFAULT_TURN_TRACKER.controlTurn(player);

    }

    /**
     * controlla che il turno del player sia uguale al turno corrente della sessione
     * @param player player a cui bisogna controllare il turno con il turno corrente
     * @return vero se turno corrente uguale a quello del player passato
     */
    public boolean controlSessionTurn(Player player){

        return turnTracker.controlTurn(player);

    }

    /**
     *
     * @return il turno corrente del player
     */

    public static int getCurrentPlayer(){

        return DEFAULT_TURN_TRACKER.getCurrentPlayer();
    }

    /**
     *
     * @return il player corrente della sessione
     */
    public int getSessionCurrentPlayer(){

        return turnTracker.getCurrentPlayer();
    }

    /**
     *
     * @return il turno corrente di gioco
     */
    public static int getCurrentTurn(){

        return DEFAULT_TURN_TRACKER.getCurrentTurn();

    }

    /**
     *
     * @return il turno corrente di gioco della sessione
     */
    public int getSessionCurrentTurn(){

        return turnTracker.getCurrentTurn();

    }

    /**
     * azzera il turno corrente
     */
    public static void resetCurrentTurn(){

        DEFAULT_TURN_TRACKER.resetCurrentTurn();

    }

    /**
     * azzera il turno corrente della sessione
     */
    public void resetSessionCurrentTurn(){

        turnTracker.resetCurrentTurn();

    }

    /**
     *
     * @return il numero di player che hanno giocato il proprio turno
     */
    public static int getNumPlayedTurn(){

        return DEFAULT_TURN_TRACKER.getNumPlayedTurn();

    }

    /**
     *
     * @return il numero di player che hanno giocato il proprio turno nella sessione
     */
    public int getSessionNumPlayedTurn(){

        return turnTracker.getNumPlayedTurn();

    }

    /**
     * aggiorna i parametri per il turno successivo del player passato
     * @param board game board
     * @param player player a cui aggiornare i parametri per il turno successivo
     */
    public static void nextTurnParameters(GameBoard board,Player player){

        DEFAULT_TURN_TRACKER.nextTurnParameters(board,player);
    }

    /**
     * aggiorna i parametri per il turno successivo del player passato nella sessione
     * @param board game board
     * @param player player a cui aggiornare i parametri per il turno successivo
     */
    public void nextSessionTurnParameters(GameBoard board,Player player){

        turnTracker.nextTurnParameters(board,player);
    }

    /**
     * azzera il numero di player che ha giocato il proprio turno
     */
    public static void resetNumPlayedTurn() {

        DEFAULT_TURN_TRACKER.resetNumPlayedTurn();

    }

    /**
     * azzera il numero di player che ha giocato il proprio turno nella sessione
     */
    public void resetSessionNumPlayedTurn() {

        turnTracker.resetNumPlayedTurn();

    }

    /**
     * azzera l'indice che indica il player corrente
     */
    public static void resetCurrentPlayer(){

        DEFAULT_TURN_TRACKER.resetCurrentPlayer(BeginRound.getIndex());
    }

    /**
     * azzera l'indice che indica il player corrente nella sessione
     * @param firstPlayerIndex indice del primo player del round
     */
    public void resetSessionCurrentPlayer(int firstPlayerIndex){

        turnTracker.resetCurrentPlayer(firstPlayerIndex);
    }



}
