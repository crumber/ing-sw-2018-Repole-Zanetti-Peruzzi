package repolezanettiperuzzi.model.actions;

import repolezanettiperuzzi.model.GameBoard;
import repolezanettiperuzzi.model.Player;
import repolezanettiperuzzi.model.toolcards.FluxRemover;

import java.util.List;


public class UseCardAction {

    //For all card and tool card 11's final effect
    public int doAction(Player player, GameBoard board, int whichToolCard, List<Integer> parameterForCard){

        int resultOfAction;

        resultOfAction=board.getToolCards(whichToolCard).check(board,player,parameterForCard);

        //if check is correct, do active action, reduce player's tokens
        if(resultOfAction==1){

            board.getToolCards(whichToolCard).effect(board,player,parameterForCard);
            player.reduceFavorTokens(board.getCostToolCard(whichToolCard));

            //increment tool card cost if its cost is 1
            if(board.getCostToolCard(whichToolCard)==1) {

                board.setCostToolCard(whichToolCard);

            }
        }

        return resultOfAction;
    }

    //only for tool card 11
    public int doActionPreEffect(Player player, GameBoard board, int whichToolCard, List<Integer> parameterForCard){

        int resultOfAction;

        resultOfAction=((FluxRemover)board.getToolCards(whichToolCard)).checkPreEffect(board,player,parameterForCard);

        //if check is correct, do active action, not reduce player's tokens
        if(resultOfAction==1) {

            // return +11 -> quest for the client
            resultOfAction = ((FluxRemover)board.getToolCards(whichToolCard)).preEffect(board, player, parameterForCard);

        }

        return resultOfAction;
    }
}
