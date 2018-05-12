package repolezanettiperuzzi.model;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class RoundTrackTest {

    @Test
    public void addDice() {

        RoundTrack testRoundTrack=new RoundTrack();
        Die d1= new Die(Colour.PURPLE);
        Die d2= new Die(Colour.GREEN);
        Die d3= new Die(Colour.RED);
        Die d4= new Die(Colour.BLUE);
        ArrayList<Die> diceDraft1= new ArrayList<>();
        ArrayList<Die> diceDraft2= new ArrayList<>();

        d1.rollDie();
        d2.rollDie();
        d3.rollDie();
        d4.rollDie();

        diceDraft1.add(d1);
        diceDraft1.add(d2);
        diceDraft2.add(d3);
        diceDraft2.add(d4);

        testRoundTrack.addDice(diceDraft1);
        testRoundTrack.addDice(diceDraft2);

        Die dieTest=testRoundTrack.getDieRoundTrack(0,0);
        assertEquals(dieTest,d1);

    }

    @Test
    public void setDieOnRoundTrack(){

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
    }

}
