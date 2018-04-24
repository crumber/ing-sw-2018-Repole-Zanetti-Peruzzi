package repolezanettiperuzzi.model;

import java.util.ArrayList;

public class RoundTrack {

    private static final int MAXROUNDTRACK = 10;
    private ArrayList<ArrayList<Die>> dieRoundTrack = new ArrayList<>();

    /*
    costruttore che inizializza gli arraylist esterni sennò l'addDie
    genera errore dovuto al fatto che l'indice è più grande della dimensione
     */
    public RoundTrack() {

        for (int i = 0; i < MAXROUNDTRACK; i++) {
            dieRoundTrack.add(new ArrayList<Die>());
        }
    }

    public void addDice(ArrayList<Die> remainingDice) {

        dieRoundTrack.add(remainingDice);

    }

    public ArrayList<ArrayList<Die>> getDieRoundTrack() {
        return dieRoundTrack;
    }
}