package repolezanettiperuzzi.application.actions;

import repolezanettiperuzzi.domain.ActionResult;

/**
 * Classe che modellizza i codici degli errori
 * @author Alessandro Peruzzi
 */
public class WhichErrorAction {

    /**
     *
     * @param numError intero che rappresenta l'errore ( sempre negativo)
     * @return ritorna la Stringa che rappresenta l'errore in codice
     */
    public String doAction(int numError){

        return doAction(ActionResult.fromErrorCode(numError));
    }

    public String doAction(ActionResult result){

        return result.toClientErrorMessage();
    }
}
