package repolezanettiperuzzi.controller;

/**
 * Parameters carried by the socket exit command.
 */
class SocketExitRequest {

    private final ExitScene scene;

    private SocketExitRequest(ExitScene scene){
        this.scene=scene;
    }

    static SocketExitRequest from(SocketClientMessage message){
        return new SocketExitRequest(ExitScene.from(message.getParameter(0)));
    }

    ExitScene getScene(){
        return scene;
    }
}
