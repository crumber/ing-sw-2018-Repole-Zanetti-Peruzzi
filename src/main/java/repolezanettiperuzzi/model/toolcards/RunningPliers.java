package repolezanettiperuzzi.model.toolcards;

import repolezanettiperuzzi.model.GameBoard;
import repolezanettiperuzzi.model.Player;
import repolezanettiperuzzi.model.actions.InsertDieWithCheckAction;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe che rappresenta la tool card 8
 * @author Alessandro Peruzzi
 */
public class RunningPliers extends ToolCard {

    /**
     * Costruttore della classe, imposta l'id
     */
    public RunningPliers(){

        id=8;

    }

    int posDieOnDraft;
    int whichRow;
    int whichColumn;

    /**
     * svolge i controlli sui parametri e la situazione per l'attivazione della carta
     * @param board è la game board
     * @param player indica il player che vuole attivare la carta
     * @param parameterForCard è una lista di interi che rappresentano i vari valori dei parametri per l'attivazione della carta scelti dal client
     * @return ritorna 1 se i controlli sono andati bene sennò un valore negativo che indica l'errore
     */
    @Override
    public void effect(GameBoard board, Player player, List<Integer> parameterForCard){

        posDieOnDraft=parameterForCard.get(0);
        whichRow=parameterForCard.get(1);
        whichColumn=parameterForCard.get(2);

        player.getWindow().insertDie(board.getDieDraft(posDieOnDraft),whichRow,whichColumn,"both");
        board.removeDieFromDraft(posDieOnDraft);
        player.setInsertDieInThisTurn(true);
        player.incrTurn();

    }

    /**
     * attiva l'effetto della carta
     * @param board è la game board
     * @param player indica il player che vuole attivare la carta
     * @param parameterForCard è una lista di interi che rappresentano i vari valori dei parametri per l'attivazione della carta scelti dal client
     */
    public int check(GameBoard board, Player player, List<Integer> parameterForCard){

        posDieOnDraft=parameterForCard.get(0);
        whichRow=parameterForCard.get(1);
        whichColumn=parameterForCard.get(2);

        if(player.getTurn()!=0){

            resultOfAction=-30;

        }else if(!player.getInsertDieInThisTurn()){

            resultOfAction=-31;

        }else{

            InsertDieWithCheckAction controlInsert= new InsertDieWithCheckAction();
            resultOfAction=controlInsert.checkInsert(player,board,posDieOnDraft,whichRow,whichColumn,true);

        }

      return resultOfAction;
    }

}
