package repolezanettiperuzzi.model.toolcards;

import repolezanettiperuzzi.model.RealPlayer;
import repolezanettiperuzzi.model.Value;

public class GrozingPliers extends ToolCard {
    @Override
    public int effect() {
        return 0;
    }

    public boolean effect(RealPlayer player, int x, int y, int change){

        if(change==0){
            if(player.getWindow().getDieValue(x,y).getNumber()!=1){
                return player.getWindow().changeDieValue(x,y, Value.intToValue(player.getWindow().getDieValue(x, y).getNumber() - 1));

            }
        }else if(change==1){
            if(player.getWindow().getDieValue(x,y).getNumber()!=6){
                return player.getWindow().changeDieValue(x,y, Value.intToValue(player.getWindow().getDieValue(x, y).getNumber() + 1));

            }
        }

        return false;
    }
}
