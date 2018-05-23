package repolezanettiperuzzi.controller;

import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.RemoteException;

public class SetConnectionsState extends ControllerState {

    @Override
    public void doAction(Controller controller) throws ParseException, IOException {

        //sperando che il server per rmi non blocchi il thread
        ControllerRMIServer rmiServer = new ControllerRMIServer(controller);
        //rmiServer.startServer();

        int nPlayers = 0;
        int port = 8080;

        try (ServerSocket serverSocket = new ServerSocket(port)){
            System.out.println("Server ready");

            Timer timer = new Timer(6000);
            timer.start();
            serverSocket.setSoTimeout((int)timer.getRemainingTime());

            while(nPlayers<4 || timer.timeout()){
                Socket socket = serverSocket.accept();
                HandlerControllerSocket handler = new HandlerControllerSocket(controller, socket);
                handler.handleMessage();
                nPlayers++;
                serverSocket.setSoTimeout((int)timer.getRemainingTime());
            }

        } catch (InterruptedIOException e){
            System.out.println("Timeout");
            //System.exit(-2); // timer scaduto
        } catch (IOException e){
            System.out.println("Could not listen on port " + port);
            //System.exit(-1); // porta non disponibile
            return;
        }

        controller.setState(new FetchState());

    }
}
