package repolezanettiperuzzi.model.publiccards;

import repolezanettiperuzzi.model.Colour;
import repolezanettiperuzzi.model.Window;

public class ColumnColorVariety implements PublicCard {

    @Override
    public int effect(Window finalWindow){

        int numColumn=0;
        int score;

        for(int i=0;i<5;i++) { // count number of column with 4 different colour of dice

            int [] counterColour= {0,0,0,0,0};
            int numOfColour=0;
            boolean noRepeatValue=true;

            for (int j=0; j < 4; j++) {

                if (finalWindow.thereIsDie(j, i)) {

                    if (finalWindow.getDieColour(j, i).equals(Colour.RED)) {

                        counterColour[0] += 1;
                        numOfColour++;

                        if (counterColour[0] == 2) {

                            noRepeatValue = false;
                            break;
                        }
                    }

                    if (finalWindow.getDieColour(j, i).equals(Colour.GREEN)) {

                        counterColour[1] += 1;
                        numOfColour++;

                        if (counterColour[1] == 2) {

                            noRepeatValue = false;
                            break;
                        }
                    }

                    if (finalWindow.getDieColour(j, i).equals(Colour.PURPLE)) {

                        counterColour[2] += 1;
                        numOfColour++;

                        if (counterColour[2] == 2) {

                            noRepeatValue = false;
                            break;
                        }
                    }

                    if (finalWindow.getDieColour(j, i).equals(Colour.BLUE)) {

                        counterColour[3] += 1;
                        numOfColour++;

                        if (counterColour[3] == 2) {

                            noRepeatValue = false;
                            break;
                        }
                    }

                    if (finalWindow.getDieColour(j, i).equals(Colour.YELLOW)) {

                        counterColour[4] += 1;
                        numOfColour++;

                        if (counterColour[4] == 2) {

                            noRepeatValue = false;
                            break;
                        }
                    }
                }
            }
            if( noRepeatValue && numOfColour==4 ){   // if there are 4 dice and five different colour in column, incrise by one

                numColumn++;

            }
        }

        score= 5*numColumn;
        return score; // return 5 * number of coloùumn whit 5 different colour of dice
    }
}
