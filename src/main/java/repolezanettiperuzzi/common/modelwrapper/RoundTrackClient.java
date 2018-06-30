package repolezanettiperuzzi.common.modelwrapper;

import java.io.Serializable;
import java.util.ArrayList;

public class RoundTrackClient implements Serializable {

    private static final int MAXROUNDTRACK = 10;
    private ArrayList<ArrayList<DieClient>> dieRoundTrack = new ArrayList<ArrayList<DieClient>>();

    /**
     *
     * @param die
     * @param round Round che parte da 1
     */
    public void addDie(DieClient die, int round){
        dieRoundTrack.get(round).add(die);
    }

    public void addDice(ArrayList<DieClient> remainingDice) {

        dieRoundTrack.add(remainingDice);

    }

    public DieClient getDieRoundTrack(int whichRound, int whichDieRound) {

        if(whichRound>=dieRoundTrack.size() || whichDieRound>=dieRoundTrack.get(whichRound).size()){

            return null;
        }

        return dieRoundTrack.get(whichRound).get(whichDieRound);

    }

    public void setDieOnRoundTrack(int whichRound , int posDie, DieClient d) {

        dieRoundTrack.get(whichRound).set(posDie, d);

    }

    public boolean isEmptyRoundTrack(){

        return dieRoundTrack.isEmpty();

    }

    public int sizeRoundTrack(){

        return dieRoundTrack.size();

    }

    public int sizeRound(int whichRound){

        return dieRoundTrack.get(whichRound).size();

    }

}