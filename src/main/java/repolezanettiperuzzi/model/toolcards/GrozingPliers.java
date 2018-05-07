package repolezanettiperuzzi.model.toolcards;

import repolezanettiperuzzi.model.GameBoard;
import repolezanettiperuzzi.model.RealPlayer;
import repolezanettiperuzzi.model.Value;

import java.util.ArrayList;
import java.util.List;

public class GrozingPliers extends ToolCard {

    int id=1;

    //list of parameter: 0- gameboar 1- position die on draft 2-change(0 decrement 1 increment)
    private GameBoard board;
    private int numDieFromDraft;
    private int change;

    List<Object> resultOfAction = new ArrayList<>();

    public int getId() {
        return id;
    }

    //control thant change is 1 or 0, that there is a die in this position on draft, that die can increment (if is six no) /decrement (if is one no)
    @Override
    public List<Object> check(List<Object> parameterForCard){

        board=(GameBoard)parameterForCard.get(0);
        numDieFromDraft=(Integer)parameterForCard.get(1);
        change=(Integer)parameterForCard.get(2);

        if(change>1 || change<0){

            resultOfAction.add(-1);
            resultOfAction.add("your change does not exist!");

        }else if(board.getDieDraft(numDieFromDraft)==null){

            resultOfAction.add(-1);
            resultOfAction.add("there isn't die on draft in the position you have chosen ");

        }else if(change==0 && board.getDieDraft(numDieFromDraft).getValueDie().getNumber()!=1){

            resultOfAction.add(1);

        }else if(change==0){

            resultOfAction.add(-1);
            resultOfAction.add("you don't decrease this die because you don't decrease one");
        }

        if(change==1 && board.getDieDraft(numDieFromDraft).getValueDie().getNumber()!=6){

            resultOfAction.add(1);

        }else if(change==1){

            resultOfAction.add(-1);
            resultOfAction.add("you don't increase this die because you don't increase six");
        }

        return  resultOfAction;
    }


    //increment die (if is six no) or decrement die (if is one no)
    @Override
    public void effect(List<Object> parameterForCard) {

        board=(GameBoard)parameterForCard.get(0);
        numDieFromDraft=(Integer)parameterForCard.get(1);
        change=(Integer)parameterForCard.get(2);


        if (change == 0) {

            board.getDieDraft(numDieFromDraft).setValue(Value.intToValue((board.getDieDraft(numDieFromDraft).getValueDie().getNumber()) - 1));

        }

        if (change == 1) {

            board.getDieDraft(numDieFromDraft).setValue(Value.intToValue((board.getDieDraft(numDieFromDraft).getValueDie().getNumber()) + 1));

        }
    }
}
