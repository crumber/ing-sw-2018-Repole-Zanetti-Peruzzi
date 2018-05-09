package repolezanettiperuzzi.model.actions;

import repolezanettiperuzzi.model.GameBoard;
import repolezanettiperuzzi.model.RealPlayer;

public class CheckCostToolCardAction {

    public int checkCostToolCard(GameBoard board, RealPlayer player, int whichCard ){

        int resultOfCheck;

        if(player.getFlavorTokens()<board.getCostToolCard(whichCard)){

            resultOfCheck=-8;

        }else{

            resultOfCheck=1;

        }

        return resultOfCheck;
    }
}
