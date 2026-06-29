package repolezanettiperuzzi.controller;

/**
 * Socket commands accepted from a client.
 */
enum SocketClientAction {

    INIT("init"),
    WAITING_OK("waitingOk"),
    CHOOSE_WINDOW_OK("chooseWindowOk"),
    CHOSEN_WINDOW("chosenWindow"),
    GAME_OK("gameOk"),
    INSERT_DIE("insertDie"),
    RESPONSE_TOOL_CARD("responseToolCard"),
    CHOOSE_CARD("chooseCard"),
    END_TURN("endTurn"),
    EXIT("exit"),
    UNKNOWN("");

    private final String wireValue;

    SocketClientAction(String wireValue){
        this.wireValue=wireValue;
    }

    static SocketClientAction fromWireValue(String wireValue){
        for(SocketClientAction action: values()){
            if(action.wireValue.equals(wireValue)){
                return action;
            }
        }

        return UNKNOWN;
    }
}
