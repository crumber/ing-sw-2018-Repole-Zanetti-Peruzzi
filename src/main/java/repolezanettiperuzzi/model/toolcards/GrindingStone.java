package repolezanettiperuzzi.model.toolcards;

import repolezanettiperuzzi.model.GameBoard;
import repolezanettiperuzzi.model.Player;
import repolezanettiperuzzi.model.Value;

import java.util.List;

public class GrindingStone extends ToolCard {

    private int posDieOnDraft;

    public GrindingStone(){

        id=10;

    }

    //control that there is die in this position on draft
    @Override
    public int check(GameBoard board, Player player, List<Integer> parameterForCard) {

        posDieOnDraft=parameterForCard.get(0);

        resultOfAction=checkDieOnDraft(board,player,posDieOnDraft);

        return  resultOfAction;
    }

    //change die's value (in this position on draft)
    @Override
    public void effect(GameBoard board, Player player, List<Integer> parameterForCard){

        posDieOnDraft=parameterForCard.get(0);

        if(board.getDieDraft(posDieOnDraft).getValueDie()==Value.ONE){

            board.getDieDraft(posDieOnDraft).setValue(Value.SIX);

        }else if(board.getDieDraft(posDieOnDraft).getValueDie()==Value.SIX){

            board.getDieDraft(posDieOnDraft).setValue(Value.ONE);

        }else if(board.getDieDraft(posDieOnDraft).getValueDie()==Value.FIVE){

            board.getDieDraft(posDieOnDraft).setValue(Value.TWO);

        }else if(board.getDieDraft(posDieOnDraft).getValueDie()==Value.TWO){

            board.getDieDraft(posDieOnDraft).setValue(Value.FIVE);

        }else if(board.getDieDraft(posDieOnDraft).getValueDie()==Value.FOUR){

            board.getDieDraft(posDieOnDraft).setValue(Value.THREE);

        }else{

            board.getDieDraft(posDieOnDraft).setValue(Value.FOUR);

        }
    }
}
