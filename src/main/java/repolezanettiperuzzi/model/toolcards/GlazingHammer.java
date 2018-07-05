package repolezanettiperuzzi.model.toolcards;

import repolezanettiperuzzi.model.GameBoard;
import repolezanettiperuzzi.model.Player;

import java.util.List;

/**
 * Classe che rappresenta la tool card 7
 * @author Alessandro Peruzzi
 */
public class GlazingHammer extends ToolCard {

    /**
     * Costruttore della classe, imposta l'id
     */
    public GlazingHammer(){

        id=7;

    }

    /**
     * svolge i controlli sui parametri e la situazione per l'attivazione della carta
     * @param board è la game board
     * @param player indica il player che vuole attivare la carta
     * @param parameterForCard è una lista di interi che rappresentano i vari valori dei parametri per l'attivazione della carta scelti dal client
     * @return ritorna 1 se i controlli sono andati bene sennò un valore negativo che indica l'errore
     */
    //control that is second turn of round and that player don't insert die in this turn
    @Override
    public int check(GameBoard board, Player player, List<Integer> parameterForCard) {

        if (player.getTurn()!=1) {

            resultOfAction=-12;

        }else if(player.getInsertDieInThisTurn()){

            resultOfAction=-28;

        }
        else {

            resultOfAction=1;

        }

        return resultOfAction;
    }

    /**
     * attiva l'effetto della carta
     * @param board è la game board
     * @param player indica il player che vuole attivare la carta
     * @param parameterForCard è una lista di interi che rappresentano i vari valori dei parametri per l'attivazione della carta scelti dal client
     */
    //roll all dice on draft
    @Override
    public void effect(GameBoard board, Player player, List<Integer> parameterForCard){

        int numDiceDraft= board.getSizeDraft();
        for(int i=0;i<numDiceDraft;i++){

            board.getDieDraft(i).rollDie();

        }
    }
}
