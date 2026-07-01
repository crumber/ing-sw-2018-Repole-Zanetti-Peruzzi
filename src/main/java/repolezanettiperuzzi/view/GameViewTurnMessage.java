package repolezanettiperuzzi.view;

import repolezanettiperuzzi.infrastructure.client.socket.GameViewSocketMessage;

/**
 * Payload carried by the client socket turn message.
 */
class GameViewTurnMessage {

    private final String actualPlayer;
    private final int currentTime;

    private GameViewTurnMessage(String actualPlayer, int currentTime){
        this.actualPlayer=actualPlayer;
        this.currentTime=currentTime;
    }

    static GameViewTurnMessage from(GameViewSocketMessage message){
        return new GameViewTurnMessage(message.getToken(1), Integer.parseInt(message.getToken(2)));
    }

    String getActualPlayer(){
        return actualPlayer;
    }

    int getCurrentTime(){
        return currentTime;
    }
}
