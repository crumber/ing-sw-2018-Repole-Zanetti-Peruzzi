package repolezanettiperuzzi.model.publiccards;

import org.junit.Test;
import repolezanettiperuzzi.model.Value;
import repolezanettiperuzzi.model.Window;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MediumShadesTest {


    @Test
    public void effect() {

        MediumShades cardMediumShade= new MediumShades();
        Window finalWindow=mock (Window.class);
        when(finalWindow.getDieValue(0,0)).thenReturn(Value.THREE);
        when(finalWindow.getDieValue(0,1)).thenReturn(Value.THREE);
        when(finalWindow.getDieValue(0,2)).thenReturn(Value.THREE);
        when(finalWindow.getDieValue(0,3)).thenReturn(Value.FOUR);
        when(finalWindow.getDieValue(0,4)).thenReturn(Value.FOUR);

        for (int i=1;i<4;i++){

            for(int j=0;j<5;j++){

                when(finalWindow.getDieValue(i,j)).thenReturn(Value.SIX);

            }
        }

        Window finalWindow2=mock (Window.class);
        when(finalWindow2.getDieValue(0,0)).thenReturn(Value.THREE);
        when(finalWindow2.getDieValue(0,1)).thenReturn(Value.THREE);
        when(finalWindow2.getDieValue(0,2)).thenReturn(Value.FOUR);
        when(finalWindow2.getDieValue(0,3)).thenReturn(Value.FOUR);
        when(finalWindow2.getDieValue(0,4)).thenReturn(Value.FOUR);

        for (int i=1;i<4;i++){

            for(int j=0;j<5;j++){

                when(finalWindow2.getDieValue(i,j)).thenReturn(Value.SIX);

            }
        }
        assertEquals(4,cardMediumShade.effect(finalWindow));
        assertEquals(4,cardMediumShade.effect(finalWindow2));
    }
}