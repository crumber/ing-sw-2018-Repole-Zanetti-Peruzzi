package RepoleZanettiPeruzzi.Model;

import org.junit.Test;

import static org.junit.Assert.*;

public class BoxTest {

    @Test
    public void testGetBoundColourNull(){

        Box testBox= new Box();
        assertNull(testBox.getBoundColour());

    }

    @Test
    public void testGetBoundColour(){

        Box testBox = new Box(Colour.RED);
        assertEquals(Colour.RED,testBox.getBoundColour());
        assertNotEquals(testBox.getBoundColour(), Colour.YELLOW);
        assertNull(testBox.getBoundValue());

    }

    @Test
    public void testGetBoundValueNull(){

        Box testBox = new Box();
        assertNull(testBox.getBoundValue());

    }

    @Test
    public void testGetBoundValue(){

        Box testBox = new Box(Value.FIVE);
        assertEquals(Value.FIVE,testBox.getBoundValue());
        assertNotEquals(testBox.getBoundValue(), Value.ONE);
        assertNull(testBox.getBoundColour());
    }


}