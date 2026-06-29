package repolezanettiperuzzi.controller;

import org.json.simple.parser.ParseException;

import java.io.IOException;

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

        controller.createEndRoundAction().doAction(controller.board);

        if(controller.getCurrentRound()==10){

            controller.cancelTimer();

            controller.setState(new EndGameState());


        }else{


            controller.resetCurrentTurn();
            controller.setState(new BeginRoundState());


        }

    }
}
