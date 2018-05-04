package repolezanettiperuzzi.model.toolcards;

import repolezanettiperuzzi.model.GameBoard;
import repolezanettiperuzzi.model.Value;

public class GrindingStone extends ToolCard {

    int id=10;

    public int getId() {
        return id;
    }

    @Override
    public int effect() {
        return 0;
    }

    public boolean effect(GameBoard board, int posDieDraft){

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

        return true;
    }
}
