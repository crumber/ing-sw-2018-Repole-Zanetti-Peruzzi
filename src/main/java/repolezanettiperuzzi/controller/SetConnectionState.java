package repolezanettiperuzzi.controller;

import org.json.simple.parser.ParseException;
import repolezanettiperuzzi.model.Player;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.RemoteException;

public class SetConnectionState extends ControllerState{

    @Override
    public void doAction(Controller controller) throws IOException, ParseException {
        //sperando che il server per rmi non blocchi il thread
        ControllerRMIServer rmiServer = new ControllerRMIServer(controller);
        //rmiServer.startServer();

        int nPlayers = 0;
        int port = 8080;

        try (ServerSocket serverSocket = new ServerSocket(port)){
            System.out.println("Server ready");

            Timer timer = new Timer(5000);
            timer.start();
            serverSocket.setSoTimeout((int)timer.getRemainingTime()); // sposta da 2 giocatori

            while(nPlayers<4 && !timer.timeout()){
                Socket socket = serverSocket.accept();
                HandlerControllerSocket handler = new HandlerControllerSocket(controller, socket);
                handler.handleMessage();
                nPlayers++;
                notifyOnNewPlayer(controller);
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

    public void notifyOnNewPlayer(Controller controller) throws IOException, ParseException {
        for(int i = 0; i<controller.board.getNPlayers(); i++){
            System.out.println("NPlayer "+i+"\n");
            Player player = controller.board.getPlayers().get(i);
            if(player.getConnection().equals("S")){
                System.out.println("Creo Socket indirizzo "+player.getAddress()+" porta "+ player.getPort()+"\n");
                try(Socket socket = new Socket(player.getAddress(), player.getPort())){
                    System.out.println("Creo Handler\n");
                    HandlerControllerSocket handlerControllerSocket = new HandlerControllerSocket(controller, socket);
                    handlerControllerSocket.notifyOnNewPlayer();
                } catch(IOException e){
                    e.printStackTrace();
                }
            } else if(controller.board.getPlayers().get(i).getConnection().equals("RMI")){

            }

        }
    }
}
