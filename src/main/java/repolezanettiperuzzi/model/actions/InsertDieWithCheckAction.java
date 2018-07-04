package repolezanettiperuzzi.model.actions;

import repolezanettiperuzzi.model.GameBoard;
import repolezanettiperuzzi.model.Player;

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

        int resultOfAction;

        if(!(card8) && (player.getInsertDieInThisTurn())){

            resultOfAction=-28;

        } else if(board.getDieDraft(posDieOnDraft)==null){

            resultOfAction=-9;

        }else if(whichRow<0 || whichRow>player.getWindow().numRow()-1 || whichColumn>player.getWindow().numColumn()-1 || whichColumn<0){

            resultOfAction=-1;

        }else if(player.getWindow().isEmpty() && whichColumn!=0 && whichColumn!=player.getWindow().numColumn()-1 && whichRow!=0 && whichRow!=player.getWindow().numRow()-1){

            resultOfAction=-27;

        }else if(player.getWindow().thereIsDie(whichRow,whichColumn)){

            resultOfAction=-3;

        }else if(!player.getWindow().controlAdjacencies(whichRow,whichColumn) && !player.getWindow().isEmpty()){

            resultOfAction=-4;

        }else if(!player.getWindow().controlAllBoundBox(whichRow,whichColumn,board.getDieDraft(posDieOnDraft))){

            resultOfAction=-7;

        }else if(player.getWindow().controlAllBoundAdjacencies(board.getDieDraft(posDieOnDraft),whichRow,whichColumn)){

            resultOfAction=-25;

        }else{

            resultOfAction=1;

        }

        return resultOfAction;
    }

    /**
     * inserisce il dado nella window del player e toglie il dado dal draft
     * @param player player che vuole inserire il dado
     * @param board Game board
     * @param parameterForInserDie lista di interi che indicano i parametri per inserire il dado
     * @return 1 effetto attivato
     */
    public int doAction(Player player, GameBoard board, List<Integer> parameterForInserDie){

        int resultOfAction;
        int posDieOnDraft=parameterForInserDie.get(0);
        int whichRow=parameterForInserDie.get(1);
        int whichColumn=parameterForInserDie.get(2);
        resultOfAction=checkInsert(player,board,posDieOnDraft,whichRow,whichColumn,false);

        if(resultOfAction==1){

            player.getWindow().insertDie(board.getDieDraft(posDieOnDraft),whichRow,whichColumn,"both");
            board.removeDieFromDraft(posDieOnDraft);
            player.setInsertDieInThisTurn(true);

        }

        return resultOfAction;
    }
}
