package repolezanettiperuzzi.domain.cards.toolcards;

import repolezanettiperuzzi.domain.ActionResult;
import repolezanettiperuzzi.model.GameBoard;
import repolezanettiperuzzi.model.Player;
import repolezanettiperuzzi.model.BoxRestriction;
import repolezanettiperuzzi.application.actions.InsertDieWithCheckAction;

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
     */
    @Override
    public void effect(GameBoard board, Player player, List<Integer> parameterForCard){

        posDieOnDraft=parameterForCard.get(0);
        whichRow=parameterForCard.get(1);
        whichColumn=parameterForCard.get(2);

        player.getWindow().insertDie(board.getDieDraft(posDieOnDraft),whichRow,whichColumn,BoxRestriction.BOTH);
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
    public ActionResult checkResult(GameBoard board, Player player, List<Integer> parameterForCard){

        posDieOnDraft=parameterForCard.get(0);
        whichRow=parameterForCard.get(1);
        whichColumn=parameterForCard.get(2);

        if(player.getTurn()!=0){

            return ActionResult.NOT_FIRST_TURN;

        }else if(!player.getInsertDieInThisTurn()){

            return ActionResult.DIE_NOT_INSERTED_IN_TURN;

        }else{

            InsertDieWithCheckAction controlInsert= new InsertDieWithCheckAction();
            return controlInsert.checkInsertResult(player,board,posDieOnDraft,whichRow,whichColumn,true);

        }
    }

}
