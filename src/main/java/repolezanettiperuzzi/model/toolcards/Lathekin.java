package repolezanettiperuzzi.model.toolcards;

import repolezanettiperuzzi.model.RealPlayer;

public class Lathekin extends ToolCard {
    @Override
    public int effect() {
        return 0;
    }


//chiama nel controllore due volte il metodo
    public boolean effect(RealPlayer player, int xStart, int yStart, int xEnd, int yEnd){

        return player.getWindow().moveDie(xStart,yStart,xEnd,yEnd,"both");

    }
}
