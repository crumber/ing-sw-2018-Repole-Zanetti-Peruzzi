package repolezanettiperuzzi.model.actions;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe che rappresenta la creazione dei parametri per inserire un dado
 * @author Alessandro Peruzzi
 */
public class CreateListForInsertDieAction {

    /**
     * converte da stringa a arraylist di interi la stringa ricevuta dal client
     * @param clientAnswers stringa contenente le risposte del client
     * @return trasforma la lista in interi che inserisce in un'arrayList e lo ritorna
     */
    public List<Integer> doAction(String clientAnswers){

        ArrayList<Integer> parameterForCard=new ArrayList<>();
        String[] clientAnswersArray= clientAnswers.split(" "); //NON DEVONO ESSERCI PIù SPAZI O DA ERRORE VA IMPOSTO LATO CLIENT O SAREBBE MEGLIO QUI

        for(int i=0;i<3;i++){

            parameterForCard.add(Integer.parseInt(clientAnswersArray[i]));

        }

        return parameterForCard;
    }
}
