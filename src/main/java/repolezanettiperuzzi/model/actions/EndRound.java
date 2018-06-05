package repolezanettiperuzzi.model.actions;

import repolezanettiperuzzi.model.GameBoard;

public class EndRound extends Action{

    public void doAction(GameBoard board){

        //reset turn of all players
        for(int i = 0; i<board.getNPlayers(); i++){
            board.getPlayer(i).resetTurn();
        }

        //move dice from the Draft to the RoundTrack and clear the Draft
        board.addDiceToRoundTrack();

        //increase index of first player for the next round
        BeginRound.increaseIndex();

        //if the index is greater then the number of players reset the index to the first player of the ArrayList
        if (BeginRound.getIndex()>board.getNPlayers()-1) {

            BeginRound.resetIndex();

        }

    }

}
