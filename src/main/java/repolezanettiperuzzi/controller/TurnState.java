package repolezanettiperuzzi.controller;

import org.json.simple.parser.ParseException;
import repolezanettiperuzzi.model.actions.BeginRound;
import repolezanettiperuzzi.model.actions.BeginTurn;

import java.io.IOException;

public class TurnState extends ControllerState {

    //TODO inserire attributo currentTurn nella board
    private int currentTurn = 0;

    @Override
    public void doAction(Controller controller) throws IOException, ParseException {

        BeginTurn beginTurn = new BeginTurn();

        int player = BeginRound.getIndex();

        this.currentTurn++;

        for (int i = 0; i < controller.board.getNPlayers(); i++) {

            if (player >= controller.board.getNPlayers()){

                player = 0;

            }

            beginTurn.doAction(controller.board.getPlayer(player));

            if (controller.board.getPlayer(player).getTurn() == currentTurn) {

                //TODO creo connessione col client per chiedergli quali azioni vuole fare
                //TODO quando inserisce dado settare a true InsertDieInThisTurn

            }

            player++;

        }

        this.currentTurn++;

        player = BeginRound.getIndex();

        for (int i = 0; i < controller.board.getNPlayers(); i++){

            if (player < 0){

                player = 3;

            }

            beginTurn.doAction(controller.board.getPlayer(player));

            if (controller.board.getPlayer(player).getTurn() == currentTurn) {

                //TODO creo connessione col client per chiedergli quali azioni vuole fare

            }

            player--;

        }

        //TODO manage action from client with two methods in socket class

        controller.setState(new EndRoundState());

    }

    //TODO metodi che gestiscono azioni inerisci dado e usa carta

}
