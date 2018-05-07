package repolezanettiperuzzi.model.toolcards;

import repolezanettiperuzzi.model.GameBoard;
import repolezanettiperuzzi.model.RealPlayer;

import java.util.ArrayList;
import java.util.List;

public class Lathekin extends ToolCard {

    int id=4;

    //list of parameter: 0-board 1-player; 2-xStart die 1; 3-yStart die 1; 4-xEnd die 1; 5-yEnd die 1; 6-xStart die 2; 7-yStart die 2; 8-xEnd die 2; 9-yEnd die 2
    private GameBoard board;
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
    List<Object> requestForToolCard = new ArrayList<>();

    public int getId() {
        return id;
    }

    //control for all dice (two) that exist start/end position, that there is die in start position, that there isn't die in end position, control that die respects all bound(colour value and there is die near end position)
    @Override
    public List<Object> check(List<Object> parameterForCard) {

        board=(GameBoard)parameterForCard.get(0);
        player=(RealPlayer)parameterForCard.get(1);
        x1Start=(Integer)parameterForCard.get(2);
        y1Start=(Integer)parameterForCard.get(3);
        x1End=(Integer)parameterForCard.get(4);
        y1End=(Integer)parameterForCard.get(5);
        x2Start=(Integer)parameterForCard.get(6);
        y2Start=(Integer)parameterForCard.get(7);
        x2End=(Integer)parameterForCard.get(8);
        y2End=(Integer)parameterForCard.get(9);

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

        board=(GameBoard)parameterForCard.get(0);
        player=(RealPlayer)parameterForCard.get(1);
        x1Start=(Integer)parameterForCard.get(2);
        y1Start=(Integer)parameterForCard.get(3);
        x1End=(Integer)parameterForCard.get(4);
        y1End=(Integer)parameterForCard.get(5);
        x2Start=(Integer)parameterForCard.get(6);
        y2Start=(Integer)parameterForCard.get(7);
        x2End=(Integer)parameterForCard.get(8);
        y2End=(Integer)parameterForCard.get(9);

        player.getWindow().moveDie(x1Start,y1Start,x1End,y1End,"both");
        player.getWindow().moveDie(x2Start,y2Start,x2End,y2End,"both");

    }

    @Override
    public List<Object> requestCard(){

        requestForToolCard.add("From which box do you want to move the die 1 (insert number of row and number of column, like this: 1 3) ?\n");
        requestForToolCard.add("In which box do you want to move the die 1 (insert number of row and number of column, like this: 1 3) ?\n");
        requestForToolCard.add("From which box do you want to move the die 2 (insert number of row and number of column, like this: 1 3) ?\n");
        requestForToolCard.add("In which box do you want to move the die 2 (insert number of row and number of column, like this: 1 3) ?\n");

        return  requestForToolCard;

    }
}
