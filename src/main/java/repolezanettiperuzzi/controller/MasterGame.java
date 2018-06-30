package repolezanettiperuzzi.controller;

import org.json.simple.parser.ParseException;
import repolezanettiperuzzi.model.Colour;
import repolezanettiperuzzi.model.GameBoard;
import repolezanettiperuzzi.view.ShutdownConsole;


import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.registry.Registry;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class MasterGame {
    private int nPlayer;
    private static GameBoard board;

    public static void main(String[] args) throws IOException, ParseException, InterruptedException {

        board = new GameBoard();

        Controller controller = new Controller(board.getPlayers(),board);
        controller.setState(new ResetGameState());
        printRMIStart();
        ControllerRMIServer rmiServer = new ControllerRMIServer(controller);
        Registry registry = rmiServer.startServer();
        Runtime.getRuntime().addShutdownHook(new ShutdownRMIServer(registry, controller.getHandlerRMI()));

        int port = 8080;

        ExecutorService executor = Executors.newCachedThreadPool();

        printSocketStart();
        try (ServerSocket serverSocket = new ServerSocket(port)) {

            boolean isOpen = true;
            String serverAddress = InetAddress.getLocalHost().getHostAddress();
            System.out.println("Server socket ready with address "+serverAddress+" port "+port);

            while (isOpen) {

                Socket socket = serverSocket.accept();
                executor.submit(new HandlerControllerSocket(controller, socket));

            }


        }
    }

    public static void printRMIStart() throws InterruptedException {
        System.out.print("Starting RMI Server (this may take up to 40 seconds)");
        Thread.sleep(500);
        System.out.print(".");
        Thread.sleep(500);
        System.out.print(".");
        Thread.sleep(500);
        System.out.print(".\n");
    }

    public static void printSocketStart() throws InterruptedException {
        System.out.print("Starting Socket Server (this may take up to 15 seconds)");
        Thread.sleep(500);
        System.out.print(".");
        Thread.sleep(500);
        System.out.print(".");
        Thread.sleep(500);
        System.out.print(".\n");
    }

}
