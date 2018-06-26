package repolezanettiperuzzi.model.publiccards;

import repolezanettiperuzzi.model.Value;
import repolezanettiperuzzi.model.Window;

public class ShadeVariety extends PublicCard {

    @Override
    public int effect(Window finalWindow){

        int [] counterValue= {0,0,0,0,0,0};
        int min=10000; //big number
        int score;

        for(int i=0;i<finalWindow.numRow();i++) {

            for (int j = 0; j < finalWindow.numColumn(); j++) {  // count number of all value

                if (finalWindow.thereIsDie(i, j)) {

                    if (finalWindow.getDieValue(i, j).equals(Value.ONE)) {

                        counterValue[0] += 1;

                    }

                    if (finalWindow.getDieValue(i, j).equals(Value.TWO)) {

                        counterValue[1] += 1;

                    }

                    if (finalWindow.getDieValue(i, j).equals(Value.THREE)) {

                        counterValue[2] += 1;

                    }

                    if (finalWindow.getDieValue(i, j).equals(Value.FOUR)) {

                        counterValue[3] += 1;

                    }

                    if (finalWindow.getDieValue(i, j).equals(Value.FIVE)) {

                        counterValue[4] += 1;

                    }

                    if (finalWindow.getDieValue(i, j).equals(Value.SIX)) {

                        counterValue[5] += 1;

                    }
                }
            }
        }
        for(int i=0;i<6;i++){

            if(counterValue[i]<min){

                min=counterValue[i];

            }
        }

        score=5*min;
        return score; // return 5 * min(numOne, numTwo, numThree, numFour, numFive, numSix)
    }
}
