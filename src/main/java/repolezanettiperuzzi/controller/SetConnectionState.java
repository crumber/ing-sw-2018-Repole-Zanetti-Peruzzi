package repolezanettiperuzzi.controller;

import org.json.simple.JSONArray;
import org.json.simple.parser.ParseException;
import repolezanettiperuzzi.model.Player;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.*;


public class SetConnectionState extends ControllerState{

    private final Logger LOGGER = Logger.getLogger(SetConnectionState.class.getName());

    @Override
    public void doAction(Controller controller) throws IOException, ParseException {
        //sperando che il server per rmi non blocchi il thread
        ControllerRMIServer rmiServer = new ControllerRMIServer(controller);
        //rmiServer.startServer();

        int nPlayers = 0;
        int port = 8080;

        cleanJson("gamedata/playersinfo.json");

        try (ServerSocket serverSocket = new ServerSocket(port)){
            System.out.println("Server ready");

            Timer timer = new Timer(300000);
            timer.start();
            serverSocket.setSoTimeout((int)timer.getRemainingTime()); // sposta da 2 giocatori

            while(nPlayers<4 && !timer.timeout()){
                Socket socket = serverSocket.accept();
                HandlerControllerSocket handler = new HandlerControllerSocket(controller, socket);
                handler.handleMessage();
                nPlayers++;
                //Thread.sleep(500); da aggiunger in caso il messaggio sia inviato troppo presto alla view non dandogli tempo
                //di caricare la nuova scene e controller della waitingRoom
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
            if(player.getConnection().equals("Socket")){
                System.out.println("Creo Socket indirizzo "+player.getAddress()+" porta "+ player.getPort()+"\n");
                try(Socket socket = new Socket(player.getAddress(), player.getPort())){
                    System.out.println("Creo Handler\n");
                    HandlerControllerSocket handlerControllerSocket = new HandlerControllerSocket(controller, socket);
                    handlerControllerSocket.notifyOnNewPlayer();
                } catch(IOException e){
                    LOGGER.log(Level.WARNING,"IOException: ",e); //da verificare
                }
            } else if(controller.board.getPlayers().get(i).getConnection().equals("RMI")){

            }

        }
    }

    //method to clean the json file every time the game restarts
    private void cleanJson(String jsonPath){
        JSONArray jsonArr = new JSONArray();
        try (FileWriter file = new FileWriter(jsonPath)) {
            file.write(jsonArr.toJSONString());
            file.close();
        } catch (IOException e) {
            System.out.println("Cannot write on file");
        }
    }
}
