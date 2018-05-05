package repolezanettiperuzzi.model.toolcards;

import repolezanettiperuzzi.model.GameBoard;
import repolezanettiperuzzi.model.RealPlayer;
import repolezanettiperuzzi.model.Value;

import java.util.ArrayList;
import java.util.List;

public class GrindingStone extends ToolCard {

    int id=10;
    List<Object> resultOfAction= new ArrayList<>();

    public int getId() {
        return id;
    }

    public List<Object> check(GameBoard boar, int posDieDraft) {

        if(boar.getDieDraft(posDieDraft)==null){

            resultOfAction.add(-1);
            resultOfAction.add("there isn't die in this position on draft");

        }else {

            resultOfAction.add(1);

        }

        return  resultOfAction;
    }


    public void effect(GameBoard board, int posDieDraft){

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
