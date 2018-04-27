package repolezanettiperuzzi.model.publiccards;

import org.junit.Test;
import repolezanettiperuzzi.model.Value;
import repolezanettiperuzzi.model.Window;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DeepShadesTest {


    @Test
    public void effect() {

        DeepShades cardDeepShade= new DeepShades();
        Window finalWindow=mock (Window.class);
        when(finalWindow.getDieValue(0,0)).thenReturn(Value.FIVE);
        when(finalWindow.getDieValue(0,1)).thenReturn(Value.FIVE);
        when(finalWindow.getDieValue(0,2)).thenReturn(Value.FIVE);
        when(finalWindow.getDieValue(0,3)).thenReturn(Value.SIX);
        when(finalWindow.getDieValue(0,4)).thenReturn(Value.SIX);

        for (int i=1;i<4;i++){

            for(int j=0;j<5;j++){

                when(finalWindow.getDieValue(i,j)).thenReturn(Value.ONE);

            }
        }

        Window finalWindow2=mock (Window.class);
        when(finalWindow2.getDieValue(0,0)).thenReturn(Value.FIVE);
        when(finalWindow2.getDieValue(0,1)).thenReturn(Value.FIVE);
        when(finalWindow2.getDieValue(0,2)).thenReturn(Value.SIX);
        when(finalWindow2.getDieValue(0,3)).thenReturn(Value.SIX);
        when(finalWindow2.getDieValue(0,4)).thenReturn(Value.SIX);

        for (int i=1;i<4;i++){

            for(int j=0;j<5;j++){

                when(finalWindow2.getDieValue(i,j)).thenReturn(Value.ONE);

            }
        }

        assertEquals(4, cardDeepShade.effect(finalWindow));
        assertEquals(4, cardDeepShade.effect(finalWindow2));
    }
}