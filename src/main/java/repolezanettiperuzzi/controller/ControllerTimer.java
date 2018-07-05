package repolezanettiperuzzi.controller;

import org.json.simple.parser.ParseException;
import repolezanettiperuzzi.common.DynamicPath;
import repolezanettiperuzzi.model.actions.BeginTurn;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

/**
 * Classe che rappresenta il controller del timer
 * @author Giampiero Repole
 */
public class ControllerTimer extends TimerTask {

    private int currentTime;
    private Timer timer;
    private Controller controller;
    private ControllerState nextState;
    private String currentState;

    /**
     * Costruttore
     * @param timerType Tipo di timer
     * @param controller Controller
     */
    public ControllerTimer(String timerType, Controller controller) throws IOException {

        this.timer = new Timer();
        this.controller = controller;
        this.currentState=timerType;

        DynamicPath dp = new DynamicPath("");

        if(dp.isJar()){

            URI timerUri= null;

            try{

                timerUri = ControllerTimer.class.getResource("/gamedata/timer.txt").toURI();

            }catch(URISyntaxException e){

                e.printStackTrace();
            }

            BufferedReader bR = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/gamedata/timer.txt")));

            switch(timerType) {

                case "setConnection":
                    this.nextState = new FetchState();
                    this.currentTime = Integer.parseInt(bR.readLine());
                    break;

                case "chooseWindow":
                    this.nextState = new BeginRoundState();
                    bR.readLine();
                    this.currentTime = Integer.parseInt(bR.readLine());
                    break;

                case "playerTurn":
                    this.nextState = new TurnState();
                    bR.readLine();
                    bR.readLine();
                    this.currentTime = Integer.parseInt(bR.readLine());
                    break;
                default :
                    break;

            }
            bR.close();

        }else{

            BufferedReader file = new BufferedReader(new FileReader("gamedata/timer.txt"));



            switch(timerType) {

                case "setConnection":
                    this.nextState = new FetchState();
                    this.currentTime = Integer.parseInt(file.readLine());
                    break;

                case "chooseWindow":
                    this.nextState = new BeginRoundState();
                    file.readLine();
                    this.currentTime = Integer.parseInt(file.readLine());
                    break;

                case "playerTurn":
                    this.nextState = new TurnState();
                    file.readLine();
                    file.readLine();
                    this.currentTime = Integer.parseInt(file.readLine());
                    break;

            }

            file.close();

        }

    }

    /**
     * Aggiorna il timer ad ogni esecuzione
     */
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
                        controller.setStateNoDoAction(new TurnState());
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

    /**
     *
     * @return Il timer corrente
     */
    public int getCurrentTime(){

        return this.currentTime;
        
    }

    /**
     *
     * @return Il timer
     */
    public Timer getTimer(){

        return this.timer;

    }

}

