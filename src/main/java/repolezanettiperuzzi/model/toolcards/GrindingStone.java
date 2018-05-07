package repolezanettiperuzzi.model.toolcards;

import repolezanettiperuzzi.model.GameBoard;
import repolezanettiperuzzi.model.RealPlayer;
import repolezanettiperuzzi.model.Value;

import java.util.ArrayList;
import java.util.List;

public class GrindingStone extends ToolCard {

    int id=10;

    //list of parameter: 0- gameboar 1- position die on draft
    private GameBoard board;
    private int posDieDraft;

    List<Object> resultOfAction= new ArrayList<>();

    public int getId() {
        return id;
    }

    //control that there is die in this position on draft
    @Override
    public List<Object> check(List<Object> parameterForCard) {

        board=(GameBoard)parameterForCard.get(0);
        posDieDraft=(Integer)parameterForCard.get(1);

        if(board.getDieDraft(posDieDraft)==null){

            resultOfAction.add(-1);
            resultOfAction.add("there isn't die in this position on draft");

        }else {

            resultOfAction.add(1);

        }

        return  resultOfAction;
    }

    //change die's value (in this position on draft)
    @Override
    public void effect(List<Object> parameterForCard){

        board=(GameBoard)parameterForCard.get(0);
        posDieDraft=(Integer)parameterForCard.get(1);

        if(board.getDieDraft(posDieDraft).getValueDie()==Value.ONE){

            board.getDieDraft(posDieDraft).setValue(Value.SIX);

        }

        if(board.getDieDraft(posDieDraft).getValueDie()==Value.SIX){

            board.getDieDraft(posDieDraft).setValue(Value.ONE);

        }

        if(board.getDieDraft(posDieDraft).getValueDie()==Value.FIVE){

            board.getDieDraft(posDieDraft).setValue(Value.TWO);

        }

        if(board.getDieDraft(posDieDraft).getValueDie()==Value.TWO){

            board.getDieDraft(posDieDraft).setValue(Value.FIVE);

        }

        if(board.getDieDraft(posDieDraft).getValueDie()==Value.FOUR){

            board.getDieDraft(posDieDraft).setValue(Value.THREE);

        }

        if(board.getDieDraft(posDieDraft).getValueDie()==Value.THREE){

            board.getDieDraft(posDieDraft).setValue(Value.FOUR);

        }
    }
}
