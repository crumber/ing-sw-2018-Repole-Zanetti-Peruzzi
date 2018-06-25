package repolezanettiperuzzi.view;

import repolezanettiperuzzi.common.modelwrapper.BoxClient;
import repolezanettiperuzzi.common.modelwrapper.ColourClient;
import repolezanettiperuzzi.common.modelwrapper.ValueClient;
import repolezanettiperuzzi.common.modelwrapper.WindowClient;

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
            case "startGame":
                gameView.enterGame();
                //TODO caricare la view per il turno e notificare il controller dopo che e' stata caricata
                break;
            case "winChooseWindow":
                gameView.showWinOnChooseWindowAlert();
                break;
            case "exit":
                gameView.shutdownClient();
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
