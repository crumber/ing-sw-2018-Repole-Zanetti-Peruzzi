package repolezanettiperuzzi.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe che modellizza la Round Track del gioco
 * @author Alessandro Peruzzi
 * @author Giampiero Repole
 * @author Andrea Zanetti
 */
public class RoundTrack {

    private static final int MAXROUNDTRACK = 10;
    private ArrayList<ArrayList<Die>> dieRoundTrack = new ArrayList<>();


    /**
     * Aggiunge alla RoundTrack  i dadi rimasti nel Draft a fine Round
     * @param remainingDice ArrayList di dadi rimasti
     */
    public void addDice(List<Die> remainingDice) {

        dieRoundTrack.add((ArrayList<Die>)remainingDice);

    }

    /**
     * Prende un dado dalla RoundTrack
     * @param whichRound round attuale
     * @param whichDieRound dado che si vuole prendere
     * @return dado selezionato
     */
    public Die getDieRoundTrack(int whichRound, int whichDieRound) {

        if(whichRound>=dieRoundTrack.size() || whichDieRound>=dieRoundTrack.get(whichRound).size()){

            return null;
        }

        return dieRoundTrack.get(whichRound).get(whichDieRound);

    }

    /**
     * Inserisce un dado nella RoundTrack
     * @param whichRound round attualr
     * @param posDie posizione in cui si vuole inserire il dado
     * @param d dado che si vuole inserire
     */
    public void setDieOnRoundTrack(int whichRound , int posDie, Die d) {

        dieRoundTrack.get(whichRound).set(posDie, d);

    }
}