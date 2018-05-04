package repolezanettiperuzzi.model.toolcards;

import repolezanettiperuzzi.model.Colour;
import repolezanettiperuzzi.model.RealPlayer;

public class TapWheel extends ToolCard {

    int id=12;

    public int getId() {
        return id;
    }

    public boolean effect(RealPlayer player, int xStart1, int yStart1, int xEnd1, int yEnd1, int xStart2, int yStart2, int xEnd2, int yEnd2, Colour colour){

        if(player.getWindow().getDieColour(xStart1,yStart1).equals(player.getWindow().getDieColour(xStart2,yStart2))
                &&(colour.equals(player.getWindow().getDieColour(xStart1,yStart1)))){

            return (player.getWindow().moveDie(xStart1,yStart1,xEnd1,yEnd1,"both"))
                    &&(player.getWindow().moveDie(xStart2,yStart2,xEnd2,yEnd2,"both"));

        }

        return false;
    }
}