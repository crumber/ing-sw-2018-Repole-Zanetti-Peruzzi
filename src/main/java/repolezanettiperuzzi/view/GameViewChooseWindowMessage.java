package repolezanettiperuzzi.view;

import repolezanettiperuzzi.infrastructure.client.socket.GameViewSocketMessage;

import java.util.ArrayList;

import repolezanettiperuzzi.shared.dto.WindowClient;

/**
 * Payload carried by the client socket chooseWindow message.
 */
class GameViewChooseWindowMessage {

    private final ArrayList<WindowClient> windows;
    private final int currentTime;

    private GameViewChooseWindowMessage(ArrayList<WindowClient> windows, int currentTime){
        this.windows=windows;
        this.currentTime=currentTime;
    }

    static GameViewChooseWindowMessage from(GameViewSocketMessage message){
        ArrayList<WindowClient> windows = new ArrayList<>();
        String[] tokens = message.getTokens();
        int i = 1;
        while(i < tokens.length-1){
            GameViewWindowPayloadParser.ParsedWindow parsedWindow = GameViewWindowPayloadParser.parseWindow(tokens,i);
            windows.add(parsedWindow.getWindow());
            i = parsedWindow.getNextIndex();
        }

        return new GameViewChooseWindowMessage(windows, Integer.parseInt(tokens[i]));
    }

    ArrayList<WindowClient> getWindows(){
        return new ArrayList<>(windows);
    }

    int getCurrentTime(){
        return currentTime;
    }
}
