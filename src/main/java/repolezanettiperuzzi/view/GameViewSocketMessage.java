package repolezanettiperuzzi.view;

import java.util.Arrays;

/**
 * Parsed message received by the client socket view.
 */
class GameViewSocketMessage {

    private final GameViewSocketAction action;
    private final String[] tokens;

    private GameViewSocketMessage(GameViewSocketAction action, String[] tokens){
        this.action=action;
        this.tokens=tokens;
    }

    static GameViewSocketMessage parse(String message){
        String[] tokens = message.split(" ");
        return new GameViewSocketMessage(GameViewSocketAction.fromWireValue(tokens[0]), tokens);
    }

    GameViewSocketAction getAction(){
        return action;
    }

    String getToken(int index){
        return tokens[index];
    }

    int getTokenCount(){
        return tokens.length;
    }

    String[] getTokens(){
        return Arrays.copyOf(tokens,tokens.length);
    }
}
