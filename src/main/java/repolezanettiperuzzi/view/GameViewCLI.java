package repolezanettiperuzzi.view;

import repolezanettiperuzzi.common.HandlerSkeletonRMI;

import java.io.Console;
import java.io.IOException;
import java.util.Scanner;

public class GameViewCLI implements Runnable {

    private GameView gV;

    public GameViewCLI(GameView gV){
        this.gV = gV;
    }

    public void loginScene(){
        Console console = System.console();
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

        System.out.println("ciaoIO");
    }
}
