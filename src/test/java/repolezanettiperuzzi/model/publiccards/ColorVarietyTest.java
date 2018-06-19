package repolezanettiperuzzi.model.publiccards;

import org.junit.Test;
import org.mockito.internal.matchers.Null;
import repolezanettiperuzzi.model.Colour;
import repolezanettiperuzzi.model.Value;
import repolezanettiperuzzi.model.Window;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ColorVarietyTest {


    @Test
    public void testEffect() {

        ColorVariety cardColourVariety= new ColorVariety();
        Window finalWindow=mock (Window.class);

        when(finalWindow.numRow()).thenReturn(4);
        when(finalWindow.numColumn()).thenReturn(5);
        when(finalWindow.getDieColour(0,0)).thenReturn(Colour.RED);
        when(finalWindow.getDieColour(0,1)).thenReturn(Colour.BLUE);
        when(finalWindow.getDieColour(0,2)).thenReturn(Colour.PURPLE);
        when(finalWindow.getDieColour(0,3)).thenReturn(Colour.YELLOW);
        when(finalWindow.getDieColour(0,4)).thenReturn(Colour.GREEN);

        for (int i=0;i<4;i++){

            for(int j=0;j<5;j++){

                when(finalWindow.thereIsDie(i,j)).thenReturn(true);

                if(i>0) {

                    when(finalWindow.getDieColour(i, j)).thenReturn(Colour.RED);

                }

            }
        }

        assertEquals(4, cardColourVariety.effect(finalWindow));
    }
}