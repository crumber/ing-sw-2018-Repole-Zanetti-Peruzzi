package repolezanettiperuzzi.controller;

import org.json.simple.parser.ParseException;
import repolezanettiperuzzi.model.GameBoard;
import repolezanettiperuzzi.model.Player;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

public class Controller {

    //view
    protected ArrayList<Player> view;
    //model
    protected GameBoard board;

    protected ControllerState currentState;

    private boolean isTimerOn;

    private Timer timer;
    private ControllerTimer task;

    private HandlerControllerRMI handlerRMI;

    public Controller(List<Player> view, GameBoard board){

       this.currentState = null;
       this.view = (ArrayList<Player>)view;
       this.board = board;
       this.isTimerOn = false;
       this.handlerRMI = null;

    }

    public synchronized void setState(ControllerState nextState) throws IOException, ParseException {

        this.currentState=nextState;
        this.currentAction();

    }

    public synchronized ControllerState getState(){
        return this.currentState;

    }

    public boolean isTimerOn(){
        return this.isTimerOn;
    }

    public synchronized void setTimer(String timerType){

        //TODO creare il timer su un altro thread
        this.task = new ControllerTimer(timerType,this);
        this.timer = this.task.getTimer();
        timer.schedule(this.task,0,1000);
        this.isTimerOn = true;

    }

    public synchronized void cancelTimer(){

        //TODO cancello il timer
        this.timer.cancel();
        this.timer.purge();
        this.isTimerOn = false;
        
    }

    public void setIsTimerOn(boolean condition){

        this.isTimerOn=condition;

    }

    public synchronized int getCurrentTime(){
        if(isTimerOn){
            return this.task.getCurrentTime();
        } else {
            return -1;
        }
    }

    //this method do the action of the current state
    private void currentAction() throws IOException, ParseException {

        this.currentState.doAction(this);

    }

    public void setHandlerRMI(HandlerControllerRMI handlerRMI){
        this.handlerRMI = handlerRMI;
    }

    public HandlerControllerRMI getHandlerRMI(){
        return this.handlerRMI;
    }

    public void notifyExitToClient(String playerID) throws IOException {
        Player player = board.getPlayerByName(playerID);
        if(player.getConnection().equals("Socket")){
            Socket socket = null;
            try {
                socket = new Socket(player.getAddress(), player.getPort());
            } catch (IOException e) {
                e.printStackTrace();
            }
            HandlerControllerSocket handlerControllerSocket = new HandlerControllerSocket(this, socket);
            handlerControllerSocket.notifyExitToClient();
        } else if(board.getPlayerByName(playerID).getConnection().equals("RMI")){

        }
    }

    public void setLiveStatusOffline(String playerName){
        System.out.println("exit game "+playerName);
        for(int i = 0; i<this.board.getNPlayers(); i++){
            if(this.board.getPlayer(i).getName().equals(playerName)){
                this.board.getPlayer(i).setLiveStatus(false);
                break;
            }
        }
    }


}
