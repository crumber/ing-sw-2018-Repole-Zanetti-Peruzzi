package repolezanettiperuzzi.model;

import java.util.ArrayList;

public class RoundTrack {

    private static final int MAXROUNDTRACK = 10;
    private ArrayList<ArrayList<Die>> dieRoundTrack = new ArrayList<ArrayList<Die>>();


    public void addDice(ArrayList<Die> remainingDice) {

        dieRoundTrack.add(remainingDice);

    }

    public Die getDieRoundTrack(int whichRound, int whichDieRound) {

        if(whichRound>=dieRoundTrack.size() || whichDieRound>=dieRoundTrack.get(whichRound).size()){

            return null;
        }

        return dieRoundTrack.get(whichRound).get(whichDieRound);

    }

    public void setDieOnRoundTrack(int whichRound , int posDie, Die d) {

        dieRoundTrack.get(whichRound).set(posDie, d);

    }
}