package repolezanettiperuzzi.view;

import repolezanettiperuzzi.controller.Controller;

import java.util.Timer;
import java.util.TimerTask;

public class CLITimer extends TimerTask {

    private String players;
    private Timer timer;
    private int currentTime;

    public CLITimer(String players, int currentTime){
        this.players = players;
        this.currentTime = currentTime;
        this.timer = new Timer();
    }

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

    public void setTimeAndPlayer(int currentTime, String players){
        this.currentTime = currentTime;
        this.players = players;
    }

    public Timer getTimer(){

        return this.timer;

    }
}
