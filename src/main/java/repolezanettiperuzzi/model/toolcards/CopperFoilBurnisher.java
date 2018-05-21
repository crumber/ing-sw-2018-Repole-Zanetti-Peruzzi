package repolezanettiperuzzi.model.toolcards;

import repolezanettiperuzzi.model.*;

import java.util.List;

public class CopperFoilBurnisher extends ToolCard{

    public CopperFoilBurnisher(){

        id=3;

    }

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

        } else{

            Die dTemp= player.getWindow().removeDie(xStart,yStart);

            if(!player.getWindow().controlColourBoundBox(xEnd,yEnd,dTemp)){

                resultOfAction=-5;

            } else if(player.getWindow().controlColourBoundAdjacences(dTemp,xEnd,yEnd)){

                resultOfAction=-23;

            } else{

                resultOfAction=1;
            }

            player.getWindow().insertDie(dTemp,xStart,yStart,"both");
        }

        return resultOfAction;
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

