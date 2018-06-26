package repolezanettiperuzzi.model.publiccards;

import repolezanettiperuzzi.model.Window;

public class ColorDiagonals extends PublicCard {

    @Override
    public int effect(Window finalWindow){

        int score=0;
        int posRowControl;
        int posColumnControl;
        int posRowControlNext;
        int posColumnControlNext;

        for(int i=0;i<finalWindow.numRow();i++){ // control part 1 diagonale da sinistra a destra

            posRowControl=i;
            posRowControlNext=i+1;

            for(int j=0;j<finalWindow.numColumn();j++) {

                posColumnControl=j;
                posColumnControlNext=j+1;

                if(finalWindow.thereIsDie(posRowControl,posColumnControl) && finalWindow.thereIsDie(posRowControlNext,posColumnControlNext)){

                    if (finalWindow.getDieColour(posRowControl, posColumnControl).equals(finalWindow.getDieColour(posRowControlNext, posColumnControlNext))) {

                        score++;

                    }
                }

                posRowControl++;
                posRowControlNext++;

            }
        }

        for(int j=1;j<finalWindow.numColumn();j++){ // control part 2 diagonale da sinistra a destra

            posColumnControl=j;
            posColumnControlNext=j+1;

            for(int i=0;i<finalWindow.numRow();i++) {

                posRowControl=i;
                posRowControlNext=i+1;

                if( finalWindow.thereIsDie(posRowControl,posColumnControl) && finalWindow.thereIsDie(posRowControlNext,posColumnControlNext)) {

                    if (finalWindow.getDieColour(posRowControl, posColumnControl).equals(finalWindow.getDieColour(posRowControlNext, posColumnControlNext))) {

                        score++;

                    }
                }

                posColumnControl++;
                posColumnControlNext++;
            }
        }



        for(int i=0;i<finalWindow.numRow();i++){ // control part 1 diagonale da destra a sinistra

            posRowControl=i;
            posRowControlNext=i+1;

            for(int j=finalWindow.numColumn()-1;j>0;j--) {

                posColumnControl=j;
                posColumnControlNext=j-1;

                if(finalWindow.thereIsDie(posRowControl,posColumnControl) && finalWindow.thereIsDie(posRowControlNext,posColumnControlNext)) {

                    if (finalWindow.getDieColour(posRowControl, posColumnControl).equals(finalWindow.getDieColour(posRowControlNext, posColumnControlNext))) {

                        score++;

                    }
                }
                posRowControl++;
                posRowControlNext++;
            }
        }

        for(int j=finalWindow.numColumn()-2;j>0;j--){ // control part 2 diagonale da destra a sinistra

            posColumnControl=j;
            posColumnControlNext=j-1;

            for(int i=0;i<finalWindow.numRow();i++) {

                posRowControl=i;
                posRowControlNext=i+1;

                if(finalWindow.thereIsDie(posRowControl,posColumnControl) && finalWindow.thereIsDie(posRowControlNext,posColumnControlNext)) {

                    if (finalWindow.getDieColour(posRowControl, posColumnControl).equals(finalWindow.getDieColour(posRowControlNext, posColumnControlNext))) {

                        score++;

                    }
                }

                posColumnControl--;
                posColumnControlNext--;
            }
        }


        return score;
    }
}
