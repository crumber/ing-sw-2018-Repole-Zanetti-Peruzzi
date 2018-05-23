package repolezanettiperuzzi.controller;

import org.json.simple.parser.ParseException;
import repolezanettiperuzzi.model.GameBoard;
import repolezanettiperuzzi.model.Player;
import repolezanettiperuzzi.model.actions.InitializeGame;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class FetchState extends ControllerState {


    @Override
    public void doAction(Controller controller) throws IOException, ParseException {

        new InitializeGame().doAction(controller.board);
        controller.setState(new StartGameState());

    }

}
