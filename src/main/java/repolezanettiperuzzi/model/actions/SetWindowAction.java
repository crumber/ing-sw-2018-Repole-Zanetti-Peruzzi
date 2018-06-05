package repolezanettiperuzzi.model.actions;

import repolezanettiperuzzi.model.Player;
import repolezanettiperuzzi.model.Window;

public class SetWindowAction extends Action {

    public void doAction(Player player, Window choice){

        player.setWindow(choice);
        assignFavorToken(player);

    }

    private void  assignFavorToken(Player player){

        player.setFavorTokens(player.getWindow().getFTokens());

    }
}
