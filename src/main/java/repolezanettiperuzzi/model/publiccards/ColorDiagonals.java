package repolezanettiperuzzi.model.publiccards;

import repolezanettiperuzzi.model.Window;

public class ColorDiagonals implements PublicCard {

    @Override
    public int effect(Window finalWindow){

        int score=0;
        int posRowControl;
        int posColumnControl;

        for(int i=0;i<3;i++){ // control part 1 diagonale da sinistra a destra

            posRowControl=i;

            for(int j=0;j<3;j++) {

                posColumnControl=j;

                if (i==0 && finalWindow.getDieColour(posRowControl, posColumnControl).equals(finalWindow.getDieColour(posRowControl + 1, posColumnControl + 1))) {

                    score++;

                }

                if (i==1 && j<2 && finalWindow.getDieColour(posRowControl, posColumnControl).equals(finalWindow.getDieColour( posRowControl + 1, posColumnControl + 1))) {

                    score++;

                }

                if (i==2 && j<1 && finalWindow.getDieColour(posRowControl, posColumnControl).equals(finalWindow.getDieColour( posRowControl + 1, posColumnControl + 1))) {

                    score++;

                }

                posRowControl++;
            }
        }

        for(int j=1;j<4;j++){ // control part 2 diagonale da sinistra a destra

            posColumnControl=j;

            for(int i=0;i<3;i++) {

                posRowControl=i;

                if (j==1 && finalWindow.getDieColour(posRowControl, posColumnControl).equals(finalWindow.getDieColour(posRowControl + 1, posColumnControl + 1))) {

                    score++;

                }

                if (j==2 && i<2 && finalWindow.getDieColour(posRowControl, posColumnControl).equals(finalWindow.getDieColour( posRowControl + 1, posColumnControl + 1))) {

                    score++;

                }

                if (j==3 && i<1 && finalWindow.getDieColour(posRowControl, posColumnControl).equals(finalWindow.getDieColour( posRowControl + 1, posColumnControl + 1))) {

                    score++;

                }

                posColumnControl++;
            }
        }



        for(int i=0;i<3;i++){ // control part 1 diagonale da destra a sinistra

            posRowControl=i;

            for(int j=4;j>1;j--) {

                posColumnControl=j;

                if (i==0 && finalWindow.getDieColour(posRowControl, posColumnControl).equals(finalWindow.getDieColour(posRowControl + 1, posColumnControl - 1))) {

                    score++;

                }

                if (i==1 && j>2 && finalWindow.getDieColour(posRowControl, posColumnControl).equals(finalWindow.getDieColour( posRowControl + 1, posColumnControl - 1))) {

                    score++;

                }

                if (i==2 && j>3 && finalWindow.getDieColour(posRowControl, posColumnControl).equals(finalWindow.getDieColour( posRowControl + 1, posColumnControl - 1))) {

                    score++;

                }

                posRowControl++;
            }
        }

        for(int j=3;j>0;j--){ // control part 2 diagonale da destra a sinistra

            posColumnControl=j;

            for(int i=0;i<3;i++) {

                posRowControl=i;

                if (j==3 && finalWindow.getDieColour(posRowControl, posColumnControl).equals(finalWindow.getDieColour(posRowControl + 1, posColumnControl - 1))) {

                    score++;

                }

                if (j==2 && i<2 && finalWindow.getDieColour(posRowControl, posColumnControl).equals(finalWindow.getDieColour( posRowControl + 1, posColumnControl - 1))) {

                    score++;

                }

                if (j==1 && i<1 && finalWindow.getDieColour(posRowControl, posColumnControl).equals(finalWindow.getDieColour( posRowControl + 1, posColumnControl - 1))) {

                    score++;

                }

                posColumnControl--;
            }
        }


        return score;
    }
}
