package repolezanettiperuzzi.application.actions;

import repolezanettiperuzzi.model.GameBoard;

/**
 * Classe che rappresenta la fine del round
 * @author Giampiero Repole
 */
public class EndRound{

    private final BeginRound beginRound;

    public EndRound(){
        this(new BeginRound());
    }

    public EndRound(RoundTracker roundTracker){
        this(new BeginRound(roundTracker));
    }

    private EndRound(BeginRound beginRound){
        this.beginRound=beginRound;
    }

    /**
     * azzera i turni di tutti i player, sposta i dadi rimasti sul draft nel roundtrack e imposta i parametri per il turno successivo
     * @param board Game board
     */
    public void doAction(GameBoard board){

        //reset turn of all players
        for(int i = 0; i<board.getNPlayers(); i++){
            board.getPlayer(i).resetTurn();
        }

        //move dice from the Draft to the RoundTrack and clear the Draft
        board.addDiceToRoundTrack();

        //increase index of first player for the next round
        beginRound.increaseIndex();

        //if the index is greater then the number of players reset the index to the first player of the ArrayList
        if (beginRound.getIndex()>board.getNPlayers()-1) {

            beginRound.resetIndex();

        }

    }

}
