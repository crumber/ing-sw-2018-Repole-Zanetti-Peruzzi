package repolezanettiperuzzi.domain.cards.toolcards;

import repolezanettiperuzzi.domain.ActionResult;
import repolezanettiperuzzi.model.GameBoard;
import repolezanettiperuzzi.model.Player;
import repolezanettiperuzzi.model.Value;

import java.util.List;

/**
 * Classe che rappresenta la tool card 1
 * @author Alessandro Peruzzi
 */
public class GrozingPliers extends ToolCard {

    private int numDieFromDraft;
    private int change;

    /**
     * Costruttore della classe, imposta l'id
     */
    public GrozingPliers(){

        id=1;

    }

    /**
     * svolge i controlli sui parametri e la situazione per l'attivazione della carta
     * @param board è la game board
     * @param player indica il player che vuole attivare la carta
     * @param parameterForCard è una lista di interi che rappresentano i vari valori dei parametri per l'attivazione della carta scelti dal client
     * @return ritorna 1 se i controlli sono andati bene sennò un valore negativo che indica l'errore
     */
    //control thant change is 1 or 0, that there is a die in this position on draft, that die can increment (if is six no) /decrement (if is one no)
    @Override
    public ActionResult checkResult(GameBoard board, Player player, List<Integer> parameterForCard){

        numDieFromDraft=parameterForCard.get(0);
        change=parameterForCard.get(1);

        if(change>1 || change<0){

            return ActionResult.CHOICE_NOT_EXIST;

        }

        ActionResult draftResult = checkDieOnDraftResult(board,player,numDieFromDraft);

        if(!draftResult.isSuccess()){

            return draftResult;

        }else if(change==0 && board.getDieDraft(numDieFromDraft).getValueDie().getNumber()!=1){

            return ActionResult.SUCCESS;

        }else if(change==0){

            return ActionResult.DECREASE_IS_MINIMUM;
        }

        if(change==1 && board.getDieDraft(numDieFromDraft).getValueDie().getNumber()!=6){

            return ActionResult.SUCCESS;

        }else if(change==1){

            return ActionResult.INCREASE_IS_MAXIMUM;
        }

        return ActionResult.SUCCESS;
    }

    /**
     * attiva l'effetto della carta
     * @param board è la game board
     * @param player indica il player che vuole attivare la carta
     * @param parameterForCard è una lista di interi che rappresentano i vari valori dei parametri per l'attivazione della carta scelti dal client
     */
    //increment die (if is six no) or decrement die (if is one no)
    @Override
    public void effect(GameBoard board, Player player, List<Integer> parameterForCard) {

        numDieFromDraft=parameterForCard.get(0);
        change=parameterForCard.get(1);

        if (change == 0) {

            board.getDieDraft(numDieFromDraft).setValue(Value.intToValue((board.getDieDraft(numDieFromDraft).getValueDie().getNumber()) - 1));

        }

        if (change == 1) {

            board.getDieDraft(numDieFromDraft).setValue(Value.intToValue((board.getDieDraft(numDieFromDraft).getValueDie().getNumber()) + 1));

        }
    }
}
