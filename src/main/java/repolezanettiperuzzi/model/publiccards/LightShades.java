package repolezanettiperuzzi.model.publiccards;

import repolezanettiperuzzi.model.Value;
import repolezanettiperuzzi.model.Window;

/**
 * Classe che rappresenta la public card Light Shades
 * @author Alessandro Peruzzi
 */
public class LightShades extends PublicCard {

    /**
     *
     * @param finalWindow window su cui calcolare il punteggio
     * @return numero di set 1 e 2 ripetuti
     */
    @Override
    public int effect(Window finalWindow){

        int numOne=0;
        int numTwo=0;
        int score;

        for(int i=0;i<finalWindow.numRow();i++) { // count number of one and two

            for (int j = 0; j < finalWindow.numColumn(); j++) {

                if (finalWindow.thereIsDie(i, j)) {

                    if (finalWindow.getDieValue(i, j).equals(Value.ONE)) {

                        numOne++;

                    }

                    if (finalWindow.getDieValue(i, j).equals(Value.TWO)) {

                        numTwo++;

                    }
                }
            }
        }
        if(numOne<numTwo){

            score= 2*numOne;

        }
        else{

            score= 2*numTwo;
        }

        return score; // return 2 * min(numOne, numTwo)
    }
}