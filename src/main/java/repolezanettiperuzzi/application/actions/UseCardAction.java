package repolezanettiperuzzi.application.actions;

import repolezanettiperuzzi.domain.ActionResult;
import repolezanettiperuzzi.model.GameBoard;
import repolezanettiperuzzi.model.Player;
import repolezanettiperuzzi.domain.cards.toolcards.FluxRemover;

import java.util.List;

/**
 * Classe che rappresenta l'utilizzo di una tool card
 * @author Alessandro Peruzzi
 */
public class UseCardAction {

    /**
     * svolge l'effetto della tool card se il check restituisce 1
     * @param player player che attiva la carta
     * @param board Game board
     * @param whichToolCard intero che indica quale carta della game board
     * @param parameterForCard lista di interi che rappresentano i parametri per la tool card
     * @return ritorna 1 se l'effetto è andato a buon fine sennò ritorna un valore negativo che indica l'errore
     */
    //For all card and tool card 11's final effect
    public int doAction(Player player, GameBoard board, int whichToolCard, List<Integer> parameterForCard){

        ActionResult resultOfAction=board.getToolCard(whichToolCard).checkResult(board,player,parameterForCard);
        System.out.println("resultOfAction "+resultOfAction.getCode());
        //if check is correct, do active action, reduce player's tokens
        if(resultOfAction.isSuccess()){

            board.getToolCard(whichToolCard).effect(board,player,parameterForCard);
            player.setUsedCardInThisTurn(true);
            player.reduceFavorTokens(board.getCostToolCard(whichToolCard));

            //increment tool card cost if its cost is 1
            if(board.getCostToolCard(whichToolCard)==1) {

                board.setCostToolCard(whichToolCard);

            }
        }

        return resultOfAction.getCode();
    }

    /**
     * si usa solo per carta tool 11 che è divisa in due parti, svolge una parte dell'effect e se va tutto bene
     * restituisce 11 per porre la successiva interrogazione al client
     * @param player player che vuole attivare la carta
     * @param board game board
     * @param whichToolCard intero che indica quale carta della game board
     * @param parameterForCard lista di interi che rappresentano i parametri per la tool card
     * @return ritorna 11 se l'effetto è andato a buon fine sennò ritorna un valore negativo che indica l'errore
     */
    //only for tool card 11
    public int doActionPreEffect(Player player, GameBoard board, int whichToolCard, List<Integer> parameterForCard){

        ActionResult resultOfAction=((FluxRemover)board.getToolCard(whichToolCard)).checkPreEffectResult(board,player,parameterForCard);

        //if check is correct, do active action, not reduce player's tokens
        if(resultOfAction.isSuccess()) {

            // return +11 -> quest for the client
            resultOfAction = ((FluxRemover)board.getToolCard(whichToolCard)).preEffectResult(board, player, parameterForCard);

        }

        return resultOfAction.getCode();
    }
}
