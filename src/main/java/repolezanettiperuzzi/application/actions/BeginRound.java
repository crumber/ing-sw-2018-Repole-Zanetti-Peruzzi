package repolezanettiperuzzi.application.actions;

import repolezanettiperuzzi.model.GameBoard;

/**
 * Classe che rappresenta l'inizio del round
 * @author Giampiero Repole
 */
public class BeginRound {

    private static final RoundTracker roundTracker = new RoundTracker();

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

        roundTracker.increaseIndex();

    }

    /**
     *
     * @return l'intero che indica chi è il primo player
     */
    public static int getIndex(){

        return roundTracker.getIndex();

    }

    /**
     * razzera l'indice  che indica chi è il primo player
     */
    public static void resetIndex(){

        roundTracker.resetIndex();

    }

    /**
     * aumenta il round di gioco
     */
    public static void increaseRound(){

        roundTracker.increaseRound();

    }

    /**
     * Azzera il round di gioco.
     */
    public static void resetRound(){

        roundTracker.resetRound();

    }

    /**
     *
     * @return il round di gioco
     */
    public static int getRound(){

        return roundTracker.getRound();

    }

}
