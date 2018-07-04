package repolezanettiperuzzi.common.modelwrapper;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Classe che modellizza il Roundtrack nel client
 * @author Andrea Zanetti
 * @author Giampiero Repole
 * @author Alessandro Peruzzi
 */
public class RoundTrackClient implements Serializable {

    private static final int MAXROUNDTRACK = 10;
    private ArrayList<ArrayList<DieClient>> dieRoundTrack = new ArrayList<ArrayList<DieClient>>();

    /**
     * Aggiunge il dado al roundtrack
     * @param die dado da aggiungere
     * @param round Round che parte da 1
     */
    public void addDie(DieClient die, int round){
        dieRoundTrack.get(round).add(die);
    }

    /**
     * Aggiunge i dadi del draft al roundtrack
     * @param remainingDice Dadi presenti sul draft
     */
    public void addDice(ArrayList<DieClient> remainingDice) {

        dieRoundTrack.add(remainingDice);

    }

    /**
     *
     * @param whichRound Intero che indica quale round del roundtrack
     * @param whichDieRound Intero che indica quale dado del round
     * @return Il dado presente nella posizione del roundtrack
     */
    public DieClient getDieRoundTrack(int whichRound, int whichDieRound) {

        if(whichRound>=dieRoundTrack.size() || whichDieRound>=dieRoundTrack.get(whichRound).size()){

            return null;
        }

        return dieRoundTrack.get(whichRound).get(whichDieRound);

    }

    /**
     *
     * @return True se il roundtrack è vuoto sennò false
     */
    public boolean isEmptyRoundTrack(){

        return dieRoundTrack.isEmpty();

    }

    /**
     *
     * @return Numero round presenti nel roundtrack
     */
    public int sizeRoundTrack(){

        return dieRoundTrack.size();

    }

    /**
     *
     * @param whichRound Intero che indica quale round del round track
     * @return Il numero di dadi presenti nel round del roundtrack
     */
    public int sizeRound(int whichRound){

        return dieRoundTrack.get(whichRound).size();

    }

}