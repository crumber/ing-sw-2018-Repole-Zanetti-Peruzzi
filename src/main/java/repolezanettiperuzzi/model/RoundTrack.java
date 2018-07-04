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
     * @param whichRound Round attuale
     * @param whichDieRound Dado che si vuole prendere
     * @return Dado selezionato
     */
    public Die getDieRoundTrack(int whichRound, int whichDieRound) {

        if(whichRound>=dieRoundTrack.size() || whichDieRound>=dieRoundTrack.get(whichRound).size()){

            return null;
        }

        return dieRoundTrack.get(whichRound).get(whichDieRound);

    }

    /**
     * Inserisce un dado nella RoundTrack
     * @param whichRound Round attualr
     * @param posDie Posizione in cui si vuole inserire il dado
     * @param d Dado che si vuole inserire
     */
    public void setDieOnRoundTrack(int whichRound , int posDie, Die d) {

        dieRoundTrack.get(whichRound).set(posDie, d);

    }

    /**
     * @return Restituisce la stringa che rappresenta il roundtrack: intero che rappresenta turno segiuto dalettera che rappresenta colore dado
     * seguito da numero che rapprenta valore dado e ogni dado del turno separato da _ mentre ogni turno separato da -
     */
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