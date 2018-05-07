package repolezanettiperuzzi.model.toolcards;

import repolezanettiperuzzi.model.GameBoard;
import repolezanettiperuzzi.model.RealPlayer;

import java.util.ArrayList;
import java.util.List;

public class TapWheel extends ToolCard {

    int id=12;

    //list of parameter: 0-board 1-player; 2-xStart die 1; 3-yStart die 1; 4-xEnd die 1; 5-yEnd die 1; 6-xStart die 2; 7-yStart die 2; 8-xEnd die 2; 9-yEnd die 2; 10-which round 11-which die on round track
    private GameBoard board;
    private RealPlayer player;
    private int xStart1;
    private int yStart1;
    private int xEnd1;
    private int yEnd1;
    private int xStart2;
    private int yStart2;
    private int xEnd2;
    private int yEnd2;
    private int whichRound;
    private int whichDieOnRoundTrack;

    List<Object> resultOfAction= new ArrayList<>();
    List<Object> requestForToolCard = new ArrayList<>();

    public int getId() {
        return id;
    }

    //control for all dice (two) that exist start/end position,that each dice have same colour , that there is die in start position, that there isn't die in end position, control that die respects all bound(colour value and there is die near end position)
    @Override
    public List<Object> check(List<Object> parameterForCard) {

        board=(GameBoard)parameterForCard.get(0);
        player=(RealPlayer)parameterForCard.get(1);
        xStart1=(Integer)parameterForCard.get(2);
        yStart1=(Integer)parameterForCard.get(3);
        xEnd1=(Integer)parameterForCard.get(4);
        yEnd1=(Integer)parameterForCard.get(5);
        xStart2=(Integer)parameterForCard.get(6);
        yStart2=(Integer)parameterForCard.get(7);
        xEnd2=(Integer)parameterForCard.get(8);
        yEnd2=(Integer)parameterForCard.get(9);
        whichRound=(Integer)parameterForCard.get(10);
        whichDieOnRoundTrack=(Integer)parameterForCard.get(11);

        if (xStart1 < 0 || xStart1 > 3 || yStart1 < 0 || yStart1 > 4 || xEnd1 < 0 || xEnd1 > 3 || yEnd1 < 0 || yEnd1 > 4) {

            resultOfAction.add(-1);
            resultOfAction.add("you don't move die 1 because your star position or finish position aren't exist!!!!");

        } else if (!player.getWindow().thereIsDie(xStart1, yStart1)) {

            resultOfAction.add(-1);
            resultOfAction.add("you don't move die 1 because you don't choose a box without die");

        } else if (player.getWindow().thereIsDie(xEnd1, yEnd1)) {
            resultOfAction.add(-1);
            resultOfAction.add("you don't move die 1 into box because there is a die in this box");

        } else if (!player.getWindow().controlAdjacences(xEnd1, yEnd1)) {

            resultOfAction.add(-1);
            resultOfAction.add("you don't move die 1 into box because there are not any die near this box");

        } else if (!player.getWindow().controlAllBoundBox(xEnd1, yEnd1, player.getWindow().getDieFromBoardBox(xStart1, yStart1))) {

            resultOfAction.add(-1);
            resultOfAction.add("you don't move die 1 into box because your die don't respect bound");

        } else if (!player.getWindow().getDieColour(xStart1, yStart1).equals(player.getWindow().getDieColour(xStart2, yStart2))&& (board.getDieFromRoundTrack(whichRound,whichDieOnRoundTrack).getColourDie().equals(player.getWindow().getDieColour(xStart1, yStart1)))){

            resultOfAction.add(-1);
            resultOfAction.add("you don't move dice because  they do not have the same color");

        } else if (xStart2<0 || xStart2>3 || yStart2<0 || yStart2>4 || xEnd2<0 || xEnd2>3 || yEnd2<0 || yEnd2>4) {

            resultOfAction.add(-1);
            resultOfAction.add("you don't move die 2 because your star position or finish position aren't exist!!!!");

        } else if (!player.getWindow().thereIsDie(xStart2, yStart2)) {

            resultOfAction.add(-1);
            resultOfAction.add("you don't move die 2 because you do not choose a box without die");

        } else if (player.getWindow().thereIsDie(xEnd2, yEnd2)) {

            resultOfAction.add(-1);
            resultOfAction.add("you don't move die 2 into box because there is a die in this box");

        } else if (!player.getWindow().controlAdjacences(xEnd2, yEnd2)) {

            resultOfAction.add(-1);
            resultOfAction.add("you don't move die 2 into box because there are not any die near this box");

        } else if (!player.getWindow().controlAllBoundBox(xEnd2, yEnd2, player.getWindow().getDieFromBoardBox(xStart2, yStart2))) {

            resultOfAction.add(-1);
            resultOfAction.add("you don't move die 2 into box because your die do not respect bound");

        }else {

            resultOfAction.add(1);

        }

        return resultOfAction;
    }

    //move both dice (with all bound)
    @Override
    public void effect(List<Object> parameterForCard){

        board=(GameBoard)parameterForCard.get(0);
        player=(RealPlayer)parameterForCard.get(1);
        xStart1=(Integer)parameterForCard.get(2);
        yStart1=(Integer)parameterForCard.get(3);
        xEnd1=(Integer)parameterForCard.get(4);
        yEnd1=(Integer)parameterForCard.get(5);
        xStart2=(Integer)parameterForCard.get(6);
        yStart2=(Integer)parameterForCard.get(7);
        xEnd2=(Integer)parameterForCard.get(8);
        yEnd2=(Integer)parameterForCard.get(9);
        whichRound=(Integer)parameterForCard.get(10);
        whichDieOnRoundTrack=(Integer)parameterForCard.get(11);

        player.getWindow().moveDie(xStart1,yStart1,xEnd1,yEnd1,"both");
        player.getWindow().moveDie(xStart2,yStart2,xEnd2,yEnd2,"both");

    }

    @Override
    public List<Object> requestCard(){

        requestForToolCard.add("From which box do you want to move the die 1 (insert number of row and number of column, like this: 1 3) ?\n");
        requestForToolCard.add("In which box do you want to move the die 1 (insert number of row and number of column, like this: 1 3) ?\n");
        requestForToolCard.add("From which box do you want to move the die 2 (insert number of row and number of column, like this: 1 3) ?\n");
        requestForToolCard.add("In which box do you want to move the die 2 (insert number of row and number of column, like this: 1 3) ?\n");
        requestForToolCard.add("Which die on round track (insert number of round and number of die position on round, like this: 3 2 -> round 3 die 2) ?\n");
        return  requestForToolCard;

    }
}