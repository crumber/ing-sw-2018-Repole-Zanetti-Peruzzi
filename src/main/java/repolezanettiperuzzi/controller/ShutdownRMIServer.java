package repolezanettiperuzzi.controller;

import repolezanettiperuzzi.view.GameView;

import java.io.IOException;
import java.rmi.NoSuchObjectException;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Timer;

public class ShutdownRMIServer extends Thread{

    private Registry registry;
    private HandlerControllerRMI handlerRMI;

    public ShutdownRMIServer(Registry registry, HandlerControllerRMI handlerRMI){
        this.registry = registry;
        this.handlerRMI = handlerRMI;
    }

    public void run()
    {
        try {
            UnicastRemoteObject.unexportObject(handlerRMI, true);
            UnicastRemoteObject.unexportObject(registry, true);
        } catch (NoSuchObjectException e) {
            e.printStackTrace();
        }
    }
}
