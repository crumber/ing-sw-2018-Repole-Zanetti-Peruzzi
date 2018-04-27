package repolezanettiperuzzi.model.publiccards;
import repolezanettiperuzzi.model.Colour;
import repolezanettiperuzzi.model.Window;

public class ColorVariety implements PublicCard {

    @Override
    public int effect(Window finalWindow){

        int [] counterColour= {0,0,0,0,0};
        int min=10000; //big number
        int score;

        for(int i=0;i<4;i++){

            for(int j=0;j<5;j++){  // count number of all colour

                if( finalWindow.getDieColour(i,j).equals(Colour.RED)){

                    counterColour[0]+=1;

                }

                if( finalWindow.getDieColour(i,j).equals(Colour.PURPLE)){

                    counterColour[1]+=1;

                }

                if( finalWindow.getDieColour(i,j).equals(Colour.GREEN)){

                    counterColour[2]+=1;

                }

                if( finalWindow.getDieColour(i,j).equals(Colour.BLUE)){

                    counterColour[3]+=1;

                }

                if( finalWindow.getDieColour(i,j).equals(Colour.YELLOW)){

                    counterColour[4]+=1;

                }
            }
        }

        for(int i=0;i<5;i++){

            if(counterColour[i]<min){

                min=counterColour[i];

            }
        }

        score= 4*min;
        return score; // return 4 * min( red, green, purple, blue, yellow)
    }
}