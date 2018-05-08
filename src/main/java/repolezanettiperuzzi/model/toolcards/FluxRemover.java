package repolezanettiperuzzi.model.toolcards;

import repolezanettiperuzzi.model.Die;
import repolezanettiperuzzi.model.GameBoard;
import repolezanettiperuzzi.model.RealPlayer;
import repolezanettiperuzzi.model.Value;

import java.util.ArrayList;
import java.util.List;

public class FluxRemover extends ToolCard {

    int id=11;
    private GameBoard board;
    private RealPlayer player;
    private int posDieDraft;
    private  int dieValue;

    List<Object> resultOfAction= new ArrayList<>();
    List<Object> requestForToolCard = new ArrayList<>();

    public int getId() {
        return id;
    }

    @Override
    public List<Object> check(List<Object> parameterForCard){

        board=(GameBoard)parameterForCard.get(0);
        player=(RealPlayer)parameterForCard.get(1);
        posDieDraft=(Integer)parameterForCard.get(2);
        dieValue=(Integer)parameterForCard.get(3);

        if(dieValue<1 || dieValue>6){

            resultOfAction.add(-1);
            resultOfAction.add("does'n exist this value for die's value!");

        }else{

            resultOfAction.add(1);
        }

        return resultOfAction;
    }

    @Override
    public List<Object> checkPreEffect(List<Object> parameterForCard){

        board=(GameBoard)parameterForCard.get(0);
        player=(RealPlayer)parameterForCard.get(1);
        posDieDraft=(Integer)parameterForCard.get(2);

        if(board.getDieDraft(posDieDraft)==null){

            resultOfAction.add(-1);
            resultOfAction.add("there isn't die in this draft position");

        }else{

            resultOfAction.add(1);
        }

        return resultOfAction;
    }

    //metodo solo di questa tool card perchè la sua azione è divisa in due perchè a metà ho un'altra interrogazione del client per sapere il valore del dado

    //put die in bag , remove die from draft, take another die from bag and add this die in draft's final position (change die's position in parameter for card)
    // return new quest for client (choose value from 1 to 6)
    @Override
    public List<Object> preEffect(List<Object> parameterForCard){

        board=(GameBoard)parameterForCard.get(0);
        player=(RealPlayer)parameterForCard.get(1);
        posDieDraft=(Integer)parameterForCard.get(2);

        board.putDieInBag(posDieDraft); //put die in the bag
        board.removeDieFromDraft(posDieDraft); //removed die from draft
        Die newDie= board.takeDieFromBag(); // take another die from bag
        board.addDieToDraft(newDie); // add die in draft in final position

        int posNewDie = board.getSizeDraft() - 1 ;
        parameterForCard.add(2, posNewDie);// change the die's position in parameterForCard

        //new player quest
        requestForToolCard.add("Choose value of die in position "+posNewDie +" ( the value have to be between 1 and 6 )\n");

        return requestForToolCard;
    }

    // chiamata finale dove setta al valore scelto dal giocatore il dado precedentemente pescato
    @Override
    public void effect(List<Object> parameterForCard){

        board=(GameBoard)parameterForCard.get(0);
        player=(RealPlayer)parameterForCard.get(1);
        posDieDraft=(Integer)parameterForCard.get(2);
        dieValue=(Integer)parameterForCard.get(3);

        board.getDieDraft(posDieDraft).setValue(Value.intToValue(dieValue));

    }

    @Override
    public List<Object> requestCard(){

        int maxChooseDraftDie = board.getSizeDraft();
        requestForToolCard.add("Which die on draft (from 0 to " + maxChooseDraftDie + ") ?\n");

        return  requestForToolCard;
    }
}
