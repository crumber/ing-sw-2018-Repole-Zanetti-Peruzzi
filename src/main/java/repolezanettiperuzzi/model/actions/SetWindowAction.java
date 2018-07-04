package repolezanettiperuzzi.model.actions;

import repolezanettiperuzzi.model.Player;
import repolezanettiperuzzi.model.Window;

/**
 * Classe che rappresenta la scelta della window da parte del client
 * @author Alessandro Peruzzi
 */
public class SetWindowAction{

    /**
     * assegna la window scelta al player e i respettivi FV
     * @param player player che ha scelto la window
     * @param choice window scelta
     */
    public void doAction(Player player, Window choice){

        player.setWindow(choice);
        assignFavorToken(player);

    }

    /**
     * metodo che assegna i FV della window del player al player
     * @param player player a cui assegnare i FV
     */
    private void assignFavorToken(Player player){

        player.setFavorTokens(player.getWindow().getFTokens());

    }
}
