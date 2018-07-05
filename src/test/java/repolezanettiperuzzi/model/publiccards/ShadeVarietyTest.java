package repolezanettiperuzzi.model.publiccards;

import org.junit.Test;
import repolezanettiperuzzi.model.Value;
import repolezanettiperuzzi.model.Window;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

//testa che ritorni i punteggi corretti dovuti alle regole della public card
public class ShadeVarietyTest {


    @Test
    public void testEffect() {

        ShadeVariety cardShadeVariety= new ShadeVariety();
        Window finalWindow=mock (Window.class);
        when(finalWindow.numRow()).thenReturn(4);
        when(finalWindow.numColumn()).thenReturn(5);
        when(finalWindow.getDieValue(0,0)).thenReturn(Value.ONE);
        when(finalWindow.getDieValue(0,1)).thenReturn(Value.TWO);
        when(finalWindow.getDieValue(0,2)).thenReturn(Value.THREE);
        when(finalWindow.getDieValue(0,3)).thenReturn(Value.FOUR);
        when(finalWindow.getDieValue(0,4)).thenReturn(Value.FIVE);

        for (int i=0;i<4;i++){

            for(int j=0;j<5;j++){

                when(finalWindow.thereIsDie(i,j)).thenReturn(true);

                if(i>0) {

                    when(finalWindow.getDieValue(i, j)).thenReturn(Value.SIX);

                }
            }
        }

        assertEquals(5, cardShadeVariety.effect(finalWindow));
    }
}