package repolezanettiperuzzi.controller;

/**
 * Parameters carried by the socket exit command.
 */
class SocketExitRequest {

    private final SocketExitScene scene;

    private SocketExitRequest(SocketExitScene scene){
        this.scene=scene;
    }

    static SocketExitRequest from(SocketClientMessage message){
        return new SocketExitRequest(SocketExitScene.fromWireValue(message.getParameter(0)));
    }

    SocketExitScene getScene(){
        return scene;
    }
}
