package repolezanettiperuzzi.model.actions;

import repolezanettiperuzzi.model.GameBoard;

public class ParameterRequestInsertDieAction {

    public String doAction(){

        String draft="dieDraft ";
        String end="endPos ";

        String quest = draft+end;

        return quest;

    }
}
