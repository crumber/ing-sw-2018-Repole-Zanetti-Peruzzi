package repolezanettiperuzzi.view;

import repolezanettiperuzzi.common.ClientStubRMI;
import repolezanettiperuzzi.common.ControllerStubRMI;
import repolezanettiperuzzi.common.DynamicPath;

import java.rmi.NoSuchObjectException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class GameViewRMIServer {

    private GameView gameview;
    private ClientStubRMI clientStub;

    public GameViewRMIServer(GameView gameview){
        this.gameview = gameview;
    }

    public ControllerStubRMI bind() throws RemoteException, NotBoundException {
        System.setProperty("java.rmi.server.useCodebaseOnly", "false");
        System.setProperty("java.rmi.server.logCalls", "true");

        System.setProperty("java.rmi.server.codebase", new DynamicPath("view/").getPath()+" "+new DynamicPath("common/").getPath());
        System.setProperty("java.security.policy", new DynamicPath("RMI/stupid.policy").getPath());


        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }

        String serverIp = "127.0.0.1";

        Registry registry= LocateRegistry.getRegistry(serverIp, 1099);
        System.out.print("RMI registry bindings: ");
        String[] e = registry.list();

        for (int i=0; i<e.length; i++)
            System.out.println(e[i]);

        String remoteObjectName = "controller";
        ControllerStubRMI h = (ControllerStubRMI) registry.lookup(remoteObjectName);

        System.out.println("Registering for callback");
        this.clientStub = (ClientStubRMI) UnicastRemoteObject.exportObject(gameview, 0);
        return h;
    }

    public ClientStubRMI getClientStub(){
        return this.clientStub;
    }

    public void unexportRMI(){
        try {
            UnicastRemoteObject.unexportObject(gameview, true);
        } catch (NoSuchObjectException e) {
            e.printStackTrace();
        }
    }
}
