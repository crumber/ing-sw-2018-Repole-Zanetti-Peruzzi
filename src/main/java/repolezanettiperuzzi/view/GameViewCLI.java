package repolezanettiperuzzi.view;

import repolezanettiperuzzi.common.HandlerSkeletonRMI;

import java.io.IOException;

public class GameViewCLI implements Runnable {

    private GameView gV;

    public GameViewCLI(GameView gV){
        this.gV = gV;
    }

    public void refreshWaitingRoom(int setTimer, String[] players){
        //se setTimer e' maggiore di 0 allora setto il timer
        System.out.println("Giocatori in attesa: \n");
        int i = 0;
        while(i<players.length){
            System.out.println(players[i]);
            i++;
        }
        System.out.println("\n\n\n\n");
    }

    public void setWaitingRoomScene()  {
        System.out.println("Sala d'attesa: \n\n");
        try {
            gV.waitingRoomLoaded();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateView() {
        //aggiorno View
    }

    @Override
    public void run() {

    }
}
