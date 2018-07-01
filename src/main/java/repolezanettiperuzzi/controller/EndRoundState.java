package repolezanettiperuzzi.controller;

import org.json.simple.parser.ParseException;
import repolezanettiperuzzi.model.Player;
import repolezanettiperuzzi.model.actions.BeginRound;
import repolezanettiperuzzi.model.actions.BeginTurn;
import repolezanettiperuzzi.model.actions.EndRound;

import java.io.IOException;
import java.net.Socket;

public class EndRoundState extends ControllerState {

    @Override
    public void doAction(Controller controller) throws IOException, ParseException {

        EndRound endRound = new EndRound();

        endRound.doAction(controller.board);

        //TODO if round number>10 pass to EndGameState

        if(BeginRound.getRound()==10){

            for (Player player : controller.board.getPlayers()){

                if(player.getConnection().equals("Socket")){

                    try (Socket socket = new Socket(player.getAddress(), player.getPort())) {

                        HandlerControllerSocket handler = new HandlerControllerSocket(controller,socket);
                        handler.notifyOnEndGame();

                    }

                }else if(player.getConnection().equals("RMI")){


                }
            }

            controller.setState(new EndGameState());


        }else{


            BeginTurn.resetCurrentTurn();
            System.out.println(BeginTurn.getCurrentTurn()+" "+BeginTurn.getNumPlayedTurn()+" "+BeginTurn.getCurrentPlayer());
            controller.setState(new BeginRoundState());


        }

    }
}
