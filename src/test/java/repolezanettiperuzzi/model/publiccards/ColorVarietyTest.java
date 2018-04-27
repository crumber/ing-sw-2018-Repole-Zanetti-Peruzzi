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
    public void effect() {

        ColorVariety cardColourVariety= new ColorVariety();
        Window finalWindow=mock (Window.class);
        Window zeroFinalWindow=mock (Window.class);
        when(finalWindow.getDieColour(0,0)).thenReturn(Colour.RED);
        when(finalWindow.getDieColour(0,1)).thenReturn(Colour.BLUE);
        when(finalWindow.getDieColour(0,2)).thenReturn(Colour.PURPLE);
        when(finalWindow.getDieColour(0,3)).thenReturn(Colour.YELLOW);
        when(finalWindow.getDieColour(0,4)).thenReturn(Colour.GREEN);

        for (int i=1;i<4;i++){

            for(int j=0;j<5;j++){

                when(finalWindow.getDieColour(i,j)).thenReturn(Colour.RED);

            }
        }

        assertEquals(4, cardColourVariety.effect(finalWindow));
    }
}