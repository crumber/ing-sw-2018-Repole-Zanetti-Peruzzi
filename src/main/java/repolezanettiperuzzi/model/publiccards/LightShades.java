package repolezanettiperuzzi.model.publiccards;

import repolezanettiperuzzi.model.Value;
import repolezanettiperuzzi.model.Window;

public class LightShades implements PublicCard {

    @Override
    public int effect(Window finalWindow){

        int numOne=0;
        int numTwo=0;
        int score;

        for(int i=0;i<4;i++){ // count number of one and two

            for(int j=0;j<5;j++){

                if( finalWindow.getDieValue(i,j).equals(Value.ONE)){

                    numOne++;

                }

                if( finalWindow.getDieValue(i,j).equals(Value.TWO)){

                    numTwo++;

                }
            }
        }

        if(numOne<numTwo){

            score= 2*numOne;

        }
        else{

            score= 2*numTwo;
        }

        return score; // return 2 * min(numOne, numTwo)
    }
}

