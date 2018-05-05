package repolezanettiperuzzi.model.toolcards;

import repolezanettiperuzzi.model.*;

import java.util.ArrayList;
import java.util.List;


public class CopperFoilBurnisher extends ToolCard{

    int id=3;
    List<Object> resultOfAction = new ArrayList<>();

    public List<Object> check(RealPlayer player, int xStart, int yStart, int xEnd, int yEnd){

        if(xStart<0 || xStart>3 || yStart<0 || yStart>4 || xEnd<0 || xEnd>3 || yEnd<0 || yEnd>4){

            resultOfAction.add(-1);
            resultOfAction.add("you don't move die because your star position or finish position aren't exist!!!!");

        } else if (!player.getWindow().thereIsDie(xStart, yStart)) {

            resultOfAction.add(-1);
            resultOfAction.add("you don't move die because you don't choose a box with die");

        } else if (player.getWindow().thereIsDie(xEnd, yEnd)) {

            resultOfAction.add(-1);
            resultOfAction.add("you don't move die into box because there's a die in this box");

        } else if (!player.getWindow().controlAdjacences(xEnd, yEnd)) {

            resultOfAction.add(-1);
            resultOfAction.add("you don't move die into box because there aren't any die near this box");

        } else if(!player.getWindow().controlColourBoundBox(xEnd,yEnd,player.getWindow().getDieFromBoardBox(xStart,yStart))){

            resultOfAction.add(-1);
            resultOfAction.add("you don't move die into box because your die don't respect colour bound");
        }
        else{

            resultOfAction.add(1);
        }

        return resultOfAction;
    }

    public int getId() {
        return id;
    }


    public void effect(RealPlayer player, int xStart, int yStart, int xEnd, int yEnd){

         player.getWindow().moveDie(xStart,yStart,xEnd,yEnd,"colour");

    }


}

