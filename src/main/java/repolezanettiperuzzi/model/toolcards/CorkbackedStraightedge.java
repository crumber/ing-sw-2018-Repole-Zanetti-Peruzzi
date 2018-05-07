package repolezanettiperuzzi.model.toolcards;

import repolezanettiperuzzi.model.GameBoard;
import repolezanettiperuzzi.model.RealPlayer;

import java.util.ArrayList;
import java.util.List;

public class CorkbackedStraightedge extends ToolCard {

    int id=9;

    //list of parameter: 0-game board 1-player 2-position die on draft 3-which row 4-which column
    private GameBoard board;
    private RealPlayer player;
    private int posDieOnDraft;
    private int whichRow;
    private int whichColumn;


    List<Object> resultOfAction= new ArrayList<>();

    public int getId() {
        return id;
    }

    //control that there is a die in draft position, that exist window's position, that there isn't die in this position, there aren't dice near this position and die respect bound of box
    @Override
    public List<Object> check(List<Object> parameterForCard) {

        board=(GameBoard)parameterForCard.get(0);
        player=(RealPlayer)parameterForCard.get(1);
        posDieOnDraft=(Integer)parameterForCard.get(2);
        whichRow=(Integer)parameterForCard.get(3);
        whichColumn=(Integer)parameterForCard.get(4);

        if (board.getDieDraft(posDieOnDraft) == null) {

            resultOfAction.add(-1);
            resultOfAction.add("there isn't die on draft in the position you have chosen ");

        } else if(whichRow<0 || whichRow>3 || whichColumn<0 || whichColumn>4){

            resultOfAction.add(-1);
            resultOfAction.add("you don't put die because your position don't exist!!!!");

        } else if (player.getWindow().thereIsDie(whichRow, whichColumn)) {

            resultOfAction.add(-1);
            resultOfAction.add("you don't move die into box because there is a die in this box");

        } else if (player.getWindow().controlAdjacences(whichRow, whichColumn)) {

            resultOfAction.add(-1);
            resultOfAction.add("you don't move die into box because there are die near this box");

        } else if(!player.getWindow().controlAllBoundBox(whichRow,whichColumn,board.getDieDraft(posDieOnDraft))) {

            resultOfAction.add(-1);
            resultOfAction.add("you don't move die into box because your die don't respect bound");
        }
        else{

            resultOfAction.add(1);

        }

        return resultOfAction;
    }

    //insert die in box where there aren't dice near , remove die from draft
    @Override
    public void effect(List<Object> parameterForCard){

        board=(GameBoard)parameterForCard.get(0);
        player=(RealPlayer)parameterForCard.get(1);
        posDieOnDraft=(Integer)parameterForCard.get(2);
        whichRow=(Integer)parameterForCard.get(3);
        whichColumn=(Integer)parameterForCard.get(4);

        player.getWindow().insertDie(board.getDieDraft(posDieOnDraft),whichRow,whichColumn,"both");
        board.removeDieFromDraft(posDieOnDraft);

    }

}
