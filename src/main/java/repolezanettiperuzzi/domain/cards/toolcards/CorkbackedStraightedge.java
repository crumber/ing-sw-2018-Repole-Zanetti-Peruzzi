package repolezanettiperuzzi.domain.cards.toolcards;

import repolezanettiperuzzi.domain.ActionResult;
import repolezanettiperuzzi.model.GameBoard;
import repolezanettiperuzzi.model.Player;
import repolezanettiperuzzi.model.BoxRestriction;

import java.util.List;

/**
 * Classe che rappresenta la tool card 9
 * @author Alessandro Peruzzi
 */
public class CorkbackedStraightedge extends ToolCard {

    private int posDieOnDraft;
    private int whichRow;
    private int whichColumn;

    /**
     * Costruttore della classe, imposta l'id
     */
    public CorkbackedStraightedge(){

        id=9;

    }

    /**
     * svolge i controlli sui parametri e la situazione per l'attivazione della carta
     * @param board è la game board
     * @param player indica il player che vuole attivare la carta
     * @param parameterForCard è una lista di interi che rappresentano i vari valori dei parametri per l'attivazione della carta scelti dal client
     * @return ritorna 1 se i controlli sono andati bene sennò un valore negativo che indica l'errore
     */
    //control that there is a die in draft position, that exist window's position, that there isn't die in this position, there aren't dice near this position and die respect bound of box
    @Override
    public ActionResult checkResult(GameBoard board, Player player, List<Integer> parameterForCard) {

        posDieOnDraft=parameterForCard.get(0);
        whichRow=parameterForCard.get(1);
        whichColumn=parameterForCard.get(2);

        ActionResult draftResult = checkDieOnDraftResult(board,player,posDieOnDraft);

        if (!draftResult.isSuccess()) {

            return draftResult;

        } else if(whichRow<0 || whichRow>player.getWindow().numRow()-1 || whichColumn<0 || whichColumn>player.getWindow().numColumn()-1){

            return ActionResult.STARTING_OR_FINAL_POSITION_NOT_EXIST;

        } else if (player.getWindow().thereIsDie(whichRow, whichColumn)) {

            return ActionResult.POSITION_OCCUPIED;

        } else if (player.getWindow().controlAdjacencies(whichRow, whichColumn)) {

            return ActionResult.DIE_NEXT_TO_POSITION;

        } else if(!player.getWindow().controlAllBoundBox(whichRow,whichColumn,board.getDieDraft(posDieOnDraft))) {

            return ActionResult.BOX_RESTRICTION_VIOLATED;

        }

        return ActionResult.SUCCESS;
    }

    /**
     * attiva l'effetto della carta: inserisce un dado dove non c'è un'altro dado e rimuove il dado dal draft
     * @param board è la game board
     * @param player indica il player che vuole attivare la carta
     * @param parameterForCard è una lista di interi che rappresentano i vari valori dei parametri per l'attivazione della carta scelti dal client
     */
    //insert die in box where there aren't dice near , remove die from draft
    @Override
    public void effect(GameBoard board, Player player, List<Integer> parameterForCard){

        posDieOnDraft=parameterForCard.get(0);
        whichRow=parameterForCard.get(1);
        whichColumn=parameterForCard.get(2);

        player.getWindow().insertDie(board.getDieDraft(posDieOnDraft),whichRow,whichColumn,BoxRestriction.BOTH);
        board.removeDieFromDraft(posDieOnDraft);

    }
}
