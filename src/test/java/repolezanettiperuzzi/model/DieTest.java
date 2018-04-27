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
}