package repolezanettiperuzzi.application.actions;

import repolezanettiperuzzi.model.GameBoard;
import repolezanettiperuzzi.model.Player;

/**
 * Classe che rappresenta l'inizio del turno
 * @author Giampiero Repole
 */
public class BeginTurn {

    private static final TurnTracker turnTracker = new TurnTracker();

    private GameBoard board;

    private Player player;

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

        return turnTracker.controlTurn(player);

    }

    /**
     *
     * @return il turno corrente del player
     */

    public static int getCurrentPlayer(){

        return turnTracker.getCurrentPlayer();
    }

    /**
     *
     * @return il turno corrente di gioco
     */
    public static int getCurrentTurn(){

        return turnTracker.getCurrentTurn();

    }

    /**
     * azzera il turno corrente
     */
    public static void resetCurrentTurn(){

        turnTracker.resetCurrentTurn();

    }

    /**
     *
     * @return il numero di player che hanno giocato il proprio turno
     */
    public static int getNumPlayedTurn(){

        return turnTracker.getNumPlayedTurn();

    }

    /**
     * aggiorna i parametri per il turno successivo del player passato
     * @param board game board
     * @param player player a cui aggiornare i parametri per il turno successivo
     */
    public static void nextTurnParameters(GameBoard board,Player player){

        turnTracker.nextTurnParameters(board,player);
    }

    /**
     * azzera il numero di player che ha giocato il proprio turno
     */
    public static void resetNumPlayedTurn() {

        turnTracker.resetNumPlayedTurn();

    }

    /**
     * azzera l'indice che indica il player corrente
     */
    public static void resetCurrentPlayer(){

        turnTracker.resetCurrentPlayer(BeginRound.getIndex());
    }



}
