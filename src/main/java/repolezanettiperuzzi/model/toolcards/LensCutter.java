package repolezanettiperuzzi.model.toolcards;

import repolezanettiperuzzi.model.Die;
import repolezanettiperuzzi.model.GameBoard;
import repolezanettiperuzzi.model.Player;

import java.util.List;

public class LensCutter extends ToolCard {

    int id=5;

    int resultOfAction;

    private int posDieOnDraft;
    private int whichRound;
    private int whichDieRound;

    public int getId() {
        return id;
    }

    //control that there is die in position on draft and control that there is die in position on round track
    @Override
    public int check(GameBoard board, Player player, List<Integer> parameterForCard){

        posDieOnDraft=parameterForCard.get(0);
        whichRound=parameterForCard.get(1);
        whichDieRound=parameterForCard.get(2);

        if(checkDieOnDraft(board,player,posDieOnDraft)!=1){

            resultOfAction=checkDieOnDraft(board,player,posDieOnDraft);

        }else{

            resultOfAction=checkDieOnRoundTrack(board,player,whichRound,whichDieRound);
        }

        return resultOfAction;
    }

    //switch dice (one on round track and one on draft)
    @Override
    public void effect(GameBoard board, Player player, List<Integer> parameterForCard){

        posDieOnDraft=parameterForCard.get(0);
        whichRound=parameterForCard.get(1);
        whichDieRound=parameterForCard.get(2);

        Die dieOnDraft= board.getDieDraft(posDieOnDraft);
        Die dieOnRoundTrack= board.getDieFromRoundTrack(whichRound,whichDieRound);

        board.setDieDraft(posDieOnDraft,dieOnRoundTrack); //put die on round track in the draft
        board.setDieToRoundTrack(whichRound,whichDieRound,dieOnDraft); //put die on draft in the round track (pos: which round, which die)

    }
}
