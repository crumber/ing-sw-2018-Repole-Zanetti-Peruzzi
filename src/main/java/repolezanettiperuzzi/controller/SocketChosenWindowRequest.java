package repolezanettiperuzzi.controller;

/**
 * Parameters carried by the socket chosenWindow command.
 */
class SocketChosenWindowRequest {

    private final String windowName;

    private SocketChosenWindowRequest(String windowName){
        this.windowName=windowName;
    }

    static SocketChosenWindowRequest from(SocketClientMessage message){
        return new SocketChosenWindowRequest(message.getParameter(0).replace("-", " "));
    }

    String getWindowName(){
        return windowName;
    }
}
