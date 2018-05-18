package repolezanettiperuzzi.controller;

import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MultiEchoSocketServer {
    private int port;
    private Controller controller;

    public MultiEchoSocketServer(int port, Controller controller){
        this.port = port;
        this.controller = controller;
    }
    public void startServer() throws ParseException {
        ExecutorService executor = Executors.newCachedThreadPool();
        try (ServerSocket serverSocket = new ServerSocket(port)){
            System.out.println("Server ready");
            //quando si disconnettono tutti i giocatori o quando chiudo il processo del server allora posso concludere il loop
            while(controller.isListening()){
                Socket socket = serverSocket.accept();
                Scanner in = new Scanner(socket.getInputStream());
                String line = in.nextLine();
                InetAddress addr = socket.getInetAddress();
                int port = socket.getPort();
                in.close();
                socket.close();
                HandlerControllerSocket handler = new HandlerControllerSocket(line, addr, port, controller);
                handler.handleMessage();
            }
        } catch (IOException e){
            System.err.println("Could not listen on port " + port);
            System.exit(-1); // porta non disponibile
            return;
        }
    }
}

