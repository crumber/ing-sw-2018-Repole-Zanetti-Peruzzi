package repolezanettiperuzzi.view;

//  import repolezanettiperuzzi.controller.ControllerStub;
import repolezanettiperuzzi.controller.HandlerSkeletonRMI;
import repolezanettiperuzzi.controller.HandlerStubRMI;
import repolezanettiperuzzi.controller.HandlerStubSocket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

//lato client della view che chiama i metodi in remoto del controller
//prendo i dati gia' elaborati da RMI o Socket e li passo a GameViewCLI o GameViewGUI
public class GameView  {

    public static void main(String args[]) throws InterruptedException, IOException {
        int port = 0;
        try(ServerSocket serverSocket = new ServerSocket(0)) {

            try (Socket echoSocket = new Socket("127.0.0.1", 8080)) {
                PrintWriter out = new PrintWriter(echoSocket.getOutputStream(), true);
                out.println("ale init zan S CLI "+serverSocket.getLocalPort());
                BufferedReader in = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
                String[] line = in.readLine().split(" ");
                System.out.println(line[0] + " on port " + line[1] + "\n");
                port = Integer.parseInt(line[1]);
                out.close();
                in.close();
                echoSocket.close();
            } catch (UnknownHostException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            System.out.println("Server avviato");
            boolean read = true;
            while (read) {
                Socket socket = serverSocket.accept();
                System.out.println("Connessione ricevuta");
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String[] line = in.readLine().split(" ");
                switch (line[0]) {
                    case "newplayers":
                        System.out.println("Giocatori in attesa: \n");
                        int i = 0;
                        while(i<line.length-1){
                            System.out.println(line[i+1]);
                            i++;
                        }
                        System.out.println("\n\n\n\n");
                        break;
                    case "quit":
                        read = false;
                }
                in.close();
                socket.close();
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateView() {
        //aggiorno View
    }
}
