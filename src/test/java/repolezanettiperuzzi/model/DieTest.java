package repolezanettiperuzzi.model;

import org.junit.Test;

import static org.junit.Assert.*;

public class DieTest {

    @Test
    public void getColourDie() {

        Die d1= new Die(Colour.PURPLE);
        assertTrue(d1.getColourDie()==Colour.PURPLE);

    }

    @Test
    public void getValueDie() {

        Die d1= new Die(Colour.PURPLE);
        assertTrue(d1.getValueDie()==Value.ONE);
    }

    @Test
    public void  setValueDie(){

        Die d1=new Die((Colour.RED));
        d1.setValue(Value.SIX);
        assertTrue(d1.getValueDie()==Value.SIX);
    }

    @Test
    public void rollDie(){

        Die d=new Die(Colour.GREEN);
        d.rollDie();

        boolean itIsValue=false;
        if(d.getValueDie()==Value.SIX || d.getValueDie()==Value.ONE || d.getValueDie()==Value.TWO || d.getValueDie()==Value.THREE || d.getValueDie()==Value.FOUR || d.getValueDie()==Value.FIVE){

            itIsValue=true;

        }

        assertTrue(itIsValue);
    }
}