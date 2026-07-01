package repolezanettiperuzzi.infrastructure.client.socket;

/**
 * Reasons carried by the client socket notregistered message.
 */
public enum GameViewNotRegisteredReason {

    ALREADY_ONLINE("alreadyonline"),
    WRONG_PASSWORD("wrongpwd"),
    GAME_ALREADY_STARTED("gameAlreadyStarted"),
    ALREADY_FOUR_PLAYERS("already4Players"),
    UNKNOWN("");

    private final String wireValue;

    GameViewNotRegisteredReason(String wireValue){
        this.wireValue=wireValue;
    }

    public static GameViewNotRegisteredReason fromWireValue(String wireValue){
        for(GameViewNotRegisteredReason reason: values()){
            if(reason.wireValue.equals(wireValue)){
                return reason;
            }
        }

        return UNKNOWN;
    }
}
