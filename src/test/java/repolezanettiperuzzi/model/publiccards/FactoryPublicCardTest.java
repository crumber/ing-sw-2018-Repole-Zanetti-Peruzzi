package repolezanettiperuzzi.model.publiccards;

import org.junit.Test;
import repolezanettiperuzzi.model.toolcards.FactoryToolCard;

import static org.junit.Assert.*;

//test della factory delle public card
public class FactoryPublicCardTest {

    //testa che ritorni la classe corretta
    @Test
    public void testGetPublicCard() {

        assertEquals(ColorVariety.class,FactoryPublicCard.getPublicCard("pc1").getClass());
        assertEquals(ColorDiagonals.class,FactoryPublicCard.getPublicCard("pc2").getClass());
        assertEquals(ColumnColorVariety.class,FactoryPublicCard.getPublicCard("pc3").getClass());
        assertEquals(ColumnShadeVariety.class,FactoryPublicCard.getPublicCard("pc4").getClass());
        assertEquals(RowColorVariety.class,FactoryPublicCard.getPublicCard("pc5").getClass());
        assertEquals(RowShadeVariety.class,FactoryPublicCard.getPublicCard("pc6").getClass());
        assertEquals(ShadeVariety.class,FactoryPublicCard.getPublicCard("pc7").getClass());
        assertEquals(MediumShades.class,FactoryPublicCard.getPublicCard("pc8").getClass());
        assertEquals(LightShades.class,FactoryPublicCard.getPublicCard("pc9").getClass());
        FactoryPublicCard factoryPublicCard=new FactoryPublicCard();
        assertEquals(DeepShades.class,factoryPublicCard.getPublicCard("pc10").getClass());
        assertEquals(null,factoryPublicCard.getPublicCard("pc23"));
    }
}