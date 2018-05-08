package repolezanettiperuzzi.model.toolcards;

import repolezanettiperuzzi.model.GameBoard;
import repolezanettiperuzzi.model.RealPlayer;

import java.util.ArrayList;
import java.util.List;

public class GlazingHammer extends ToolCard {

    int id=7;

    //list of parameter: 0- gameboard 1- player
    private GameBoard board;
    private RealPlayer player;

    List<Object> resultOfAction= new ArrayList<>();
    List<Object> requestForToolCard = new ArrayList<>();

    public int getId() {
        return id;
    }

    //control that is second turn of round and that player don't insert die in this turn
    @Override
    public List<Object> check(List<Object> parameterForCard) {

        board=(GameBoard)parameterForCard.get(0);
        player=(RealPlayer) parameterForCard.get(1);

        if (player.getTurn()!=2) {

            resultOfAction.add(-1);
            resultOfAction.add("you don't use this card because isn't second turn of this round");

        }else if (player.getInserDieInThisTurn()) {

            resultOfAction.add(-1);
            resultOfAction.add("you don't use this card because you have already inserted a die");

        }else {

            resultOfAction.add(1);

        }

        return resultOfAction;
    }

    //roll all dice on draft
    @Override
    public void effect(List<Object> parameterForCard){

        board=(GameBoard)parameterForCard.get(0);
        player=(RealPlayer) parameterForCard.get(1);

        int numDiceDraft= board.sizeDraft();
        for(int i=0;i<numDiceDraft;i++){

            board.getDieDraft(i).rollDie();

        }
    }

    @Override
    public List<Object> requestCard(){

        return  requestForToolCard;

    }

}
