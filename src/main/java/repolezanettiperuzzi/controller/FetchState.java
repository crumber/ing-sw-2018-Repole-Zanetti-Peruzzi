package repolezanettiperuzzi.controller;

import org.json.simple.parser.ParseException;
import repolezanettiperuzzi.model.GameBoard;
import repolezanettiperuzzi.model.Player;
import repolezanettiperuzzi.model.Window;
import repolezanettiperuzzi.model.actions.InitializeGame;
import repolezanettiperuzzi.model.actions.SetWindowAction;
import repolezanettiperuzzi.model.actions.TakeTwoCardWindowAction;

import java.io.IOException;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;


public class FetchState extends ControllerState {

    private GameBoard board;
    private HashMap<Player,ArrayList<Window>> playersWindowsChoices;
    private ArrayList<Window> windows;
    private Controller controller;

    @Override
    public void doAction(Controller controller) throws IOException, ParseException {

        this.controller = controller;
        this.board=controller.board;
        playersWindowsChoices = new HashMap<>();
        InitializeGame init = new InitializeGame();
        init.doAction(board);
        this.windows = (ArrayList<Window>) init.getWindows();




    }

    public void sendWindows(Player player) throws IOException {

        if(player.getConnection().equals("Socket")){

            playersWindowsChoices.put(player,(ArrayList<Window>) new TakeTwoCardWindowAction().doAction(windows));
            String message = this.windowsToString(playersWindowsChoices.get(player));
            Socket socket = new Socket(player.getAddress(),player.getPort());
            HandlerControllerSocket handler = new HandlerControllerSocket(this.controller,socket);
            handler.askWindow(message);

        }else if(player.getConnection().equals("RMI")){

        }

    }

    //create a string that contains the windows selectable from the player
    private String windowsToString(ArrayList<Window> windows) throws IOException {

        StringBuilder message = new StringBuilder();

        for (Window window: windows) {

            ArrayList<String> lines = (ArrayList<String>) Files.readAllLines(Paths.get("cards/gamemaps/" + window.fileName + ".txt" ));

            for(String line: lines){

                message.append(line.replace(" ","-"));
                message.append(" ");

            }

            message.append("_ ");
        }

        return String.valueOf(message);

    }


    public void setChosenWindow(Player player, String choose){

        for (Window window : this.playersWindowsChoices.get(player) ) {

            if(window.getName().equals(choose)){

                new SetWindowAction().doAction(player,window);
                break;

            }

        }

    }

}
