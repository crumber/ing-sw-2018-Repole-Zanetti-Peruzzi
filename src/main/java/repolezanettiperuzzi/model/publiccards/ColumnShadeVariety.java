package repolezanettiperuzzi.model.publiccards;

import repolezanettiperuzzi.model.Value;
import repolezanettiperuzzi.model.Window;

/**
 * Classe che rappresenta la public card Column Shade Variety
 * @author Alessandro Peruzzi
 */
public class ColumnShadeVariety extends PublicCard {

    /**
     *
     * @param finalWindow window su cui calcolare il punteggio
     * @return numero colonne senza valori ripetuti
     */
    @Override
    public int effect(Window finalWindow){

        int numColumn=0;
        int score;

        for(int i=0;i<finalWindow.numColumn();i++) {  // count number of column with 4 different value of dice

            int [] counterValue= {0,0,0,0,0,0};
            int numOfValue=0;
            boolean noRepeatValue=true;

            for (int j=0; j < finalWindow.numRow(); j++) {

                if (finalWindow.thereIsDie(j, i)) {

                    if (finalWindow.getDieValue(j, i).equals(Value.ONE)) {

                        counterValue[0] += 1;
                        numOfValue++;

                        if (counterValue[0] == 2) {

                            noRepeatValue = false;
                            break;
                        }
                    }

                    if (finalWindow.getDieValue(j, i).equals(Value.TWO)) {

                        counterValue[1] += 1;
                        numOfValue++;

                        if (counterValue[1] == 2) {

                            noRepeatValue = false;
                            break;
                        }
                    }

                    if (finalWindow.getDieValue(j, i).equals(Value.THREE)) {

                        counterValue[2] += 1;
                        numOfValue++;

                        if (counterValue[2] == 2) {

                            noRepeatValue = false;
                            break;
                        }
                    }

                    if (finalWindow.getDieValue(j, i).equals(Value.FOUR)) {

                        counterValue[3] += 1;
                        numOfValue++;

                        if (counterValue[3] == 2) {

                            noRepeatValue = false;
                            break;
                        }
                    }

                    if (finalWindow.getDieValue(j, i).equals(Value.FIVE)) {

                        counterValue[4] += 1;
                        numOfValue++;

                        if (counterValue[4] == 2) {

                            noRepeatValue = false;
                            break;
                        }
                    }

                    if (finalWindow.getDieValue(j, i).equals(Value.SIX)) {

                        counterValue[5] += 1;
                        numOfValue++;

                        if (counterValue[5] == 2) {

                            noRepeatValue = false;
                            break;
                        }
                    }
                }
            }
            if( noRepeatValue && numOfValue==finalWindow.numRow() ){   // if there are four dice and four different value in column, incrise by one

                numColumn++;

            }
        }

        score= 4*numColumn;
        return score; // return 4 * number of Column whit 4 different value of dice
    }
}
