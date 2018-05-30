package repolezanettiperuzzi.model.publiccards;

import repolezanettiperuzzi.model.Window;

public class ColorDiagonals implements PublicCard {

    @Override
    public int effect(Window finalWindow){

        int score=0;
        int posRowControl;
        int posColumnControl;
        int posRowControlNext;
        int posColumnControlNext;

        for(int i=0;i<3;i++){ // control part 1 diagonale da sinistra a destra

            posRowControl=i;
            posRowControlNext=i+1;

            for(int j=0;j<3;j++) {

                posColumnControl=j;
                posColumnControlNext=j+1;

                if(posColumnControl<3 && posRowControl<3 && finalWindow.thereIsDie(posRowControl,posColumnControl) && finalWindow.thereIsDie(posRowControlNext,posColumnControlNext)){

                    if (i==0 && finalWindow.getDieColour(posRowControl, posColumnControl).equals(finalWindow.getDieColour(posRowControlNext, posColumnControlNext))) {

                        score++;

                    }

                    if (i==1 && j<2 && finalWindow.getDieColour(posRowControl, posColumnControl).equals(finalWindow.getDieColour( posRowControlNext, posColumnControlNext))) {

                        score++;

                    }

                    if (i==2 && j<1 && finalWindow.getDieColour(posRowControl, posColumnControl).equals(finalWindow.getDieColour( posRowControlNext, posColumnControlNext))) {

                        score++;

                    }
                }

                posRowControl++;
                posRowControlNext++;
            }
        }

        for(int j=1;j<4;j++){ // control part 2 diagonale da sinistra a destra

            posColumnControl=j;
            posColumnControlNext=j+1;

            for(int i=0;i<3;i++) {

                posRowControl=i;
                posRowControlNext=i+1;

                if(posColumnControl<4 && posRowControl<3 && finalWindow.thereIsDie(posRowControl,posColumnControl) && finalWindow.thereIsDie(posRowControlNext,posColumnControlNext)) {

                    if (j == 1 && finalWindow.getDieColour(posRowControl, posColumnControl).equals(finalWindow.getDieColour(posRowControlNext, posColumnControlNext))) {

                        score++;

                    }

                    if (j == 2 && i < 2 && finalWindow.getDieColour(posRowControl, posColumnControl).equals(finalWindow.getDieColour(posRowControlNext, posColumnControlNext))) {

                        score++;

                    }

                    if (j == 3 && i < 1 && finalWindow.getDieColour(posRowControl, posColumnControl).equals(finalWindow.getDieColour(posRowControlNext, posColumnControlNext))) {

                        score++;

                    }
                }

                posColumnControl++;
                posColumnControlNext++;
            }
        }



        for(int i=0;i<3;i++){ // control part 1 diagonale da destra a sinistra

            posRowControl=i;
            posRowControlNext=i+1;

            for(int j=4;j>1;j--) {

                posColumnControl=j;
                posColumnControlNext=j-1;

                if(posColumnControl>1 && posRowControl<3 && finalWindow.thereIsDie(posRowControl,posColumnControl) && finalWindow.thereIsDie(posRowControlNext,posColumnControlNext)) {

                    if (i == 0 && finalWindow.getDieColour(posRowControl, posColumnControl).equals(finalWindow.getDieColour(posRowControlNext, posColumnControlNext))) {

                        score++;

                    }

                    if (i == 1 && j > 2 && finalWindow.getDieColour(posRowControl, posColumnControl).equals(finalWindow.getDieColour(posRowControlNext, posColumnControlNext))) {

                        score++;

                    }

                    if (i == 2 && j > 3 && finalWindow.getDieColour(posRowControl, posColumnControl).equals(finalWindow.getDieColour(posRowControlNext, posColumnControlNext))) {

                        score++;

                    }
                }
                posRowControl++;
                posRowControlNext++;
            }
        }

        for(int j=3;j>0;j--){ // control part 2 diagonale da destra a sinistra

            posColumnControl=j;
            posColumnControlNext=j-1;

            for(int i=0;i<3;i++) {

                posRowControl=i;
                posRowControlNext=i+1;

                if(posColumnControl>0 && posRowControl<3 && finalWindow.thereIsDie(posRowControl,posColumnControl) && finalWindow.thereIsDie(posRowControlNext,posColumnControlNext)) {

                    if (j == 3 && finalWindow.getDieColour(posRowControl, posColumnControl).equals(finalWindow.getDieColour(posRowControlNext, posColumnControlNext))) {

                        score++;

                    }

                    if (j == 2 && i < 2 && finalWindow.getDieColour(posRowControl, posColumnControl).equals(finalWindow.getDieColour(posRowControlNext, posColumnControlNext))) {

                        score++;

                    }

                    if (j == 1 && i < 1 && finalWindow.getDieColour(posRowControl, posColumnControl).equals(finalWindow.getDieColour(posRowControlNext, posColumnControlNext))) {

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
