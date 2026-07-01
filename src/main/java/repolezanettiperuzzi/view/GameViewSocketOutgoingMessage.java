package repolezanettiperuzzi.view;

/**
 * Builds socket messages sent by the client view.
 */
class GameViewSocketOutgoingMessage {

    private GameViewSocketOutgoingMessage(){
    }

    static String init(String username, String pwd, String conn, String ui, int localPort){
        return username + " " + GameViewSocketCommand.INIT.getWireValue() + " " + pwd + " " + conn + " " + ui + " " + localPort;
    }

    static String waitingRoomLoaded(String username){
        return username + " " + GameViewSocketCommand.WAITING_OK.getWireValue();
    }

    static String chooseWindowSceneLoaded(String username){
        return username + " " + GameViewSocketCommand.CHOOSE_WINDOW_OK.getWireValue();
    }

    static String gameSceneLoaded(String username){
        return username + " " + GameViewSocketCommand.GAME_OK.getWireValue();
    }

    static String exit(String username, String typeView){
        return username + " " + GameViewSocketCommand.EXIT.getWireValue() + " " + typeView;
    }

    static String insertDie(String username, int draftPos, int xWindowPos, int yWindowPos){
        return username + " " + GameViewSocketCommand.INSERT_DIE.getWireValue() + " " + draftPos + " " + xWindowPos + " " + yWindowPos;
    }

    static String chooseCard(String username, int numCard){
        return username + " " + GameViewSocketCommand.CHOOSE_CARD.getWireValue() + " " + numCard;
    }

    static String responseToolCard(String username, int nCard, String response){
        return username + " " + GameViewSocketCommand.RESPONSE_TOOL_CARD.getWireValue() + " " + nCard + " " + response;
    }

    static String chosenWindow(String username, String windowName){
        return username + " " + GameViewSocketCommand.CHOSEN_WINDOW.getWireValue() + " " + windowName;
    }

    static String endTurn(String username){
        return username + " " + GameViewSocketCommand.END_TURN.getWireValue();
    }
}
