package repolezanettiperuzzi.infrastructure.client.socket;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ClientSocketConnectionTest {

    @Test
    public void sendsOneLineAndClosesSocket() throws Exception {
        ServerSocket serverSocket = new ServerSocket(0);
        ExecutorService executor = Executors.newSingleThreadExecutor();
        try {
            Future<String> receivedMessage = executor.submit(() -> {
                try(Socket acceptedSocket = serverSocket.accept();
                    BufferedReader in = new BufferedReader(new InputStreamReader(acceptedSocket.getInputStream()))){
                    return in.readLine();
                }
            });

            Socket socket = new Socket("127.0.0.1",serverSocket.getLocalPort());
            ClientSocketConnection connection = new ClientSocketConnection(socket);

            connection.sendMessage("ale waitingOk");

            assertEquals("ale waitingOk",receivedMessage.get(2, TimeUnit.SECONDS));
            assertTrue(socket.isClosed());
        } finally {
            executor.shutdownNow();
            serverSocket.close();
        }
    }
}
