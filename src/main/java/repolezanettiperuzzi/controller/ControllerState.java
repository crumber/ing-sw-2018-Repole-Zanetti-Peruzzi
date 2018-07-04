package repolezanettiperuzzi.controller;

import org.json.simple.parser.ParseException;
import repolezanettiperuzzi.model.GameBoard;
import repolezanettiperuzzi.model.Player;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 * Classe che rappresenta un generico stato del controller
 * @author Giampiero Repole
 */
public abstract class ControllerState {

    private GameBoard board;
    private ArrayList<Player> view;

   /* public ControllerState(ArrayList<Player> view, GameBoard board ){

        this.board=board;
        this.view = view;

    }*/

    /**
     * Esegue l'action dello stato
     * @param controller Controller
     * @throws ParseException Errore durante l'analisi
     * @throws IOException Fallimento o interruzione delle operazioni I/O
     */
    public abstract void doAction(Controller controller) throws ParseException, IOException;



}
