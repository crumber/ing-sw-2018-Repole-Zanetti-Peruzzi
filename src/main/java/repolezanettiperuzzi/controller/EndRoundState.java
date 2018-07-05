package repolezanettiperuzzi.controller;

import org.json.simple.parser.ParseException;
import repolezanettiperuzzi.model.Player;
import repolezanettiperuzzi.model.actions.BeginRound;
import repolezanettiperuzzi.model.actions.BeginTurn;
import repolezanettiperuzzi.model.actions.EndRound;

import java.io.IOException;
import java.net.Socket;

/**
 * Classe che rappresenta lo stato finale del round
 * @author Giampiero Repole
 */
public class EndRoundState extends ControllerState {

    /**
     * Svolge le azioni di fine round
     * @param controller Controller
     * @throws IOException Fallimento o interruzione delle operazioni I/O
     * @throws ParseException Errore durante l'analisi
     */
    @Override
    public void doAction(Controller controller) throws IOException, ParseException {

        EndRound endRound = new EndRound();

        endRound.doAction(controller.board);

        if(BeginRound.getRound()==10){

            controller.cancelTimer();

            controller.setState(new EndGameState());


        }else{


            BeginTurn.resetCurrentTurn();
            controller.setState(new BeginRoundState());


        }

    }
}
