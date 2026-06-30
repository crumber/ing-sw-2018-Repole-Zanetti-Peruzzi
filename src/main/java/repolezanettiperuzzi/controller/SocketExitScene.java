package repolezanettiperuzzi.controller;

/**
 * Client scenes that can send the socket exit command.
 */
enum SocketExitScene {

    WAITING_ROOM("waitingRoom"),
    CHOOSE_WINDOW("chooseWindow"),
    GAME("game"),
    UNKNOWN("");

    private final String wireValue;

    SocketExitScene(String wireValue){
        this.wireValue=wireValue;
    }

    static SocketExitScene fromWireValue(String wireValue){
        for(SocketExitScene scene: values()){
            if(scene.wireValue.equals(wireValue)){
                return scene;
            }
        }

        return UNKNOWN;
    }
}
