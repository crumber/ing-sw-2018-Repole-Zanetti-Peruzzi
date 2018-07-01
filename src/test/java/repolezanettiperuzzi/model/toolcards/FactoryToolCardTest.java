package repolezanettiperuzzi.model.toolcards;

import org.junit.Test;

import static org.junit.Assert.*;

public class FactoryToolCardTest {

    @Test
    public void testGetToolCardTest() {


        assertEquals(CopperFoilBurnisher.class,FactoryToolCard.getToolCard("tc1").getClass());
        assertEquals(CorkbackedStraightedge.class,FactoryToolCard.getToolCard("tc2").getClass());
        assertEquals(EglomiseBrush.class,FactoryToolCard.getToolCard("tc3").getClass());
        assertEquals(FluxBrush.class,FactoryToolCard.getToolCard("tc4").getClass());
        assertEquals(FluxRemover.class,FactoryToolCard.getToolCard("tc5").getClass());
        assertEquals(GlazingHammer.class,FactoryToolCard.getToolCard("tc6").getClass());
        assertEquals(GrindingStone.class,FactoryToolCard.getToolCard("tc7").getClass());
        assertEquals(GrozingPliers.class,FactoryToolCard.getToolCard("tc8").getClass());
        assertEquals(Lathekin.class,FactoryToolCard.getToolCard("tc9").getClass());
        assertEquals(LensCutter.class,FactoryToolCard.getToolCard("tc10").getClass());
        assertEquals(RunningPliers.class,FactoryToolCard.getToolCard("tc11").getClass());
        assertEquals(TapWheel.class,FactoryToolCard.getToolCard("tc12").getClass());
        FactoryToolCard factoryToolCard=new FactoryToolCard();
        assertNull(factoryToolCard.getToolCard("tc142"));

    }
}