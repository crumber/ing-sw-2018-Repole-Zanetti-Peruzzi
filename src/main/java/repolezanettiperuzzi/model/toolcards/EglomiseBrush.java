package repolezanettiperuzzi.model.toolcards;

import repolezanettiperuzzi.model.RealPlayer;

import java.util.ArrayList;
import java.util.List;

public class EglomiseBrush extends ToolCard {


    int id=2;
    List<Object> resultOfAction= new ArrayList<>();

    public int getId() {
        return id;
    }

    public List<Object> check(RealPlayer player, int xStart, int yStart, int xEnd, int yEnd) {

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

    public void effect(RealPlayer player, int xStart, int yStart, int xEnd, int yEnd){

         player.getWindow().moveDie(xStart,yStart,xEnd,yEnd,"value");

    }
}
