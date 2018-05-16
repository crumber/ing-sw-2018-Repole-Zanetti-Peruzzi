package repolezanettiperuzzi.controller;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MultiEchoSocketServer {
    private int port;
    public MultiEchoSocketServer(int port){
        this.port = port;
    }
    public void startServer() {
        ExecutorService executor = Executors.newCachedThreadPool();
        try (ServerSocket serverSocket = new ServerSocket(port)){
            System.out.println("Server ready");
            //quando si sconnettono tutti i giocatori o quando chiudo il processo del server allora posso concludere il loop
            /*while(true){
                Socket socket = serverSocket.accept();
                Scanner in = new Scanner(socket.getInputStream());
                String line = in.nextLine();
                in.close();
                socket.close();
                HandlerControllerSocket handlerControllerSocket = new HandlerControllerSocket(line);
            }*/
        } catch (IOException e){
            System.err.println("Could not listen on port " + port);
            System.exit(-1); // porta non disponibile
            return;
        }
    }
}

