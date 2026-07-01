package repolezanettiperuzzi.infrastructure.client.socket;

/**
 * Socket messages accepted by the client view.
 */
public enum GameViewSocketAction {

    REGISTERED("registered"),
    UPDATED_PLAYERS("updatedplayers"),
    NOT_REGISTERED("notregistered"),
    CHANGE_VIEW("changeView"),
    CHOOSE_WINDOW("chooseWindow"),
    SHOW_WINDOW("showWindow"),
    START_GAME("startGame"),
    WIN_CHOOSE_WINDOW("winChooseWindow"),
    NOT_YOUR_TURN("notYourTurn"),
    ERROR("error"),
    TURN("turn"),
    UPDATE_VIEW("updateView"),
    REQUEST_CARD("requestCard"),
    END_GAME("endGame"),
    WIN_BEFORE_END("winBeforeEnd"),
    EXIT("exit"),
    UNKNOWN("");

    private final String wireValue;

    GameViewSocketAction(String wireValue){
        this.wireValue=wireValue;
    }

    static GameViewSocketAction fromWireValue(String wireValue){
        for(GameViewSocketAction action: values()){
            if(action.wireValue.equals(wireValue)){
                return action;
            }
        }

        return UNKNOWN;
    }
}
