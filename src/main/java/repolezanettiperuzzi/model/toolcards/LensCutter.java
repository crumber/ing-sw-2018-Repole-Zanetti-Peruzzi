package repolezanettiperuzzi.model.toolcards;

import repolezanettiperuzzi.model.Die;
import repolezanettiperuzzi.model.GameBoard;

public class LensCutter extends ToolCard {

    @Override
    public int effect() {
        return 0;
    }

    public boolean effect(GameBoard boar, int posDieOnDraft, int whichRound, int whichDieRound){

        Die dieOnDraft= boar.getDieDraft(posDieOnDraft);
        Die dieOnRoundTrack= boar.getRoundTrack().getDieRoundTrack(whichRound,whichDieRound);

        boar.setDieDraft(posDieOnDraft,dieOnRoundTrack); //put die on round track in the draft
        boar.getRoundTrack().setDieOnRoundTrack(whichRound,whichDieRound,dieOnDraft); //put die on draft in the round track (pos: which round, which die)
        return true;

    }
}
