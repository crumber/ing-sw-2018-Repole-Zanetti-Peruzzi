package repolezanettiperuzzi.model.toolcards;

import repolezanettiperuzzi.model.GameBoard;
import repolezanettiperuzzi.model.Player;

import java.util.List;

public class CorkbackedStraightedge extends ToolCard {

    int id=9;

    int resultOfAction;

    private int posDieOnDraft;
    private int whichRow;
    private int whichColumn;

    public int getId() {
        return id;
    }

    //control that there is a die in draft position, that exist window's position, that there isn't die in this position, there aren't dice near this position and die respect bound of box
    @Override
    public int check(GameBoard board, Player player, List<Integer> parameterForCard) {

        posDieOnDraft=parameterForCard.get(0);
        whichRow=parameterForCard.get(1);
        whichColumn=parameterForCard.get(2);

        if (checkDieOnDraft(board,player,posDieOnDraft) != 1) {

            resultOfAction=checkDieOnDraft(board,player,posDieOnDraft);

        } else if(whichRow<0 || whichRow>3 || whichColumn<0 || whichColumn>4){

            resultOfAction=-1;

        } else if (player.getWindow().thereIsDie(whichRow, whichColumn)) {

            resultOfAction=-3;

        } else if (player.getWindow().controlAdjacences(whichRow, whichColumn)) {

            resultOfAction=-10;

        } else if(!player.getWindow().controlAllBoundBox(whichRow,whichColumn,board.getDieDraft(posDieOnDraft))) {

            resultOfAction=-7;

        }else if(player.getWindow().controlAllBoundAdjacences(board.getDieDraft(posDieOnDraft),whichRow,whichColumn)){

            resultOfAction=-25;

        } else{

            resultOfAction=1;

        }

        return resultOfAction;
    }

    //insert die in box where there aren't dice near , remove die from draft
    @Override
    public void effect(GameBoard board, Player player, List<Integer> parameterForCard){

        posDieOnDraft=parameterForCard.get(0);
        whichRow=parameterForCard.get(1);
        whichColumn=parameterForCard.get(2);

        player.getWindow().insertDie(board.getDieDraft(posDieOnDraft),whichRow,whichColumn,"both");
        board.removeDieFromDraft(posDieOnDraft);

    }
}
