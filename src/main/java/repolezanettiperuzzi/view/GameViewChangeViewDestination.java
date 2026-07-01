package repolezanettiperuzzi.view;

/**
 * Destinations carried by the client socket changeView message.
 */
enum GameViewChangeViewDestination {

    CHOOSE_WINDOW("chooseWindow"),
    UNKNOWN("");

    private final String wireValue;

    GameViewChangeViewDestination(String wireValue){
        this.wireValue=wireValue;
    }

    static GameViewChangeViewDestination fromWireValue(String wireValue){
        for(GameViewChangeViewDestination destination: values()){
            if(destination.wireValue.equals(wireValue)){
                return destination;
            }
        }

        return UNKNOWN;
    }
}
