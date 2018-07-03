package repolezanettiperuzzi.model.publiccards;

import repolezanettiperuzzi.model.Colour;
import repolezanettiperuzzi.model.Window;

/**
 * Classe che rappresenta la public card Row Color Variety
 * @author Alessandro Peruzzi
 */
public class RowColorVariety extends PublicCard {

    /**
     *
     * @param finalWindow window su cui calcolare il punteggio
     * @return numero righe con colori non ripetuti
     */
    @Override
    public int effect(Window finalWindow){

        int numRow=0;
        int score;

        for(int i=0;i<finalWindow.numRow();i++) { // count number of row with 5 different colour of dice

            int [] counterColour= {0,0,0,0,0};
            int numOfColour=0;
            boolean repeatValue=false;

            for (int j=0; j < finalWindow.numColumn(); j++) {

                if (finalWindow.thereIsDie(i, j)) {

                    if (finalWindow.getDieColour(i, j).equals(Colour.RED)) {

                        counterColour[0] += 1;
                        numOfColour++;

                        if (counterColour[0] == 2) {

                            repeatValue = true;
                            break;
                        }
                    }

                    if (finalWindow.getDieColour(i, j).equals(Colour.GREEN)) {

                        counterColour[1] += 1;
                        numOfColour++;

                        if (counterColour[1] == 2) {

                            repeatValue = true;
                            break;
                        }
                    }

                    if (finalWindow.getDieColour(i, j).equals(Colour.PURPLE)) {

                        counterColour[2] += 1;
                        numOfColour++;

                        if (counterColour[2] == 2) {

                            repeatValue = true;
                            break;
                        }
                    }

                    if (finalWindow.getDieColour(i, j).equals(Colour.BLUE)) {

                        counterColour[3] += 1;
                        numOfColour++;

                        if (counterColour[3] == 2) {

                            repeatValue = true;
                            break;
                        }
                    }

                    if (finalWindow.getDieColour(i, j).equals(Colour.YELLOW)) {

                        counterColour[4] += 1;
                        numOfColour++;

                        if (counterColour[4] == 2) {

                            repeatValue = true;
                            break;
                        }
                    }
                }
            }
            if( !repeatValue && numOfColour==finalWindow.numColumn() ){   // if there are five dice and five different colour in row, incrise by one

                numRow++;

            }
        }

        score= 6*numRow;
        return score; // return 6 * number of Row whit 5 different colour of dice
    }
}
