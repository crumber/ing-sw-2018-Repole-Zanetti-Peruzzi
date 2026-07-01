package repolezanettiperuzzi.controller;

/**
 * Parameters carried by the RMI exit command.
 */
class RmiExitRequest {

    private final ExitScene scene;

    RmiExitRequest(String typeView){
        this.scene=ExitScene.from(typeView);
    }

    ExitScene getScene(){
        return scene;
    }
}
