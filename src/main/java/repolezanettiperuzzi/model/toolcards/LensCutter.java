package repolezanettiperuzzi.model.toolcards;

import repolezanettiperuzzi.model.Die;
import repolezanettiperuzzi.model.GameBoard;

import java.util.ArrayList;
import java.util.List;

public class LensCutter extends ToolCard {

    int id=5;

    //list of parameter: 0-game board 1-position Die On Draft 2-which Round 3-which Die Round
    private GameBoard board;
    private int posDieOnDraft;
    private int whichRound;
    private int whichDieRound;

    List<Object> resultOfAction= new ArrayList<>();

    public int getId() {
        return id;
    }

    //control that there is die in position on draft and control that there is die in position on round track
    @Override
    public List<Object> check(List<Object> parameterForCard){

        board=(GameBoard)parameterForCard.get(0);
        posDieOnDraft=(Integer)parameterForCard.get(1);
        whichRound=(Integer)parameterForCard.get(2);
        whichDieRound=(Integer)parameterForCard.get(3);

        if(board.getDieDraft(posDieOnDraft)==null){

            resultOfAction.add(-1);
            resultOfAction.add("there isn't die on draft in the position you have chosen ");

        }else if(board.getDieFromRoundTrack(whichRound,whichDieRound)==null){

            resultOfAction.add(-1);
            resultOfAction.add("there isn't die on round track in the position you have chosen");

        }else {

            resultOfAction.add(1);

        }

        return resultOfAction;
    }

    //switch dice (one on round track and one on draft)
    @Override
    public void effect(List<Object> parameterForCard){

        board=(GameBoard)parameterForCard.get(0);
        posDieOnDraft=(Integer)parameterForCard.get(1);
        whichRound=(Integer)parameterForCard.get(2);
        whichDieRound=(Integer)parameterForCard.get(3);

        Die dieOnDraft= board.getDieDraft(posDieOnDraft);
        Die dieOnRoundTrack= board.getDieFromRoundTrack(whichRound,whichDieRound);

        board.setDieDraft(posDieOnDraft,dieOnRoundTrack); //put die on round track in the draft
        board.setDieToRoundTrack(whichRound,whichDieRound,dieOnDraft); //put die on draft in the round track (pos: which round, which die)

    }
}
