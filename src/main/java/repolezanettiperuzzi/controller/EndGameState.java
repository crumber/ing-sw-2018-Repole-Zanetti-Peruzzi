package repolezanettiperuzzi.controller;


import repolezanettiperuzzi.model.actions.CalculateScore;

public class EndGameState extends ControllerState{

    @Override
    public void doAction(Controller controller) {

        new CalculateScore().doAction(controller.board);

    }

}
