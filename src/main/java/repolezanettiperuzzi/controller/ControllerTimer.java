package repolezanettiperuzzi.controller;

import org.json.simple.parser.ParseException;
import repolezanettiperuzzi.model.actions.BeginTurn;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;


public class ControllerTimer extends TimerTask {

    private int currentTime;
    private Timer timer;
    private Controller controller;
    private ControllerState nextState;
    private String currentState;



    public ControllerTimer(String timerType, Controller controller){

        this.timer = new Timer();
        this.controller = controller;
        this.currentState=timerType;
        this.currentTime = 30;

        //TODO apro file configurazione durata timer

        switch(timerType){

            case "setConnection" :
                this.nextState = new FetchState();
                break;

            case "chooseWindow" :
                this.nextState = new BeginRoundState();
                break;

            case "playerTurn" :
                this.nextState = new TurnState();
                break;

            default :
                break;

        }
    }

    @Override
    public void run() {
        currentTime--;//aggiorna il timer ad ogni esecuzione
        if (currentTime==0){

            timer.cancel();
            timer.purge();
            controller.setIsTimerOn(false);

            switch (currentState){

                case "setConnection" :

                    try {
                        controller.setState(new SetConnectionState());
                        ((SetConnectionState) controller.getState()).notifyOnBeginChooseWindow();
                    } catch (IOException | ParseException e) {
                        e.printStackTrace();
                    }
                    break;
                case "chooseWindow" :

                    try {
                        controller.setState(new FetchState());
                        ((FetchState) controller.getState()).checkConnectedPlayers();
                    } catch (IOException | ParseException e) {
                        e.printStackTrace();
                    }
                    break;

                case "playerTurn" :

                    try {
                        ((TurnState) controller.getState()).passToNextTurn(controller.board.getPlayer(BeginTurn.getCurrentPlayer()));
                    } catch (IOException | ParseException e) {
                        e.printStackTrace();
                    }
                    break;

                default :
                    break;


            }


        }

    }

    public int getCurrentTime(){

        return this.currentTime;
        
    }

    public Timer getTimer(){

        return this.timer;

    }

}

