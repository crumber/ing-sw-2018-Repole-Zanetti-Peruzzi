package repolezanettiperuzzi.view;

import java.io.IOException;
import java.util.Timer;

/**
 * Classe che rappresenta Spegnimento
 * @author Andrea Zanetti
 */
public class ShutdownConsole extends Thread{

    private String lastScene;
    private Timer timer;
    private GameView gameview;

    /**
     * Costruttore
     * @param lastScene Ultima scena
     * @param timer Timer
     * @param gameview Game view
     */
    public ShutdownConsole(String lastScene, Timer timer, GameView gameview){
        this.lastScene = lastScene;
        this.timer = timer;
        this.gameview = gameview;
    }

    /**
     * Imposta l'ultima scena
     * @param lastScene Ultima scena
     */
    public void setScene(String lastScene){
        this.lastScene = lastScene;
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
