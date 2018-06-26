package repolezanettiperuzzi.view;

import repolezanettiperuzzi.common.modelwrapper.BoxClient;
import repolezanettiperuzzi.common.modelwrapper.DieClient;
import repolezanettiperuzzi.common.modelwrapper.GameBoardClient;
import repolezanettiperuzzi.common.modelwrapper.WindowClient;

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
    private String lastScene;

    final String ANSI_RESET = "\u001B[0m";
    final String ANSI_BLACK = "\u001B[30m";
    final String ANSI_RED = "\u001B[31m";
    final String ANSI_GREEN = "\u001B[32m";
    final String ANSI_YELLOW = "\u001B[33m";
    final String ANSI_BLUE = "\u001B[34m";
    final String ANSI_PURPLE = "\u001B[35m";
    final String ANSI_CYAN = "\u001B[36m";
    final String ANSI_WHITE = "\u001B[37m";

    public GameViewCLI(GameView gV){
        this.gV = gV;
        this.isTimerOn = false;
    }

    public void loginScene(String message){
        Console console = System.console();
        System.out.print("\033[H\033[2J");
        System.out.flush();
        if (console == null) {
            System.out.println("\nCLI non disponibile su Intellij. Fai partire il jar.");
            System.exit(0);
        }
        System.out.println("\n\n//// "+ message +"////\n\n");
        Scanner scanner = new Scanner(System.in);
        String username;
        do {
            System.out.print("Username: ");
            username = scanner.nextLine();
        }while(username.equals(""));
        String password;
        do {
            System.out.print("Password: ");
            password = new String(console.readPassword());
        }while(password.equals(""));
        String connection;
        do {
            System.out.print("Connection ('s' for Socket 'r' for RMI): ");
            connection = scanner.nextLine();
        }while(!connection.equals("s") && !connection.equals("r"));
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
        this.lastScene = "waitingRoom";
        Runtime.getRuntime().addShutdownHook(new ShutdownConsole(lastScene, timer, gV));
        System.out.println("Sala d'attesa: \n\n");
        try {
            gV.waitingRoomLoaded();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setChooseWindowScene(){
        this.lastScene = "chooseWindow";
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

        String highLowEdge="+———";
        String space="        ";  //space ha lo stesso numero di caratteri di CARDN:
        String bigspace="                  ";
        String typeEdge1="+———+";
        String typeEdge2="+———";
        String middleEdge="|———";
        String typeEdge3="+———|";
        String nFV="  FV: ";
        String nameWindowChoose;
        boolean choose;

        if(windows.get(0).rowSize()==1){


            highLowEdge+="+";
            middleEdge+="|";

        }else{

            for(int i=1;i<windows.get(0).columnSize()-1;i++){

                highLowEdge+=typeEdge2;
                middleEdge+=typeEdge2;

            }

            highLowEdge+=typeEdge1;
            middleEdge+=typeEdge3;
        }

        for(int j=0; j<2; j++) {

            for (int i = 0; i < windows.get(0).rowSize(); i++) {

                if (i == 0 && j==0) {

                    System.out.print(space + "WINDOW 1: " + highLowEdge + space + "WINDOW 2: "+ highLowEdge  + "\n");

                }else if( i==0 && j!=0){

                    j=2;
                    System.out.print(space + "WINDOW 3: " + highLowEdge + space + "WINDOW 4: " + highLowEdge + "\n");

                }

                System.out.print(bigspace);
                for(int k=0; k<windows.get(0).columnSize();k++) {

                    System.out.print(createWindowCli(windows.get(j), i, k));

                }
                System.out.print( "|" + bigspace);


                for(int k=0; k<windows.get(0).columnSize();k++) {

                    System.out.print(createWindowCli(windows.get(j+1), i, k));

                }
                System.out.print( "|" + "\n");

                if (i < windows.get(0).rowSize() - 1) {

                    System.out.print(bigspace + middleEdge + bigspace + middleEdge + "\n");

                } else if (i == windows.get(0).rowSize() - 1) {

                    System.out.print(bigspace + highLowEdge + bigspace + highLowEdge + "\n\n");

                }
            }
        }

        System.out.println(space + "WINDOW 1:  name:" + windows.get(0).getName() + nFV + windows.get(0).getFTokens());
        System.out.println(space + "WINDOW 2:  name:" + windows.get(1).getName() + nFV + windows.get(1).getFTokens());
        System.out.println(space + "WINDOW 3:  name:" + windows.get(2).getName() + nFV + windows.get(2).getFTokens());
        System.out.println(space + "WINDOW 4:  name:" + windows.get(3).getName() + nFV + windows.get(3).getFTokens() + "\n");

        do{
            choose=true;
            System.out.print(space +"Choose your window, write its number: ");
            Scanner scanner = new Scanner(System.in);
            int numWindow = scanner.nextInt();
            if (numWindow >0 && numWindow<5) {

                nameWindowChoose = windows.get(numWindow).getName();

            } else {

                choose=false;
            }

        }while (!choose);

        //ANDARE AVANTI, HO LA SCELTA DEL GIOCATORE (NOME DELLA WINDOW)
    }

    public String createWindowCli(WindowClient window, int x,int y){

        String bound;
        BoxClient[][] boxes = window.getBoardBox();

        if(window.thereIsDie(x,y)){

            bound=drawDie(window.getDieFromBoardBox(x,y));

        }
        else if(boxes[x][y].getBoundColour()!=null) {

            switch (boxes[x][y].getBoundColour()) {

                case RED: {

                    bound = "| "+ANSI_RED+"R "+ANSI_RESET;
                    break;

                }
                case GREEN: {

                    bound = "| "+ANSI_GREEN+"G "+ANSI_RESET;
                    break;

                }
                case BLUE: {

                    bound = "| "+ANSI_BLUE+"B "+ANSI_RESET;
                    break;

                }
                case PURPLE: {

                    bound = "| "+ANSI_PURPLE+"P "+ANSI_RESET;
                    break;

                }
                default: {

                    bound = "| "+ANSI_YELLOW+"Y "+ANSI_RESET;
                    break;

                }

            }
        }
        else if(boxes[x][y].getBoundValue()!=null){

            switch (boxes[x][y].getBoundValue()){

                case ONE: {

                    bound="| 1 ";
                    break;

                }
                case TWO: {

                    bound="| 2 ";
                    break;

                }
                case THREE: {

                    bound="| 3 ";
                    break;

                }
                case FOUR: {

                    bound="| 4 ";
                    break;

                }
                case FIVE: {

                    bound="| 5 ";
                    break;

                }
                default: {

                    bound="| 6 ";
                    break;
                }
            }

        }else{

            bound="|   ";
        }

        return bound;
    }

    public String drawDie(DieClient dieClient){

        String bound;

        switch (dieClient.getColourDie()){

            case RED: {

                bound = "| "+ANSI_RED;
                break;

            }
            case GREEN: {

                bound = "| "+ANSI_GREEN;
                break;

            }
            case BLUE: {

                bound = "| "+ANSI_BLUE;
                break;

            }
            case PURPLE: {

                bound = "| "+ANSI_PURPLE;
                break;

            }
            default: {

                bound = "| "+ANSI_YELLOW;
                break;

            }

        }
        switch (dieClient.getValueDie()){

            case ONE: {

                bound+="1 "+ANSI_RESET;
                break;

            }
            case TWO: {

                bound+="2 "+ANSI_RESET;
                break;

            }
            case THREE: {

                bound+="3 "+ANSI_RESET;
                break;

            }
            case FOUR: {

                bound+="4 "+ANSI_RESET;
                break;

            }
            case FIVE: {

                bound+="5 "+ANSI_RESET;
                break;

            }
            default: {

                bound+="6 "+ANSI_RESET;
                break;
            }

        }

        return bound;

    }

    public void updateView(GameBoardClient boardClient, String myName) {

        String highLowEdge="+———";
        String typeEdge1="+———+";
        String typeEdge2="+———";
        String middleEdge="|———";
        String typeEdge3="+———|";
        String space="   ";
        String middlespace="          ";
        String bigspace="                ";
        int myNumber=0; //indica quale numero ha il giocatore nella boardgame (utile nella stampa my window)

        if(boardClient.getPlayer(0).getWindow().rowSize() ==1){

            highLowEdge+="+";
            middleEdge+="|";

        }else{

            for(int i=1;i<boardClient.getPlayer(0).getWindow().columnSize()-1;i++){

                highLowEdge+=typeEdge2;
                middleEdge+=typeEdge2;

            }

            highLowEdge+=typeEdge1;
            middleEdge+=typeEdge3;
        }

        for(int n=0; n<boardClient.getNPlayers();n++) {

            if (myName.compareTo(boardClient.getPlayer(n).getName()) == 0) {

                myNumber = n;
            }
        }


        //WINDOWS
        System.out.print("\n");

        for (int i = 0; i <boardClient.getPlayer(0).getWindow().rowSize(); i++) {

            if (i == 0) {

                int l=0;
                System.out.print(space);
                for(int n=0; n<boardClient.getNPlayers();n++) {

                    if(myName.compareTo(boardClient.getPlayer(n).getName())!=0) {

                        System.out.print("WINDOW " + l + ": " + highLowEdge + bigspace);
                        l++;

                    }

                }
                System.out.print("\n");

            }

            System.out.print(space +middlespace);

            for(int n=0; n<boardClient.getNPlayers();n++) {

                if(myName.compareTo(boardClient.getPlayer(n).getName())!=0) {

                    for (int k = 0; k < boardClient.getPlayer(n).getWindow().columnSize(); k++) {

                        System.out.print(createWindowCli(boardClient.getPlayer(n).getWindow(), i, k));

                    }
                    System.out.print("|" + bigspace + middlespace);

                }

            }
            System.out.print("\n");

            if (i < boardClient.getPlayer(0).getWindow().rowSize() - 1) {

                System.out.print(space +middlespace);

                for(int n=0; n<boardClient.getNPlayers();n++) {

                    if(myName.compareTo(boardClient.getPlayer(n).getName())!=0) {

                        System.out.print(middleEdge + bigspace + middlespace);

                    }

                }
                System.out.print("\n");


            } else if (i == boardClient.getPlayer(0).getWindow().rowSize() - 1) {

                System.out.print(space +middlespace);
                for (int n = 0; n < boardClient.getNPlayers(); n++) {

                    if(myName.compareTo(boardClient.getPlayer(n).getName())!=0) {

                        System.out.print(highLowEdge + bigspace + middlespace);

                    }

                }

                System.out.print("\n");
                int l=0;
                for (int n = 0; n < boardClient.getNPlayers(); n++) {

                    if(myName.compareTo(boardClient.getPlayer(n).getName())!=0) {

                        System.out.print("\n" + space + "WINDOW " + l + " BELONGS TO: " + boardClient.getPlayer(n).getName() + space + "FAVOR TOKEN: " + boardClient.getPlayer(n).getFavorTokens());
                        l++;

                    }
                }
            }
        }
        System.out.print("\n\n\n\n");


        // ROUND TRACK AND ROUND GAME
        System.out.print(space +bigspace +ANSI_CYAN +"ROUND TRACK" +ANSI_RESET +bigspace +bigspace +bigspace +bigspace +"ROUND GAME: " +boardClient.getRound() +"\n\n");
        for(int i=0;i<10;i++){

            System.out.print(space +"ROUND " +(i+1) +": ");

            if(!boardClient.isEmptyRoundTrack()) {

                if(i<boardClient.sizeRoundTrack()) {

                    for (int j = 0; j < boardClient.sizeDiceRoundTrack(i); j++) {

                        System.out.print(drawDie(boardClient.getDieFromRoundTrack(i, j)) + " ");

                    }
                }
            }

            System.out.print("\n");
        }
        System.out.print("\n\n");


        //PERSONAL WINDOW, DICE ON DRAFT AND FAVOUR TOKEN

        for (int i = 0; i <boardClient.getPlayer(myNumber).getWindow().rowSize(); i++) {

            if (i == 0) {

                System.out.print(space + "YOUR WINDOW: " + highLowEdge + bigspace +"DRAFT: ");

                for(int j=0;j<boardClient.getSizeDraft();j++){

                    System.out.print(drawDie(boardClient.getDieDraft(j)) +" ");

                }

                System.out.print("\n");
            }

            System.out.print(bigspace);

            for(int k=0; k<boardClient.getPlayer(myNumber).getWindow().columnSize();k++) {

                System.out.print(createWindowCli(boardClient.getPlayer(myNumber).getWindow(), i, k));

            }

            System.out.print( "|" + "\n");

            if (i < boardClient.getPlayer(myNumber).getWindow().rowSize() - 1) {

                System.out.print(bigspace + middleEdge +"\n");

            } else if (i == boardClient.getPlayer(myNumber).getWindow().rowSize() - 1) {

                System.out.print(bigspace + highLowEdge + bigspace + "YOUR FAVOUR TOKEN: " + boardClient.getPlayer(myNumber).getFavorTokens() + bigspace + "SECRET COLOUR: ");

                switch (boardClient.getPlayer(myNumber).getSecretColour()) {

                    case RED: {

                        System.out.println(ANSI_RED +"RED\n\n" +ANSI_RESET);
                        break;

                    }case BLUE: {

                        System.out.println(ANSI_BLUE +"BLUE\n\n" +ANSI_RESET);
                        break;

                    }case GREEN: {

                        System.out.println(ANSI_GREEN +"GREEN\n\n" +ANSI_RESET);
                        break;

                    }case PURPLE: {

                        System.out.println(ANSI_PURPLE +"PURPLE\n\n" +ANSI_RESET);
                        break;

                    }default: {

                        System.out.println(ANSI_YELLOW +"YELLOW\n\n" +ANSI_RESET);
                        break;

                    }
                }
            }


        }

        //PUBLIC CARD


    }

    @Override
    public void run() {

        System.out.println("ciaoIO");
    }
}
