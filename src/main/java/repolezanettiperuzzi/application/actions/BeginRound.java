package repolezanettiperuzzi.application.actions;

import repolezanettiperuzzi.model.GameBoard;

/**
 * Classe che rappresenta l'inizio del round
 * @author Giampiero Repole
 */
public class BeginRound {

    private final RoundTracker roundTracker;

    public BeginRound(){
        this(new RoundTracker());
    }

    public BeginRound(RoundTracker roundTracker){
        if(roundTracker==null){
            throw new IllegalArgumentException("roundTracker cannot be null");
        }

        this.roundTracker=roundTracker;
    }

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
     * aumenta l'indice che indica chi e' il primo player della sessione
     */
    public void increaseIndex(){

        roundTracker.increaseIndex();

    }

    /**
     *
     * @return l'intero che indica chi e' il primo player della sessione
     */
    public int getIndex(){

        return roundTracker.getIndex();

    }

    /**
     * razzera l'indice che indica chi e' il primo player della sessione
     */
    public void resetIndex(){

        roundTracker.resetIndex();

    }

    /**
     * aumenta il round di gioco della sessione
     */
    public void increaseRound(){

        roundTracker.increaseRound();

    }

    /**
     * Azzera il round di gioco della sessione.
     */
    public void resetRound(){

        roundTracker.resetRound();

    }

    /**
     *
     * @return il round di gioco della sessione
     */
    public int getRound(){

        return roundTracker.getRound();

    }

}
