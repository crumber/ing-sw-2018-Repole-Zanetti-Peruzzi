package repolezanettiperuzzi.domain.cards.toolcards;

import repolezanettiperuzzi.domain.ActionResult;
import repolezanettiperuzzi.model.Die;
import repolezanettiperuzzi.model.GameBoard;
import repolezanettiperuzzi.model.Player;
import repolezanettiperuzzi.model.BoxRestriction;

import java.util.List;

/**
 * Classe che rappresenta la tool card 2
 * @author Alessandro Peruzzi
 */
public class EglomiseBrush extends ToolCard {

    private int xStart;
    private int yStart;
    private int xEnd;
    private int yEnd;

    /**
     * Costruttore della classe, imposta l'id
     */
    public EglomiseBrush(){

        id=2;

    }

    /**
     * svolge i controlli sui parametri e la situazione per l'attivazione della carta
     * @param board è la game board
     * @param player indica il player che vuole attivare la carta
     * @param parameterForCard è una lista di interi che rappresentano i vari valori dei parametri per l'attivazione della carta scelti dal client
     * @return ritorna 1 se i controlli sono andati bene sennò un valore negativo che indica l'errore
     */
    //check that the position exists, that there is a die in the initial position, that there isn't a die in the final position, that respects bound (value and die near final position)
    @Override
    public ActionResult checkResult(GameBoard board, Player player, List<Integer> parameterForCard){

        xStart=parameterForCard.get(0);
        yStart=parameterForCard.get(1);
        xEnd=parameterForCard.get(2);
        yEnd=parameterForCard.get(3);

        ActionResult moveResult = checkMoveOneDieResult(board,player,xStart,yStart,xEnd,yEnd);

        if(!moveResult.isSuccess()) {

            return moveResult;

        } else{

            Die dTemp= player.getWindow().removeDie(xStart,yStart);
            ActionResult result;

            if(!player.getWindow().controlValueBoundBox(xEnd,yEnd,dTemp)){

                result = ActionResult.VALUE_RESTRICTION_VIOLATED;

            } else if(player.getWindow().controlValueBoundAdjacencies(dTemp,xEnd,yEnd)){

                result = ActionResult.ADJACENT_SAME_VALUE;

            } else{

                result = ActionResult.SUCCESS;

            }

            player.getWindow().insertDie(dTemp,xStart,yStart,BoxRestriction.BOTH);
            return result;
        }
    }

    /**
     * attiva l'effetto della carta
     * @param board è la game board
     * @param player indica il player che vuole attivare la carta
     * @param parameterForCard è una lista di interi che rappresentano i vari valori dei parametri per l'attivazione della carta scelti dal client
     */
    //move die from (xstart,ystart) into (xend,endy). respects bound of value
    @Override
    public void effect(GameBoard board, Player player, List<Integer> parameterForCard){

        xStart=parameterForCard.get(0);
        yStart=parameterForCard.get(1);
        xEnd=parameterForCard.get(2);
        yEnd=parameterForCard.get(3);

        player.getWindow().moveDie(xStart,yStart,xEnd,yEnd,BoxRestriction.VALUE);

    }
}
