package repolezanettiperuzzi.view;

import repolezanettiperuzzi.common.HandlerSkeletonRMI;
import repolezanettiperuzzi.controller.ControllerTimer;

import java.io.Console;
import java.io.IOException;
import java.util.Scanner;
import java.util.Timer;

public class GameViewCLI implements Runnable {

    private GameView gV;
    private boolean isTimerOn;
    private CLITimer cliTimer;
    private Timer timer;

    public GameViewCLI(GameView gV){
        this.gV = gV;
        this.isTimerOn = false;
    }

    public void loginScene(){
        Console console = System.console();
        System.out.print("\033[H\033[2J");
        System.out.flush();
        if (console == null) {
            System.out.println("\nCLI non disponibile su Intellij. Fai partire jar.");
            System.exit(0);
        }
        System.out.println("\n\n//// Schermata di login ////\n\n");
        Scanner scanner = new Scanner(System.in);
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = new String(console.readPassword());
        System.out.print("Connection ('s' for Socket 'r' for RMI): ");
        String connection = scanner.nextLine();
        if(connection.equals("s")){
            connection = "Socket";
        } else if(connection.equals("r")){
            connection = "RMI";
        }
        try {
            gV.onLogin(username, password, connection, "CLI");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void refreshWaitingRoom(int setTimer, String[] players){
        String playersString = "";
        //se setTimer e' maggiore di 0 allora setto il timer
        System.out.println("Giocatori in attesa: \n");
        int i = 0;
        while(i<players.length){
            playersString += players[i]+"\n";
            i++;
        }

        if(!isTimerOn && setTimer!=0 && setTimer!=-1) {
            this.cliTimer = new CLITimer(playersString, setTimer);
            timer = cliTimer.getTimer();
            timer.schedule(cliTimer, 0, 1000);
            isTimerOn = true;
        } else if(isTimerOn && setTimer!=0 && setTimer!=-1){
            cliTimer.setTimeAndPlayer(setTimer, playersString);
        } else if(isTimerOn && setTimer==-1){
            timer.cancel();
            timer.purge();
            isTimerOn = false;
            System.out.print("\033[H\033[2J");
            System.out.flush();
            System.out.println("Giocatori in attesa: \n");
            i = 0;
            while(i<players.length){
                System.out.println(players[i]);
                i++;
            }
        } else if(!isTimerOn && setTimer==0){
            System.out.print("\033[H\033[2J");
            System.out.flush();
            System.out.println("Giocatori in attesa: \n");
            i = 0;
            while(i<players.length){
                System.out.println(players[i]);
                i++;
            }
        }

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

        System.out.println("ciaoIO");
    }
}
