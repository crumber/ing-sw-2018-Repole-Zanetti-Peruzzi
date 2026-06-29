package repolezanettiperuzzi.application.actions;

import repolezanettiperuzzi.domain.ActionResult;
import repolezanettiperuzzi.model.GameBoard;
import repolezanettiperuzzi.model.Player;
import repolezanettiperuzzi.model.BoxRestriction;

import java.util.List;

/**
 * Classe che rappresenta l'inserimento di un dado
 * @author Alessandro Peruzzi
 */
public class InsertDieWithCheckAction{

    /**
     * esegue il controllo per inserire il dado
     * @param player player che vuole inserire il dado
     * @param board game board
     * @param posDieOnDraft int che indica il dado del draft
     * @param whichRow int che indica in che riga vuole mettere il dado
     * @param whichColumn int che indica in che colonna vuole mettere il dado
     * @param card8 booleano che indica se è la carta numero 8 (true se lo è)
     * @return ritorna 1 se il controllo è andato a buon fine sennò ritorna un numero negativo che indica l'errore
     */
    public int checkInsert(Player player, GameBoard board,int posDieOnDraft, int whichRow, int whichColumn, boolean card8){

        return checkInsertResult(player, board, posDieOnDraft, whichRow, whichColumn, card8).getCode();
    }

    public ActionResult checkInsertResult(Player player, GameBoard board,int posDieOnDraft, int whichRow, int whichColumn, boolean card8){

        if(!(card8) && (player.getInsertDieInThisTurn())){

            return ActionResult.ALREADY_INSERTED_DIE;

        } else if(board.getDieDraft(posDieOnDraft)==null){

            return ActionResult.EMPTY_DRAFT_POSITION;

        }else if(whichRow<0 || whichRow>player.getWindow().numRow()-1 || whichColumn>player.getWindow().numColumn()-1 || whichColumn<0){

            return ActionResult.STARTING_OR_FINAL_POSITION_NOT_EXIST;

        }else if(player.getWindow().isEmpty() && whichColumn!=0 && whichColumn!=player.getWindow().numColumn()-1 && whichRow!=0 && whichRow!=player.getWindow().numRow()-1){

            return ActionResult.FIRST_PLACEMENT_NOT_BOUNDARY;

        }else if(player.getWindow().thereIsDie(whichRow,whichColumn)){

            return ActionResult.POSITION_OCCUPIED;

        }else if(!player.getWindow().controlAdjacencies(whichRow,whichColumn) && !player.getWindow().isEmpty()){

            return ActionResult.NO_ADJACENT_DIE;

        }else if(!player.getWindow().controlAllBoundBox(whichRow,whichColumn,board.getDieDraft(posDieOnDraft))){

            return ActionResult.BOX_RESTRICTION_VIOLATED;

        }else if(player.getWindow().controlAllBoundAdjacencies(board.getDieDraft(posDieOnDraft),whichRow,whichColumn)){

            return ActionResult.ADJACENT_SAME_COLOUR_OR_VALUE;

        }

        return ActionResult.SUCCESS;
    }

    /**
     * inserisce il dado nella window del player e toglie il dado dal draft
     * @param player player che vuole inserire il dado
     * @param board Game board
     * @param parameterForInserDie lista di interi che indicano i parametri per inserire il dado
     * @return 1 effetto attivato
     */
    public int doAction(Player player, GameBoard board, List<Integer> parameterForInserDie){

        int posDieOnDraft=parameterForInserDie.get(0);
        int whichRow=parameterForInserDie.get(1);
        int whichColumn=parameterForInserDie.get(2);
        ActionResult resultOfAction=checkInsertResult(player,board,posDieOnDraft,whichRow,whichColumn,false);

        if(resultOfAction.isSuccess()){

            player.getWindow().insertDie(board.getDieDraft(posDieOnDraft),whichRow,whichColumn,BoxRestriction.BOTH);
            board.removeDieFromDraft(posDieOnDraft);
            player.setInsertDieInThisTurn(true);

        }

        return resultOfAction.getCode();
    }
}
