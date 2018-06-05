package repolezanettiperuzzi.controller;

import repolezanettiperuzzi.model.actions.EndRound;

public class EndRoundState extends ControllerState {

    @Override
    public void doAction(Controller controller){

        EndRound endRound = new EndRound();

        endRound.doAction(controller.board);

        //TODO if round number>10 pass to EndGameState

    }
}
