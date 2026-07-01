package repolezanettiperuzzi.infrastructure.client.socket;

import java.util.Arrays;

/**
 * Payload carried by the client socket updatedplayers message.
 */
public class GameViewUpdatedPlayersMessage {

    private final int timer;
    private final String[] players;

    private GameViewUpdatedPlayersMessage(int timer, String[] players){
        this.timer=timer;
        this.players=players;
    }

    public static GameViewUpdatedPlayersMessage from(GameViewSocketMessage message){
        return new GameViewUpdatedPlayersMessage(
                Integer.parseInt(message.getToken(1)),
                Arrays.copyOfRange(message.getTokens(),2,message.getTokenCount())
        );
    }

    public int getTimer(){
        return timer;
    }

    public String[] getPlayers(){
        return Arrays.copyOf(players,players.length);
    }
}
