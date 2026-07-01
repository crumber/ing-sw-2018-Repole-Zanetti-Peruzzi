package repolezanettiperuzzi.infrastructure.client.socket;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Sends one client socket message to the controller.
 */
public class ClientSocketConnection {

    private Socket socket;

    public ClientSocketConnection(String serverIp) throws IOException {
        this(new Socket(serverIp,8080));
    }

    ClientSocketConnection(Socket socket){
        this.socket=socket;
    }

    public void sendMessage(String message) throws IOException {
        PrintWriter out = new PrintWriter(socket.getOutputStream(),true);
        out.println(message);
        out.close();
        socket.close();
    }
}
