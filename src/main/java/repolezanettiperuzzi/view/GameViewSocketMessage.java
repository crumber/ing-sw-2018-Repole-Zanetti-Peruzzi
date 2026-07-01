package repolezanettiperuzzi.view;

import java.util.Arrays;

/**
 * Parsed message received by the client socket view.
 */
class GameViewSocketMessage {

    private final String command;
    private final String[] tokens;

    private GameViewSocketMessage(String command, String[] tokens){
        this.command=command;
        this.tokens=tokens;
    }

    static GameViewSocketMessage parse(String message){
        String[] tokens = message.split(" ");
        return new GameViewSocketMessage(tokens[0], tokens);
    }

    String getCommand(){
        return command;
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
