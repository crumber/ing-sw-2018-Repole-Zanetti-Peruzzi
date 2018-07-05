package repolezanettiperuzzi.common;

import repolezanettiperuzzi.common.modelwrapper.GameBoardClient;
import repolezanettiperuzzi.common.modelwrapper.WindowClient;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 * Classe che rappresenta il client per RMI
 * @author Andrea Zanetti
 */
//interfaccia della view lato server a cui potra' accedere il controller
public interface ClientStubRMI extends Remote {

    /**
     * Aggiorna la view
     * @param board Game board
     * @throws RemoteException Eccezione di RMI
     */
    void updateView(GameBoardClient board) throws RemoteException;

    /**
     * Prepara la waiting room
     * @throws RemoteException Eccezione di RMI
     */
    void enterWaitingRoom() throws RemoteException;

    /**
     * Aggiornamento waiting room
     * @param setTimer Timer
     * @param players Array di stringhe che rappresentano i player
     * @throws RemoteException Eccezione di RMI
     */
    void refreshWaitingRoom(int setTimer, String[] players) throws RemoteException;

    /**
     * Prepara per mostrare le window da scegliere
     * @throws RemoteException Eccezione di RMI
     */
    void enterChooseWindow() throws RemoteException;

    /**
     * Mostra le window
     * @param windows Lista di 4 window da cui scegliere
     * @param currentTime Timer
     * @throws RemoteException Eccezione di RMI
     */
    void viewWindows(ArrayList<WindowClient> windows, int currentTime) throws RemoteException;

    /**
     * Mostra la window scelta
     * @param window window scelta
     * @param currentTime Timer
     * @throws RemoteException Eccezione di RMI
     */
    void viewOneWindow(WindowClient window, int currentTime) throws RemoteException;

    /**
     * Mostra che sei l'ultimo giocatore rimasto
     * @throws RemoteException Eccezione di RMI
     */
    void showWinOnChooseWindowAlert() throws RemoteException;

    /**
     * Prepara per la fase di gioco
     * @throws RemoteException Eccezione di RMI
     */
    void enterGame() throws RemoteException;

    void notifyTurn(String actualPlayer, int currentTime) throws RemoteException;

    void receiveCardParameters(String parameters) throws RemoteException;

    void notYourTurn() throws RemoteException;

    void viewError(String error) throws RemoteException;

    void showWinBeforeEndGameAlert() throws RemoteException;

    void receiveRanking(String score) throws RemoteException;
}
