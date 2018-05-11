package repolezanettiperuzzi.model.toolcards;

import repolezanettiperuzzi.model.GameBoard;
import repolezanettiperuzzi.model.Player;

import java.util.List;

public class FluxBrush extends ToolCard {

    int id=6;

    private int posDieOnDraft;

    int resultOfAction;

    public int getId() {
        return id;
    }

    //control that there is die in this position on draft
    @Override
    public int check(GameBoard board, Player player, List<Integer> parameterForCard) {

        posDieOnDraft=parameterForCard.get(0);

        resultOfAction=checkDieOnDraft(board,player,posDieOnDraft);

        return resultOfAction;
    }

    //roll die in this position on draft
    @Override
    public void effect(GameBoard board, Player player, List<Integer> parameterForCard){

        posDieOnDraft=parameterForCard.get(0);
        board.getDieDraft(posDieOnDraft).rollDie();

    }
}
