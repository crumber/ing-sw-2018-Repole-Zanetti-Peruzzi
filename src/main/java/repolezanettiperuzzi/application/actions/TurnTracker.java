package repolezanettiperuzzi.application.actions;

import repolezanettiperuzzi.model.GameBoard;
import repolezanettiperuzzi.model.Player;

/**
 * Tracks turn order for one game session.
 */
public class TurnTracker {

    private int currentTurn = 0;
    private int numPlayedTurn = 0;
    private int currentPlayer = 0;

    /**
     * @return Indice del player corrente
     */
    public int getCurrentPlayer(){
        return currentPlayer;
    }

    /**
     * @return Turno corrente
     */
    public int getCurrentTurn(){
        return currentTurn;
    }

    /**
     * @return Numero di player che hanno giocato nel turno corrente
     */
    public int getNumPlayedTurn(){
        return numPlayedTurn;
    }

    /**
     * Controlla che il turno del player sia uguale al turno corrente
     * @param player Player a cui controllare il turno
     * @return Vero se il turno del player corrisponde al turno corrente
     */
    public boolean controlTurn(Player player){
        return player.getTurn() == currentTurn;
    }

    /**
     * Azzera il turno corrente.
     */
    public void resetCurrentTurn(){
        currentTurn=0;
    }

    /**
     * Azzera il numero di player che ha giocato il proprio turno.
     */
    public void resetNumPlayedTurn(){
        numPlayedTurn=0;
    }

    /**
     * Imposta il player corrente.
     * @param firstPlayerIndex Indice del primo player del round
     */
    public void resetCurrentPlayer(int firstPlayerIndex){
        currentPlayer=firstPlayerIndex;
    }

    /**
     * Aggiorna i parametri per il turno successivo.
     * @param board Game board
     * @param player Player che ha appena giocato
     */
    public void nextTurnParameters(GameBoard board, Player player){

        if(currentTurn==0) {

            if ((currentPlayer == board.getNPlayers() - 1) && numPlayedTurn < board.getNPlayers() - 1) {

                currentPlayer = 0;

            } else {

                currentPlayer++;

            }

            player.incrTurn();

            numPlayedTurn++;

            if (numPlayedTurn == board.getNPlayers()) {

                currentTurn++;
                resetNumPlayedTurn();
                currentPlayer--;

            }


        }else if(currentTurn==1){

            if(currentPlayer == 0 && numPlayedTurn < board.getNPlayers()-1){

                currentPlayer = board.getNPlayers() - 1;

            } else {

                currentPlayer--;

            }

            numPlayedTurn++;


        }
    }
}
