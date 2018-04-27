package repolezanettiperuzzi.model.publiccards;

import repolezanettiperuzzi.model.Value;
import repolezanettiperuzzi.model.Window;

public class ColumnShadeVariety implements PublicCard {

    @Override
    public int effect(Window finalWindow){

        int numColumn=0;
        int score;

        for(int i=0;i<5;i++) {  // count number of column with 4 different value of dice

            int [] counterValue= {0,0,0,0,0,0};
            int numOfValue=0;
            int j;

            for (j=0; j < 4; j++) {

                if (finalWindow.getDieValue(j, i).equals(Value.ONE)) {

                    counterValue[0] += 1;
                    numOfValue++;

                    if(counterValue[0]==2){

                        j=20;
                    }
                }

                if (finalWindow.getDieValue(j, i).equals(Value.TWO)) {

                    counterValue[1] += 1;
                    numOfValue++;

                    if(counterValue[0]==2){

                        j=20;
                    }
                }

                if (finalWindow.getDieValue(j, i).equals(Value.THREE)) {

                    counterValue[2] += 1;
                    numOfValue++;

                    if(counterValue[0]==2){

                        j=20;
                    }
                }

                if (finalWindow.getDieValue(j, i).equals(Value.FOUR)) {

                    counterValue[3] += 1;
                    numOfValue++;

                    if(counterValue[0]==2){

                        j=20;
                    }
                }

                if (finalWindow.getDieValue(j, i).equals(Value.FIVE)) {

                    counterValue[4] += 1;
                    numOfValue++;

                    if(counterValue[0]==2){

                        j=20;
                    }
                }

                if (finalWindow.getDieValue(j, i).equals(Value.SIX)) {

                    counterValue[5] += 1;
                    numOfValue++;

                    if(counterValue[0]==2){

                        j=20;
                    }
                }
            }

            if( j!=20 && numOfValue==4 ){   // if there are four dice and four different value in column, incrise by one

                numColumn++;

            }
        }

        score= 4*numColumn;
        return score; // return 4 * number of Column whit 4 different value of dice
    }
}
