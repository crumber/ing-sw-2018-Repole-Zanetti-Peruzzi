package repolezanettiperuzzi.view;

/**
 * Classe che modellizza il timer della cli
 * @author Andrea Zanetti
 */
import repolezanettiperuzzi.controller.Controller;

import java.util.Timer;
import java.util.TimerTask;

public class CLITimer extends TimerTask {

    private String players;
    private Timer timer;
    private int currentTime;

    /**
     * Costruttore
     * @param players Stringa che indica il player
     * @param currentTime Intero che indica valore timer
     */
    public CLITimer(String players, int currentTime){
        this.players = players;
        this.currentTime = currentTime;
        this.timer = new Timer();
    }

    /**
     * Mostra i giocatori in attesa e il timer
     */
    @Override
    public void run() {

        System.out.print("\033[H\033[2J");
        System.out.flush();
        System.out.println("Giocatori in attesa:                     Timer: "+currentTime+"\n");
        System.out.println(players);

        currentTime--;

        if (currentTime==0) {

            timer.cancel();
            timer.purge();
        }

    }

    /**
     * Inizializza il timer e il player
     * @param currentTime Intero che indica valore timer corrente
     * @param players Stringa che rapprenta il player
     */
    public void setTimeAndPlayer(int currentTime, String players){
        this.currentTime = currentTime;
        this.players = players;
    }

    /**
     *
     * @return Tempo rimanente
     */
    public Timer getTimer(){

        return this.timer;

    }
}
