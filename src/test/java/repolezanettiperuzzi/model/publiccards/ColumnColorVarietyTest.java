package repolezanettiperuzzi.model.publiccards;

import org.junit.Test;
import repolezanettiperuzzi.model.Colour;
import repolezanettiperuzzi.model.Value;
import repolezanettiperuzzi.model.Window;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

//testa che ritorni i punteggi corretti dovuti alle regole della public card
public class ColumnColorVarietyTest {

    @Test
    public void testEffect() {

        ColumnColorVariety cardColumnColourVariety= new ColumnColorVariety();
        Window finalWindow=mock (Window.class);
        Window finalWindowZero=mock (Window.class);

        when(finalWindow.numRow()).thenReturn(3);
        when(finalWindow.numColumn()).thenReturn(5);
        when(finalWindowZero.numRow()).thenReturn(3);
        when(finalWindowZero.numColumn()).thenReturn(5);
        when(finalWindow.getDieColour(0,0)).thenReturn(Colour.BLUE);
        when(finalWindow.getDieColour(1,0)).thenReturn(Colour.RED);
        when(finalWindow.getDieColour(2,0)).thenReturn(Colour.PURPLE);

        for (int j=0;j<5;j++){

            for(int i=0;i<3;i++){

                when(finalWindow.thereIsDie(i,j)).thenReturn(true);

                if(j==1){

                    when(finalWindow.getDieColour(i,j)).thenReturn(Colour.YELLOW);

                }

                if(j==2){

                    when(finalWindow.getDieColour(i,j)).thenReturn(Colour.RED);

                }

                if(j==3){

                    when(finalWindow.getDieColour(i,j)).thenReturn(Colour.BLUE);

                }

                if(j==4){

                    when(finalWindow.getDieColour(i,j)).thenReturn(Colour.PURPLE);

                }

            }
        }
        for (int j=0;j<5;j++){

            for(int i=0;i<3;i++){

                when(finalWindowZero.thereIsDie(i,j)).thenReturn(true);
                when(finalWindowZero.getDieColour(i,j)).thenReturn(Colour.GREEN);

            }
        }

        assertEquals(5, cardColumnColourVariety.effect(finalWindow));
        assertEquals(0, cardColumnColourVariety.effect(finalWindowZero));
    }
}