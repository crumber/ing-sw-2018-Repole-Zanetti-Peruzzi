package repolezanettiperuzzi.model;

import org.junit.Test;

import static org.junit.Assert.*;

public class DieTest {

    @Test
    public void testGetColourDie() {

        Die d1= new Die(Colour.PURPLE);
        assertTrue(d1.getColourDie()==Colour.PURPLE);

    }

    @Test
    public void testGetValueDie() {

        Die d1= new Die(Colour.PURPLE);
        assertTrue(d1.getValueDie()==Value.ONE);
    }

    @Test
    public void  testSetValueDie(){

        Die d=new Die((Colour.RED));
        Die d1=new Die((Colour.RED));
        Die d2=new Die((Colour.RED));
        Die d3=new Die((Colour.RED));
        Die d4=new Die((Colour.RED));
        Die d5=new Die((Colour.RED));
        Die d6=new Die((Colour.RED));

        d.setValue(Value.intToValue(1));
        d2.setValue(Value.intToValue(2));
        d3.setValue(Value.intToValue(3));
        d4.setValue(Value.intToValue(4));
        d5.setValue(Value.intToValue(5));
        d6.setValue(Value.intToValue(6));
        d1.setValue(Value.SIX);

        assertTrue(d1.getValueDie()==Value.SIX);
        assertTrue(d.getValueDie()==Value.ONE);
        assertTrue(d2.getValueDie()==Value.TWO);
        assertTrue(d3.getValueDie()==Value.THREE);
        assertTrue(d4.getValueDie()==Value.FOUR);
        assertTrue(d5.getValueDie()==Value.FIVE);
        assertTrue(d6.getValueDie()==Value.SIX);
    }

    @Test
    public void testRollDie(){

        Die d=new Die(Colour.GREEN);
        d.rollDie();

        boolean itIsValue=false;
        if(d.getValueDie()==Value.SIX || d.getValueDie()==Value.ONE || d.getValueDie()==Value.TWO || d.getValueDie()==Value.THREE || d.getValueDie()==Value.FOUR || d.getValueDie()==Value.FIVE){

            itIsValue=true;

        }

        assertTrue(itIsValue);
    }

    @Test
    public void testToString(){

        Die d1=new Die(Colour.RED);
        Die d2=new Die(Colour.BLUE);
        Die d3=new Die(Colour.GREEN);
        Die d4=new Die(Colour.PURPLE);
        Die d5=new Die(Colour.YELLOW);
        Die d6=new Die(Colour.YELLOW);
        d1.setValue(Value.ONE);
        d2.setValue(Value.TWO);
        d3.setValue(Value.THREE);
        d4.setValue(Value.FOUR);
        d5.setValue(Value.FIVE);
        d6.setValue(Value.SIX);

        assertEquals("R1",d1.toString());
        assertEquals("B2",d2.toString());
        assertEquals("G3",d3.toString());
        assertEquals("P4",d4.toString());
        assertEquals("Y5",d5.toString());
        assertEquals("Y6",d6.toString());

    }
}