package repolezanettiperuzzi.infrastructure.client.socket;

/**
 * Destinations carried by the client socket changeView message.
 */
public enum GameViewChangeViewDestination {

    CHOOSE_WINDOW("chooseWindow"),
    UNKNOWN("");

    private final String wireValue;

    GameViewChangeViewDestination(String wireValue){
        this.wireValue=wireValue;
    }

    public static GameViewChangeViewDestination fromWireValue(String wireValue){
        for(GameViewChangeViewDestination destination: values()){
            if(destination.wireValue.equals(wireValue)){
                return destination;
            }
        }

        return UNKNOWN;
    }
}
