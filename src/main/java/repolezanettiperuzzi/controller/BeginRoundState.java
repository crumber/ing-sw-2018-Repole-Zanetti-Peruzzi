package repolezanettiperuzzi.controller;

import org.json.simple.parser.ParseException;
import repolezanettiperuzzi.model.Player;
import repolezanettiperuzzi.model.actions.BeginRound;

import java.io.IOException;
import java.net.Socket;

/**
 * Classe che rappresenta il controller all'inizio del round
 * @author Giampiero Repole
 */
public class BeginRoundState extends ControllerState {

    private Controller controller;

    /**
     * Crea l'azione e chiama l'azione che inizializza il nuovo round
     * @param controller Controller
     * @throws IOException Fallimento o interruzione delle operazioni I/O
     * @throws ParseException Errore durante l'analisi
     */
    @Override
    public void doAction(Controller controller) throws IOException, ParseException {

        this.controller = controller;

        BeginRound beginRound = new BeginRound();//create action

        beginRound.doAction(controller.board);//call action to set new round

        controller.setState(new TurnState());

    }

    /**
     *
     * @param player Player
     * @throws IOException Fallimento o interruzione delle operazioni I/O
     */
    public void sendBeginRound(Player player) throws IOException {

        if(player.getConnection().equals("Socket")){

            Socket socket = new Socket(player.getAddress(),player.getPort());
            HandlerControllerSocket handler = new HandlerControllerSocket(this.controller,socket);
            handler.notifyBeginRound();

        }else if(player.getConnection().equals("RMI")){

        }


    }
}
