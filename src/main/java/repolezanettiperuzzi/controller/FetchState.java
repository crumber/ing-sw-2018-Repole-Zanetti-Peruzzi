package repolezanettiperuzzi.controller;

import org.json.simple.parser.ParseException;
import repolezanettiperuzzi.model.Window;
import repolezanettiperuzzi.model.actions.BeginRound;
import repolezanettiperuzzi.model.actions.InitializeGame;
import repolezanettiperuzzi.model.actions.TakeClientWindowAction;
import repolezanettiperuzzi.model.actions.TakeTwoCardWindowAction;

import java.io.IOException;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class FetchState extends ControllerState {


    @Override
    public void doAction(Controller controller) throws IOException, ParseException {

        InitializeGame init = new InitializeGame();
        init.doAction(controller.board);
        ArrayList<Window> windows =(ArrayList<Window>) init.getWindows();

        for (int i = 0; i < controller.board.getNPlayers(); i++ ){

            if(controller.board.getPlayer(i).getConnection().equals("Socket")){

                ArrayList<Window> choices = new TakeTwoCardWindowAction().doAction(windows);
                String message = this.windowsToString(choices);
                Socket socket = new Socket(controller.board.getPlayer(i).getAddress(),controller.board.getPlayer(i).getPort());
                HandlerControllerSocket handler = new HandlerControllerSocket(controller,socket);
                init.setChosenWindow(controller.board.getPlayer(i), new TakeClientWindowAction().doAction(choices,handler.askWindow(message)));

            }else if(controller.board.getPlayer(i).getConnection().equals("RMI")){


            }

            //assign for each player the tokens that belong to him
            init.assignFavorTokens(controller.board);
        }

        controller.setState(new RoundState());


    }

    //create a string that contains the windows selectable from the player
    private String windowsToString(ArrayList<Window> windows) throws IOException {

        StringBuilder message = new StringBuilder();

        for (Window window: windows) {

            ArrayList<String> lines = (ArrayList<String>) Files.readAllLines(Paths.get("cards/gamemaps/" + window.fileName + ".txt" ));

            for(String line: lines){

                message.append(line);

            }

        }

        return String.valueOf(message);

    }

}
