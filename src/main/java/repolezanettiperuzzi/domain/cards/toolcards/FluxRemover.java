package repolezanettiperuzzi.domain.cards.toolcards;

import repolezanettiperuzzi.domain.ActionResult;
import repolezanettiperuzzi.model.Die;
import repolezanettiperuzzi.model.GameBoard;
import repolezanettiperuzzi.model.Player;
import repolezanettiperuzzi.model.Value;

import java.util.List;

/**
 * Classe che rappresenta la tool card 11
 * @author Alessandro Peruzzi
 */
public class FluxRemover extends ToolCard {

    private int posDieOnDraft;
    private  int dieValue;

    /**
     * Costruttore della classe, imposta l'id
     */
    public FluxRemover(){

        id=11;

    }

    /**
     * svolge i controlli sui parametri e la situazione per l'attivazione della carta (per l'effect)
     * @param board è la game board
     * @param player indica il player che vuole attivare la carta
     * @param parameterForCard è una lista di interi che rappresentano i vari valori dei parametri per l'attivazione della carta scelti dal client
     * @return ritorna 1 se i controlli sono andati bene sennò un valore negativo che indica l'errore
     */
    @Override
    public ActionResult checkResult(GameBoard board, Player player, List<Integer> parameterForCard){

        dieValue=parameterForCard.get(0);

        if(dieValue<1 || dieValue>6){

            return ActionResult.WRONG_NUMBER;

        }

        return ActionResult.SUCCESS;
    }

    /**
     * svolge i controlli sui parametri e la situazione per l'attivazione della carta (per l'attivazione del preEffect)
     * @param board è la game board
     * @param player indica il player che vuole attivare la carta
     * @param parameterForCard è una lista di interi che rappresentano i vari valori dei parametri per l'attivazione della carta scelti dal client
     * @return ritorna 1 se i controlli sono andati bene sennò un valore negativo che indica l'errore
     */
    public int checkPreEffect(GameBoard board, Player player, List<Integer> parameterForCard){

        return checkPreEffectResult(board, player, parameterForCard).getCode();
    }

    public ActionResult checkPreEffectResult(GameBoard board, Player player, List<Integer> parameterForCard){

        posDieOnDraft=parameterForCard.get(0);

        return checkDieOnDraftResult(board,player,posDieOnDraft);
    }

    /**
     * attiva l'effetto iniziale della carta
     * @param board è la game board
     * @param player indica il player che vuole attivare la carta
     * @param parameterForCard è una lista di interi che rappresentano i vari valori dei parametri per l'attivazione della carta scelti dal client
     * @return ritorna 11 che indica ha svolto l'effetto(inserire nella dice bag il dado e pescarne uno nuovo). il valore 11 sarà usato per porre
     * la nuova richiesta per lo step successivo di questa carta
     */
    //put die in bag , remove die from draft, take another die from bag and add this die in draft's final position (change die's position in parameter for card)
    // return 11 -> new quest for client (choose value from 1 to 6)
    public int preEffect(GameBoard board, Player player, List<Integer> parameterForCard){

        return preEffectResult(board, player, parameterForCard).getCode();
    }

    public ActionResult preEffectResult(GameBoard board, Player player, List<Integer> parameterForCard){

        posDieOnDraft=parameterForCard.get(0);

        board.putDieInBag(posDieOnDraft); //put die in the bag
        board.removeDieFromDraft(posDieOnDraft); //removed die from draft
        Die newDie= board.takeDieFromBag(); // take another die from bag
        board.addDieToDraft(newDie); // add die in draft in final position

        return ActionResult.FLUX_REMOVER_SECOND_STEP_REQUIRED;
    }

    /**
     * attiva l'effetto finale della carta
     * @param board è la game board
     * @param player indica il player che vuole attivare la carta
     * @param parameterForCard è una lista di interi che rappresentano i vari valori dei parametri per l'attivazione della carta scelti dal client
     */
    // set die's value
    @Override
    public void effect(GameBoard board, Player player, List<Integer> parameterForCard){

        dieValue=parameterForCard.get(0);

        board.getDieDraft(board.getSizeDraft()-1).setValue(Value.intToValue(dieValue));

    }
}
