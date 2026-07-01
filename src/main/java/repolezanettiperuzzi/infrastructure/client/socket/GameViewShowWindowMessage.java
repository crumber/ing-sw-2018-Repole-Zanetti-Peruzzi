package repolezanettiperuzzi.infrastructure.client.socket;

import repolezanettiperuzzi.shared.dto.WindowClient;

/**
 * Payload carried by the client socket showWindow message.
 */
public class GameViewShowWindowMessage {

    private final WindowClient window;
    private final int currentTime;

    private GameViewShowWindowMessage(WindowClient window, int currentTime){
        this.window=window;
        this.currentTime=currentTime;
    }

    public static GameViewShowWindowMessage from(GameViewSocketMessage message){
        String[] tokens = message.getTokens();
        GameViewWindowPayloadParser.ParsedWindow parsedWindow = GameViewWindowPayloadParser.parseWindow(tokens,1);
        return new GameViewShowWindowMessage(parsedWindow.getWindow(), Integer.parseInt(tokens[parsedWindow.getNextIndex()]));
    }

    public WindowClient getWindow(){
        return window;
    }

    public int getCurrentTime(){
        return currentTime;
    }
}
