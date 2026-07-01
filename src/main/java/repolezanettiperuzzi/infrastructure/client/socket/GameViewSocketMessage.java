package repolezanettiperuzzi.infrastructure.client.socket;

import java.util.Arrays;

/**
 * Parsed message received by the client socket view.
 */
public class GameViewSocketMessage {

    private final GameViewSocketAction action;
    private final String[] tokens;

    private GameViewSocketMessage(GameViewSocketAction action, String[] tokens){
        this.action=action;
        this.tokens=tokens;
    }

    public static GameViewSocketMessage parse(String message){
        String[] tokens = message.split(" ");
        return new GameViewSocketMessage(GameViewSocketAction.fromWireValue(tokens[0]), tokens);
    }

    public GameViewSocketAction getAction(){
        return action;
    }

    public String getToken(int index){
        return tokens[index];
    }

    public String getFirstPayloadToken(){
        return getToken(1);
    }

    public int getTokenCount(){
        return tokens.length;
    }

    public String[] getTokens(){
        return Arrays.copyOf(tokens,tokens.length);
    }
}
