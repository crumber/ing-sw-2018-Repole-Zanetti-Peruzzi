package repolezanettiperuzzi.model.toolcards;

import repolezanettiperuzzi.model.RealPlayer;

public class EglomiseBrush extends ToolCard {
    @Override
    public int effect() {
        return 0;
    }

    public boolean effect(RealPlayer player, int xStart, int yStart, int xEnd, int yEnd){

        return player.getWindow().moveDie(xStart,yStart,xEnd,yEnd,"value");

    }
}
