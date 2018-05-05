package repolezanettiperuzzi.model.toolcards;

import repolezanettiperuzzi.model.RealPlayer;

import java.util.ArrayList;
import java.util.List;

public class Lathekin extends ToolCard {

    int id=4;
    List<Object> resultOfAction= new ArrayList<>();

    public int getId() {
        return id;
    }

    public List<Object> check(RealPlayer player, int x1Start, int y1Start, int x1End, int y1End, int x2Start, int y2Start, int x2End, int y2End) {

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

    public void effect(RealPlayer player, int x1Start, int y1Start, int x1End, int y1End, int x2Start, int y2Start, int x2End, int y2End){

         player.getWindow().moveDie(x1Start,y1Start,x1End,y1End,"both");
         player.getWindow().moveDie(x2Start,y2Start,x2End,y2End,"both");

    }
}
