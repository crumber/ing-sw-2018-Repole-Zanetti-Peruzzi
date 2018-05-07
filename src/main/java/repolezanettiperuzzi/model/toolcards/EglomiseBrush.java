package repolezanettiperuzzi.model.toolcards;

import repolezanettiperuzzi.model.GameBoard;
import repolezanettiperuzzi.model.RealPlayer;

import java.util.ArrayList;
import java.util.List;

public class EglomiseBrush extends ToolCard {


    int id=2;

    //list of parameter: 0-board 1-player 2-xstart 3-ystart 4-xend 5-yend
    private GameBoard board;
    private RealPlayer player;
    private int xStart;
    private int yStart;
    private int xEnd;
    private int yEnd;

    List<Object> resultOfAction= new ArrayList<>();
    List<Object> requestForToolCard = new ArrayList<>();

    public int getId() {
        return id;
    }

    //check that the position exists, that there is a die in the initial position, that there isn't a die in the final position, that respects bound (value and die near final position)
    @Override
    public List<Object> check(List<Object> parameterForCard) {

        board=(GameBoard)parameterForCard.get(0);
        player=(RealPlayer)parameterForCard.get(1);
        xStart=(Integer)parameterForCard.get(2);
        yStart=(Integer)parameterForCard.get(3);
        xEnd=(Integer)parameterForCard.get(4);
        yEnd=(Integer)parameterForCard.get(5);

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

        board=(GameBoard)parameterForCard.get(0);
        player=(RealPlayer)parameterForCard.get(1);
        xStart=(Integer)parameterForCard.get(2);
        yStart=(Integer)parameterForCard.get(3);
        xEnd=(Integer)parameterForCard.get(4);
        yEnd=(Integer)parameterForCard.get(5);

        player.getWindow().moveDie(xStart,yStart,xEnd,yEnd,"value");

    }

    @Override
    public List<Object> requestCard(){

        requestForToolCard.add("From which box do you want to move the die (insert number of row and number of column, like this: 1 3) ?\n");
        requestForToolCard.add("In which box do you want to move the die (insert number of row and number of column, like this: 1 3) ?\n");

        return  requestForToolCard;

    }
}
