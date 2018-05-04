package repolezanettiperuzzi.model.toolcards;

import repolezanettiperuzzi.model.GameBoard;
import repolezanettiperuzzi.model.RealPlayer;
import repolezanettiperuzzi.model.Value;

public class GrozingPliers extends ToolCard {

    int id=1;

    public int getId() {
        return id;
    }

    @Override
    public int effect() {
        return 0;
    }

    public boolean effect(RealPlayer player, GameBoard board, int numDieFromDraft, int change){

        if(change==0){

            if(board.getDieDraft(numDieFromDraft).getValueDie().getNumber()!=1){

                board.getDieDraft(numDieFromDraft).setValue(Value.intToValue((board.getDieDraft(numDieFromDraft).getValueDie().getNumber()) - 1));
                return  true;
            }

        }else if(change==1){

            if(board.getDieDraft(numDieFromDraft).getValueDie().getNumber()!=6){

                board.getDieDraft(numDieFromDraft).setValue(Value.intToValue((board.getDieDraft(numDieFromDraft).getValueDie().getNumber()) + 1));
                return true;
            }
        }

        return false;
    }
}
