package repolezanettiperuzzi.controller;

/**
 * Parameters carried by the RMI exit command.
 */
class RmiExitRequest {

    private final RmiExitScene scene;

    RmiExitRequest(String typeView){
        this.scene=RmiExitScene.from(typeView);
    }

    RmiExitScene getScene(){
        return scene;
    }
}
