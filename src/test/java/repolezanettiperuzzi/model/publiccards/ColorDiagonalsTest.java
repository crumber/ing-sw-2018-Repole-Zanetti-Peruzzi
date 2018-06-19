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

        ColorDiagonals cardColourDiagonals = new ColorDiagonals();
        Window finalWindow = mock(Window.class);
        when(finalWindow.numRow()).thenReturn(4);
        when(finalWindow.numColumn()).thenReturn(5);
        for (int i = 0; i < 4; i++) {

            for (int j = 0; j < 5; j++) {

                when(finalWindow.thereIsDie(i, j)).thenReturn(true);
                when(finalWindow.getDieColour(i, j)).thenReturn(Colour.PURPLE);

            }
        }
        assertEquals(24, cardColourDiagonals.effect(finalWindow));


        Window finalWindow2 = mock(Window.class);
        when(finalWindow2.numRow()).thenReturn(4);
        when(finalWindow2.numColumn()).thenReturn(5);
        for (int i = 0; i < 4; i++) {

            for (int j = 0; j < 5; j++) {

                when(finalWindow2.thereIsDie(i, j)).thenReturn(false);
                when(finalWindow2.getDieColour(i, j)).thenReturn(Colour.PURPLE);

            }
        }
        assertEquals(0, cardColourDiagonals.effect(finalWindow2));


        Window finalWindow3 = mock(Window.class);
        when(finalWindow3.numRow()).thenReturn(4);
        when(finalWindow3.numColumn()).thenReturn(5);
        for (int i = 0; i < 4; i++) {

            for (int j = 0; j < 5; j++) {

                when(finalWindow.thereIsDie(i, j)).thenReturn(true);

                if (i == 0) {

                    when(finalWindow3.getDieColour(i, j)).thenReturn(Colour.PURPLE);

                }
                if (i == 1) {


                    when(finalWindow3.getDieColour(i, j)).thenReturn(Colour.RED);

                }
                if (i == 2) {

                    when(finalWindow3.getDieColour(i, j)).thenReturn(Colour.BLUE);

                }
                if (i == 3) {

                    when(finalWindow3.getDieColour(i, j)).thenReturn(Colour.YELLOW);

                }
            }
        }
        assertEquals(0, cardColourDiagonals.effect(finalWindow3));


        Window finalWindow4 = mock(Window.class);
        when(finalWindow4.numRow()).thenReturn(4);
        when(finalWindow4.numColumn()).thenReturn(5);
        for (int i = 0; i < 4; i++) {

            for (int j = 0; j < 5; j++) {

                if (i == 0) {

                    when(finalWindow4.thereIsDie(i, j)).thenReturn(false);
                    when(finalWindow4.getDieColour(i, j)).thenReturn(Colour.PURPLE);

                }
                if (i == 1) {

                    when(finalWindow4.thereIsDie(i, j)).thenReturn(true);
                    when(finalWindow4.getDieColour(i, j)).thenReturn(Colour.RED);

                }
                if (i == 2) {

                    when(finalWindow4.thereIsDie(i, j)).thenReturn(false);
                    when(finalWindow4.getDieColour(i, j)).thenReturn(Colour.BLUE);

                }
                if (i == 3) {

                    when(finalWindow4.thereIsDie(i, j)).thenReturn(true);
                    when(finalWindow4.getDieColour(i, j)).thenReturn(Colour.YELLOW);

                }
            }
        }
        assertEquals(0, cardColourDiagonals.effect(finalWindow4));


        Window finalWindow5 = mock(Window.class);
        when(finalWindow5.numRow()).thenReturn(4);
        when(finalWindow5.numColumn()).thenReturn(5);
        for (int i = 0; i < 4; i++) {

            for (int j = 0; j < 5; j++) {

                when(finalWindow5.thereIsDie(i, j)).thenReturn(true);

                if (i == 0) {

                    when(finalWindow5.getDieColour(i, j)).thenReturn(Colour.PURPLE);

                }
                if (i == 1) {

                    when(finalWindow5.getDieColour(i, j)).thenReturn(Colour.RED);

                }
                if (i == 2) {

                    when(finalWindow5.getDieColour(i, j)).thenReturn(Colour.RED);

                }
                if (i == 3) {

                    when(finalWindow5.getDieColour(i, j)).thenReturn(Colour.BLUE);

                }
            }
        }
        assertEquals(8, cardColourDiagonals.effect(finalWindow5));


        Window finalWindow6 = mock(Window.class);
        when(finalWindow6.numRow()).thenReturn(4);
        when(finalWindow6.numColumn()).thenReturn(5);
        for (int i = 0; i < 4; i++) {

            for (int j = 0; j < 5; j++) {

                when(finalWindow6.thereIsDie(i, j)).thenReturn(true);

                if (i == 0) {

                    when(finalWindow6.getDieColour(i, j)).thenReturn(Colour.PURPLE);

                }
                if (i == 1) {

                    when(finalWindow6.getDieColour(i, j)).thenReturn(Colour.PURPLE);

                }
                if (i == 2) {

                    when(finalWindow6.getDieColour(i, j)).thenReturn(Colour.BLUE);

                }
                if (i == 3) {

                    when(finalWindow6.getDieColour(i, j)).thenReturn(Colour.BLUE);

                }
            }
        }
        assertEquals(16, cardColourDiagonals.effect(finalWindow6));


        Window finalWindow7 = mock(Window.class);
        when(finalWindow7.numRow()).thenReturn(4);
        when(finalWindow7.numColumn()).thenReturn(5);
        for (int i = 0; i < 4; i++) {

            for (int j = 0; j < 5; j++) {

                when(finalWindow7.thereIsDie(i, j)).thenReturn(true);

                if (j == 0) {

                    when(finalWindow7.getDieColour(i, j)).thenReturn(Colour.PURPLE);

                }
                if (j == 1) {

                    when(finalWindow7.getDieColour(i, j)).thenReturn(Colour.PURPLE);

                }
                if (j == 2) {

                    when(finalWindow7.getDieColour(i, j)).thenReturn(Colour.BLUE);

                }
                if (j == 3) {

                    when(finalWindow7.getDieColour(i, j)).thenReturn(Colour.GREEN);

                }
                if (j == 4) {

                    when(finalWindow7.getDieColour(i, j)).thenReturn(Colour.GREEN);

                }
            }
        }
        assertEquals(12, cardColourDiagonals.effect(finalWindow7));


        Window finalWindow8 = mock(Window.class);
        when(finalWindow8.numRow()).thenReturn(4);
        when(finalWindow8.numColumn()).thenReturn(5);
        for (int i = 0; i < 4; i++) {

            for (int j = 0; j < 5; j++) {

                when(finalWindow8.thereIsDie(i, j)).thenReturn(false);

                if (i == 0) {

                    when(finalWindow8.getDieColour(i, j)).thenReturn(Colour.PURPLE);

                }
                if (i == 1) {

                    when(finalWindow8.getDieColour(i, j)).thenReturn(Colour.RED);

                }
                if (i == 2) {

                    when(finalWindow8.getDieColour(i, j)).thenReturn(Colour.BLUE);

                }
                if (i == 3) {

                    when(finalWindow8.getDieColour(i, j)).thenReturn(Colour.GREEN);

                }
            }
        }
        assertEquals(0, cardColourDiagonals.effect(finalWindow8));


        Window finalWindow9 = mock(Window.class);
        when(finalWindow9.numRow()).thenReturn(11);
        when(finalWindow9.numColumn()).thenReturn(8);
        for (int i = 0; i < 11; i++) {

            for (int j = 0; j < 8; j++) {

                when(finalWindow9.thereIsDie(i, j)).thenReturn(true);
                when(finalWindow9.getDieColour(i, j)).thenReturn(Colour.BLUE);

            }
        }
        assertEquals(140, cardColourDiagonals.effect(finalWindow9));

    }
}