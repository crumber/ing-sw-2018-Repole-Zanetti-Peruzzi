package repolezanettiperuzzi.model.toolcards;

import repolezanettiperuzzi.model.GameBoard;

import java.util.ArrayList;
import java.util.List;

public class FluxBrush extends ToolCard {

    int id=6;

    //list of parameter: 0- gameboar 1- position die on draft
    private GameBoard board;
    private int posDieDraft;

    List<Object> resultOfAction= new ArrayList<>();

    public int getId() {
        return id;
    }

    //control that there is die in this position on draft
    @Override
    public List<Object> check(List<Object> parameterForCard) {

        board=(GameBoard)parameterForCard.get(0);
        posDieDraft=(Integer)parameterForCard.get(1);

        if (board.getDieDraft(posDieDraft) == null) {

            resultOfAction.add(-1);
            resultOfAction.add("there isn't die on draft in the position you have chosen ");

        } else {

            resultOfAction.add(1);

        }

        return resultOfAction;
    }

    //roll die in this position on draft
    @Override
    public void effect(List<Object> parameterForCard){

        board=(GameBoard)parameterForCard.get(0);
        posDieDraft=(Integer)parameterForCard.get(1);

        board.getDieDraft(posDieDraft).rollDie();

    }
}
