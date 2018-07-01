package repolezanettiperuzzi.model;

import org.junit.Test;
import repolezanettiperuzzi.common.DynamicPath;
import repolezanettiperuzzi.model.publiccards.PublicCard;
import repolezanettiperuzzi.model.toolcards.Lathekin;
import repolezanettiperuzzi.model.toolcards.ToolCard;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DeckTest {

    Deck testDeck;

    @Test
    public void testDrawPublicCard() throws IOException {

        testDeck=new Deck("cards/publiccards","cards/toolcards");
        ArrayList<PublicCard> beforeDraw=(ArrayList<PublicCard>)testDeck.getPublicCardsDeck();
        assertEquals(10,beforeDraw.size());

        PublicCard cardPublic=testDeck.drawPublicCard();
        ArrayList<PublicCard> afterDraw=(ArrayList<PublicCard>)testDeck.getPublicCardsDeck();
        assertEquals(9,afterDraw.size());

    }

    @Test
    public void testDrawToolCard() throws IOException {

        testDeck = new Deck("cards/publiccards", "cards/toolcards");
        int nToolCard=0;

        for(int i=0; i<12;i++){

            ToolCard testToolCard=testDeck.drawToolCard();

            if(testToolCard.getId()>=0 && testToolCard.getId()<13){

                nToolCard++;

            }
        }

        assertEquals(12,nToolCard);
    }

    @Test
    public void testDeck() throws IOException{

        DynamicPath dP = mock(DynamicPath.class);
        when(dP.isJar()).thenReturn(true);

        testDeck= new Deck("cards/publiccards","cards/toolcards");


    }
}