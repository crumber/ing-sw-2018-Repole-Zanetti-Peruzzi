package repolezanettiperuzzi.model;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BoxTest {

    private Box testBox;
    private Die die;

   /* //test box without bounds
    @Test
    public void testGetBoundNull(){

        testBox= new Box();
        assertNull(testBox.getBoundColour());
        assertNull(testBox.getBoundValue());

    }


    //test box with colour bound
    @Test
    public void testGetBoundColour(){

        testBox = new Box(Colour.RED);
        assertEquals(Colour.RED,testBox.getBoundColour());
        assertNotEquals(testBox.getBoundColour(), Colour.YELLOW);
        assertNull(testBox.getBoundValue());

    }

    //test box with value bound
    @Test
    public void testGetBoundValue(){

        testBox = new Box(Value.FIVE);
        assertEquals(Value.FIVE,testBox.getBoundValue());
        assertNotEquals(testBox.getBoundValue(), Value.ONE);
        assertNull(testBox.getBoundColour());
    }

    //test when a die is inserted in a box with colour bound
    @Test
    public void testSetDieColourBound(){

        //box with colour bound
        testBox = new Box(Colour.RED);
        die = mock(Die.class);
        when(die.getColourDie()).thenReturn(Colour.RED);
        when(die.getValueDie()).thenReturn(Value.ONE);
        testBox.setDie(die,"colour");
        assertSame(testBox.die,die);

        //box without bound
        testBox.removeDie();
        testBox = new Box();
        testBox.setDie(die,"colour");
        assertSame(testBox.die,die);

        //fail to set a die with a different colour than the one in the box
        testBox.removeDie();
        testBox = new Box(Colour.RED);
        when(die.getColourDie()).thenReturn(Colour.YELLOW);
        when(die.getValueDie()).thenReturn(Value.TWO);
        testBox.setDie(die,"colour");
        assertNotSame(testBox.die,die);

    }

    //test when we have to consider both colour and value bound in a box
    @Test
    public void testSetDieBoth(){

        //box with value bound
        testBox = new Box(Value.ONE);
        die = mock(Die.class);
        when(die.getColourDie()).thenReturn(Colour.YELLOW);
        when(die.getValueDie()).thenReturn(Value.ONE);
        testBox.setDie(die,"both");
        assertSame(testBox.die,die);

        //box without bound
        testBox.removeDie();
        testBox = new Box();
        testBox.setDie(die,"both");
        assertSame(testBox.die,die);

        //box with colour bound
        testBox.removeDie();
        testBox = new Box(Colour.YELLOW);
        testBox.setDie(die,"both");
        assertSame(testBox.die,die);

        //fail to set a die with a different colour than the one in the box
        testBox.removeDie();
        when(die.getColourDie()).thenReturn(Colour.BLUE);
        when(die.getValueDie()).thenReturn(Value.FIVE);
        testBox.setDie(die,"both");
        assertNotSame(testBox.removeDie(),die);

    }

    @Test
    public void testSetDieValueBound(){

        //box with value bound
        testBox = new Box(Value.ONE);
        die = mock(Die.class);
        when(die.getColourDie()).thenReturn(Colour.RED);
        when(die.getValueDie()).thenReturn(Value.ONE);
        testBox.setDie(die,"value");
        assertSame(testBox.die,die);

        //box without bound
        testBox.removeDie();
        testBox = new Box();
        testBox.setDie(die,"value");
        assertSame(testBox.die,die);

        //fail set die in a box with different value
        testBox.removeDie();
        testBox = new Box(Value.TWO);
        testBox.setDie(die, "value");
        assertNotSame(testBox.die,die);

    }

*/

}
