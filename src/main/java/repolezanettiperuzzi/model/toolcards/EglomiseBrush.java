package repolezanettiperuzzi.model.toolcards;

import repolezanettiperuzzi.model.RealPlayer;

import java.util.ArrayList;
import java.util.List;

public class EglomiseBrush extends ToolCard {


    int id=2;

    //list of parameter: 0-player 1-xstart 2-ystart 3-xend 4yend
    private RealPlayer player;
    private int xStart;
    private int yStart;
    private int xEnd;
    private int yEnd;

    List<Object> resultOfAction= new ArrayList<>();

    public int getId() {
        return id;
    }

    //check that the position exists, that there is a die in the initial position, that there isn't a die in the final position, that respects bound (value and die near final position)
    @Override
    public List<Object> check(List<Object> parameterForCard) {

        player=(RealPlayer)parameterForCard.get(0);
        xStart=(Integer)parameterForCard.get(1);
        yStart=(Integer)parameterForCard.get(2);
        xEnd=(Integer)parameterForCard.get(3);
        yEnd=(Integer)parameterForCard.get(4);

        if(xStart<0 || xStart>3 || yStart<0 || yStart>4 || xEnd<0 || xEnd>3 || yEnd<0 || yEnd>4){

            resultOfAction.add(-1);
            resultOfAction.add("you don't move die because your star position or finish position aren't exist!!!!");

        } else if (!player.getWindow().thereIsDie(xStart, yStart)) {

            resultOfAction.add(-1);
            resultOfAction.add("you don't move die because you don't choose a box without die");

        } else if (player.getWindow().thereIsDie(xEnd, yEnd)) {

            resultOfAction.add(-1);
            resultOfAction.add("you don't move die into box because there is a die in this box");

        } else if (!player.getWindow().controlAdjacences(xEnd, yEnd)) {

            resultOfAction.add(-1);
            resultOfAction.add("you don't move die into box because there are not any die near this box");

        }else if(!player.getWindow().controlValueBoundBox(xEnd,yEnd,player.getWindow().getDieFromBoardBox(xStart,yStart))){

        resultOfAction.add(-1);
        resultOfAction.add("you don't move die into box because your die don't respect value bound");

        } else{

        resultOfAction.add(1);

        }

        return resultOfAction;
    }

    //move die from (xstart,ystart) into (xend,endy). respects bound of value
    @Override
    public void effect(List<Object> parameterForCard){

        player=(RealPlayer)parameterForCard.get(0);
        xStart=(Integer)parameterForCard.get(1);
        yStart=(Integer)parameterForCard.get(2);
        xEnd=(Integer)parameterForCard.get(3);
        yEnd=(Integer)parameterForCard.get(4);

        player.getWindow().moveDie(xStart,yStart,xEnd,yEnd,"value");

    }
}
