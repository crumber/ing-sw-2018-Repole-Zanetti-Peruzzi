package RepoleZanettiPeruzzi.Model;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BoxTest {

    private Box testBox;
    private Die die;

    @Test
    public void testGetBoundColourNull(){

        testBox= new Box();
        assertNull(testBox.getBoundColour());

    }

    @Test
    public void testGetBoundColour(){

        testBox = new Box(Colour.RED);
        assertEquals(Colour.RED,testBox.getBoundColour());
        assertNotEquals(testBox.getBoundColour(), Colour.YELLOW);
        assertNull(testBox.getBoundValue());

    }

    @Test
    public void testGetBoundValueNull(){

        testBox = new Box();
        assertNull(testBox.getBoundValue());

    }

    @Test
    public void testGetBoundValue(){

        testBox = new Box(Value.FIVE);
        assertEquals(Value.FIVE,testBox.getBoundValue());
        assertNotEquals(testBox.getBoundValue(), Value.ONE);
        assertNull(testBox.getBoundColour());
    }

    @Test
    public void testSetDieColourBound(){
        testBox = new Box(Colour.RED);
        die = mock(Die.class);
        when(die.getColourDie()).thenReturn(Colour.RED);
        when(die.getValueDie()).thenReturn(Value.ONE);
        testBox.setDie(die);
        assertSame(testBox.removeDie(),die);

        when(die.getColourDie()).thenReturn(Colour.YELLOW);
        when(die.getValueDie()).thenReturn(Value.TWO);
        testBox.setDie(die);
        assertNotSame(testBox.removeDie(),die);

    }

    @Test
    public void testSetDieValueBound(){

        testBox = new Box(Value.ONE);
        die = mock(Die.class);
        when(die.getColourDie()).thenReturn(Colour.YELLOW);
        when(die.getValueDie()).thenReturn(Value.ONE);
        testBox.setDie(die);
        assertSame(testBox.removeDie(),die);

        when(die.getColourDie()).thenReturn(Colour.BLUE);
        when(die.getValueDie()).thenReturn(Value.FIVE);
        testBox.setDie(die);
        assertNotSame(testBox.removeDie(),die);

    }

    @Test
    public void testSetDieNoBound(){

        testBox = new Box();
        die = mock(Die.class);
        when(die.getColourDie()).thenReturn(Colour.RED);
        when(die.getValueDie()).thenReturn(Value.SIX);
        testBox.setDie(die);
        assertSame(testBox.removeDie(),die);

    }

}