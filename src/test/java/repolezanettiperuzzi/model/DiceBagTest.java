package repolezanettiperuzzi.model;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

//Test dei metodi della classe dice bag
public class DiceBagTest {

    private DiceBag testDiceBag;

    //testa che vengano pescati n dadi
    @Test
    public void testTakeDice() {

        testDiceBag= new DiceBag();
        int nDice= 2;
        int size = testDiceBag.getSize();

        ArrayList<Die> extractDice = (ArrayList<Die>) testDiceBag.takeDice(nDice);

        //test that the size is decreased by number of Dice(nDice)
        assertEquals(size-nDice,testDiceBag.getSize());

    }

    //testa che venga pescato un dado
    @Test
    public void testTakeDie(){

        testDiceBag=new DiceBag();
        int newSize=testDiceBag.getSize();
        Die dieTest=testDiceBag.takeDie();

        assertEquals(newSize-1,testDiceBag.getSize());

    }

    //testa che venga inserito il dado nella dice bag
    @Test
    public void testSetDieInBag(){

        testDiceBag=new DiceBag();
        Die chosenDie=testDiceBag.takeDie();

        testDiceBag.setDieInBag(chosenDie);
        assertEquals(90,testDiceBag.getSize());
    }
}