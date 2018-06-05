package repolezanettiperuzzi.controller;

import org.json.simple.parser.ParseException;
import repolezanettiperuzzi.model.GameBoard;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class MasterGame {

    private int nPlayer;
    private static GameBoard board;

    public static void main(String[] args) throws IOException, ParseException {

        board = new GameBoard();

        Controller controller = new Controller(board.getPlayers(),board);
        controller.setState(new ResetGameState());

        //sperando che il server per rmi non blocchi il thread
        ControllerRMIServer rmiServer = new ControllerRMIServer(controller);
        //rmiServer.startServer();



        int port = 8080;

        ExecutorService executor = Executors.newCachedThreadPool();

        try (ServerSocket serverSocket = new ServerSocket(port)) {

            boolean isOpen = true;

            while (isOpen) {

                System.out.println("Server ready");
                Socket socket = serverSocket.accept();
                executor.submit(new HandlerControllerSocket(controller, socket));
            }


        }
    }




}
