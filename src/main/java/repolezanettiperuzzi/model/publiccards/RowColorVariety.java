package repolezanettiperuzzi.model.publiccards;

import repolezanettiperuzzi.model.Colour;
import repolezanettiperuzzi.model.Window;

public class RowColorVariety implements PublicCard {

    @Override
    public int effect(Window finalWindow){

        int numRow=0;
        int score;

        for(int i=0;i<4;i++) { // count number of row with 5 different colour of dice

            int [] counterColour= {0,0,0,0,0};
            int numOfColour=0;
            int j;

            for (j=0; j < 5; j++) {

                if (finalWindow.getDieColour(i, j).equals(Colour.RED)) {

                    counterColour[0] += 1;
                    numOfColour++;

                    if(counterColour[0]==2){

                        j=10;
                    }
                }

                if (finalWindow.getDieColour(i, j).equals(Colour.GREEN)) {

                    counterColour[1] += 1;
                    numOfColour++;

                    if(counterColour[0]==2){

                        j=10;
                    }
                }

                if (finalWindow.getDieColour(i, j).equals(Colour.PURPLE)) {

                    counterColour[2] += 1;
                    numOfColour++;

                    if(counterColour[0]==2){

                        j=10;
                    }
                }

                if (finalWindow.getDieColour(i, j).equals(Colour.BLUE)) {

                    counterColour[3] += 1;
                    numOfColour++;

                    if(counterColour[0]==2){

                        j=10;
                    }
                }

                if (finalWindow.getDieColour(i, j).equals(Colour.YELLOW)) {

                    counterColour[4] += 1;
                    numOfColour++;

                    if(counterColour[0]==2){

                        j=10;
                    }
                }
            }

            if( j!=10 && numOfColour==5 ){   // if there are five dice and five different colour in row, incrise by one

                numRow++;

            }
        }

        score= 6*numRow;
        return score; // return 6 * number of Row whit 5 different colour of dice
    }
}
