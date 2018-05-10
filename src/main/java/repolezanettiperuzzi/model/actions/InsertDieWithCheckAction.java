package repolezanettiperuzzi.model.actions;

import repolezanettiperuzzi.model.GameBoard;
import repolezanettiperuzzi.model.RealPlayer;

import java.util.List;

public class InsertDieWithCheckAction extends Action{

    public int doAction(RealPlayer player, GameBoard board, List<Integer> parameterForInserDie){

        int resultOfAction;
        int posDieOnDraft=parameterForInserDie.get(0);
        int whichRow=parameterForInserDie.get(1);
        int whichColumn=parameterForInserDie.get(2);

        if(board.getDieDraft(posDieOnDraft)==null){

            resultOfAction=-9;

        }else if(player.getWindow().isEmpty() && whichColumn!=0 && whichColumn!=4 && whichRow!=0 && whichRow!=3){

            resultOfAction=-27;

        }else if(player.getWindow().thereIsDie(whichRow,whichColumn)){

            resultOfAction=-3;

        }else if(!player.getWindow().controlAdjacences(whichRow,whichColumn) && !player.getWindow().isEmpty()){

            resultOfAction=-4;

        }else if(player.getWindow().controlAllBoundBox(whichRow,whichColumn,board.getDieDraft(posDieOnDraft))){

            resultOfAction=-7;

        }else if(player.getWindow().controlAllBoundAdjacences(board.getDieDraft(posDieOnDraft),whichRow,whichColumn)){

            resultOfAction=-25;

        }else if(whichRow<0 || whichRow>3 || whichColumn>4 || whichColumn<0){

            resultOfAction=-1;

        }else{

            resultOfAction=1;

        }

        if(resultOfAction==1){

            player.getWindow().insertDie(board.getDieDraft(posDieOnDraft),whichRow,whichColumn,"boat");
            board.removeDieFromDraft(posDieOnDraft);

        }

        return resultOfAction;
    }
}
