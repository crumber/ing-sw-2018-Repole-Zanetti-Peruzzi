package repolezanettiperuzzi.model.publiccards;

        import org.junit.Test;
        import repolezanettiperuzzi.model.Value;
        import repolezanettiperuzzi.model.Window;

        import static org.junit.Assert.*;
        import static org.mockito.Mockito.mock;
        import static org.mockito.Mockito.when;

public class LightShadesTest {

    @Test
    public void effect() {

        LightShades cardLightShade= new LightShades();
        Window finalWindow=mock (Window.class);
        when(finalWindow.getDieValue(0,0)).thenReturn(Value.ONE);
        when(finalWindow.getDieValue(0,1)).thenReturn(Value.ONE);
        when(finalWindow.getDieValue(0,2)).thenReturn(Value.ONE);
        when(finalWindow.getDieValue(0,3)).thenReturn(Value.TWO);
        when(finalWindow.getDieValue(0,4)).thenReturn(Value.TWO);

        for (int i=1;i<4;i++){

            for(int j=0;j<5;j++){

                when(finalWindow.getDieValue(i,j)).thenReturn(Value.SIX);

            }
        }

        Window finalWindow2=mock (Window.class);
        when(finalWindow2.getDieValue(0,0)).thenReturn(Value.ONE);
        when(finalWindow2.getDieValue(0,1)).thenReturn(Value.ONE);
        when(finalWindow2.getDieValue(0,2)).thenReturn(Value.TWO);
        when(finalWindow2.getDieValue(0,3)).thenReturn(Value.TWO);
        when(finalWindow2.getDieValue(0,4)).thenReturn(Value.TWO);

        for (int i=1;i<4;i++){

            for(int j=0;j<5;j++){

                when(finalWindow2.getDieValue(i,j)).thenReturn(Value.SIX);

            }
        }

        assertEquals(4, cardLightShade.effect(finalWindow));
        assertEquals(4, cardLightShade.effect(finalWindow2));
    }
}