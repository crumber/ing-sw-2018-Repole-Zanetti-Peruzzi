package repolezanettiperuzzi.model.publiccards;

import org.junit.Test;
import repolezanettiperuzzi.model.Value;
import repolezanettiperuzzi.model.Window;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RowShadeVarietyTest {


    @Test
    public void testEffect() {

        RowShadeVariety cardRowShadeVariety= new RowShadeVariety();
        Window finalWindow=mock (Window.class);
        when(finalWindow.getDieValue(0,0)).thenReturn(Value.ONE);
        when(finalWindow.getDieValue(0,1)).thenReturn(Value.THREE);
        when(finalWindow.getDieValue(0,2)).thenReturn(Value.FIVE);
        when(finalWindow.getDieValue(0,3)).thenReturn(Value.FOUR);
        when(finalWindow.getDieValue(0,4)).thenReturn(Value.TWO);

        for (int i=1;i<4;i++){

            for(int j=0;j<5;j++){

                if(i==1){

                    when(finalWindow.getDieValue(i,j)).thenReturn(Value.ONE);

                }

                if(i==2){

                    when(finalWindow.getDieValue(i,j)).thenReturn(Value.TWO);

                }
                if(i==3){

                    when(finalWindow.getDieValue(i,j)).thenReturn(Value.THREE);

                }
            }
        }

        Window finalWindow2=mock (Window.class);
        when(finalWindow2.getDieValue(0,0)).thenReturn(Value.ONE);
        when(finalWindow2.getDieValue(0,1)).thenReturn(Value.THREE);
        when(finalWindow2.getDieValue(0,2)).thenReturn(Value.FIVE);
        when(finalWindow2.getDieValue(0,3)).thenReturn(Value.FOUR);
        when(finalWindow2.getDieValue(0,4)).thenReturn(Value.TWO);

        for (int i=1;i<4;i++){

            for(int j=0;j<5;j++){

                if(i==1){

                    when(finalWindow2.getDieValue(i,j)).thenReturn(Value.FOUR);

                }

                if(i==2){

                    when(finalWindow2.getDieValue(i,j)).thenReturn(Value.FIVE);

                }
                if(i==3){

                    when(finalWindow2.getDieValue(i,j)).thenReturn(Value.SIX);

                }
            }
        }

        assertEquals(5, cardRowShadeVariety.effect(finalWindow));
        assertEquals(5, cardRowShadeVariety.effect(finalWindow2));
    }
}