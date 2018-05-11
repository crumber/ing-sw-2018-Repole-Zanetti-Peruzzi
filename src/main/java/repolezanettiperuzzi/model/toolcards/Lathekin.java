package repolezanettiperuzzi.model.toolcards;

import repolezanettiperuzzi.model.GameBoard;
import repolezanettiperuzzi.model.Player;

import java.util.List;

public class Lathekin extends ToolCard {

    int id = 4;

    int resultOfAction;

    private int x1Start;
    private int y1Start;
    private int x1End;
    private int y1End;
    private int x2Start;
    private int y2Start;
    private int x2End;
    private int y2End;

    public int getId() {
        return id;
    }

    //control for all dice (two) that exist start/end position, that there is die in start position, that there isn't die in end position, control that die respects all bound(colour value and there is die near end position)
    @Override
    public int check(GameBoard board, Player player, List<Integer> parameterForCard) {

        x1Start = parameterForCard.get(0);
        y1Start = parameterForCard.get(1);
        x1End = parameterForCard.get(2);
        y1End = parameterForCard.get(3);
        x2Start = parameterForCard.get(4);
        y2Start = parameterForCard.get(5);
        x2End = parameterForCard.get(6);
        y2End = parameterForCard.get(7);

        resultOfAction = checkMoveTwoDice(board, player, x1Start, y1Start, x1End, y1End, x2Start, y2Start, x2End, y2End);

        return resultOfAction;
    }

    //move both dice (with all bound)
    @Override
    public void effect(GameBoard board, Player player, List<Integer> parameterForCard) {

        x1Start = parameterForCard.get(0);
        y1Start = parameterForCard.get(1);
        x1End = parameterForCard.get(2);
        y1End = parameterForCard.get(3);
        x2Start = parameterForCard.get(4);
        y2Start = parameterForCard.get(5);
        x2End = parameterForCard.get(6);
        y2End = parameterForCard.get(7);

        player.getWindow().moveDie(x1Start, y1Start, x1End, y1End, "both");
        player.getWindow().moveDie(x2Start, y2Start, x2End, y2End, "both");

    }
}

