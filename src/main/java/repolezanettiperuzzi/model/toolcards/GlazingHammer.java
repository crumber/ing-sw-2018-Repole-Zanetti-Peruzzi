package repolezanettiperuzzi.model.toolcards;

import repolezanettiperuzzi.model.GameBoard;
import repolezanettiperuzzi.model.RealPlayer;

import java.util.ArrayList;
import java.util.List;

public class GlazingHammer extends ToolCard {

    int id=7;
    List<Object> resultOfAction= new ArrayList<>();

    public int getId() {
        return id;
    }

    public List<Object> check(GameBoard boar, RealPlayer player) {

        if (player.getTurn()!=2) {

            resultOfAction.add(-1);
            resultOfAction.add("you don't use this card because isn't second turn of this round");

        }else if (player.getInserDieInThisTurn()) {

            resultOfAction.add(-1);
            resultOfAction.add("you don't use this card because you have already inserted a die");

        }else {

            resultOfAction.add(1);

        }

        return resultOfAction;
    }

    public void effect(GameBoard board){

        int numDiceDraft= board.sizeDraft();
        for(int i=0;i<numDiceDraft;i++){

            board.getDieDraft(i).rollDie();

        }
    }

}
