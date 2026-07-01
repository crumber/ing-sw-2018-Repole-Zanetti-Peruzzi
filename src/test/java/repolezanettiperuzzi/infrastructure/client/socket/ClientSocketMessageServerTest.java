package repolezanettiperuzzi.infrastructure.client.socket;

import org.junit.Test;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ClientSocketMessageServerTest {

    @Test
    public void forwardsReceivedLineToCallback() throws Exception {
        AtomicReference<String> receivedMessage = new AtomicReference<>();
        CountDownLatch receivedLatch = new CountDownLatch(1);
        ClientSocketMessageServer messageServer = new ClientSocketMessageServer(message -> {
            receivedMessage.set(message);
            receivedLatch.countDown();
        });

        Thread serverThread = new Thread(messageServer);
        serverThread.start();
        try {
            int localPort = waitForLocalPort(messageServer);
            Socket socket = new Socket("127.0.0.1",localPort);
            PrintWriter out = new PrintWriter(socket.getOutputStream(),true);

            out.println("registered");
            out.close();
            socket.close();

            assertTrue(receivedLatch.await(2, TimeUnit.SECONDS));
            assertEquals("registered",receivedMessage.get());
        } finally {
            messageServer.shutdownServer();
            serverThread.join(2000);
        }
    }

    private int waitForLocalPort(ClientSocketMessageServer messageServer) throws InterruptedException {
        int attempts = 0;
        while(messageServer.getLocalServerPort()==0 && attempts<20){
            Thread.sleep(50);
            attempts++;
        }

        return messageServer.getLocalServerPort();
    }
}
