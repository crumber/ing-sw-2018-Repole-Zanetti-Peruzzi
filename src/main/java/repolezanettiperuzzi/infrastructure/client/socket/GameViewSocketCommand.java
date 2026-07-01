package repolezanettiperuzzi.infrastructure.client.socket;

/**
 * Socket commands sent by the client view.
 */
enum GameViewSocketCommand {

    INIT("init"),
    WAITING_OK("waitingOk"),
    CHOOSE_WINDOW_OK("chooseWindowOk"),
    GAME_OK("gameOk"),
    EXIT("exit"),
    INSERT_DIE("insertDie"),
    CHOOSE_CARD("chooseCard"),
    RESPONSE_TOOL_CARD("responseToolCard"),
    CHOSEN_WINDOW("chosenWindow"),
    END_TURN("endTurn");

    private final String wireValue;

    GameViewSocketCommand(String wireValue){
        this.wireValue=wireValue;
    }

    String getWireValue(){
        return wireValue;
    }
}
