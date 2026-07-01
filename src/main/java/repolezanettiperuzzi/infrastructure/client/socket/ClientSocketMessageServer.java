package repolezanettiperuzzi.infrastructure.client.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.function.Consumer;

/**
 * Listens for controller socket callbacks and forwards each received line.
 */
public class ClientSocketMessageServer implements Runnable {

    private final Consumer<String> onReceiveCallback;
    private ServerSocket serverSocket;
    private int localServerPort;
    private boolean serverLoop;

    public ClientSocketMessageServer(Consumer<String> onReceiveCallback){
        this.onReceiveCallback=onReceiveCallback;
    }

    @Override
    public void run(){
        try(ServerSocket openedServerSocket = new ServerSocket(0)){
            this.serverSocket=openedServerSocket;
            this.localServerPort=openedServerSocket.getLocalPort();
            serverLoop=true;
            while(serverLoop){
                Socket socket = openedServerSocket.accept();
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                onReceiveCallback.accept(in.readLine());
                in.close();
                socket.close();
            }
        } catch (IOException e){
            if(serverLoop){
                e.printStackTrace();
            }
        }
    }

    public void shutdownServer(){
        this.serverLoop=false;
        if(serverSocket!=null){
            try {
                serverSocket.close();
            } catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    public int getLocalServerPort(){
        return localServerPort;
    }
}
