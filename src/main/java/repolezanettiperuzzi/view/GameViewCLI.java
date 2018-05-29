package repolezanettiperuzzi.view;

import repolezanettiperuzzi.controller.HandlerSkeletonRMI;
import repolezanettiperuzzi.controller.HandlerStubRMI;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class GameViewCLI implements HandlerSkeletonRMI {

    public void refreshWaitingRoom(int setTimer, String[] players){
        //se setTimer e' maggiore di 0 allora setto il timer
        System.out.println("Giocatori in attesa: \n");
        int i = 0;
        while(i<players.length){
            System.out.println(players[i]);
            i++;
        }
        System.out.println("\n\n\n\n");
    }

    @Override
    public void updateView() {
        //aggiorno View
    }
}
