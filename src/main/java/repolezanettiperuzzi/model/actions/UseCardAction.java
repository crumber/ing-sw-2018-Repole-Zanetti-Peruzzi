package repolezanettiperuzzi.model.actions;

import com.sun.org.apache.xpath.internal.operations.String;
import repolezanettiperuzzi.model.GameBoard;
import repolezanettiperuzzi.model.RealPlayer;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UseCardAction {

    public List<Object> doAction(RealPlayer player, GameBoard board, int whichToolCard, List<Object> parameterForCard){

        List<Object> resultOfAction = new ArrayList<>();

        if(player.getFlavorTokens()>=board.getCostToolCard(whichToolCard)){

            resultOfAction=board.getToolCards(whichToolCard).check(parameterForCard);

            //if check is correct, do active action, reduce player's tokens
            if((Integer)resultOfAction.get(0)==1){

                board.getToolCards(whichToolCard).effect(parameterForCard);

                player.reduceFlavorTokens(board.getCostToolCard(whichToolCard));

                //increment tool card cost if it's cost is 1
                if(board.getCostToolCard(whichToolCard)==1) {

                    board.setCostToolCard(whichToolCard);

                }
            }

        }
        else{

            resultOfAction.add(-1);
            resultOfAction.add("You don't have enough flavor tokens!");

        }

        return resultOfAction;
    }
}
