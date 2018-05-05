package repolezanettiperuzzi.model.toolcards;

import repolezanettiperuzzi.model.GameBoard;

import java.util.ArrayList;
import java.util.List;

public class FluxBrush extends ToolCard {

    int id=6;
    List<Object> resultOfAction= new ArrayList<>();

    public int getId() {
        return id;
    }

    public List<Object> check(GameBoard boar, int posDieDraft) {

        if (boar.getDieDraft(posDieDraft) == null) {

            resultOfAction.add(-1);
            resultOfAction.add("there isn't die on draft in the position you have chosen ");

        } else {

            resultOfAction.add(1);

        }

        return resultOfAction;
    }

    public void effect(GameBoard board, int posDieDraft){

        board.getDieDraft(posDieDraft).rollDie();

    }
}
