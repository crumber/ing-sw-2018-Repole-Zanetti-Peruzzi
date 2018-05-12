package repolezanettiperuzzi.model;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class WindowTest {

    /*private Window testWindow;
    private Box[][] testBoxes;
    private String name;
    private Die testDie,testDie2,testDie3;


    @Test
    public void testInsertDie(){

        testBoxes = new Box[4][5];

        for ( int i = 0; i < 4; i++){

            for ( int j = 0; j < 5; j++){

                testBoxes[i][j]= new Box();

            }
        }

        name = "Virtus";
        testWindow = new Window(name,5, testBoxes);
        testDie = new Die(Colour.YELLOW);

    //test isEmpty branch

        //test x==0 branch
        assertTrue(testWindow.insertDie(testDie,0,3,"value"));
        testWindow.removeDie(0,3);

        //test x==1,y==2 branch
        assertFalse(testWindow.insertDie(testDie,1,2,"both"));

    //test controlAdjacency(1,3)==true branch
        testWindow.insertDie(testDie,0,3,"both");
        testDie2 = new Die(Colour.RED);
        testDie2.setValue(Value.THREE);
        assertTrue(testWindow.insertDie(testDie2,0,4,"colour"));

    // test controlAdjacency(3,0)==false && isEmpty==false
        testDie3 = new Die(Colour.GREEN);
        assertFalse(testWindow.insertDie(testDie3,3,0,"both"));



    }

    //test moveDice method
    @Test
    public void testMoveDice(){

        testBoxes = new Box[4][5];

        for ( int i = 0; i < 4; i++){

            for ( int j = 0; j < 5; j++){

                testBoxes[i][j]= new Box();

            }
        }

        name = "Virtus";
        testWindow = new Window(name,5, testBoxes);
        testDie = new Die(Colour.YELLOW);
        testDie2 = new Die(Colour.RED);
        testDie2.setValue(Value.FIVE);

        //test box source is not empty and box destination is controlAdjacency==true
        testWindow.insertDie(testDie,0,0,"both");
        testWindow.insertDie(testDie2,0,1,"both");
        assertTrue(testWindow.moveDie(0,1,1,1,"both"));

        //test box destination is controlAdjacency==true
        assertFalse(testWindow.moveDie(1,1,3,4,"both"));
    }

    //testing removeDie() method when a box is empty
    @Test
    public void testRemoveDieNull(){

        testBoxes = new Box[4][5];

        for ( int i = 0; i < 4; i++){

            for ( int j = 0; j < 5; j++){

                testBoxes[i][j]= new Box();

            }
        }

        name = "Virtus";
        testWindow = new Window(name,5, testBoxes);

        assertNull(testWindow.removeDie(0,0));

    }

    @Test
    public void testGetName(){

        testBoxes = new Box[4][5];

        for ( int i = 0; i < 4; i++){

            for ( int j = 0; j < 5; j++){

                testBoxes[i][j]= new Box();

            }
        }

        name = "Virtus";
        testWindow = new Window(name,5, testBoxes);

        assertEquals("Virtus",testWindow.getName());

    }

    @Test
    public void testGetTokens(){

        testBoxes = new Box[4][5];

        for ( int i = 0; i < 4; i++){

            for ( int j = 0; j < 5; j++){

                testBoxes[i][j]= new Box();

            }
        }

        name = "Virtus";
        testWindow = new Window(name,5, testBoxes);

        assertEquals(5,testWindow.getFTokens());

    }

    @Test
    public void testGetDieValue(){

        testBoxes = new Box[4][5];

        for ( int i = 0; i < 4; i++){

            for ( int j = 0; j < 5; j++){

                testBoxes[i][j]= new Box();

            }
        }

        name = "Virtus";
        testWindow = new Window(name,5, testBoxes);
        testDie = mock(Die.class);
        when(testDie.getValueDie()).thenReturn(Value.ONE);
        testWindow.insertDie(testDie,0,0,"both");
        assertEquals(Value.ONE,testWindow.getDieValue(0,0));
    }

    @Test
    public void testGetDieColour(){

        testBoxes = new Box[4][5];

        for ( int i = 0; i < 4; i++){

            for ( int j = 0; j < 5; j++){

                testBoxes[i][j]= new Box();

            }
        }

        name = "Virtus";
        testWindow = new Window(name,5, testBoxes);
        testDie = mock(Die.class);
        when(testDie.getColourDie()).thenReturn(Colour.RED);
        testWindow.insertDie(testDie,0,0,"both");
        assertEquals(Colour.RED,testWindow.getDieColour(0,0));

    }

*/
}

