package repolezanettiperuzzi.infrastructure.client.socket;

/**
 * Payload carried by the client socket turn message.
 */
public class GameViewTurnMessage {

    private final String actualPlayer;
    private final int currentTime;

    private GameViewTurnMessage(String actualPlayer, int currentTime){
        this.actualPlayer=actualPlayer;
        this.currentTime=currentTime;
    }

    public static GameViewTurnMessage from(GameViewSocketMessage message){
        return new GameViewTurnMessage(message.getToken(1), Integer.parseInt(message.getToken(2)));
    }

    public String getActualPlayer(){
        return actualPlayer;
    }

    public int getCurrentTime(){
        return currentTime;
    }
}
