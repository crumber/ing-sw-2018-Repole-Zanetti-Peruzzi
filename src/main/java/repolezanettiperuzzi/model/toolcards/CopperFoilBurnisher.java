package repolezanettiperuzzi.model.toolcards;

import repolezanettiperuzzi.model.*;


public class CopperFoilBurnisher extends ToolCard{

    int id=3;

    public int getId() {
        return id;
    }

    @Override
    public int effect(){

        return 0;

    }

    public boolean effect(RealPlayer player, int xStart, int yStart, int xEnd, int yEnd){

        return player.getWindow().moveDie(xStart,yStart,xEnd,yEnd,"colour");

    }


}

