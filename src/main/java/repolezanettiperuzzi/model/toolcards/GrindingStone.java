package repolezanettiperuzzi.model.toolcards;

import repolezanettiperuzzi.model.GameBoard;
import repolezanettiperuzzi.model.Player;
import repolezanettiperuzzi.model.Value;

import java.util.List;

/**
 * Classe che rappresenta la tool card 10
 * @author Alessandro Peruzzi
 */
public class GrindingStone extends ToolCard {

    private int posDieOnDraft;

    /**
     * Costruttore della classe, imposta l'id
     */
    public GrindingStone(){

        id=10;

    }

    /**
     * svolge i controlli sui parametri e la situazione per l'attivazione della carta
     * @param board è la game board
     * @param player indica il player che vuole attivare la carta
     * @param parameterForCard è una lista di interi che rappresentano i vari valori dei parametri per l'attivazione della carta scelti dal client
     * @return ritorna 1 se i controlli sono andati bene sennò un valore negativo che indica l'errore
     */
    //control that there is die in this position on draft
    @Override
    public int check(GameBoard board, Player player, List<Integer> parameterForCard) {

        posDieOnDraft=parameterForCard.get(0);

        resultOfAction=checkDieOnDraft(board,player,posDieOnDraft);

        return  resultOfAction;
    }

    /**
     * attiva l'effetto della carta
     * @param board è la game board
     * @param player indica il player che vuole attivare la carta
     * @param parameterForCard è una lista di interi che rappresentano i vari valori dei parametri per l'attivazione della carta scelti dal client
     */
    //change die's value (in this position on draft)
    @Override
    public void effect(GameBoard board, Player player, List<Integer> parameterForCard){

        posDieOnDraft=parameterForCard.get(0);

        if(board.getDieDraft(posDieOnDraft).getValueDie()==Value.ONE){

            board.getDieDraft(posDieOnDraft).setValue(Value.SIX);

        }else if(board.getDieDraft(posDieOnDraft).getValueDie()==Value.SIX){

            board.getDieDraft(posDieOnDraft).setValue(Value.ONE);

        }else if(board.getDieDraft(posDieOnDraft).getValueDie()==Value.FIVE){

            board.getDieDraft(posDieOnDraft).setValue(Value.TWO);

        }else if(board.getDieDraft(posDieOnDraft).getValueDie()==Value.TWO){

            board.getDieDraft(posDieOnDraft).setValue(Value.FIVE);

        }else if(board.getDieDraft(posDieOnDraft).getValueDie()==Value.FOUR){

            board.getDieDraft(posDieOnDraft).setValue(Value.THREE);

        }else{

            board.getDieDraft(posDieOnDraft).setValue(Value.FOUR);

        }
    }
}
