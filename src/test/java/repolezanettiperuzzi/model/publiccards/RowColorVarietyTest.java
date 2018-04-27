package repolezanettiperuzzi.model.publiccards;

import org.junit.Test;
import repolezanettiperuzzi.model.Colour;
import repolezanettiperuzzi.model.Value;
import repolezanettiperuzzi.model.Window;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RowColorVarietyTest {

    @Test
    public void effect() {

        RowColorVariety cardRowColourVariety= new RowColorVariety();
        Window finalWindow=mock (Window.class);
        when(finalWindow.getDieColour(0,0)).thenReturn(Colour.YELLOW);
        when(finalWindow.getDieColour(0,1)).thenReturn(Colour.RED);
        when(finalWindow.getDieColour(0,2)).thenReturn(Colour.BLUE);
        when(finalWindow.getDieColour(0,3)).thenReturn(Colour.GREEN);
        when(finalWindow.getDieColour(0,4)).thenReturn(Colour.PURPLE);

        for (int i=1;i<4;i++){

            for(int j=0;j<5;j++){

                if(i==1){

                    when(finalWindow.getDieColour(i,j)).thenReturn(Colour.BLUE);

                }

                if(i==2){

                    when(finalWindow.getDieColour(i,j)).thenReturn(Colour.GREEN);

                }

                if(i==3){

                    when(finalWindow.getDieColour(i,j)).thenReturn(Colour.RED);

                }
            }
        }

        Window finalWindow2=mock (Window.class);
        when(finalWindow2.getDieColour(0,0)).thenReturn(Colour.YELLOW);
        when(finalWindow2.getDieColour(0,1)).thenReturn(Colour.RED);
        when(finalWindow2.getDieColour(0,2)).thenReturn(Colour.BLUE);
        when(finalWindow2.getDieColour(0,3)).thenReturn(Colour.GREEN);
        when(finalWindow2.getDieColour(0,4)).thenReturn(Colour.PURPLE);

        for (int i=1;i<4;i++){

            for(int j=0;j<5;j++){

                if(i==1){

                    when(finalWindow2.getDieColour(i,j)).thenReturn(Colour.PURPLE);

                }

                if(i==2){

                    when(finalWindow2.getDieColour(i,j)).thenReturn(Colour.YELLOW);

                }

                if(i==3){

                    when(finalWindow2.getDieColour(i,j)).thenReturn(Colour.RED);

                }
            }
        }

        assertEquals(6, cardRowColourVariety.effect(finalWindow));
        assertEquals(6, cardRowColourVariety.effect(finalWindow2));
    }
}