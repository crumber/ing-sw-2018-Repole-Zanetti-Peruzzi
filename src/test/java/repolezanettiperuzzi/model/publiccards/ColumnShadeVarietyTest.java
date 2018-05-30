package repolezanettiperuzzi.model.publiccards;

import org.junit.Test;
import repolezanettiperuzzi.model.Value;
import repolezanettiperuzzi.model.Window;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ColumnShadeVarietyTest {

    @Test
    public void testEffect() {

        ColumnShadeVariety cardColumnShadeVariety= new ColumnShadeVariety();
        Window finalWindow=mock (Window.class);
        Window finalWindowZero=mock (Window.class);
        when(finalWindow.getDieValue(0,0)).thenReturn(Value.ONE);
        when(finalWindow.getDieValue(1,0)).thenReturn(Value.THREE);
        when(finalWindow.getDieValue(2,0)).thenReturn(Value.FIVE);
        when(finalWindow.getDieValue(3,0)).thenReturn(Value.TWO);

        for (int j=0;j<5;j++){

            for(int i=0;i<4;i++){

                when(finalWindow.thereIsDie(i,j)).thenReturn(true);

                if(j==1){

                    when(finalWindow.getDieValue(i,j)).thenReturn(Value.ONE);

                }

                if(j==2){

                    when(finalWindow.getDieValue(i,j)).thenReturn(Value.TWO);

                }

                if(j==3){

                    when(finalWindow.getDieValue(i,j)).thenReturn(Value.THREE);

                }

                if(j==4){

                    when(finalWindow.getDieValue(i,j)).thenReturn(Value.FOUR);

                }

            }
        }

        for (int j=0;j<5;j++){

            for(int i=0;i<4;i++){

                if(j<2){

                    when(finalWindowZero.thereIsDie(i,j)).thenReturn(true);
                    when(finalWindowZero.getDieValue(i,j)).thenReturn(Value.FIVE);

                }

                if(j>=2){

                    when(finalWindowZero.thereIsDie(i,j)).thenReturn(true);
                    when(finalWindowZero.getDieValue(i,j)).thenReturn(Value.SIX);

                }
            }
        }

        assertEquals(4, cardColumnShadeVariety.effect(finalWindow));
        assertEquals(0, cardColumnShadeVariety.effect(finalWindowZero));
    }
}