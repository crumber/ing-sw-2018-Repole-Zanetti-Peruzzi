package repolezanettiperuzzi.view;

import repolezanettiperuzzi.common.modelwrapper.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Consumer;

/**
 * Classe che modella la game view (socket)
 * @author Andrea Zanetti
 */
public class GameViewSocket implements Runnable{

    private Socket socket;
    private Consumer<String> onReceiveCallback;
    private int localServerPort;
    private GameView gameView;
    private boolean serverLoop;

    /**
     * Costruttore
     * @param onReceiveCallback Riferimento all'oggetto remoto del client
     */
    public GameViewSocket(Consumer<String> onReceiveCallback){
        this.onReceiveCallback = onReceiveCallback;
    }

    /**
     * Costruttore
     * @param gameView Game view
     * @throws IOException Fallimento o interruzione delle operazioni I/O
     */
    public GameViewSocket(GameView gameView, String serverIp) throws IOException {
        this.gameView = gameView;
        this.socket = new Socket(serverIp, 8080);
    }

    @Override
    public void run(){
        try(ServerSocket serverSocket = new ServerSocket(0)){
            this.localServerPort = serverSocket.getLocalPort();
            serverLoop = true;
            while(serverLoop){
                this.socket = serverSocket.accept();
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                onReceiveCallback.accept(in.readLine());
                in.close();
                socket.close();
            }

        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void shutdownServer(){
        this.serverLoop = false;
    }

    /**
     *  Gestore messaggio
     * @param message Messaggio
     */
    public void handleMessage(String message){
        String[] line = message.split(" ");
        switch(line[0]){
            case "registered":
                gameView.enterWaitingRoom();
                break;
            case "updatedplayers":

                String players[] = new String[line.length-2];
                for(int i = 0; i<players.length; i++){
                    players[i] = line[i+2];
                }

                gameView.refreshWaitingRoom(Integer.parseInt(line[1]), players);
                break;
            case "notregistered":
                if(line[1].equals("alreadyonline")){
                    gameView.showPlayerAlreadyOnlineAlert();
                } else if(line[1].equals("wrongpwd")){
                    gameView.showWrongPwdAlert();
                } else if(line[1].equals("gameAlreadyStarted")){
                    gameView.showGameAlreadyStarted();
                } else if(line[1].equals("already4Players")){
                    gameView.showAlready4Players();
                }
                break;
            case "changeView":
                if(line[1].equals("chooseWindow")){
                    gameView.enterChooseWindow();
                }
                break;
            case "chooseWindow":
                receivedWindows(line);
                break;
            case "showWindow":
                receivedOneWindow(line);
                break;
            case "startGame":
                gameView.enterGame();
                break;
            case "winChooseWindow":
                gameView.showWinOnChooseWindowAlert();
                break;
            case "notYourTurn":
                gameView.notYourTurn();
                break;
            case "error":
                gameView.viewError(line[1]);
                break;
            case "turn":
                gameView.notifyTurn(line[1], Integer.parseInt(line[2]));
                break;
            case "updateView":
                updateView(line[1]);
                break;
            case "requestCard":
                gameView.receiveCardParameters(line[1]);
                break;
            case "endGame":
                gameView.receiveRanking(line[1]);
                break;
            case "winBeforeEnd":
                gameView.showWinBeforeEndGameAlert();
                break;
            case "exit":
                gameView.shutdownClient();
                break;
        }
    }

    /**
     * Inizializzazione
     * @param username Username
     * @param pwd Password
     * @param conn Tipo connessione
     * @param UI Interfaccia grafica
     * @param localPort Intero che rappresenta la porta locale
     * @throws IOException Fallimento o interruzione delle operazioni I/O
     */
    public void init(String username, String pwd, String conn, String UI, int localPort) throws IOException {
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        out.println(username + " init " + pwd + " " + conn + " " + UI + " " + localPort);
        out.close();
        socket.close();
    }

    /**
     * Caricamento waiting room
     * @param username Nome utente
     * @throws IOException  Fallimento o interruzione delle operazioni I/O
     */
    public void waitingRoomLoaded(String username) throws IOException {
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        out.println(username + " waitingOk");
        out.close();
        socket.close();
    }

    /**
     * Caricamento scelta delle window
     * @param username Nome utente
     * @throws IOException  Fallimento o interruzione delle operazioni I/O
     */
    public void chooseWindowSceneLoaded(String username) throws IOException{
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        out.println(username + " chooseWindowOk");
        out.close();
        socket.close();
    }

    /**
     * Caricamento scena gioco
     * @param username Nome utente
     * @throws IOException  Fallimento o interruzione delle operazioni I/O
     */
    public void gameSceneLoaded(String username) throws IOException{
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        out.println(username + " gameOk");
        out.close();
        socket.close();
    }

    /**
     * Notify uscita
     * @param username Nome utente
     * @param typeView Tipo view
     * @throws IOException  Fallimento o interruzione delle operazioni I/O
     */
    public void notifyOnExit(String username, String typeView) throws IOException {
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        out.println(username + " exit " + typeView);
        out.close();
        socket.close();
    }

    /**
     * Invio inserimento dado
     * @param username Nome utente
     * @param draftPos Posizione draft
     * @param xWindowPos Riga
     * @param yWindowPos Colonna
     * @throws IOException  Fallimento o interruzione delle operazioni I/O
     */
    public void sendInsertDie(String username, int draftPos, int xWindowPos, int yWindowPos) throws IOException {
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        out.println(username + " insertDie "+draftPos+" "+xWindowPos+" "+yWindowPos );
        out.close();
        socket.close();
    }

    /**
     * Invio scelta della carta
     * @param username Nome player
     * @param numCard Numero carta
     * @throws IOException  Fallimento o interruzione delle operazioni I/O
     */
    public void sendChooseCard(String username, int numCard) throws IOException {

        PrintWriter out= new PrintWriter(socket.getOutputStream(),true);
        out.println(username+" chooseCard "+numCard);
        out.close();
        socket.close();

    }

    /**
     * Invio risposte tool card
     * @param username Nome utente
     * @param nCard Numero card
     * @param response Risosta
     * @throws IOException  Fallimento o interruzione delle operazioni I/O
     */
    public void sendResponseToolCard(String username, int nCard, String response) throws IOException {
        PrintWriter out= new PrintWriter(socket.getOutputStream(),true);
        out.println(username+" responseToolCard "+nCard+" "+response);
        out.close();
        socket.close();
    }

    /**
     * Ricezione delle windows
     * @param line String
     */
    private void receivedWindows(String[] line){
        ArrayList<WindowClient> chosenWindows = new ArrayList<>();
        String windowName = "";
        int favorToken;
        ArrayList<ArrayList<String>> boxesList;
        int i = 1;
        int currentTime = 0 ;
        while(i < line.length-1){
            boxesList = new ArrayList<>();
            windowName = line[i];
            i++;
            favorToken = Integer.parseInt(line[i]);
            i++;
            while(!line[i].equals("_")){
                boxesList.add(new ArrayList<>(Arrays.asList(line[i].split("-"))));
                i++;
            }
            BoxClient[][] boxMatrix = arrayListToMatrix(boxesList);
            chosenWindows.add(new WindowClient(windowName, favorToken, boxMatrix));
            i++;
        }
        currentTime = Integer.parseInt(line[i]);
        gameView.viewWindows(chosenWindows,currentTime);
    }

    /**
     * Ricezione della window
     * @param line Stringa
     */
    private void receivedOneWindow(String[] line){
        WindowClient window;
        String windowName = "";
        int favorToken;
        ArrayList<ArrayList<String>> boxesList;
        int i = 1;
        int currentTime = 0 ;
        boxesList = new ArrayList<>();
        windowName = line[i];
        i++;
        favorToken = Integer.parseInt(line[i]);
        i++;
        while(!line[i].equals("_")){
            boxesList.add(new ArrayList<>(Arrays.asList(line[i].split("-"))));
            i++;
        }
        BoxClient[][] boxMatrix = arrayListToMatrix(boxesList);
        window = new WindowClient(windowName, favorToken, boxMatrix);
        i++;
        currentTime = Integer.parseInt(line[i]);
        gameView.viewOneWindow(window,currentTime);
    }

    /**
     * Crea la matrice
     * @param chosenWindows Window
     * @return Box client
     */
    private BoxClient[][] arrayListToMatrix(ArrayList<ArrayList<String>> chosenWindows){
        int n = chosenWindows.size();
        int m = chosenWindows.get(0).size();
        BoxClient[][] boxMatrix = new BoxClient[n][m];
        for(int i = 0; i < n; i++){
            for(int j = 0; j < m; j++){
                switch (chosenWindows.get(i).get(j)){
                    case "Y":
                        boxMatrix[i][j] = new BoxClient(ColourClient.YELLOW);
                        break;
                    case "R":
                        boxMatrix[i][j] = new BoxClient(ColourClient.RED);
                        break;
                    case "P":
                        boxMatrix[i][j] = new BoxClient(ColourClient.PURPLE);
                        break;
                    case "G":
                        boxMatrix[i][j] = new BoxClient(ColourClient.GREEN);
                        break;
                    case "B":
                        boxMatrix[i][j] = new BoxClient(ColourClient.BLUE);
                        break;
                    case "0":
                        boxMatrix[i][j] = new BoxClient();
                        break;
                    case "1":
                        boxMatrix[i][j] = new BoxClient(ValueClient.ONE);
                        break;
                    case "2":
                        boxMatrix[i][j] = new BoxClient(ValueClient.TWO);
                        break;
                    case "3":
                        boxMatrix[i][j] = new BoxClient(ValueClient.THREE);
                        break;
                    case "4":
                        boxMatrix[i][j] = new BoxClient(ValueClient.FOUR);
                        break;
                    case "5":
                        boxMatrix[i][j] = new BoxClient(ValueClient.FIVE);
                        break;
                    case "6":
                        boxMatrix[i][j] = new BoxClient(ValueClient.SIX);
                        break;
                }
            }
        }
        return boxMatrix;
    }

    /**
     * Aggiornamento view
     * @param message Messaggio
     */
    public void updateView(String message){
        GameBoardClient board = new GameBoardClient();
        String[] boardElems = message.split("\\+");
        int numPlayers = Integer.parseInt(boardElems[0]);
        int i;
        for(i = 1; i<(numPlayers+1); i++){
            String[] playerElems = boardElems[i].split("\\*");
            String playerName = playerElems[0];
            board.addPlayer(playerName);
            ColourClient secretColour = ColourClient.stringToColour(playerElems[1]);
            board.getPlayerByName(playerName).setSecretColour(secretColour);
            String windowName = playerElems[2]; // nome della window con i trattini inclusi
            int favorTokens = Integer.parseInt(playerElems[3]);
            String window = playerElems[4].replace("_", " ");
            board.getPlayerByName(playerName).setWindow(new WindowClient(windowName, favorTokens, window));
            board.getPlayerByName(playerName).setFavorTokens(Integer.parseInt(playerElems[5]));
            board.getPlayerByName(playerName).setLiveStatus(Boolean.parseBoolean(playerElems[6]));
        }
        if(boardElems[i].length()==2) {


            board.setRound(Character.getNumericValue(boardElems[i].charAt(0)));
            board.setTurn(Character.getNumericValue(boardElems[i].charAt(1)));


        }else{

            board.setRound(Integer.parseInt(boardElems[i].substring(0,2)));
            board.setTurn(Character.getNumericValue(boardElems[i].charAt(2)));


        }
        i++;

        if(!boardElems[i].equals("")) {
            String[] dice = boardElems[i].split("_");
            for (int j = 0; j < dice.length; j++) {
                board.addDieToDraft(new DieClient(dice[j]));
            }
        }
        i++;

        if(!boardElems[i].equals("")) {
            String[] roundAndDice = boardElems[i].split("-");
            for(int j = 0; j<roundAndDice.length; j++){
                String dice = roundAndDice[j].substring(1);
                String[] die = dice.split("_");
                ArrayList<DieClient> dieRound = new ArrayList<>();

                if(die[0].length()!=0) {
                    for (int k = 0; k < die.length; k++) {

                        dieRound.add(new DieClient(die[k]));

                    }
                }
                board.getRoundTrack().addDice(dieRound);
            }
        }
        i++;

        String[] toolCards = boardElems[i].split("\\*");
        for(int j = 0; j<3; j++) {
            String[] toolCardsElems = toolCards[j].split("_");
            String cardName = toolCardsElems[0];
            int id = Integer.parseInt(toolCardsElems[1]);
            String description = toolCardsElems[2].replace("-", " ");
            int favor = Integer.parseInt(toolCardsElems[3]);
            board.addToolCard(cardName, description, id, favor);
        }
        i++;

        String[] publicCards = boardElems[i].split("\\*");
        for(int j = 0; j<3; j++) {
            String[] publicCardsElems = publicCards[j].split("_");
            String cardName = publicCardsElems[0];
            String description = publicCardsElems[1].replace("-", " ");
            int value = Integer.parseInt(publicCardsElems[2]);
            board.addPublicCard(cardName, description, value);
        }
        i++;

        gameView.updateView(board);
    }

    /**
     * Invio window scelta
     * @param username Nome utente
     * @param windowName Nome windows
     * @throws IOException  Fallimento o interruzione delle operazioni I/O
     */
    public void sendChosenWindow(String username, String windowName) throws IOException {

        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        out.println(username + " chosenWindow "+windowName);
        out.close();
        socket.close();
    }

    /**
     *
     * @return Local server port
     */
    public int getLocalServerPort(){
        return this.localServerPort;
    }

    /**
     * Invio fine turno
     * @param username Nome utente
     * @throws IOException  Fallimento o interruzione delle operazioni I/O
     */
    public void sendEndTurn(String username) throws IOException {

        PrintWriter out = new PrintWriter(socket.getOutputStream(),true);
        out.println(username + " endTurn");
        out.close();
        socket.close();

    }


}
