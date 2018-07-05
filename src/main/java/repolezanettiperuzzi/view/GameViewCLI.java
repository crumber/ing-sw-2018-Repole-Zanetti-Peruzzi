package repolezanettiperuzzi.view;

import repolezanettiperuzzi.common.modelwrapper.*;

import java.io.Console;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Scanner;
import java.util.Timer;
import java.util.concurrent.*;

/**
 * Classe che modella la Game view lato cli
 * @author Alessandro Peruzzi
 * @author Andrea Zanetti
 */
public class GameViewCLI implements Runnable {

    private GameView gV;
    private boolean isTimerOn;
    private CLITimer cliTimer;
    private Timer timer;
    private String lastScene;
    private boolean hasShutdownHook;
    private ShutdownConsole shutdownConsole;
    private GameBoardClient boardClient;
    public static int globalGameTime = 0;
    private String currentPlayer;
    private boolean myTurn;
    private int lastToolCard;
    private boolean card11;

    String ANSI_RESET = "\u001B[0m";
    String ANSI_BLACK = "\u001B[30m";
    String ANSI_RED = "\u001B[31m";
    String ANSI_GREEN = "\u001B[32m";
    String ANSI_YELLOW = "\u001B[33m";
    String ANSI_BLUE = "\u001B[34m";
    String ANSI_PURPLE = "\u001B[35m";
    String ANSI_CYAN = "\u001B[36m";
    String ANSI_WHITE = "\u001B[37m";
    private static String OS = System.getProperty("os.name");

    /**
     * Costruttore
     * @param gV Game view
     */
    public GameViewCLI(GameView gV){
        this.gV = gV;
        this.isTimerOn = false;
        this.hasShutdownHook = false;
        this.myTurn = false;
        this.lastToolCard = 0;
        card11 = false;
        /*if(OS.startsWith("Windows")){
            ANSI_RESET = "";
            ANSI_BLACK = "";
            ANSI_RED = "";
            ANSI_GREEN = "";
            ANSI_YELLOW = "";
            ANSI_BLUE = "";
            ANSI_PURPLE = "";
            ANSI_CYAN = "";
            ANSI_WHITE = "";
        }*/
    }

    /**
     * Pulisce lo screen
     */
    public void clearScreen(){
        if(!OS.startsWith("Windows")){
            System.out.print("\033[H\033[2J");
            System.out.flush();
        } else {
            try {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            /*System.out.print("\f");
             PROVA QUESTA ALTRIMENTI
            for(int i = 0; i<25; i++){
                System.out.println("");
            }
             */
        }
    }

    /**
     * Svolge la readline e in caso di scadenza di timeout ritorna che è il tempo è scaduto, se inserisce un valore sbagliato ripone la domanda al client
     * @param timeout Intero che indica il valore del timeout
     * @param message Domanda
     * @param actions Parametri corretti
     * @param unit Time Unit
     * @return La stringa della risposta
     * @throws InterruptedException Interruzione thread
     */
    public String readLine(int timeout, String message, String[] actions, TimeUnit unit) throws InterruptedException {
        ExecutorService ex = Executors.newSingleThreadExecutor();
        String input = null;
        Future<String> result = null;
        try {
            result = ex.submit(new ConsoleInputReadTask(actions, message, gV, this.lastScene));
            try {
                input = result.get(timeout, unit);
            } catch (ExecutionException e) {
                e.getCause().printStackTrace();
            } catch (TimeoutException e) {
                result.cancel(true);
                myTurn = false;
                System.out.println("\nTimer expired!");
            }

        } finally {
            result.cancel(true);
            ex.shutdownNow();
        }
        return input;
    }

    /**
     * Login scene
     * @param message Messaggio da stampare
     */
    public void loginScene(String message){
        Console console = System.console();
        clearScreen();
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

    /**
     * Aggiorna la waiting room
     * @param setTimer Timer
     * @param players Array di stringhe che rappresentano i vari player
     */
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
            clearScreen();
            System.out.println("Giocatori in attesa: \n");
            i = 0;
            while(i<players.length){
                System.out.println(players[i]);
                i++;
            }
        } else if(!isTimerOn && setTimer==0){
            clearScreen();
            System.out.println("Giocatori in attesa: \n");
            i = 0;
            while(i<players.length){
                System.out.println(players[i]);
                i++;
            }
        }

    }

    /**
     * Imposta la waiting room
     */
    public void setWaitingRoomScene()  {
        this.lastScene = "waitingRoom";
        if(!this.hasShutdownHook) {
            this.shutdownConsole = new ShutdownConsole(lastScene, timer, gV);
            Runtime.getRuntime().addShutdownHook(shutdownConsole);
            this.hasShutdownHook = true;
        }
        System.out.println("Sala d'attesa: \n\n");
        try {
            gV.waitingRoomLoaded();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Imposta la scelta della window
     */
    public void setChooseWindowScene(){
        this.lastScene = "chooseWindow";
        if(!this.hasShutdownHook) {
            this.shutdownConsole = new ShutdownConsole(lastScene, timer, gV);
            Runtime.getRuntime().addShutdownHook(shutdownConsole);
            this.hasShutdownHook = true;
        } else {
            shutdownConsole.setScene(lastScene);
        }
        clearScreen();
        System.out.println("Choose window: \n\n");
        if(isTimerOn) {
            this.timer.cancel();
            this.timer.purge();
            this.isTimerOn = false;
        }
        try {
            gV.chooseWindowSceneLoaded();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Imposta la scena di gioco
     */
    public void setGameScene(){
        this.lastScene= "game";
        if(!this.hasShutdownHook) {
            this.shutdownConsole = new ShutdownConsole(lastScene, timer, gV);
            Runtime.getRuntime().addShutdownHook(shutdownConsole);
            this.hasShutdownHook = true;
        } else {
            shutdownConsole.setScene(lastScene);
        }
        clearScreen();
        System.out.println("Game Scene");
        try {
            gV.gameLoaded();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Mostra le window
     * @param windows Window scelta
     * @param currentTime Timer corrente
     */
    public void viewWindows(ArrayList<WindowClient> windows, int currentTime) {

        String highLowEdge="+———";
        String space="        ";  //space ha lo stesso numero di caratteri di CARDN:
        String bigspace="                  ";
        String typeEdge1="+———+";
        String typeEdge2="+———";
        String middleEdge="|———";
        String typeEdge3="+———|";
        String nFV="  FV: ";
        String nameWindowChoose;

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

        for(int i = 0; i<4; i++){
            System.out.println(space + "WINDOW "+(i+1)+":  name:" + windows.get(i).getName().replace("-", " ") + nFV + windows.get(i).getFTokens());
        }
        System.out.println("");

        System.out.println(space + "You have "+currentTime+" seconds before the timeout expires.");
        String message = space + "Choose your window, write its number (press 'q' to exit game): ";
        String[] actions = {"1", "2", "3", "4"}; //risposte giuste che ci si aspetta dall'utente
        Optional<String> futureInput = Optional.of("");
        try {
            //readLine personalizzata che sblocca il thread allo scadere del timer
            //vuole il tempo rimanente al timer arrivato dal server, il messaggio da visualizzare all'utente e
            //un'array di string che contengono le risposte giuste che ci si aspetta dall'utente
            futureInput = Optional.ofNullable(readLine(currentTime, message, actions, TimeUnit.SECONDS));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //entra se il contenuto non e' null, cioe' il timeout non e' scaduto
        if(futureInput.isPresent()) {
            nameWindowChoose = windows.get(Integer.parseInt(futureInput.get()) - 1).getName();
            System.out.println("You have chosen Window number " + (Integer.parseInt(futureInput.get())) + "!");
            try {
                gV.sendChosenWindow(nameWindowChoose);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Mostra la window scelta
     * @param window Window scelta
     * @param currentTime Timer
     */
    public void viewOneWindow(WindowClient window, int currentTime) {

        String space = "   ";
        String highLowEdge = "+———";
        String typeEdge1 = "+———+";
        String typeEdge2 = "+———";
        String middleEdge = "|———";
        String typeEdge3 = "+———|";
        String bigspace = "                ";
        String spaceIndRow = "               ";
        String miniSpace=" ";
        String indicColumn="  1 ";

        if(window.rowSize() ==1){

            highLowEdge+="+";
            middleEdge+="|";

        }else{

            for(int i=1;i<window.columnSize()-1;i++){

                highLowEdge+=typeEdge2;
                middleEdge+=typeEdge2;
                indicColumn+=miniSpace +miniSpace +(i+1) +miniSpace;

                if(i==(window.columnSize()-2)){

                    indicColumn+=miniSpace +miniSpace +(i+2) +miniSpace;
                }
            }

            highLowEdge+=typeEdge1;
            middleEdge+=typeEdge3;
        }

        System.out.println(space +"You have chosen Window named " + window.getName().replace("-", " "));

        for (int i = 0; i < window.rowSize(); i++) {

            if (i == 0) {

                System.out.println("\n" +bigspace +indicColumn);
                System.out.println(space + "YOUR WINDOW: " + highLowEdge);

            }

            System.out.print(spaceIndRow +(i+1));

            for (int k = 0; k < window.columnSize(); k++) {

                System.out.print(createWindowCli(window,i,k));

            }

            System.out.print("|" + "\n");

            if (i < window.rowSize() - 1) {

                System.out.print(bigspace + middleEdge + "\n");

            } else if (i == window.rowSize() - 1) {

                System.out.print(bigspace + highLowEdge);
            }
        }

        System.out.println("\nIn " + currentTime + " seconds the game will start!");
    }

    /**
     * metodo che ritorna che dado c'è nella box o che vincolo c'è
     * @param window Window del client
     * @param x Indica la riga
     * @param y Indica la colonna
     * @return Una stringa che rappresenta cosa contiene la box o il vincolo della box
     */
    public String createWindowCli(WindowClient window, int x, int y){

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

    /**
     *
     * @param dieClient Dado
     * @return Stringa che rappresenta il dado, valore dado colorato del colore del dado
     */
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

    /**
     * Aggiorna la view
     * @param boardClient Game board
     * @param myName Nome client
     */
    public void updateView(GameBoardClient boardClient, String myName) {

        this.boardClient = boardClient;
        String highLowEdge="+———";
        String typeEdge1="+———+";
        String typeEdge2="+———";
        String middleEdge="|———";
        String typeEdge3="+———|";
        String space="   ";
        String middlespace="          ";
        String bigspace="                ";
        String spaceIndRow = "               ";
        String miniSpace=" ";
        String indicColumn="  1 ";

        int myNumber=0; //indica quale numero ha il giocatore nella boardgame (utile nella stampa my window)

        if(boardClient.getPlayer(0).getWindow().rowSize() ==1){

            highLowEdge+="+";
            middleEdge+="|";

        }else{

            for(int i=1;i<boardClient.getPlayer(0).getWindow().columnSize()-1;i++){

                highLowEdge+=typeEdge2;
                middleEdge+=typeEdge2;
                indicColumn+=miniSpace +miniSpace +(i+1) +miniSpace;

                if(i==(boardClient.getPlayer(myNumber).getWindow().columnSize()-2)){

                    indicColumn+=miniSpace +miniSpace +(i+2) +miniSpace;
                }

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
        System.out.print("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");

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

                        System.out.print("\n" + space + "WINDOW " + l + " BELONGS TO: " + boardClient.getPlayer(n).getName() + space + "FAVOR TOKEN: " + boardClient.getPlayer(n).getFavorTokens() +
                        space +"Status: ");

                        if(boardClient.getPlayer(n).getLiveStatus()){

                            System.out.print(ANSI_GREEN +"ONLINE" +ANSI_RESET);

                        }else{

                            System.out.print(ANSI_RED +"OFFLINE" +ANSI_RESET);

                        }

                        l++;
                    }
                }
            }
        }
        System.out.print("\n\n\n\n");


        // ROUND TRACK AND ROUND GAME
        System.out.print(space +bigspace +ANSI_CYAN +"ROUND TRACK" +ANSI_RESET +bigspace +bigspace +bigspace +bigspace +"ROUND GAME: " +boardClient.getRound() +space +"TURN: " +boardClient.getTurn()+"\n\n");
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


        //PUBLIC CARD
        ArrayList<PublicCardClient> publicCardClients=boardClient.getPublicCards();

        for(int i=0; i<publicCardClients.size(); i++){

            System.out.println(space +ANSI_PURPLE +"PUBLIC CARD " +(i+1) +": " +ANSI_RESET +publicCardClients.get(i).getTitle() +space +"SCORE: " +publicCardClients.get(i).getValue() +"\n"
                    +space +"DESCRIPTION PUBLIC CARD " +(i+1) +": " +publicCardClients.get(i).getDescription() +"\n");

        }
        System.out.print("\n");

        //TOOL CARD
        ArrayList<ToolCardClient> toolCardClients= boardClient.getToolCards();

        for(int i=0; i<toolCardClients.size(); i++){

            System.out.println(space +ANSI_YELLOW +"TOOL CARD " +(i+1) +": " +ANSI_RESET +toolCardClients.get(i).getTitle() +space +"COST: " +boardClient.getToolCards().get(i).getFavorTokens() +"\n"
                    +space +"DESCRIPTION TOOL CARD " +(i+1) +": " +toolCardClients.get(i).getDescription() +"\n");

        }
        System.out.print("\n");


        //PERSONAL WINDOW, DICE ON DRAFT AND FAVOUR TOKEN

        for (int i = 0; i <boardClient.getPlayer(myNumber).getWindow().rowSize(); i++) {

            if (i == 0) {

                System.out.println(bigspace +indicColumn);
                System.out.print(space + "YOUR WINDOW: " + highLowEdge + bigspace +ANSI_GREEN +"DRAFT: " +ANSI_RESET);

                for(int j=0;j<boardClient.getSizeDraft();j++){

                    System.out.print(drawDie(boardClient.getDieDraft(j)) +" ");

                }

                System.out.print("\n");
            }

            System.out.print(spaceIndRow +(i+1));

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

        if(myTurn && !card11){
            showQuestion(globalGameTime/1000);
        }

    }

    @Override
    public void run() {

        System.out.println("ciaoIO");
    }

    /**
     * Notifica il turno
     * @param currentPlayer Player corrente
     * @param currentTime Timer corrente
     */
    public void notifyTurn(String currentPlayer, int currentTime){
        if(currentPlayer.equals(gV.getUsername())){
            myTurn = true;
            showQuestion(currentTime);
        } else {
            myTurn = false;
            this.currentPlayer = currentPlayer;
            System.out.println("It's "+this.currentPlayer+"'s turn!");
            System.out.println("It will end in "+currentTime+" seconds.");
        }
    }

    /**
     * Pone le domande al client su cosa vuole fare
     * @param currentTime Timer corrente
     */
    //la prima domanda è relativa a che azione voglio fare cioè i=inserire dado, u=usare carta e p=passare turno
    // dopo vengono poste le domande relative alla scelta fatta e tutto questo mentre si controlla lo scadere del timer
    // una volta finite le domande e aver creato l'array list elle risposte si chiama il metodo
    // sucessivo di quella determinata azione
    public void showQuestion(int currentTime){

        globalGameTime = currentTime*1000;
        String myName = gV.getUsername();
        String space = "   ";
        ArrayList<Integer> answer = new ArrayList<>();
        ArrayList<Object> questions;
        int myNumber=0;

        System.out.println(space + "You have "+(globalGameTime/1000)+" seconds before the timeout expires.");
        String messInsertDie = "press i to insert a die ";
        String messUsedCard = "press u to use a tool card ";
        String messagefine = "press p to pass the turn (press 'q' to exit game): ";
        String message = space;
        for (int n = 0; n < boardClient.getNPlayers(); n++) {

            if (myName.compareTo(boardClient.getPlayer(n).getName()) == 0) {

                myNumber = n;
            }
        }

        message+=messInsertDie+messUsedCard+messagefine;
        String[] actions = {"i", "u", "p"}; //risposte giuste che ci si aspetta dall'utente
        Optional<String> futureInput = Optional.of("");

        try {
            //readLine personalizzata che sblocca il thread allo scadere del timer
            //vuole il tempo rimanente al timer arrivato dal server, il messaggio da visualizzare all'utente e
            //un'array di string che contengono le risposte giuste che ci si aspetta dall'utente
            futureInput = Optional.ofNullable(readLine(globalGameTime, message, actions, TimeUnit.MILLISECONDS));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //entra se il contenuto non e' null, cioe' il timeout non e' scaduto
        if (futureInput.isPresent()) {

            if (futureInput.get().compareTo("i") == 0) {

                System.out.println(space + "You have "+(globalGameTime/1000)+" seconds before the timeout expires.");

                String messDraft = "Which die on draft (";
                String[] actionDraft = new String[boardClient.getSizeDraft()];

                questions=createMessAction(messDraft,actionDraft,boardClient.getSizeDraft());
                messDraft=(String)questions.get(0);
                actionDraft=(String[])questions.get(1);

                try {
                    //readLine personalizzata che sblocca il thread allo scadere del timer
                    //vuole il tempo rimanente al timer arrivato dal server, il messaggio da visualizzare all'utente e
                    //un'array di string che contengono le risposte giuste che ci si aspetta dall'utente
                    futureInput = Optional.ofNullable(readLine(globalGameTime, messDraft, actionDraft, TimeUnit.MILLISECONDS));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if (futureInput.isPresent()) {

                    answer.add(Integer.parseInt(futureInput.get())-1);
                    System.out.println("\nYou choose die: " + boardClient.getDieDraft(answer.get(0)).getColourDie() +" " +boardClient.getDieDraft(answer.get(0)).getValueDie() + "!");

                    String[] actionRow = new String[boardClient.getPlayer(myNumber).getWindow().rowSize()];
                    String messRow = "Where do you want put the die?\nChoose row(";

                    questions=createMessAction(messRow,actionRow, boardClient.getPlayer(myNumber).getWindow().rowSize());
                    messRow=(String)questions.get(0);
                    actionRow=(String[])questions.get(1);

                    try {
                        //readLine personalizzata che sblocca il thread allo scadere del timer
                        //vuole il tempo rimanente al timer arrivato dal server, il messaggio da visualizzare all'utente e
                        //un'array di string che contengono le risposte giuste che ci si aspetta dall'utente
                        futureInput = Optional.ofNullable(readLine(globalGameTime, messRow, actionRow, TimeUnit.MILLISECONDS));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    if (futureInput.isPresent()) {

                        answer.add(Integer.parseInt(futureInput.get()) - 1);
                        String[] actionColumn = new String[boardClient.getPlayer(myNumber).getWindow().columnSize()];
                        String messColumn = "Where do you want put the die?\nChoose column(";

                        questions=createMessAction(messColumn,actionColumn,boardClient.getPlayer(myNumber).getWindow().columnSize());
                        messColumn=(String)questions.get(0);
                        actionColumn=(String[])questions.get(1);

                        try {
                            //readLine personalizzata che sblocca il thread allo scadere del timer
                            //vuole il tempo rimanente al timer arrivato dal server, il messaggio da visualizzare all'utente e
                            //un'array di string che contengono le risposte giuste che ci si aspetta dall'utente
                            futureInput = Optional.ofNullable(readLine(globalGameTime, messColumn, actionColumn, TimeUnit.MILLISECONDS));
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        if (futureInput.isPresent()) {

                            answer.add(Integer.parseInt(futureInput.get()) - 1);

                            System.out.println("\nYou choose position row:" + (answer.get(1)+1) + " column: " + (answer.get(2)+1) + "!");

                            try {
                                gV.sendInsertDie(answer.get(0), answer.get(1), answer.get(2));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }

            }else if(futureInput.get().compareTo("u") == 0){

                String[] actionCards = {"1","2","3"};
                String messCard = "Which tool card (1,2,3)? ";

                System.out.println(space + "You have "+(globalGameTime/1000)+" seconds before the timeout expires.");

                try {
                    //readLine personalizzata che sblocca il thread allo scadere del timer
                    //vuole il tempo rimanente al timer arrivato dal server, il messaggio da visualizzare all'utente e
                    //un'array di string che contengono le risposte giuste che ci si aspetta dall'utente
                    futureInput = Optional.ofNullable(readLine(globalGameTime, messCard, actionCards, TimeUnit.MILLISECONDS));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if (futureInput.isPresent()) {

                    answer.add(Integer.parseInt(futureInput.get()) - 1);
                    lastToolCard = answer.get(0);

                    System.out.println("\nYou choose tool card:" + boardClient.getToolCards().get(answer.get(0)).getTitle()+"!");

                            try {
                                gV.sendChooseCard(answer.get(0));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                }

            }else if(futureInput.get().compareTo("p") == 0){

                myTurn = false;
                try {
                    gV.sendEndTurn();
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }

        }
    }

    /**
     * Pone domande relativa alla mossa che vuole fare il client
     * @param codeQuestions Codice domanda
     * @param myName Nome client
     */
    //come la showquestion ma relativa alla carta tool scelta, se non ha bisogno di parametri allora andra nel default
    public void cardQuestion(ArrayList<String> codeQuestions, String myName){

        int myNumber=0;
        ArrayList<Object> questions;
        ArrayList<Integer> answer=new ArrayList<>();
        Optional<String> futureInput = Optional.of("");
        // System.out.println(space + "You have "+currentTime+" seconds before the timeout expires.");

        for (int n = 0; n < boardClient.getNPlayers(); n++) {

            if (myName.compareTo(boardClient.getPlayer(n).getName()) == 0) {

                myNumber = n;
            }
        }

        for(int j=0; j<codeQuestions.size();j++){

            switch(codeQuestions.get(j)){

                case "startPos":{

                    String[] actionRow = new String[boardClient.getPlayer(myNumber).getWindow().rowSize()];
                    String messRow = "From which box do you want to move a die?\nChoose row(";

                    questions=createMessAction(messRow,actionRow,boardClient.getPlayer(myNumber).getWindow().rowSize());
                    messRow=(String)questions.get(0);
                    actionRow=(String[])questions.get(1);

                    try {
                        //readLine personalizzata che sblocca il thread allo scadere del timer
                        //vuole il tempo rimanente al timer arrivato dal server, il messaggio da visualizzare all'utente e
                        //un'array di string che contengono le risposte giuste che ci si aspetta dall'utente
                        futureInput = Optional.ofNullable(readLine(globalGameTime, messRow, actionRow, TimeUnit.MILLISECONDS));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    if (futureInput.isPresent()) {

                        answer.add(Integer.parseInt(futureInput.get()) - 1);
                        String[] actionColumn = new String[boardClient.getPlayer(myNumber).getWindow().columnSize()];
                        String messColumn = "From which box do you want to move a die?\nChoose column(";

                        questions=createMessAction(messColumn,actionColumn,boardClient.getPlayer(myNumber).getWindow().columnSize());
                        messColumn=(String)questions.get(0);
                        actionColumn=(String[])questions.get(1);

                        try {
                            //readLine personalizzata che sblocca il thread allo scadere del timer
                            //vuole il tempo rimanente al timer arrivato dal server, il messaggio da visualizzare all'utente e
                            //un'array di string che contengono le risposte giuste che ci si aspetta dall'utente
                            futureInput = Optional.ofNullable(readLine(globalGameTime, messColumn, actionColumn, TimeUnit.MILLISECONDS));
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        if (futureInput.isPresent()) {

                            answer.add(Integer.parseInt(futureInput.get()) - 1);

                            System.out.println("\nYou choose to move from this position row:" + answer.get(answer.size()-2) + " column: " + answer.get(answer.size()-1) + "!");

                        }
                    }

                    break;
                }

                case "endPos":{

                    String[] actionRow = new String[boardClient.getPlayer(myNumber).getWindow().rowSize()];
                    String messRow = "In which box do you want to move a die?\nChoose row(";

                    questions=createMessAction(messRow,actionRow,boardClient.getPlayer(myNumber).getWindow().rowSize());
                    messRow=(String)questions.get(0);
                    actionRow=(String[])questions.get(1);

                    try {
                        //readLine personalizzata che sblocca il thread allo scadere del timer
                        //vuole il tempo rimanente al timer arrivato dal server, il messaggio da visualizzare all'utente e
                        //un'array di string che contengono le risposte giuste che ci si aspetta dall'utente
                        futureInput = Optional.ofNullable(readLine(globalGameTime, messRow, actionRow, TimeUnit.MILLISECONDS));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    if (futureInput.isPresent()) {

                        answer.add(Integer.parseInt(futureInput.get()) - 1);
                        String[] actionColumn = new String[boardClient.getPlayer(myNumber).getWindow().columnSize()];
                        String messColumn = "In which box do you want to move a die?\nChoose column(";

                        questions=createMessAction(messColumn,actionColumn,boardClient.getPlayer(myNumber).getWindow().columnSize());
                        messColumn=(String)questions.get(0);
                        actionColumn=(String[])questions.get(1);

                        try {
                            //readLine personalizzata che sblocca il thread allo scadere del timer
                            //vuole il tempo rimanente al timer arrivato dal server, il messaggio da visualizzare all'utente e
                            //un'array di string che contengono le risposte giuste che ci si aspetta dall'utente
                            futureInput = Optional.ofNullable(readLine(globalGameTime, messColumn, actionColumn, TimeUnit.MILLISECONDS));
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        if (futureInput.isPresent()) {

                            answer.add(Integer.parseInt(futureInput.get()) - 1);

                            System.out.println("\nYou choose move in this position row:" + answer.get(answer.size()-2) + " column: " + answer.get(answer.size()-1) + "!");

                        }
                    }

                    break;
                }

                case "dieDraft":{

                    String messDraft = "Which die on draft (";
                    String[] actionDraft = new String[boardClient.getSizeDraft()];

                    questions=createMessAction(messDraft,actionDraft,boardClient.getSizeDraft());
                    messDraft=(String)questions.get(0);
                    actionDraft=(String[])questions.get(1);

                    try {
                        //readLine personalizzata che sblocca il thread allo scadere del timer
                        //vuole il tempo rimanente al timer arrivato dal server, il messaggio da visualizzare all'utente e
                        //un'array di string che contengono le risposte giuste che ci si aspetta dall'utente
                        futureInput = Optional.ofNullable(readLine(globalGameTime, messDraft, actionDraft, TimeUnit.MILLISECONDS));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    if (futureInput.isPresent()) {

                        answer.add(Integer.parseInt(futureInput.get()) - 1);
                        System.out.println("\nYou choose die: " + boardClient.getDieDraft((Integer.parseInt(futureInput.get()) - 1)) + "!");

                    }

                    break;
                }

                case "dieRoundTrack":{

                    String messRound = "Which round of roundtrack? (";
                    String[] actionRound = new String[boardClient.sizeRoundTrack()];

                    questions=createMessAction(messRound,actionRound,boardClient.sizeRoundTrack());
                    messRound=(String)questions.get(0);
                    actionRound=(String[])questions.get(1);

                    try {
                        //readLine personalizzata che sblocca il thread allo scadere del timer
                        //vuole il tempo rimanente al timer arrivato dal server, il messaggio da visualizzare all'utente e
                        //un'array di string che contengono le risposte giuste che ci si aspetta dall'utente
                        futureInput = Optional.ofNullable(readLine(globalGameTime, messRound, actionRound, TimeUnit.MILLISECONDS));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    if (futureInput.isPresent()) {

                        answer.add(Integer.parseInt(futureInput.get()) - 1);
                        System.out.println("\nYou choose round: " + (Integer.parseInt(futureInput.get()) - 1) + "!");

                        String messDieRound = "Which die on roundtrack? (";
                        String[] actionDieRound = new String[boardClient.sizeDiceRoundTrack(answer.get(answer.size()-1))];

                        questions=createMessAction(messDieRound,actionDieRound,boardClient.sizeDiceRoundTrack(answer.get(answer.size()-1)));
                        messDieRound=(String)questions.get(0);
                        actionDieRound=(String[])questions.get(1);

                        try {
                            //readLine personalizzata che sblocca il thread allo scadere del timer
                            //vuole il tempo rimanente al timer arrivato dal server, il messaggio da visualizzare all'utente e
                            //un'array di string che contengono le risposte giuste che ci si aspetta dall'utente
                            futureInput = Optional.ofNullable(readLine(globalGameTime, messDieRound, actionDieRound, TimeUnit.MILLISECONDS));
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        if (futureInput.isPresent()) {

                            answer.add(Integer.parseInt(futureInput.get()) - 1);
                            System.out.println("\nYou choose round: " + answer.get(answer.size()-2)
                                    +" and this die:" +boardClient.getDieFromRoundTrack(answer.get(answer.size()-2),answer.get(answer.size()-1)).getColourDie() + "!");

                        }
                    }

                    break;
                }
                case "card11":{

                    String messCard11="Which die on draft (";
                    String[] actionCard11 = new String[boardClient.getSizeDraft()];

                    questions=createMessAction(messCard11,actionCard11,boardClient.getSizeDraft());
                    messCard11=(String)questions.get(0);
                    actionCard11=(String[])questions.get(1);

                    try {
                        //readLine personalizzata che sblocca il thread allo scadere del timer
                        //vuole il tempo rimanente al timer arrivato dal server, il messaggio da visualizzare all'utente e
                        //un'array di string che contengono le risposte giuste che ci si aspetta dall'utente
                        futureInput = Optional.ofNullable(readLine(globalGameTime, messCard11, actionCard11, TimeUnit.MILLISECONDS));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    if (futureInput.isPresent()) {
                        card11 = true;
                        answer.add(Integer.parseInt(futureInput.get()) - 1);
                        System.out.println("\nYou choose die: " + boardClient.getDieDraft((Integer.parseInt(futureInput.get()) - 1)) +  "!");

                    }

                    break;
                }
                case "incrDecrDie":{

                    String messIncrDecr = "Choose 1 to increment or 0 to decrement? ";
                    String[] actionIncrDecr = {"0","1"};

                    try {
                        //readLine personalizzata che sblocca il thread allo scadere del timer
                        //vuole il tempo rimanente al timer arrivato dal server, il messaggio da visualizzare all'utente e
                        //un'array di string che contengono le risposte giuste che ci si aspetta dall'utente
                        futureInput = Optional.ofNullable(readLine(globalGameTime, messIncrDecr, actionIncrDecr, TimeUnit.MILLISECONDS));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    if (futureInput.isPresent()) {

                        answer.add(Integer.parseInt(futureInput.get()));

                        if(answer.get(answer.size()-1)==1){

                            System.out.println("\nYou choose to increment!");

                        }else{

                            System.out.println("\nYou choose to decrement!");
                        }
                    }

                    break;
                }
                case "dieValue": {

                    String messValueDie = "Choose value of die? ";
                    String[] actionValueDie = {"6", "1", "2", "3", "4", "5"};

                    try {
                        //readLine personalizzata che sblocca il thread allo scadere del timer
                        //vuole il tempo rimanente al timer arrivato dal server, il messaggio da visualizzare all'utente e
                        //un'array di string che contengono le risposte giuste che ci si aspetta dall'utente
                        futureInput = Optional.ofNullable(readLine(globalGameTime, messValueDie, actionValueDie, TimeUnit.MILLISECONDS));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    if (futureInput.isPresent()) {
                        card11 = false;
                        answer.add(Integer.parseInt(futureInput.get()));

                        System.out.println("\nYou choose value:" + answer.get(answer.size() - 1) + "!");

                    }

                    break;
                }

                default:{

                    //per carte senza check chiamare direttamente la attiva effetto
                    break;

                }
            }
        }

        // TODO  ANDRE VAI AVANTI PARTE SOTTO ANSWER CONTIENE LE RISPOSTE COME INTERI RICORDA DI FARE LA ROBA DEL TIMER
      /*  if (answer.isEmpty()) {
            try {
                //CHIAMATA AGGIORNA VIEW CHE L'EFFECT è GIA STATO FATTO
            } catch (IOException e) {
                e.printStackTrace();
            }
        }*/
        if(!answer.isEmpty()){
            String response = "";
            if(card11){
                response += "preEffect-";
            }
            try {
                for(int i = 0; i<answer.size(); i++){
                    response += answer.get(i)+"-";
                }
                response = response.substring(0, response.length()-1);
                gV.sendResponseToolCard(lastToolCard, response);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Crea il messaggio e i parametri corretti
     * @param mess Messaggio
     * @param action Parametri corretti
     * @param size Size
     * @return Il messaggio completo e i valori corretti
     */
    //metodo che evita molto codice clonato e che crea la stringa mess e l'array di stringhe action per le domande
    private  ArrayList<Object> createMessAction(String mess, String[] action, int size){

        ArrayList<Object> messActionCompleted= new ArrayList<>();

        for (int i = 0; i < size; i++) {

            if (i != size - 1) {

                mess += String.valueOf(i+1) + ",";

            } else {

                mess += String.valueOf(i+1) + "): ";

            }

            action[i] = String.valueOf(i+1);
        }

        messActionCompleted.add(mess);
        messActionCompleted.add(action);

        return messActionCompleted;
    }

    /**
     * Mostra l'errore
     * @param errorMessage Codice errore
     */
    public void viewError(String errorMessage){

        System.out.println("\n///Illegal Action\n///"+errorMessage+"\n");
        showQuestion(globalGameTime/1000);
    }

    /**
     * Stampa il ranking
     * @param ranking Array di stringhe: nome punteggio, gia ordinto in modo decrescente e di dimensione del numero dei player
     */
    public void showRanking(String[] ranking){

        String bigspace = "                  ";
        String bigSpaceX3=bigspace+bigspace+bigspace;
        String centerPosition=bigSpaceX3 +"             ";

        System.out.println("" +
                bigSpaceX3 +ANSI_BLUE +"  ____     _    _   _ _  _____ _   _  ____ \n" +
                bigSpaceX3 +ANSI_CYAN +" |  _ \\   / \\  | \\ | | |/ /_ _| \\ | |/ ___|\n" +
                bigSpaceX3 +ANSI_YELLOW +" | |_) | / _ \\ |  \\| | ' / | ||  \\| | |  _ \n" +
                bigSpaceX3 +ANSI_PURPLE +" |  _ < / ___ \\| |\\  | . \\ | || |\\  | |_| |\n" +
                bigSpaceX3 +ANSI_RED +" |_| \\_\\_/   \\_\\_| \\_|_|\\_\\___|_| \\_|\\____|"+ANSI_RESET);

        //con un for stampare il numero dei giocatori
        for(int i=0; i<boardClient.getNPlayers(); i++){

            System.out.print("\n" +centerPosition +"1- " +ranking[i]);
        }

    }

}