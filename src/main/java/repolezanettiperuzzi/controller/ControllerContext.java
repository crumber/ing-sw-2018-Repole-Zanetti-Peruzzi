package repolezanettiperuzzi.controller;

public class ControllerContext {

    private ControllerState currentState;

    public ControllerContext(){

       this.currentState = null;

    }

    public void setState(ControllerState nextState){

        this.currentState=nextState;

    }

    public ControllerState getState(){

        return this.currentState;

    }

    public void currentAction(){

        this.currentState.doAction();
    }


}
