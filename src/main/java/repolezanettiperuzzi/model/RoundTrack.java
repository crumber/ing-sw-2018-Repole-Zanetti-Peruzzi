package repolezanettiperuzzi.model;

import java.util.ArrayList;
import java.util.List;

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

    public Die getDieRoundTrack(int whichRound, int whichDieRound) {
        return dieRoundTrack.get(whichRound).get(whichDieRound);

    }

    public void setDieOnRoundTrack(int whichRound, int posDie, Die d) {

        dieRoundTrack.get(whichRound).set(posDie, d);

    }

}