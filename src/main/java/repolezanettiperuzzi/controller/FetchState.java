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

    @Override
    public void doAction(Controller controller) throws IOException, ParseException {

        this.board=controller.board;
        playersWindowsChoices = new HashMap<>();
        InitializeGame init = new InitializeGame();
        init.doAction(board);
        ArrayList<Window> windows = (ArrayList<Window>) init.getWindows();


        for (int i = 0; i < board.getNPlayers(); i++ ){

            if(board.getPlayer(i).getConnection().equals("Socket")){

                playersWindowsChoices.put(board.getPlayer(i),(ArrayList<Window>) new TakeTwoCardWindowAction().doAction(windows));
                String message = this.windowsToString(playersWindowsChoices.get(board.getPlayer(i)));
                Socket socket = new Socket(board.getPlayer(i).getAddress(),board.getPlayer(i).getPort());
                HandlerControllerSocket handler = new HandlerControllerSocket(controller,socket);
                handler.askWindow(message);

            }else if(board.getPlayer(i).getConnection().equals("RMI")){



            }

        }

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


    public void setChosenWindow(Player player, String choose){

        for (Window window : this.playersWindowsChoices.get(player) ) {

            if(window.getName().equals(choose)){

                new SetWindowAction().doAction(player,window);
                break;

            }

        }

    }

}
