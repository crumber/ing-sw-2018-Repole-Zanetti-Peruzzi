package repolezanettiperuzzi.model.actions;

import java.util.ArrayList;
import java.util.List;

public class CreateListForInsertDieAction {

    public List<Integer> doAction(String clientAnswers){

        ArrayList<Integer> parameterForCard=new ArrayList<>();
        String[] clientAnswersArray= clientAnswers.split(" "); //NON DEVONO ESSERCI PIù SPAZI O DA ERRORE VA IMPOSTO LATO CLIENT O SAREBBE MEGLIO QUI

        for(int i=0;i<3;i++){

            parameterForCard.add(Integer.parseInt(clientAnswersArray[i]));

        }

        return parameterForCard;
    }
}
