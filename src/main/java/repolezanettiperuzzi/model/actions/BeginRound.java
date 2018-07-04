package repolezanettiperuzzi.model.actions;

import repolezanettiperuzzi.model.GameBoard;

/**
 * Classe che rappresenta l'inizio del round
 * @author Giampiero Repole
 */
public class BeginRound {

    private static int firstPlayerIndex = 0;

    private static int round = 0;

    /**
     * aumenta il round e pesca i dadi dalla dice bag e aggiorna il draft
     * @param board game board
     */
    public void doAction(GameBoard board) {

        increaseRound();

        //add die to draft based on number of players
        for (int i = 0; i<((board.getNPlayers()*2)+1); i++) {

            board.addDieToDraft(board.takeDieFromBag());

        }

    }

    /**
     * aumenta l'indice che indica chi è il primo player
     */
    public static void increaseIndex(){

        firstPlayerIndex++;

    }

    /**
     *
     * @return l'intero che indica chi è il primo player
     */
    public static int getIndex(){

        return firstPlayerIndex;

    }

    /**
     * razzera l'indice  che indica chi è il primo player
     */
    public static void resetIndex(){

        firstPlayerIndex=0;

    }

    /**
     * aumenta il round di gioco
     */
    public static void increaseRound(){

        round++;

    }

    /**
     *
     * @return il round di gioco
     */
    public static int getRound(){

        return round;

    }

}
