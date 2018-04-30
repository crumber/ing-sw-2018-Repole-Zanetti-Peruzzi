package repolezanettiperuzzi.model.toolcards;

import repolezanettiperuzzi.model.GameBoard;

public class GlazingHammer extends ToolCard {

    @Override
    public int effect() {
        return 0;
    }

    public boolean effect(GameBoard board){

        int numDiceDraft= board.getDiceDraft().size();
        for(int i=0;i<numDiceDraft;i++){

            board.getDieDraft(i).rollDie();

        }

        return true;
    }

}
