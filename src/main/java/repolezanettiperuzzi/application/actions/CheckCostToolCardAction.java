package repolezanettiperuzzi.application.actions;

import repolezanettiperuzzi.domain.ActionResult;
import repolezanettiperuzzi.model.GameBoard;
import repolezanettiperuzzi.model.Player;

/**
 * Classe che rappresenta il controllo sul costo della tool card e controllo sul non utilizzo di una tool card nel turno attuale
 * @author Alessandro Peruzzi
 */
public class CheckCostToolCardAction {

    /**
     * fa un controllo se ho abbastanza FV e se non ho usato gia una tool card
     * @param board game board
     * @param player player che vuole attivare la carta
     * @param whichCard intero che indica quale tool card della game board
     * @return 1 se il controllo è andato bene sennò ritorna un valore negativo
     */
    public int checkCostToolCard(GameBoard board, Player player, int whichCard ){

        return checkCostToolCardResult(board, player, whichCard).getCode();
    }

    public ActionResult checkCostToolCardResult(GameBoard board, Player player, int whichCard ){

        if(player.getUseCardInThisTurn()){

            return ActionResult.ALREADY_USED_TOOL_CARD;

        } else if(player.getFavorTokens()<board.getCostToolCard(whichCard)){

            return ActionResult.NOT_ENOUGH_FAVOR_TOKENS;

        }

        return ActionResult.SUCCESS;
    }
}
