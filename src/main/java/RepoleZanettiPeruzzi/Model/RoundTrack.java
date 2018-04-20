package RepoleZanettiPeruzzi.Model;

import java.util.ArrayList;
import java.util.Arrays;

public class RoundTrack {

    private int maxRoundTrack = 10;
    private ArrayList<ArrayList<Die>> dieRoundTrack = new ArrayList<ArrayList<Die>>();

    /*
    costruttore che inizializza gli arraylist esterni sennò l'addDie
    genera errore dovuto al fatto che l'indice è più grande della dimensione
     */
    public RoundTrack() {

        for (int i = 0; i < maxRoundTrack; i++) {
            dieRoundTrack.add(new ArrayList<Die>());
        }
    }

    public void addDie(Die d, int numberTurn) {

        dieRoundTrack.get(numberTurn-1).add(d);
    }

    public ArrayList<ArrayList<Die>> getDieRoundTrack() {
        return dieRoundTrack;
    }
}