package repolezanettiperuzzi.application.actions;

/**
 * Tracks round number and first-player rotation for one game session.
 */
public class RoundTracker {

    private int firstPlayerIndex = 0;
    private int round = 0;

    /**
     * Aumenta l'indice che indica chi e' il primo player.
     */
    public void increaseIndex(){
        firstPlayerIndex++;
    }

    /**
     * @return Indice del primo player del round
     */
    public int getIndex(){
        return firstPlayerIndex;
    }

    /**
     * Azzera l'indice che indica chi e' il primo player.
     */
    public void resetIndex(){
        firstPlayerIndex=0;
    }

    /**
     * Aumenta il round di gioco.
     */
    public void increaseRound(){
        round++;
    }

    /**
     * Azzera il round di gioco.
     */
    public void resetRound(){
        round=0;
    }

    /**
     * @return Round di gioco
     */
    public int getRound(){
        return round;
    }
}
