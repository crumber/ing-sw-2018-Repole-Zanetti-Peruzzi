package repolezanettiperuzzi.model;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class RoundTrackTest {

    @Test
    public void testAddDice() {

        RoundTrack testRoundTrack=new RoundTrack();
        Die d1= new Die(Colour.PURPLE);
        Die d2= new Die(Colour.GREEN);
        Die d3= new Die(Colour.RED);
        Die d4= new Die(Colour.BLUE);
        ArrayList<Die> diceDraft1= new ArrayList<>();
        ArrayList<Die> diceDraft2= new ArrayList<>();

        d1.setValue(Value.TWO);
        d2.setValue(Value.THREE);
        d3.setValue(Value.FOUR);
        d4.setValue(Value.FIVE);

        diceDraft1.add(d1);
        diceDraft1.add(d2);
        diceDraft2.add(d3);
        diceDraft2.add(d4);

        testRoundTrack.addDice(diceDraft1);
        testRoundTrack.addDice(diceDraft2);

        Die dieTest=testRoundTrack.getDieRoundTrack(1,0);
        assertEquals(dieTest,d3);

    }

    @Test
    public void testSetDieOnRoundTrack(){

        RoundTrack testRoundTrack=new RoundTrack();
        Die dieTest=new Die(Colour.RED);
        Die d1= new Die(Colour.PURPLE);
        Die d2= new Die(Colour.GREEN);
        Die d3= new Die(Colour.RED);
        Die d4= new Die(Colour.BLUE);
        ArrayList<Die> diceDraft1= new ArrayList<>();

        diceDraft1.add(d1);
        diceDraft1.add(d2);
        diceDraft1.add(d3);
        diceDraft1.add(d4);

        testRoundTrack.addDice(diceDraft1);
        assertTrue(testRoundTrack.getDieRoundTrack(0,2)==d3);

        testRoundTrack.setDieOnRoundTrack(0,2, dieTest);
        assertTrue(testRoundTrack.getDieRoundTrack(0,2)==dieTest);

        assertTrue(testRoundTrack.getDieRoundTrack(120,2)==null);
        assertTrue(testRoundTrack.getDieRoundTrack(0,212)==null);

    }

    @Test
    public void testToString(){

        RoundTrack testRoundTrack=new RoundTrack();
        Die d1= new Die(Colour.PURPLE);
        Die d2= new Die(Colour.GREEN);
        Die d3= new Die(Colour.RED);
        Die d4= new Die(Colour.BLUE);

        Die d5= new Die(Colour.YELLOW);
        d5.setValue(Value.FIVE);
        Die d6= new Die(Colour.YELLOW);
        d6.setValue(Value.FOUR);
        Die d7= new Die(Colour.RED);
        d7.setValue(Value.THREE);
        Die d8= new Die(Colour.PURPLE);
        d8.setValue(Value.TWO);

        ArrayList<Die> diceDraft1= new ArrayList<>();
        ArrayList<Die> diceDraft2= new ArrayList<>();


        diceDraft1.add(d1);
        diceDraft1.add(d2);
        diceDraft1.add(d3);
        diceDraft1.add(d4);
        testRoundTrack.addDice(diceDraft1);

        diceDraft2.add(d5);
        diceDraft2.add(d6);
        diceDraft2.add(d7);
        diceDraft2.add(d8);
        testRoundTrack.addDice(diceDraft2);

        assertEquals("1P1_G1_R1_B1-2Y5_Y4_R3_P2",testRoundTrack.toString());
    }
}
