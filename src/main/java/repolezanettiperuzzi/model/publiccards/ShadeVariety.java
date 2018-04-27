package repolezanettiperuzzi.model.publiccards;

import repolezanettiperuzzi.model.Value;
import repolezanettiperuzzi.model.Window;

import java.util.ArrayList;

public class ShadeVariety implements PublicCard {

    @Override
    public int effect(Window finalWindow){

        int [] counterValue= {0,0,0,0,0,0};
        int min=10000; //big number
        int score;

        for(int i=0;i<4;i++){

            for(int j=0;j<5;j++){  // count number of all value

                if( finalWindow.getDieValue(i,j).equals(Value.ONE)){

                    counterValue[0]+=1;

                }

                if( finalWindow.getDieValue(i,j).equals(Value.TWO)){

                    counterValue[1]+=1;

                }

                if( finalWindow.getDieValue(i,j).equals(Value.THREE)){

                    counterValue[2]+=1;

                }

                if( finalWindow.getDieValue(i,j).equals(Value.FOUR)){

                    counterValue[3]+=1;

                }

                if( finalWindow.getDieValue(i,j).equals(Value.FIVE)){

                    counterValue[4]+=1;

                }

                if( finalWindow.getDieValue(i,j).equals(Value.SIX)){

                    counterValue[5]+=1;

                }
            }
        }

        for(int i=0;i<6;i++){

            if(counterValue[i]<min){

                min=counterValue[i];

            }
        }

        if( min==10000){

            score=0;        // control in case no nut is inserted

        }
        else{

            score=5*min;

        }

        return score; // return 5 * min(numOne, numTwo, numThree, numFour, numFive, numSix)
    }
}
