package repolezanettiperuzzi.model.publiccards;

import repolezanettiperuzzi.model.Window;

import java.util.ArrayList;

/**
 * Classe che rappresenta la public card Color Diagonals
 * @author Alessandro Peruzzi
 */
public class ColorDiagonals extends PublicCard {

    /**
     * @param finalWindow window su cui calcolare il punteggio
     * @return ritorna il numero di dadi dello stesso colore diagonalmente adiacenti
     */
    @Override
    public int effect(Window finalWindow){

        int posRowControl;
        int posColumnControl;
        int posRowControlNext;
        int posColumnControlNext;
        boolean firstCouple;
        int score=0;
        ArrayList<Object> temp=new ArrayList<>();

        for(int i=0;i<finalWindow.numRow();i++){ // control part 1 diagonale da sinistra a destra

            posRowControl=i;
            posRowControlNext=i+1;
            firstCouple=true;

            for(int j=0;j<finalWindow.numColumn();j++) {

                posColumnControl=j;
                posColumnControlNext=j+1;

                temp=incrementScore(finalWindow,posRowControl,posColumnControl,posRowControlNext,posColumnControlNext,firstCouple);
                score+=(Integer)temp.get(0);
                firstCouple=(Boolean)temp.get(1);

                posRowControl++;
                posRowControlNext++;

            }
        }

        for(int j=1;j<finalWindow.numColumn();j++){ // control part 2 diagonale da sinistra a destra

            posColumnControl=j;
            posColumnControlNext=j+1;
            firstCouple=true;


            for(int i=0;i<finalWindow.numRow();i++) {

                posRowControl=i;
                posRowControlNext=i+1;

                temp=incrementScore(finalWindow,posRowControl,posColumnControl,posRowControlNext,posColumnControlNext,firstCouple);
                score+=(Integer)temp.get(0);
                firstCouple=(Boolean)temp.get(1);

                posColumnControl++;
                posColumnControlNext++;
            }
        }



        for(int i=0;i<finalWindow.numRow();i++){ // control part 1 diagonale da destra a sinistra

            posRowControl=i;
            posRowControlNext=i+1;
            firstCouple=true;


            for(int j=finalWindow.numColumn()-1;j>0;j--) {

                posColumnControl=j;
                posColumnControlNext=j-1;

                temp=incrementScore(finalWindow,posRowControl,posColumnControl,posRowControlNext,posColumnControlNext,firstCouple);
                score+=(Integer)temp.get(0);
                firstCouple=(Boolean)temp.get(1);

                posRowControl++;
                posRowControlNext++;
            }
        }

        for(int j=finalWindow.numColumn()-2;j>0;j--){ // control part 2 diagonale da destra a sinistra

            posColumnControl=j;
            posColumnControlNext=j-1;
            firstCouple=true;


            for(int i=0;i<finalWindow.numRow();i++) {

                posRowControl=i;
                posRowControlNext=i+1;

                temp=incrementScore(finalWindow,posRowControl,posColumnControl,posRowControlNext,posColumnControlNext,firstCouple);
                score+=(Integer)temp.get(0);
                firstCouple=(Boolean)temp.get(1);

                posColumnControl--;
                posColumnControlNext--;
            }
        }


        return score;
    }


    public ArrayList<Object> incrementScore(Window finalWindow, int posRowControl, int posColumnControl, int posRowControlNext, int posColumnControlNext, boolean firstCouple){

        ArrayList<Object> result=new ArrayList<>();
        int score=0;

        if(finalWindow.thereIsDie(posRowControl,posColumnControl) && finalWindow.thereIsDie(posRowControlNext,posColumnControlNext)){

            if (finalWindow.getDieColour(posRowControl, posColumnControl).equals(finalWindow.getDieColour(posRowControlNext, posColumnControlNext))) {

                score++;

                if(firstCouple){

                    score++;
                    firstCouple=false;

                }
            }
            else {

                firstCouple=true;

            }
        }

        result.add(score);
        result.add(firstCouple);

        return result;
    }

}
