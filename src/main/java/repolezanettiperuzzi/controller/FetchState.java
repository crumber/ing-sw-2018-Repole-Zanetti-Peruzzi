package repolezanettiperuzzi.controller;

import repolezanettiperuzzi.model.GameBoard;
import repolezanettiperuzzi.model.Player;

import java.util.ArrayList;

public class FetchState extends ControllerState {


    @Override
    public void doAction(Controller controller){

        controller.setState(new StartGameState());

    }

}
