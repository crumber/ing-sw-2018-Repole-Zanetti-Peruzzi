package repolezanettiperuzzi.model.toolcards;

import repolezanettiperuzzi.model.GameBoard;
import repolezanettiperuzzi.model.RealPlayer;
import repolezanettiperuzzi.model.Value;

import java.util.ArrayList;
import java.util.List;

public class GrozingPliers extends ToolCard {

    int id=1;
    List<Object> resultOfAction = new ArrayList<>();

    public int getId() {
        return id;
    }

    public List<Object> check(GameBoard board, int numDieFromDraft, int change){

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

    public void effect(GameBoard board, int numDieFromDraft, int change) {

        if (change == 0) {

            board.getDieDraft(numDieFromDraft).setValue(Value.intToValue((board.getDieDraft(numDieFromDraft).getValueDie().getNumber()) - 1));

        }

        if (change == 1) {

            board.getDieDraft(numDieFromDraft).setValue(Value.intToValue((board.getDieDraft(numDieFromDraft).getValueDie().getNumber()) + 1));

        }
    }
}
