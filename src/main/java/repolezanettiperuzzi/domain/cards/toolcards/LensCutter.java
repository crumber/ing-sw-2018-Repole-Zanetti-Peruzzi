package repolezanettiperuzzi.domain.cards.toolcards;

import repolezanettiperuzzi.domain.ActionResult;
import repolezanettiperuzzi.model.Die;
import repolezanettiperuzzi.model.GameBoard;
import repolezanettiperuzzi.model.Player;

import java.util.List;

/**
 * Classe che rappresenta la tool card 5
 * @author Alessandro Peruzzi
 */
public class LensCutter extends ToolCard {

    private int posDieOnDraft;
    private int whichRound;
    private int whichDieRound;

    /**
     * Costruttore della classe, imposta l'id
     */
    public LensCutter(){

        id=5;

    }

    /**
     * svolge i controlli sui parametri e la situazione per l'attivazione della carta
     * @param board è la game board
     * @param player indica il player che vuole attivare la carta
     * @param parameterForCard è una lista di interi che rappresentano i vari valori dei parametri per l'attivazione della carta scelti dal client
     * @return ritorna 1 se i controlli sono andati bene sennò un valore negativo che indica l'errore
     */
    //check that there is a die in this position on draft and check that there is a die in this position on round track
    @Override
    public ActionResult checkResult(GameBoard board, Player player, List<Integer> parameterForCard){

        posDieOnDraft=parameterForCard.get(0);
        whichRound=parameterForCard.get(1);
        whichDieRound=parameterForCard.get(2);

        ActionResult draftResult = checkDieOnDraftResult(board,player,posDieOnDraft);

        if(!draftResult.isSuccess()){

            return draftResult;

        }

        return checkDieOnRoundTrackResult(board,player,whichRound,whichDieRound);
    }

    /**
     * attiva l'effetto della carta
     * @param board è la game board
     * @param player indica il player che vuole attivare la carta
     * @param parameterForCard è una lista di interi che rappresentano i vari valori dei parametri per l'attivazione della carta scelti dal client
     */
    //switch dice (one on round track and one on draft)
    @Override
    public void effect(GameBoard board, Player player, List<Integer> parameterForCard){

        posDieOnDraft=parameterForCard.get(0);
        whichRound=parameterForCard.get(1);
        whichDieRound=parameterForCard.get(2);

        Die dieOnDraft= board.getDieDraft(posDieOnDraft);
        Die dieOnRoundTrack= board.getDieFromRoundTrack(whichRound,whichDieRound);

        board.setDieDraft(posDieOnDraft,dieOnRoundTrack); //put die on round track in the draft
        board.setDieToRoundTrack(whichRound,whichDieRound,dieOnDraft); //put die on draft in the round track (pos: which round, which die)

    }
}
