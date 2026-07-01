package repolezanettiperuzzi.infrastructure.client.socket;

/**
 * Builds socket messages sent by the client view.
 */
public class GameViewSocketOutgoingMessage {

    private GameViewSocketOutgoingMessage(){
    }

    public static String init(String username, String pwd, String conn, String ui, int localPort){
        return username + " " + GameViewSocketCommand.INIT.getWireValue() + " " + pwd + " " + conn + " " + ui + " " + localPort;
    }

    public static String waitingRoomLoaded(String username){
        return username + " " + GameViewSocketCommand.WAITING_OK.getWireValue();
    }

    public static String chooseWindowSceneLoaded(String username){
        return username + " " + GameViewSocketCommand.CHOOSE_WINDOW_OK.getWireValue();
    }

    public static String gameSceneLoaded(String username){
        return username + " " + GameViewSocketCommand.GAME_OK.getWireValue();
    }

    public static String exit(String username, String typeView){
        return username + " " + GameViewSocketCommand.EXIT.getWireValue() + " " + typeView;
    }

    public static String insertDie(String username, int draftPos, int xWindowPos, int yWindowPos){
        return username + " " + GameViewSocketCommand.INSERT_DIE.getWireValue() + " " + draftPos + " " + xWindowPos + " " + yWindowPos;
    }

    public static String chooseCard(String username, int numCard){
        return username + " " + GameViewSocketCommand.CHOOSE_CARD.getWireValue() + " " + numCard;
    }

    public static String responseToolCard(String username, int nCard, String response){
        return username + " " + GameViewSocketCommand.RESPONSE_TOOL_CARD.getWireValue() + " " + nCard + " " + response;
    }

    public static String chosenWindow(String username, String windowName){
        return username + " " + GameViewSocketCommand.CHOSEN_WINDOW.getWireValue() + " " + windowName;
    }

    public static String endTurn(String username){
        return username + " " + GameViewSocketCommand.END_TURN.getWireValue();
    }
}
