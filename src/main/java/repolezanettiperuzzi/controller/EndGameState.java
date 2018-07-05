package repolezanettiperuzzi.controller;


import repolezanettiperuzzi.model.Player;
import repolezanettiperuzzi.model.actions.CalculateScore;

import java.io.IOException;
import java.net.Socket;

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
    public void doAction(Controller controller) throws IOException {

        String message = new CalculateScore().doAction(controller.board);

        //System.out.println(message);

        for (Player player : controller.board.getPlayers()){

            if(player.getConnection().equals("Socket")){

                try (Socket socket = new Socket(player.getAddress(), player.getPort())) {

                    HandlerControllerSocket handler = new HandlerControllerSocket(controller,socket);
                    handler.notifyOnEndGame(message);

                }

            }else if(player.getConnection().equals("RMI")){

                    controller.getHandlerRMI().receiveRanking(player.getName(), message);

            }
        }

        System.exit(0);
    }

}
