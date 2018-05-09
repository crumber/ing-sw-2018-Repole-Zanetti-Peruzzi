package repolezanettiperuzzi.model.toolcards;

import repolezanettiperuzzi.model.GameBoard;
import repolezanettiperuzzi.model.RealPlayer;

import java.util.List;


public class EglomiseBrush extends ToolCard {


    int id=2;

    int resultOfAction;

    private int xStart;
    private int yStart;
    private int xEnd;
    private int yEnd;


    public int getId() {
        return id;
    }

    //check that the position exists, that there is a die in the initial position, that there isn't a die in the final position, that respects bound (value and die near final position)
    @Override
    public int check(GameBoard board, RealPlayer player, List<Integer> parameterForCard){

        xStart=parameterForCard.get(0);
        yStart=parameterForCard.get(1);
        xEnd=parameterForCard.get(2);
        yEnd=parameterForCard.get(3);

        if(checkMoveOneDie(board,player,xStart,yStart,xEnd,yEnd)!=1) {

            resultOfAction = checkMoveOneDie(board, player, xStart, yStart, xEnd, yEnd);

        } else if(!player.getWindow().controlValueBoundBox(xEnd,yEnd,player.getWindow().getDieFromBoardBox(xStart,yStart))){

            resultOfAction=-6;

        } else if(player.getWindow().controlValueBoundAdjacences(player.getWindow().getDieFromBoardBox(xStart,yStart),xEnd,yEnd)){

            resultOfAction=-24;

        } else{

            resultOfAction=1;

        }

        return resultOfAction;
    }

    //move die from (xstart,ystart) into (xend,endy). respects bound of value
    @Override
    public void effect(GameBoard board, RealPlayer player, List<Integer> parameterForCard){

        xStart=parameterForCard.get(0);
        yStart=parameterForCard.get(1);
        xEnd=parameterForCard.get(2);
        yEnd=parameterForCard.get(3);

        player.getWindow().moveDie(xStart,yStart,xEnd,yEnd,"value");

    }
}
