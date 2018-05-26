package repolezanettiperuzzi.model;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class DiceBagTest {

    private DiceBag testDiceBag;


    @Test
    public void testTakeDice() {

        testDiceBag= new DiceBag();
        int nDice= 2;
        int size = testDiceBag.getSize();

        ArrayList<Die> extractDice = testDiceBag.takeDice(nDice);

        //test that the size is decreased by number of Dice(nDice)
        assertEquals(size-nDice,testDiceBag.getSize());

    }

    @Test
    public void testTakeDie(){

        testDiceBag=new DiceBag();
        int newSize=testDiceBag.getSize();
        Die dieTest=testDiceBag.takeDie();

        assertEquals(newSize-1,testDiceBag.getSize());

    }

    @Test
    public void testScletDieInBag(){

        testDiceBag=new DiceBag();
        Die chosenDie=testDiceBag.takeDie();

        testDiceBag.setDieInBag(chosenDie);
        assertEquals(90,testDiceBag.getSize());
    }
}