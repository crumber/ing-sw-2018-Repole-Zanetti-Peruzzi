package repolezanettiperuzzi.model.toolcards;

import repolezanettiperuzzi.model.GameBoard;

public class FluxBrush extends ToolCard {

    @Override
    public int effect() {
        return 0;
    }

    public boolean effect(GameBoard board, int posDieDraft){

        board.getDieDraft(posDieDraft).rollDie();
        return true;

    }
}
