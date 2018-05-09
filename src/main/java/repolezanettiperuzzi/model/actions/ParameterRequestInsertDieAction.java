package repolezanettiperuzzi.model.actions;

import repolezanettiperuzzi.model.GameBoard;

public class ParameterRequestInsertDieAction extends Action{

    public String doAction(GameBoard board){

        int numDieOnDraft=board.getSizeDraft() - 1;

        String quest = "Which die on draft would you put in your window? (choose from 0 to "+ numDieOnDraft +") ?\n+" +
                "where would you put this die (which row (from 0 to 3) and which column (from 0 to 4).. answer like this 2 3) ?\n";

        return quest;
    }
}
