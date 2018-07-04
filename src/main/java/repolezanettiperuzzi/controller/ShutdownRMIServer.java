package repolezanettiperuzzi.controller;

import repolezanettiperuzzi.view.GameView;

import java.io.IOException;
import java.rmi.NoSuchObjectException;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Timer;

/**
 * Classe che rappresenta la chiusura del server di RMI
 * @author Andrea Zanetti
 */
public class ShutdownRMIServer extends Thread{

    private Registry registry;
    private HandlerControllerRMI handlerRMI;

    /**
     * Costruttore
     * @param registry Registry di RMI
     * @param handlerRMI Handler RMI
     */
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
