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

public class GameViewSocket implements Runnable{

    private Socket socket;
    private Consumer<String> onReceiveCallback;
    private int localServerPort;
    private GameView gameView;
    private boolean serverLoop;

    public GameViewSocket(Consumer<String> onReceiveCallback){
        this.onReceiveCallback = onReceiveCallback;
    }

    public GameViewSocket(GameView gameView) throws IOException {
        this.gameView = gameView;
        this.socket = new Socket("127.0.0.1", 8080);
    }

    @Override
    public void run(){
        try(ServerSocket serverSocket = new ServerSocket(0)){
            this.localServerPort = serverSocket.getLocalPort();
            //System.out.println("port: "+localServerPort);
            serverLoop = true;
            while(serverLoop){
                //System.out.println("Attendo connessione");
                this.socket = serverSocket.accept();
                //System.out.println("Connessione accettata\n");
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

    public void handleMessage(String message){
        String[] line = message.split(" ");
        switch(line[0]){
            case "registered":
                //System.out.println("registered");
                gameView.enterWaitingRoom();
                break;
            case "updatedplayers":  // setTimer player1 player2
                //System.out.println("updatedPlayers");

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
                //System.out.println("chooseWindow");
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
                //System.out.println(line[1]);
                updateView(line[1]);
                break;
            case "exit":
                gameView.shutdownClient();
                break;
        }
    }

    public void init(String username, String pwd, String conn, String UI, int localPort) throws IOException {
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        out.println(username + " init " + pwd + " " + conn + " " + UI + " " + localPort);
        out.close();
        socket.close();
    }

    public void waitingRoomLoaded(String username) throws IOException {
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        out.println(username + " waitingOk");
        out.close();
        socket.close();
    }

    public void chooseWindowSceneLoaded(String username) throws IOException{
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        out.println(username + " chooseWindowOk");
        out.close();
        socket.close();
    }

    public void gameSceneLoaded(String username) throws IOException{
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        out.println(username + " gameOk");
        out.close();
        socket.close();
    }

    public void notifyOnExit(String username, String typeView) throws IOException {
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        out.println(username + " exit " + typeView);
        out.close();
        socket.close();
    }

    public void sendInsertDie(String username, int draftPos, int xWindowPos, int yWindowPos) throws IOException {
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        out.println(username + " insertDie "+draftPos+" "+xWindowPos+" "+yWindowPos );
        out.close();
        socket.close();
    }

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
                //board.setRound(Character.getNumericValue(roundAndDice[j].charAt(0)));
                String dice = roundAndDice[j].substring(1);
                String[] die = dice.split("_");
                ArrayList<DieClient> dieRound = new ArrayList<>();

                for(int k = 0; k<die.length; k++){

                    dieRound.add(new DieClient(die[k]));

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

    public void sendChosenWindow(String username, String windowName) throws IOException {

        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        out.println(username + " chosenWindow "+windowName);
        out.close();
        socket.close();
    }

    public int getLocalServerPort(){
        return this.localServerPort;
    }

}
