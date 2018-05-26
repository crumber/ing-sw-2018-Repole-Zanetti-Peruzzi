package repolezanettiperuzzi.model.publiccards;

import org.junit.Test;
import repolezanettiperuzzi.model.Colour;
import repolezanettiperuzzi.model.Value;
import repolezanettiperuzzi.model.Window;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ColorDiagonalsTest {

    @Test
    public void testEffect() {

        ColorDiagonals cardColourDiagonals= new ColorDiagonals();
        Window finalWindow=mock (Window.class);
        for (int i=0;i<4;i++){

            for(int j=0;j<5;j++){

                when(finalWindow.getDieColour(i,j)).thenReturn(Colour.PURPLE);

            }
        }

        assertEquals(24, cardColourDiagonals.effect(finalWindow));
    }
}