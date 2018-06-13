package repolezanettiperuzzi.view;

import repolezanettiperuzzi.view.modelwrapper.BoxClient;
import repolezanettiperuzzi.view.modelwrapper.WindowClient;

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
            System.out.println("port: "+localServerPort);
            boolean read = true;
            while(read){
                System.out.println("Attendo connessione");
                this.socket = serverSocket.accept();
                System.out.println("Connessione accettata\n");
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                onReceiveCallback.accept(in.readLine());
                in.close();
                socket.close();
            }

        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void handleMessage(String message){
        String[] line = message.split(" ");
        switch(line[0]){
            case "registered":
                System.out.println("registered");
                gameView.enterWaitingRoom();
                break;
            case "updatedplayers":  // setTimer player1 player2
                System.out.println("updatedPlayers");

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
                }
                break;
            case "changeView":
                if(line[1].equals("chooseWindow")){
                    gameView.enterChooseWIndow();
                }
                break;
            case "chooseWindow":
                receivedWindows(line);
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
        out.println(username + " waitingok");
        out.close();
        socket.close();
    }

    public void chooseWindowSceneLoaded(String username) throws IOException{
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        out.println(username + " choosewindowok");
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
        ArrayList<ArrayList<String>> boxesList = new ArrayList<ArrayList<String>>();
        int i = 1;
        while(i < line.length){
            windowName = line[i];
            i++;
            favorToken = Integer.parseInt(line[i]);
            i++;
            while(!line[i].equals("_")){
                boxesList.add((ArrayList<String>)Arrays.asList(line[i].split("-")));
                i++;
            }
            BoxClient[][] boxMatrix = arrayListToMatrix(boxesList);
            chosenWindows.add(new WindowClient(windowName, favorToken, boxMatrix));
            i++;

        }
        gameView.viewWindows(chosenWindows);
    }

    private BoxClient[][] arrayListToMatrix(ArrayList<ArrayList<String>> chosenWindows){
        int n = chosenWindows.size();
        int m = chosenWindows.get(0).size();
        BoxClient[][] boxMatrix = new BoxClient[n][m];
        for(int i = 0; i < n; i++){
            //TODO stampalo per vedere se viene giusto
            boxMatrix[i] = chosenWindows.get(i).toArray(new BoxClient[0]);
        }
        return boxMatrix;
    }

    public int getLocalServerPort(){
        return this.localServerPort;
    }

}
