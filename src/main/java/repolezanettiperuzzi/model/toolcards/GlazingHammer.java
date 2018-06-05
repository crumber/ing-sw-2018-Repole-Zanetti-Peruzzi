package repolezanettiperuzzi.model.toolcards;

import repolezanettiperuzzi.model.GameBoard;
import repolezanettiperuzzi.model.Player;

import java.util.List;

public class GlazingHammer extends ToolCard {

    public GlazingHammer(){

        id=7;

    }

    //control that is second turn of round and that player don't insert die in this turn
    @Override
    public int check(GameBoard board, Player player, List<Integer> parameterForCard) {

        if (player.getTurn()!=2) {

            resultOfAction=-12;

        }else if (player.getInsertDieInThisTurn()) {

            resultOfAction=-13;

        }else {

            resultOfAction=1;

        }

        return resultOfAction;
    }

    //roll all dice on draft
    @Override
    public void effect(GameBoard board, Player player, List<Integer> parameterForCard){

        int numDiceDraft= board.getSizeDraft();
        for(int i=0;i<numDiceDraft;i++){

            board.getDieDraft(i).rollDie();

        }
    }
}
