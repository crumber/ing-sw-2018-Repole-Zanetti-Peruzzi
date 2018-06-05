package repolezanettiperuzzi.controller;

import org.json.simple.parser.ParseException;
import repolezanettiperuzzi.model.actions.BeginRound;

import java.io.IOException;

public class BeginRoundState extends ControllerState {

    @Override
    public void doAction(Controller controller) throws IOException, ParseException {

        BeginRound beginRound = new BeginRound();//create action

        beginRound.doAction(controller.board);//call action to set new round


    }
}
