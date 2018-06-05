package repolezanettiperuzzi.model.actions;

import repolezanettiperuzzi.model.GameBoard;

public class ParameterRequestInsertDieAction {

    public String doAction(){

        String typeMess="requestInsert ";
        String draft="dieDraft ";
        String end="endPos ";

        String quest =typeMess+draft+end;

        return quest;

    }
}
