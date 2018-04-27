package repolezanettiperuzzi.model.publiccards;

import repolezanettiperuzzi.model.Value;
import repolezanettiperuzzi.model.Window;

public class RowShadeVariety implements PublicCard {

    @Override
    public int effect(Window finalWindow){

        int numRow=0;
        int score;

        for(int i=0;i<4;i++) { // count number of row with 5 different value of dice

            int [] counterValue= {0,0,0,0,0,0};
            int numOfValue=0;
            boolean repeatValue=false;

            for (int j=0; j < 5; j++) {

                if (finalWindow.getDieValue(i, j).equals(Value.ONE)) {

                    counterValue[0] += 1;
                    numOfValue++;

                    if(counterValue[0]==2){

                        repeatValue=true;
                        break;
                    }
                }

                if (finalWindow.getDieValue(i, j).equals(Value.TWO)) {

                    counterValue[1] += 1;
                    numOfValue++;

                    if(counterValue[1]==2){

                        repeatValue=true;
                        break;
                    }
                }

                if (finalWindow.getDieValue(i, j).equals(Value.THREE)) {

                    counterValue[2] += 1;
                    numOfValue++;

                    if(counterValue[2]==2){

                        repeatValue=true;
                        break;
                    }
                }

                if (finalWindow.getDieValue(i, j).equals(Value.FOUR)) {

                    counterValue[3] += 1;
                    numOfValue++;

                    if(counterValue[3]==2){

                        repeatValue=true;
                        break;
                    }
                }

                if (finalWindow.getDieValue(i, j).equals(Value.FIVE)) {

                    counterValue[4] += 1;
                    numOfValue++;

                    if(counterValue[4]==2){

                        repeatValue=true;
                        break;
                    }
                }

                if (finalWindow.getDieValue(i, j).equals(Value.SIX)) {

                    counterValue[5] += 1;
                    numOfValue++;

                    if(counterValue[5]==2){

                        repeatValue=true;
                        break;
                    }
                }
            }

            if(  !repeatValue && numOfValue==5 ){   // if there are five dice and five different value in row, incrise by one

                numRow++;

            }
        }

        score= 5*numRow;
        return score; // return 5 * number of Row whit 5 different value of dice
    }
}
