package RepoleZanettiPeruzzi.Model;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class RoundTrackTest {

    private RoundTrack testRoundTrack;

    @Test
    public void addDie() {

        testRoundTrack=new RoundTrack();
        Die d1= new Die(Colour.PURPLE);
        Die d2= new Die(Colour.GREEN);
        Die d3= new Die(Colour.RED);
        Die d4= new Die(Colour.BLUE);
        ArrayList<Die> diceDraft1= new ArrayList<>();
        ArrayList<Die> diceDraft2= new ArrayList<>();

        d2.rollDie();
        d3.rollDie();
        d4.rollDie();
        diceDraft1.add(d1);
        diceDraft1.add(d2);
        diceDraft2.add(d3);
        diceDraft2.add(d4);
        testRoundTrack.addDice(diceDraft1);
        testRoundTrack.addDice(diceDraft2);

        //test to coverage class Die
        assertTrue(d1.getColourDie()==Colour.PURPLE);
        assertTrue(d1.getValueDie()==Value.ONE);

        //test to coverage class RaundTrack
        assertTrue(testRoundTrack.getDieRoundTrack().contains(diceDraft1));
        assertTrue(testRoundTrack.getDieRoundTrack().contains(diceDraft2));

    }

}
