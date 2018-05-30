package repolezanettiperuzzi.model.publiccards;

        import org.junit.Test;
        import repolezanettiperuzzi.model.Value;
        import repolezanettiperuzzi.model.Window;

        import static org.junit.Assert.*;
        import static org.mockito.Mockito.mock;
        import static org.mockito.Mockito.when;

public class LightShadesTest {

    @Test
    public void testEffect() {

        LightShades cardLightShade= new LightShades();
        Window finalWindow=mock (Window.class);
        when(finalWindow.getDieValue(0,0)).thenReturn(Value.ONE);
        when(finalWindow.getDieValue(0,1)).thenReturn(Value.ONE);
        when(finalWindow.getDieValue(0,2)).thenReturn(Value.ONE);
        when(finalWindow.getDieValue(0,3)).thenReturn(Value.TWO);
        when(finalWindow.getDieValue(0,4)).thenReturn(Value.TWO);

        Window finalWindow2=mock (Window.class);
        when(finalWindow2.getDieValue(0,0)).thenReturn(Value.ONE);
        when(finalWindow2.getDieValue(0,1)).thenReturn(Value.ONE);
        when(finalWindow2.getDieValue(0,2)).thenReturn(Value.TWO);
        when(finalWindow2.getDieValue(0,3)).thenReturn(Value.TWO);
        when(finalWindow2.getDieValue(0,4)).thenReturn(Value.TWO);

        for (int i=0;i<4;i++){

            for(int j=0;j<5;j++){

                when(finalWindow.thereIsDie(i,j)).thenReturn(true);
                when(finalWindow2.thereIsDie(i,j)).thenReturn(true);

                if(i>0) {
                    when(finalWindow.getDieValue(i, j)).thenReturn(Value.FIVE);
                    when(finalWindow2.getDieValue(i, j)).thenReturn(Value.FIVE);
                }
            }
        }

        assertEquals(4, cardLightShade.effect(finalWindow));
        assertEquals(4, cardLightShade.effect(finalWindow2));
    }
}