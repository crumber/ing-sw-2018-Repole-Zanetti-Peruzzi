package repolezanettiperuzzi.model.toolcards;

import repolezanettiperuzzi.model.Die;
import repolezanettiperuzzi.model.GameBoard;
import repolezanettiperuzzi.model.Player;
import repolezanettiperuzzi.model.Value;

import java.util.List;

public class FluxRemover extends ToolCard {

    public FluxRemover(){

        id=11;

    }

    private int posDieOnDraft;
    private  int dieValue;

    @Override
    public int check(GameBoard board, Player player, List<Integer> parameterForCard){

        posDieOnDraft=parameterForCard.get(0);
        dieValue=parameterForCard.get(1);

        if(dieValue<1 || dieValue>6){

            resultOfAction=-11;

        }else{

            resultOfAction=1;
        }

        return resultOfAction;
    }

    @Override
    public int checkPreEffect(GameBoard board, Player player, List<Integer> parameterForCard){

        posDieOnDraft=parameterForCard.get(0);

        if(checkDieOnDraft(board,player,posDieOnDraft)!=1){

            resultOfAction=checkDieOnDraft(board,player,posDieOnDraft);

        }else{

            resultOfAction=1;
        }

        return resultOfAction;
    }


    //put die in bag , remove die from draft, take another die from bag and add this die in draft's final position (change die's position in parameter for card)
    // return 11 -> new quest for client (choose value from 1 to 6)
    @Override
    public int preEffect(GameBoard board, Player player, List<Integer> parameterForCard){

        posDieOnDraft=parameterForCard.get(0);

        board.putDieInBag(posDieOnDraft); //put die in the bag
        board.removeDieFromDraft(posDieOnDraft); //removed die from draft
        Die newDie= board.takeDieFromBag(); // take another die from bag
        board.addDieToDraft(newDie); // add die in draft in final position

        int posNewDie = board.getSizeDraft() - 1 ;
        parameterForCard.add(0, posNewDie);// change the die's position in parameterForCard

        //new player quest
        resultOfAction=11;

        return resultOfAction;
    }

    // set die's value
    @Override
    public void effect(GameBoard board, Player player, List<Integer> parameterForCard){

        posDieOnDraft=parameterForCard.get(0);
        dieValue=parameterForCard.get(1);

        board.getDieDraft(posDieOnDraft).setValue(Value.intToValue(dieValue));

    }
}
