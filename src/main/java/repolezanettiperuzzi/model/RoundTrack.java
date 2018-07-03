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

    //1B2_..._R3-2Y4_..._G5-....
    public String toString(){

        StringBuilder res = new StringBuilder();

        for(int i = 0; i<dieRoundTrack.size(); i++){

            res.append((i+1));

            for(int j = 0; j<dieRoundTrack.get(i).size(); j++){

                res.append(dieRoundTrack.get(i).get(j).toString());
                if(j<(dieRoundTrack.get(i).size()-1)) {
                    res.append("_");
                }

            }

            if(i<(dieRoundTrack.size()-1)) {
                res.append("-");
            }
        }

        return res.toString();
    }
}