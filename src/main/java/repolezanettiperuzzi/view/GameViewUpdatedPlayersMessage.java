package repolezanettiperuzzi.view;

import repolezanettiperuzzi.infrastructure.client.socket.GameViewSocketMessage;

import java.util.Arrays;

/**
 * Payload carried by the client socket updatedplayers message.
 */
class GameViewUpdatedPlayersMessage {

    private final int timer;
    private final String[] players;

    private GameViewUpdatedPlayersMessage(int timer, String[] players){
        this.timer=timer;
        this.players=players;
    }

    static GameViewUpdatedPlayersMessage from(GameViewSocketMessage message){
        return new GameViewUpdatedPlayersMessage(
                Integer.parseInt(message.getToken(1)),
                Arrays.copyOfRange(message.getTokens(),2,message.getTokenCount())
        );
    }

    int getTimer(){
        return timer;
    }

    String[] getPlayers(){
        return Arrays.copyOf(players,players.length);
    }
}
