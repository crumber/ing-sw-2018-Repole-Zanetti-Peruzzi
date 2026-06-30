package repolezanettiperuzzi.controller;

/**
 * Client scenes that can send the RMI exit command.
 */
enum RmiExitScene {

    WAITING_ROOM("waitingRoom"),
    CHOOSE_WINDOW("chooseWindow"),
    GAME("game"),
    UNKNOWN("");

    private final String value;

    RmiExitScene(String value){
        this.value=value;
    }

    static RmiExitScene from(String value){
        for(RmiExitScene scene: values()){
            if(scene.value.equals(value)){
                return scene;
            }
        }

        return UNKNOWN;
    }
}
