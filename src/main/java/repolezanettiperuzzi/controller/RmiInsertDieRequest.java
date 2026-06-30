package repolezanettiperuzzi.controller;

/**
 * Parameters carried by the RMI insertDie command.
 */
class RmiInsertDieRequest {

    private final int draftPosition;
    private final int xPosition;
    private final int yPosition;

    RmiInsertDieRequest(int draftPosition, int xPosition, int yPosition){
        this.draftPosition=draftPosition;
        this.xPosition=xPosition;
        this.yPosition=yPosition;
    }

    int getDraftPosition(){
        return draftPosition;
    }

    int getXPosition(){
        return xPosition;
    }

    int getYPosition(){
        return yPosition;
    }

    String toTurnStateParameter(){
        return draftPosition+" "+xPosition+" "+yPosition;
    }
}
