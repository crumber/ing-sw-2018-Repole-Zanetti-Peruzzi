package repolezanettiperuzzi.view;

import repolezanettiperuzzi.view.modelwrapper.BoxClient;
import repolezanettiperuzzi.view.modelwrapper.WindowClient;

import java.io.Console;
import java.io.IOException;
import java.util.ArrayList;
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
            System.out.println("\nCLI non disponibile su Intellij. Fai partire il jar.");
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
            this.timer = cliTimer.getTimer();
            this.timer.schedule(cliTimer, 0, 1000);
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

    public void setChooseWindowScene(){
        System.out.print("\033[H\033[2J");
        System.out.flush();
        System.out.println("Choose window: \n\n");
        this.timer.cancel();
        this.timer.purge();
        this.isTimerOn = false;
        try {
            gV.chooseWindowSceneLoaded();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void viewWindows(ArrayList<WindowClient> windows) {

        String highLowedge="+———+———+———+———+———+";
        String space="     ";
        String edge="|———+———+———+———+———|";

        for(int i=0; i<4; i++){

            if(i==0){

                System.out.println(highLowedge + space +highLowedge +space +highLowedge +space +highLowedge +"\n");

            }

            System.out.println(createBoundCli(windows.get(0),i,0) +createBoundCli(windows.get(0),i,1) +createBoundCli(windows.get(0),i,2) +createBoundCli(windows.get(0),i,3) +createBoundCli(windows.get(0),i,4) +space);
            System.out.println(createBoundCli(windows.get(1),i,0) +createBoundCli(windows.get(1),i,1) +createBoundCli(windows.get(1),i,2) +createBoundCli(windows.get(1),i,3) +createBoundCli(windows.get(1),i,4) +space);
            System.out.println(createBoundCli(windows.get(2),i,0) +createBoundCli(windows.get(2),i,1) +createBoundCli(windows.get(2),i,2) +createBoundCli(windows.get(2),i,3) +createBoundCli(windows.get(2),i,4) +space);
            System.out.println(createBoundCli(windows.get(3),i,0) +createBoundCli(windows.get(3),i,1) +createBoundCli(windows.get(3),i,2) +createBoundCli(windows.get(3),i,3) +createBoundCli(windows.get(3),i,4) +"\n");

            if(i>0 && i<3){

                System.out.println(edge +space +edge +space +edge +space +edge +"\n");

            }else if(i==4){

                System.out.println(highLowedge + space +highLowedge +space +highLowedge +space +highLowedge +"\n");

            }
        }
    }

    public String createBoundCli(WindowClient window, int x,int y){

        String bound="|   |";
        BoxClient[][] boxes = window.getBoardBox();
        switch (boxes[x][y].getBoundColour()){

            case RED: {

                bound="| R |";
                break;

            }
            case GREEN: {

                bound="| G |";
                break;

            }
            case BLUE: {

                bound="| B |";
                break;

            }
            case PURPLE: {

                bound="| P |";
                break;

            }
            case YELLOW: {

                bound="| Y |";
                break;

            }
            default: {

                break;

            }

        }
        switch (boxes[x][y].getBoundValue()){

            case ONE: {

                bound="| 1 |";
                break;

            }
            case TWO: {

                bound="| 2 |";
                break;

            }
            case THREE: {

                bound="| 3 |";
                break;

            }
            case FOUR: {

                bound="| 4 |";
                break;

            }
            case FIVE: {

                bound="| 5 |";
                break;

            }
            case SIX: {

                bound="| 6 |";
                break;

            }
            default:{

                break;

            }
        }

        return bound;
    }

    public void updateView() {
        //aggiorno View
    }

    @Override
    public void run() {

        System.out.println("ciaoIO");
    }
}
