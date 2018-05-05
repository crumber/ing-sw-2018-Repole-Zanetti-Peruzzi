package repolezanettiperuzzi.model.toolcards;

import repolezanettiperuzzi.model.Colour;
import repolezanettiperuzzi.model.GameBoard;
import repolezanettiperuzzi.model.RealPlayer;

import java.util.ArrayList;
import java.util.List;

public class TapWheel extends ToolCard {

    int id=12;
    List<Object> resultOfAction= new ArrayList<>();

    public int getId() {
        return id;
    }

    public List<Object> check(RealPlayer player, int xStart1, int yStart1, int xEnd1, int yEnd1, int xStart2, int yStart2, int xEnd2, int yEnd2, Colour colour) {

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

        } else if (player.getWindow().getDieColour(xStart1, yStart1).equals(player.getWindow().getDieColour(xStart2, yStart2))&& (colour.equals(player.getWindow().getDieColour(xStart1, yStart1)))){

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

    public void effect(RealPlayer player, int xStart1, int yStart1, int xEnd1, int yEnd1, int xStart2, int yStart2, int xEnd2, int yEnd2){

            player.getWindow().moveDie(xStart1,yStart1,xEnd1,yEnd1,"both");
            player.getWindow().moveDie(xStart2,yStart2,xEnd2,yEnd2,"both");

        }
    }
}