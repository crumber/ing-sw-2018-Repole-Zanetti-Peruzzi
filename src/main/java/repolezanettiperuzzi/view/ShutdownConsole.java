package repolezanettiperuzzi.view;

import java.io.IOException;
import java.util.Timer;

public class ShutdownConsole extends Thread{

    private String lastScene;
    private Timer timer;
    private GameView gameview;

    public ShutdownConsole(String lastScene, Timer timer, GameView gameview){
        this.lastScene = lastScene;
        this.timer = timer;
        this.gameview = gameview;
    }

    public void run()
    {
        try {
            if(timer!=null){
                timer.cancel();
                timer.purge();
            }
            gameview.notifyOnExit(lastScene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
