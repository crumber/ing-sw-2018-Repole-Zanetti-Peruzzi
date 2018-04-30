package repolezanettiperuzzi.model.toolcards;

import repolezanettiperuzzi.model.Die;
import repolezanettiperuzzi.model.GameBoard;

public class FluxRemover extends ToolCard {

    @Override
    public int effect() {
        return 0;
    }

    public boolean effect(GameBoard board, int posDieDraft){

        board.putDieInBag(posDieDraft); //put die in the bag
        board.removeDieFromDraft(posDieDraft); //removed die from draft
        Die newDie= board.takeDieFromBag(); // take another die from bag

        /*
        BISOGNA CHIEDERE AL PLAYER CHE VALORE VUOLE PER QUEL DADO QUINDI CANCELLARE LA PARTE SOTTO CHE MI TIRA A CASO IL DADO!!!!
         */

        newDie.rollDie();

        board.addDieToDraft(newDie); // add new die to draft
        return true;

    }
}
