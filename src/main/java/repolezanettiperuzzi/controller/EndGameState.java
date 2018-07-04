package repolezanettiperuzzi.controller;


import repolezanettiperuzzi.model.actions.CalculateScore;

/**
 * Classe che rappresenta lo stato finale del gioco
 * @author Giampiero Repole
 */
public class EndGameState extends ControllerState{

    /**
     * Calcola i punteggi dei player
     * @param controller Controller
     */
    @Override
    public void doAction(Controller controller) {

        new CalculateScore().doAction(controller.board);

    }

}
