package RepoleZanettiPeruzzi.Model;

import org.junit.Test;

import static org.junit.Assert.*;

public class DiceBagTest {

    private DiceBag testDiceBag;


    @Test
    public void takeDie() {

        testDiceBag= new DiceBag();
        int size = testDiceBag.getDiceBag().size();
        Die extractDie = testDiceBag.takeDie();
        assertEquals(size-1,testDiceBag.getDiceBag().size());  //test that the size is decreazed by one
        assertFalse(testDiceBag.getDiceBag().contains(extractDie)); //test that the die is not in Dicebag yet

    }
}