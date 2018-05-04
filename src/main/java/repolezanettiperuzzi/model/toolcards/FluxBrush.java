package repolezanettiperuzzi.model.toolcards;

import repolezanettiperuzzi.model.GameBoard;

public class FluxBrush extends ToolCard {

    int id=6;

    public int getId() {
        return id;
    }

    @Override
    public int effect() {
        return 0;
    }

    public boolean effect(GameBoard board, int posDieDraft){

        board.getDieDraft(posDieDraft).rollDie();
        return true;

    }
}
