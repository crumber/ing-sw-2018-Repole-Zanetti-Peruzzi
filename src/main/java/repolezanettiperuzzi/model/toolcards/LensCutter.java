package repolezanettiperuzzi.model.toolcards;

import repolezanettiperuzzi.model.Die;
import repolezanettiperuzzi.model.GameBoard;

import java.util.ArrayList;
import java.util.List;

public class LensCutter extends ToolCard {

    int id=5;
    List<Object> resultOfAction= new ArrayList<>();

    public int getId() {
        return id;
    }

    public List<Object> check(GameBoard boar, int posDieOnDraft, int whichRound, int whichDieRound){

        if(boar.getDieDraft(posDieOnDraft)==null){

            resultOfAction.add(-1);
            resultOfAction.add("there isn't die on draft in the position you have chosen ");

        }else if(boar.getDieFromRoundTrack(whichRound,whichDieRound)==null){

            resultOfAction.add(-1);
            resultOfAction.add("there isn't die on round track in the position you have chosen");

        }else {

            resultOfAction.add(1);

        }

        return resultOfAction;
    }

    public void effect(GameBoard boar, int posDieOnDraft, int whichRound, int whichDieRound){

        Die dieOnDraft= boar.getDieDraft(posDieOnDraft);
        Die dieOnRoundTrack= boar.getDieFromRoundTrack(whichRound,whichDieRound);

        boar.setDieDraft(posDieOnDraft,dieOnRoundTrack); //put die on round track in the draft
        boar.setDieToRoundTrack(whichRound,whichDieRound,dieOnDraft); //put die on draft in the round track (pos: which round, which die)

    }
}
