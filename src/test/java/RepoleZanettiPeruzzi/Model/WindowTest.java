package RepoleZanettiPeruzzi.Model;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class WindowTest {

    private Window testWindow;
    private Box[][] testBoxes;
    private String name;
    private Die testDie;

    @Test



    public void testWindowClass() {

        testBoxes = new Box[4][5];

        for ( int i = 0; i < 4; i++){

            for ( int j = 0; j < 5; j++){

                testBoxes[i][j]= new Box();

            }
        }


        name = "Virtus";
        testWindow= new Window(name,5,testBoxes);
        testDie= mock(Die.class);

        testWindow.insertDie(testDie,2,4);

        assertEquals(testWindow.getName(),name);
        assertEquals(testWindow.getFTokens(),5);

        assertSame(testDie,testBoxes[2][4].removeDie());
        testWindow.insertDie(testDie, 2, 4);
        testWindow.moveDie(2,4,3,1);


        assertSame(testDie,testBoxes[3][1].removeDie());


    }

}