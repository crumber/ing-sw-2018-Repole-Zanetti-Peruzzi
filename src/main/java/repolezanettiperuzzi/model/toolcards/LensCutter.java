package repolezanettiperuzzi.model.toolcards;

import repolezanettiperuzzi.model.Die;
import repolezanettiperuzzi.model.GameBoard;
import repolezanettiperuzzi.model.RealPlayer;

import java.util.ArrayList;
import java.util.List;

public class LensCutter extends ToolCard {

    int id=5;

    //list of parameter: 0-game board 1-player 2-position Die On Draft 3-which Round 4-which Die Round
    private GameBoard board;
    private RealPlayer player;
    private int posDieOnDraft;
    private int whichRound;
    private int whichDieRound;

    List<Object> resultOfAction= new ArrayList<>();
    List<Object> requestForToolCard = new ArrayList<>();

    public int getId() {
        return id;
    }

    //control that there is die in position on draft and control that there is die in position on round track
    @Override
    public List<Object> check(List<Object> parameterForCard){

        board=(GameBoard)parameterForCard.get(0);
        player=(RealPlayer)parameterForCard.get(1);
        posDieOnDraft=(Integer)parameterForCard.get(2);
        whichRound=(Integer)parameterForCard.get(3);
        whichDieRound=(Integer)parameterForCard.get(4);

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
        player=(RealPlayer)parameterForCard.get(1);
        posDieOnDraft=(Integer)parameterForCard.get(2);
        whichRound=(Integer)parameterForCard.get(3);
        whichDieRound=(Integer)parameterForCard.get(4);

        Die dieOnDraft= board.getDieDraft(posDieOnDraft);
        Die dieOnRoundTrack= board.getDieFromRoundTrack(whichRound,whichDieRound);

        board.setDieDraft(posDieOnDraft,dieOnRoundTrack); //put die on round track in the draft
        board.setDieToRoundTrack(whichRound,whichDieRound,dieOnDraft); //put die on draft in the round track (pos: which round, which die)

    }

    @Override
    public List<Object> requestCard(){

        int maxChooseDraftDie = board.getSizeDraft();
        requestForToolCard.add("Which die on draft (from 0 to " + maxChooseDraftDie + ") ?\n");
        requestForToolCard.add("Which die on round track (insert number of round and number of die position on round, like this: 3 2 -> round 3 die 2) ?\n");

        return  requestForToolCard;

    }
}
