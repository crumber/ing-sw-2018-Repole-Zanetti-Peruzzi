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

        //window con prima riga con dadi di valori tutti diversi mentre le altre righe con dadi dello stesso valore(1,2,3)
        RowShadeVariety cardRowShadeVariety= new RowShadeVariety();
        Window finalWindow=mock (Window.class);
        when(finalWindow.numRow()).thenReturn(4);
        when(finalWindow.numColumn()).thenReturn(5);
        when(finalWindow.getDieValue(0,0)).thenReturn(Value.ONE);
        when(finalWindow.getDieValue(0,1)).thenReturn(Value.THREE);
        when(finalWindow.getDieValue(0,2)).thenReturn(Value.FIVE);
        when(finalWindow.getDieValue(0,3)).thenReturn(Value.FOUR);
        when(finalWindow.getDieValue(0,4)).thenReturn(Value.TWO);

        for (int i=0;i<4;i++){

            for(int j=0;j<5;j++){

                when(finalWindow.thereIsDie(i,j)).thenReturn(true);

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

        //window con prima riga con dadi di valori tutti diversi mentre le altre righe con dadi dello stesso valore(4,5,6)
        Window finalWindow2=mock (Window.class);
        when(finalWindow2.numRow()).thenReturn(4);
        when(finalWindow2.numColumn()).thenReturn(5);
        when(finalWindow2.getDieValue(0,0)).thenReturn(Value.ONE);
        when(finalWindow2.getDieValue(0,1)).thenReturn(Value.THREE);
        when(finalWindow2.getDieValue(0,2)).thenReturn(Value.FIVE);
        when(finalWindow2.getDieValue(0,3)).thenReturn(Value.FOUR);
        when(finalWindow2.getDieValue(0,4)).thenReturn(Value.TWO);

        for (int i=0;i<4;i++){

            for(int j=0;j<5;j++){

                when(finalWindow2.thereIsDie(i,j)).thenReturn(true);

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