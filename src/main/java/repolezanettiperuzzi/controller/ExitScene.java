package repolezanettiperuzzi.controller;

/**
 * Client scenes that can send an exit command.
 */
enum ExitScene {

    WAITING_ROOM("waitingRoom"),
    CHOOSE_WINDOW("chooseWindow"),
    GAME("game"),
    UNKNOWN("");

    private final String value;

    ExitScene(String value){
        this.value=value;
    }

    static ExitScene from(String value){
        for(ExitScene scene: values()){
            if(scene.value.equals(value)){
                return scene;
            }
        }

        return UNKNOWN;
    }
}
