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

        /*
        inutile

        sposto i dadi rimasti nel draft nel roundtrack e poi pulisco il draft

        for(int i = 0; i<board.getDraftSize(); i++){
            board.setDieToRoundTrack(board.getRound(), i, board.getDieDraft(i));
        }
        board.resetDraft();
        */
    }

}
