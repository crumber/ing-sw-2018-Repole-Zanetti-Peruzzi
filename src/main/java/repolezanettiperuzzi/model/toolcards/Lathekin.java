package repolezanettiperuzzi.model.toolcards;

import repolezanettiperuzzi.model.RealPlayer;

import java.util.ArrayList;
import java.util.List;

public class Lathekin extends ToolCard {

    int id=4;

    //list of parameter: 0-player; 1-xStart die 1; 2-yStart die 1; 3-xEnd die 1; 4-yEnd die 1; 5-xStart die 2; 6-yStart die 2; 7-xEnd die 2; 8-yEnd die 2
    private RealPlayer player;
    private int x1Start;
    private int y1Start;
    private int x1End;
    private int y1End;
    private int x2Start;
    private int y2Start;
    private int x2End;
    private int y2End;

    List<Object> resultOfAction= new ArrayList<>();

    public int getId() {
        return id;
    }

    //control for all dice (two) that exist start/end position, that there is die in start position, that there isn't die in end position, control that die respects all bound(colour value and there is die near end position)
    @Override
    public List<Object> check(List<Object> parameterForCard) {

        player=(RealPlayer)parameterForCard.get(0);
        x1Start=(Integer)parameterForCard.get(1);
        y1Start=(Integer)parameterForCard.get(2);
        x1End=(Integer)parameterForCard.get(3);
        y1End=(Integer)parameterForCard.get(4);
        x2Start=(Integer)parameterForCard.get(5);
        y2Start=(Integer)parameterForCard.get(6);
        x2End=(Integer)parameterForCard.get(7);
        y2End=(Integer)parameterForCard.get(8);

        if(x1Start<0 || x1Start>3 || y1Start<0 || y1Start>4 || x1End<0 || x1End>3 || y1End<0 || y1End>4){

            resultOfAction.add(-1);
            resultOfAction.add("you don't move die 1 because your star position or finish position aren't exist!!!!");

        } else if (!player.getWindow().thereIsDie(x1Start, y1Start)) {

            resultOfAction.add(-1);
            resultOfAction.add("you don't move die 1 because you don't choose a box without die");

        } else if (player.getWindow().thereIsDie(x1End, y1End)) {

            resultOfAction.add(-1);
            resultOfAction.add("you don't move die 1 into box because there is a die in this box");

        } else if (!player.getWindow().controlAdjacences(x1End, y1End)) {

            resultOfAction.add(-1);
            resultOfAction.add("you don't move die 1 into box because there are not any die near this box");

        } else if (!player.getWindow().controlAllBoundBox(x1End, y1End, player.getWindow().getDieFromBoardBox(x1Start, y1Start))) {

            resultOfAction.add(-1);
            resultOfAction.add("you don't move die 1 into box because your die don't respect bound");

        } else if(x2Start<0 || x2Start>3 || y2Start<0 || y2Start>4 || x2End<0 || x2End>3 || y2End<0 || y2End>4){

            resultOfAction.add(-1);
            resultOfAction.add("you don't move die 2 because your star position or finish position aren't exist!!!!");

        } else if (!player.getWindow().thereIsDie(x2Start, y2Start)) {

            resultOfAction.add(-1);
            resultOfAction.add("you don't move die 2 because you don't choose a box without die");

        } else if (player.getWindow().thereIsDie(x2End, y2End)) {

            resultOfAction.add(-1);
            resultOfAction.add("you don't move die 2 into box because there is a die in this box");

        } else if (!player.getWindow().controlAdjacences(x2End, y2End)) {

            resultOfAction.add(-1);
            resultOfAction.add("you don't move die 2 into box because there are not any die near this box");

        } else if (!player.getWindow().controlAllBoundBox(x2End, y2End, player.getWindow().getDieFromBoardBox(x2Start, y2Start))) {

            resultOfAction.add(-1);
            resultOfAction.add("you don't move die 2 into box because your die don't respect bound");

        } else {

            resultOfAction.add(1);

        }

        return resultOfAction;
    }

    //move both dice (with all bound)
    @Override
    public void effect(List<Object> parameterForCard){

        player=(RealPlayer)parameterForCard.get(0);
        x1Start=(Integer)parameterForCard.get(1);
        y1Start=(Integer)parameterForCard.get(2);
        x1End=(Integer)parameterForCard.get(3);
        y1End=(Integer)parameterForCard.get(4);
        x2Start=(Integer)parameterForCard.get(5);
        y2Start=(Integer)parameterForCard.get(6);
        x2End=(Integer)parameterForCard.get(7);
        y2End=(Integer)parameterForCard.get(8);

        player.getWindow().moveDie(x1Start,y1Start,x1End,y1End,"both");
        player.getWindow().moveDie(x2Start,y2Start,x2End,y2End,"both");

    }
}
