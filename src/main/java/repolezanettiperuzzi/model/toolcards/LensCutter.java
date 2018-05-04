package repolezanettiperuzzi.model.toolcards;

import repolezanettiperuzzi.model.Die;
import repolezanettiperuzzi.model.GameBoard;

public class LensCutter extends ToolCard {

    int id=5;

    @Override
    public int effect() {
        return 0;
    }

    public boolean effect(GameBoard boar, int posDieOnDraft, int whichRound, int whichDieRound){

        Die dieOnDraft= boar.getDieDraft(posDieOnDraft);
        Die dieOnRoundTrack= boar.getDieFromRoundTrack(whichRound,whichDieRound);

        boar.setDieDraft(posDieOnDraft,dieOnRoundTrack); //put die on round track in the draft
        boar.setDieToRoundTrack(whichRound,whichDieRound,dieOnDraft); //put die on draft in the round track (pos: which round, which die)
        return true;

    }

    public int getId() {
        return id;
    }
}
