package repolezanettiperuzzi.controller;

/**
 * Parameters carried by the socket insertDie command.
 */
class SocketInsertDieRequest {

    private final String draftPosition;
    private final String xPosition;
    private final String yPosition;

    private SocketInsertDieRequest(String draftPosition, String xPosition, String yPosition){
        this.draftPosition=draftPosition;
        this.xPosition=xPosition;
        this.yPosition=yPosition;
    }

    static SocketInsertDieRequest from(SocketClientMessage message){
        return new SocketInsertDieRequest(
                message.getParameter(0),
                message.getParameter(1),
                message.getParameter(2)
        );
    }

    String getDraftPosition(){
        return draftPosition;
    }

    String getXPosition(){
        return xPosition;
    }

    String getYPosition(){
        return yPosition;
    }

    String toTurnStateParameter(){
        return draftPosition+" "+xPosition+" "+yPosition;
    }
}
