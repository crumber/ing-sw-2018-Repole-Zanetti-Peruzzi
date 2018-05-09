package repolezanettiperuzzi.model.toolcards;

import repolezanettiperuzzi.model.GameBoard;
import repolezanettiperuzzi.model.RealPlayer;
import repolezanettiperuzzi.model.Value;

import java.util.List;

public class GrozingPliers extends ToolCard {

    int id=1;

    private int numDieFromDraft;
    private int change;

    int resultOfAction;

    public int getId() {
        return id;
    }

    //control thant change is 1 or 0, that there is a die in this position on draft, that die can increment (if is six no) /decrement (if is one no)
    @Override
    public int check(GameBoard board, RealPlayer player, List<Integer> parameterForCard){

        numDieFromDraft=parameterForCard.get(0);
        change=parameterForCard.get(1);

        if(change>1 || change<0){

            resultOfAction=-14;

        }else if(checkDieOnDraft(board,player,numDieFromDraft)!=1){

            resultOfAction=checkDieOnDraft(board,player,numDieFromDraft);

        }else if(change==0 && board.getDieDraft(numDieFromDraft).getValueDie().getNumber()!=1){

            resultOfAction=1;

        }else if(change==0){

            resultOfAction=-15;
        }

        if(change==1 && board.getDieDraft(numDieFromDraft).getValueDie().getNumber()!=6){

            resultOfAction=1;

        }else if(change==1){

            resultOfAction=-16;
        }

        return  resultOfAction;
    }


    //increment die (if is six no) or decrement die (if is one no)
    @Override
    public void effect(GameBoard board, RealPlayer player, List<Integer> parameterForCard) {

        numDieFromDraft=parameterForCard.get(0);
        change=parameterForCard.get(1);

        if (change == 0) {

            board.getDieDraft(numDieFromDraft).setValue(Value.intToValue((board.getDieDraft(numDieFromDraft).getValueDie().getNumber()) - 1));

        }

        if (change == 1) {

            board.getDieDraft(numDieFromDraft).setValue(Value.intToValue((board.getDieDraft(numDieFromDraft).getValueDie().getNumber()) + 1));

        }
    }
}
