package repolezanettiperuzzi.model.actions;

import repolezanettiperuzzi.model.GameBoard;
import repolezanettiperuzzi.model.Player;

/**
 * Classe che rappresenta l'inizio del turno
 * @author Giampiero Repole
 */
public class BeginTurn {

    private static int currentTurn = 0;

    private static int numPlayedTurn = 0;

    private static int currentPlayer = 0;

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

        /*if(currentTurn==0) {


            //se nessuno ha ancora giocato il proprio turno allora prendo l'indice del primo giocatore di questo round
            if (numPlayedTurn == 0) {

                currentPlayer = BeginRound.getIndex();

            }

        }*/


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

        return player.getTurn() == currentTurn ;

    }

    /**
     *
     * @return il turno corrente del player
     */

    public static int getCurrentPlayer(){

        return currentPlayer;
    }

    /**
     *
     * @return il turno corrente di gioco
     */
    public static int getCurrentTurn(){

        return currentTurn;

    }

    /**
     * azzera il turno corrente
     */
    public static void resetCurrentTurn(){

        currentTurn=0;

    }

    /**
     *
     * @return il numero di player che hanno giocato il proprio turno
     */
    public static int getNumPlayedTurn(){

        return numPlayedTurn;

    }

    /**
     * aggiorna i parametri per il turno successivo del player passato
     * @param board game board
     * @param player player a cui aggiornare i parametri per il turno successivo
     */
    public static void nextTurnParameters(GameBoard board,Player player){

        if(currentTurn==0) {

            //se l'indice del giocatore è arrivato all'ultimo elemento dell'arraylist di giocatori e non tutti i giocatori hanno giocato il loro turno inizializzo l'indice a zero
            if ((currentPlayer == board.getNPlayers() - 1) && numPlayedTurn < board.getNPlayers() - 1) {

                currentPlayer = 0;

            } else {

                currentPlayer++;

            }

            incrTurn(player);

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

    /**
     * azzera il numero di player che ha giocato il proprio turno
     */
    public static void resetNumPlayedTurn() {

        numPlayedTurn=0;

    }

    /**
     * azzera l'indice che indica il player corrente
     */
    public static void resetCurrentPlayer(){

        currentPlayer=BeginRound.getIndex();
    }



}
