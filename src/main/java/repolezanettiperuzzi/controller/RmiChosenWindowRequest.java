package repolezanettiperuzzi.controller;

/**
 * Parameters carried by the RMI chosen-window command.
 */
class RmiChosenWindowRequest {

    private final String windowName;

    RmiChosenWindowRequest(String windowName){
        this.windowName=windowName.replace("-", " ");
    }

    String getWindowName(){
        return windowName;
    }
}
