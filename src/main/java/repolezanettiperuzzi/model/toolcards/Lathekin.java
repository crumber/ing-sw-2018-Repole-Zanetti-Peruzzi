package repolezanettiperuzzi.model.toolcards;

import repolezanettiperuzzi.model.GameBoard;
import repolezanettiperuzzi.model.Player;

import java.util.List;

/**
 * Classe che rappresenta la tool card 4
 * @author Alessandro Peruzzi
 */
public class Lathekin extends ToolCard {

    private int x1Start;
    private int y1Start;
    private int x1End;
    private int y1End;
    private int x2Start;
    private int y2Start;
    private int x2End;
    private int y2End;

    /**
     * Costruttore della classe, imposta l'id
     */
    public Lathekin(){

        id=4;

    }

    /**
     * svolge i controlli sui parametri e la situazione per l'attivazione della carta
     * @param board è la game board
     * @param player indica il player che vuole attivare la carta
     * @param parameterForCard è una lista di interi che rappresentano i vari valori dei parametri per l'attivazione della carta scelti dal client
     * @return ritorna 1 se i controlli sono andati bene sennò un valore negativo che indica l'errore
     */
    //check that exist start/end position, check that there is a die in initial position, check that there isn't a die in end position, check that this die respects all constraint (colour, value and there is die near end position). do it for two dice
    @Override
    public int check(GameBoard board, Player player, List<Integer> parameterForCard) {

        x1Start = parameterForCard.get(0);
        y1Start = parameterForCard.get(1);
        x1End = parameterForCard.get(2);
        y1End = parameterForCard.get(3);
        x2Start = parameterForCard.get(4);
        y2Start = parameterForCard.get(5);
        x2End = parameterForCard.get(6);
        y2End = parameterForCard.get(7);

        resultOfAction = checkMoveTwoDice(board, player, x1Start, y1Start, x1End, y1End, x2Start, y2Start, x2End, y2End);

        return resultOfAction;
    }

    /**
     * attiva l'effetto della carta
     * @param board è la game board
     * @param player indica il player che vuole attivare la carta
     * @param parameterForCard è una lista di interi che rappresentano i vari valori dei parametri per l'attivazione della carta scelti dal client
     */
    //move both dice (with all bound)
    @Override
    public void effect(GameBoard board, Player player, List<Integer> parameterForCard) {

        x1Start = parameterForCard.get(0);
        y1Start = parameterForCard.get(1);
        x1End = parameterForCard.get(2);
        y1End = parameterForCard.get(3);
        x2Start = parameterForCard.get(4);
        y2Start = parameterForCard.get(5);
        x2End = parameterForCard.get(6);
        y2End = parameterForCard.get(7);

        player.getWindow().moveDie(x1Start, y1Start, x1End, y1End, "both");
        player.getWindow().moveDie(x2Start, y2Start, x2End, y2End, "both");

    }
}

