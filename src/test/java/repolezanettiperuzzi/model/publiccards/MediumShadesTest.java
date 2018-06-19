package repolezanettiperuzzi.model.publiccards;

import org.junit.Test;
import repolezanettiperuzzi.model.Value;
import repolezanettiperuzzi.model.Window;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MediumShadesTest {


    @Test
    public void testEffect() {

        MediumShades cardMediumShade= new MediumShades();
        Window finalWindow=mock (Window.class);
        when(finalWindow.numRow()).thenReturn(4);
        when(finalWindow.numColumn()).thenReturn(5);
        when(finalWindow.getDieValue(0,0)).thenReturn(Value.THREE);
        when(finalWindow.getDieValue(0,1)).thenReturn(Value.THREE);
        when(finalWindow.getDieValue(0,2)).thenReturn(Value.THREE);
        when(finalWindow.getDieValue(0,3)).thenReturn(Value.FOUR);
        when(finalWindow.getDieValue(0,4)).thenReturn(Value.FOUR);

        Window finalWindow2=mock (Window.class);
        when(finalWindow2.numRow()).thenReturn(4);
        when(finalWindow2.numColumn()).thenReturn(5);
        when(finalWindow2.getDieValue(0,0)).thenReturn(Value.THREE);
        when(finalWindow2.getDieValue(0,1)).thenReturn(Value.THREE);
        when(finalWindow2.getDieValue(0,2)).thenReturn(Value.FOUR);
        when(finalWindow2.getDieValue(0,3)).thenReturn(Value.FOUR);
        when(finalWindow2.getDieValue(0,4)).thenReturn(Value.FOUR);

        for (int i=0;i<4;i++){

            for(int j=0;j<5;j++){

                when(finalWindow.thereIsDie(i,j)).thenReturn(true);
                when(finalWindow2.thereIsDie(i,j)).thenReturn(true);

                if(i>0) {

                    when(finalWindow.getDieValue(i, j)).thenReturn(Value.SIX);
                    when(finalWindow2.getDieValue(i,j)).thenReturn(Value.SIX);

                }
            }
        }

        assertEquals(4,cardMediumShade.effect(finalWindow));
        assertEquals(4,cardMediumShade.effect(finalWindow2));
    }
}