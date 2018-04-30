package repolezanettiperuzzi.model.toolcards;

import repolezanettiperuzzi.model.Die;
import repolezanettiperuzzi.model.GameBoard;

public class FluxRemover extends ToolCard {

    @Override
    public int effect() {
        return 0;
    }

    public boolean effect(GameBoard board, int posDieDraft){

        board.getDiceBag().setDieInBag(board.getDieDraft(posDieDraft)); //put die in the bag
        board.getDiceDraft().remove(posDieDraft); //removed die from draft
        Die newDie= board.getDiceBag().takeDie(); // take another die from bag

        /*
        BISOGNA CHIEDERE AL PLAYER CHE VALORE VUOLE PER QUEL DADO QUINDI CANCELLARE LA PARTE SOTTO CHE MI TIRA A CASO IL DADO!!!!
         */

        newDie.rollDie();

        board.getDiceDraft().add(newDie); // add new die to draft
        return true;

    }
}
