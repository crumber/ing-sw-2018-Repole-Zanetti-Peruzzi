package repolezanettiperuzzi.model.actions;

import repolezanettiperuzzi.model.GameBoard;
import repolezanettiperuzzi.model.RealPlayer;

import java.util.ArrayList;
import java.util.List;

public class CardParametersRequest {

    public List<Object> doAction(GameBoard board, RealPlayer player, int whichToolCard){

        List<Object> requestForToolCard= new ArrayList<>();

        if(player.getFlavorTokens()>=board.getCostToolCard(whichToolCard)){

            requestForToolCard=board.getToolCards(whichToolCard).requestCard();
        }
        else{

            requestForToolCard.add(-1);
            requestForToolCard.add("You don't have enough flavor tokens!");

        }

        return requestForToolCard;
    }
}
