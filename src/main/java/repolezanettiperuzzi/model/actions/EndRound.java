package repolezanettiperuzzi.model.actions;

import repolezanettiperuzzi.model.GameBoard;

public class EndRound extends Action{

    public void doAction(GameBoard board){
        //resetto i turni di tutti i giocatori
        for(int i = 0; i<board.getNPlayers(); i++){
            board.getPlayer(i).resetTurn();
        }

        //sposto i dadi rimasti nel draft nel roundtrack e poi pulisco il draft
        board.addDiceToRoundTrack();

        BeginRound.incrIndex();

        if(BeginRound.getIndex()>board.getNPlayers()-1){

            BeginRound.resetIndex();
        }

    }

}
