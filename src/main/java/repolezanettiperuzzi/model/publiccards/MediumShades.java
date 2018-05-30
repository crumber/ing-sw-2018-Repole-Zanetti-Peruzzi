package repolezanettiperuzzi.model.publiccards;

import repolezanettiperuzzi.model.Value;
import repolezanettiperuzzi.model.Window;

public class MediumShades implements PublicCard {

    @Override
    public int effect(Window finalWindow){

        int numThree=0;
        int numFour=0;
        int score;

        for(int i=0;i<4;i++) { // count number of three and four

            for (int j = 0; j < 5; j++) {

                if (finalWindow.thereIsDie(i, j)) {

                    if (finalWindow.getDieValue(i, j).equals(Value.THREE)) {

                        numThree++;

                    }

                    if (finalWindow.getDieValue(i, j).equals(Value.FOUR)) {

                        numFour++;

                    }
                }
            }
        }
        if(numThree<numFour){

            score= 2*numThree;

        }
        else{

            score= 2*numFour;
        }

        return score; // return 2 * min(numThree, numFour)
    }
}
