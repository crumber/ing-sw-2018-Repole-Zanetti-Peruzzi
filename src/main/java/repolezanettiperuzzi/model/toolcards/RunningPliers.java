package repolezanettiperuzzi.model.toolcards;

import repolezanettiperuzzi.model.GameBoard;
import repolezanettiperuzzi.model.Player;
import repolezanettiperuzzi.model.actions.InsertDieWithCheckAction;

import java.util.ArrayList;
import java.util.List;

public class RunningPliers extends ToolCard {

    public RunningPliers(){

        id=8;

    }

    int posDieOnDraft;
    int whichRow;
    int whichColumn;

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
