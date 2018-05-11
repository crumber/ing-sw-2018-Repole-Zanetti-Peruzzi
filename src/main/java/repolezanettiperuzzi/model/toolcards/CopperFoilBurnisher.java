package repolezanettiperuzzi.model.toolcards;

import repolezanettiperuzzi.model.*;

import java.util.List;

public class CopperFoilBurnisher extends ToolCard{

    int id=3;
    int resultOfAction;

    private int xStart;
    private int yStart;
    private int xEnd;
    private int yEnd;


    //check that the position exists, that there is a die in the initial position, that there isn't a die in the final position, that respects bound (colour and die near final position)
    @Override
    public int check(GameBoard board, Player player, List<Integer> parameterForCard){

        xStart=parameterForCard.get(0);
        yStart=parameterForCard.get(1);
        xEnd=parameterForCard.get(2);
        yEnd=parameterForCard.get(3);

        if(checkMoveOneDie(board,player,xStart,yStart,xEnd,yEnd)!=1){

            resultOfAction=checkMoveOneDie(board,player,xStart,yStart,xEnd,yEnd);

        } else if(!player.getWindow().controlColourBoundBox(xEnd,yEnd,player.getWindow().getDieFromBoardBox(xStart,yStart))){

            resultOfAction=-5;

        } else if(player.getWindow().controlColourBoundAdjacences(player.getWindow().getDieFromBoardBox(xStart,yStart),xEnd,yEnd)){

            resultOfAction=-23;

        } else{

            resultOfAction=1;
        }

        return resultOfAction;
    }

    public int getId() {
        return id;
    }


    //move die from (xstart,ystart) into (xend,endy). respects bound of colour
    @Override
    public void effect(GameBoard board, Player player, List<Integer> parameterForCard){

        xStart=parameterForCard.get(0);
        yStart=parameterForCard.get(1);
        xEnd=parameterForCard.get(2);
        yEnd=parameterForCard.get(3);

        player.getWindow().moveDie(xStart,yStart,xEnd,yEnd,"colour");

    }
}

