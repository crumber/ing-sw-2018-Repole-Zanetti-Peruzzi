package repolezanettiperuzzi.model.publiccards;

import repolezanettiperuzzi.model.Value;
import repolezanettiperuzzi.model.Window;

/**
 * Classe che rappresenta la public card Deep Shades
 * @author Alessandro Peruzzi
 */
public class DeepShades extends PublicCard {

    /**
     *
     * @param finalWindow window su cui calcolare il punteggio
     * @return numero di set 5 e 6 ripetuti
     */
    @Override
    public int effect(Window finalWindow){

        int numFive=0;
        int numSix=0;
        int score;

        for(int i=0;i<finalWindow.numRow();i++) { // count number of five and six

            for (int j = 0; j < finalWindow.numColumn(); j++) {

                if (finalWindow.thereIsDie(i, j)) {

                    if (finalWindow.getDieValue(i, j).equals(Value.FIVE)) {

                        numFive++;

                    }

                    if (finalWindow.getDieValue(i, j).equals(Value.SIX)) {

                        numSix++;

                    }
                }
            }
        }
        if(numFive<numSix){

            score= 2*numFive;

        }
        else{

            score= 2*numSix;
        }

        return score; // return 2 * min(numFive, numSix)
    }
}
