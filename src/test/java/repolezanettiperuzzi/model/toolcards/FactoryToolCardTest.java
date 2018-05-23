package repolezanettiperuzzi.model.toolcards;

import org.junit.Test;

import static org.junit.Assert.*;

public class FactoryToolCardTest {

    @Test
    public void getPublicCard() {

        assertEquals(CopperFoilBurnisher.class,FactoryToolCard.getPublicCard("tc1").getClass());
        assertEquals(CorkbackedStraightedge.class,FactoryToolCard.getPublicCard("tc2").getClass());
        assertEquals(EglomiseBrush.class,FactoryToolCard.getPublicCard("tc3").getClass());
        assertEquals(FluxBrush.class,FactoryToolCard.getPublicCard("tc4").getClass());
        assertEquals(FluxRemover.class,FactoryToolCard.getPublicCard("tc5").getClass());
        assertEquals(GlazingHammer.class,FactoryToolCard.getPublicCard("tc6").getClass());
        assertEquals(GrindingStone.class,FactoryToolCard.getPublicCard("tc7").getClass());
        assertEquals(GrozingPliers.class,FactoryToolCard.getPublicCard("tc8").getClass());
        assertEquals(Lathekin.class,FactoryToolCard.getPublicCard("tc9").getClass());
        assertEquals(LensCutter.class,FactoryToolCard.getPublicCard("tc10").getClass());
        assertEquals(RunningPliers.class,FactoryToolCard.getPublicCard("tc11").getClass());
        assertEquals(TapWheel.class,FactoryToolCard.getPublicCard("tc12").getClass());
        assertEquals(null,FactoryToolCard.getPublicCard("tc142"));

    }
}