package repolezanettiperuzzi.controller;

import org.json.simple.parser.ParseException;
import repolezanettiperuzzi.model.Player;
import repolezanettiperuzzi.model.actions.BeginRound;

import java.io.IOException;
import java.net.Socket;


public class BeginRoundState extends ControllerState {

    private Controller controller;

    @Override
    public void doAction(Controller controller) throws IOException, ParseException {

        this.controller = controller;

        BeginRound beginRound = new BeginRound();//create action

        beginRound.doAction(controller.board);//call action to set new round

        controller.setState(new TurnState());

    }

    public void sendBeginRound(Player player) throws IOException {

        if(player.getConnection().equals("Socket")){

            Socket socket = new Socket(player.getAddress(),player.getPort());
            HandlerControllerSocket handler = new HandlerControllerSocket(this.controller,socket);
            handler.notifyBeginRound();

        }else if(player.getConnection().equals("RMI")){

        }


    }
}
