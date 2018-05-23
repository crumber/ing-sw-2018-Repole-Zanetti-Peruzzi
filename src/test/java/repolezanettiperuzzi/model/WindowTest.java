package repolezanettiperuzzi.model;

import org.junit.Test;
import org.mockito.internal.matchers.Null;

import static org.junit.Assert.*;

public class WindowTest {

    private Window testWindow;
    private Box[][] testBoxes;
    private String name;
    private Die testDie,testDie2;


    @Test
    public void testInsertDie(){

        testBoxes = new Box[4][5];

        for ( int i = 0; i < 4; i++){

            for ( int j = 0; j < 5; j++){

                testBoxes[i][j]= new Box();

            }
        }

        name = "Virtus";
        testWindow = new Window(name,5, testBoxes,"test");
        testDie = new Die(Colour.YELLOW);

        testWindow.insertDie(testDie,0,0,"both");
        assertTrue(testWindow.thereIsDie(0,0));
        assertEquals(testDie,testWindow.getDieFromBoardBox(0,0));
        assertFalse(testWindow.isEmpty());

        Die dieR=testWindow.removeDie(0,0);

        assertFalse(testWindow.thereIsDie(0,0));
        assertEquals(testDie,dieR);

        assertEquals(null,testWindow.removeDie(0,0));

    }

    @Test
    public void moveDie() {
        testBoxes = new Box[4][5];

        for ( int i = 0; i < 4; i++){

            for ( int j = 0; j < 5; j++){

                testBoxes[i][j]= new Box();

            }
        }

        name = "Virtus";
        testWindow = new Window(name,5, testBoxes,"test");
        testDie = new Die(Colour.YELLOW);
        Die d1=new Die(Colour.RED);
        d1.setValue(Value.THREE);
        testWindow.insertDie(testDie,0,0,"both");
        testWindow.insertDie(d1,0,1,"both");

        testWindow.moveDie(0,0,0,2,"both");

        assertEquals(false,testWindow.thereIsDie(0,0));
        assertEquals(testDie,testWindow.getDieFromBoardBox(0,2));
        assertEquals(Colour.YELLOW,testWindow.getDieColour(0,2));
        assertEquals(Value.THREE,testWindow.getDieValue(0,1));

    }


    @Test
    public void getNameFToken() {

        testBoxes = new Box[4][5];

        for ( int i = 0; i < 4; i++){

            for ( int j = 0; j < 5; j++){

                testBoxes[i][j]= new Box();

            }
        }

        name = "Virtus";
        testWindow = new Window(name,5, testBoxes,"test");
        testDie = new Die(Colour.YELLOW);

        assertEquals("Virtus",testWindow.getName());
        assertEquals(5,testWindow.getFTokens());
        assertTrue(testWindow.isEmpty());
    }

    @Test
    public void controlAdjacences() {

        testBoxes = new Box[4][5];

        for ( int i = 0; i < 4; i++){

            for ( int j = 0; j < 5; j++){

                testBoxes[i][j]= new Box();

            }
        }

        name = "Virtus";
        testWindow = new Window(name,5, testBoxes, "test");
        testDie=new Die(Colour.RED);

        assertFalse(testWindow.controlAdjacences(1,1));

        testWindow.insertDie(testDie,0,1,"both");
        assertTrue(testWindow.controlAdjacences(1,1));

        testWindow.moveDie(0,1,0,0,"both");
        assertTrue(testWindow.controlAdjacences(1,1));

        testWindow.moveDie(0,0,0,2,"both");
        assertTrue(testWindow.controlAdjacences(1,1));

        testWindow.moveDie(0,2,2,1,"both");
        assertTrue(testWindow.controlAdjacences(1,1));

        testWindow.moveDie(2,1,2,0,"both");
        assertTrue(testWindow.controlAdjacences(1,1));

        testWindow.moveDie(2,0,2,2,"both");
        assertTrue(testWindow.controlAdjacences(1,1));

        testWindow.moveDie(2,2,1,0,"both");
        assertTrue(testWindow.controlAdjacences(1,1));

        testWindow.moveDie(1,0,1,2,"both");
        assertTrue(testWindow.controlAdjacences(1,1));

    }

    @Test
    public void controlAllBoundAdjacences() {

        testBoxes = new Box[4][5];

        for ( int i = 0; i < 4; i++){

            for ( int j = 0; j < 5; j++){

                testBoxes[i][j]= new Box();

            }
        }

        name = "Virtus";
        testWindow = new Window(name,5, testBoxes,"test");
        testDie = new Die(Colour.RED);
        Die d=new Die(Colour.RED);
        Die d1=new Die(Colour.YELLOW);

        d1.setValue(Value.THREE);

        testWindow.insertDie(d1,1,3,"both");

        assertFalse(testWindow.controlAllBoundAdjacences(testDie,2,3));

        testDie.setValue(Value.THREE);

        assertTrue(testWindow.controlAllBoundAdjacences(testDie,2,3));

        testWindow.moveDie(1,3,3,3,"both");
        assertTrue(testWindow.controlAllBoundAdjacences(testDie,2,3));

        testWindow.moveDie(3,3,2,2,"both");
        assertTrue(testWindow.controlAllBoundAdjacences(testDie,2,3));

        testWindow.moveDie(2,2,2,4,"both");
        assertTrue(testWindow.controlAllBoundAdjacences(testDie,2,3));


        testWindow.insertDie(d,0,1,"both");

        assertTrue(testWindow.controlAllBoundAdjacences(testDie,1,1));

        testWindow.moveDie(0,1,2,1,"both");
        assertTrue(testWindow.controlAllBoundAdjacences(testDie,1,1));

        testWindow.moveDie(2,1,1,0,"both");
        assertTrue(testWindow.controlAllBoundAdjacences(testDie,1,1));

        testWindow.moveDie(1,0,1,2,"both");
        assertTrue(testWindow.controlAllBoundAdjacences(testDie,1,1));
    }

    @Test
    public void calculeteSecretScore() {
        testBoxes = new Box[4][5];

        for ( int i = 0; i < 4; i++){

            for ( int j = 0; j < 5; j++){

                testBoxes[i][j]= new Box();

            }
        }

        name = "Virtus";
        testWindow = new Window(name,5, testBoxes,"test");

        testDie = new Die(Colour.YELLOW);
        testDie2=new Die(Colour.RED);

        testDie.setValue(Value.SIX);
        testDie2.setValue(Value.FIVE);
        testWindow.insertDie(testDie,0,0,"both");
        testWindow.insertDie(testDie2,0,1,"both");

        assertEquals(6,testWindow.calculeteSecretScore(Colour.YELLOW));
        assertEquals(0,testWindow.calculeteSecretScore(Colour.BLUE));


    }

    @Test
    public void controlAllBoundBox() {

        testBoxes = new Box[4][5];

        for ( int i = 0; i < 4; i++){

            for ( int j = 0; j < 5; j++){

                if(j<2){

                    testBoxes[i][j]= new Box(Colour.RED);

                }else{

                    testBoxes[i][j]=new Box(Value.SIX);
                }

            }
        }

        name = "Virtus";
        testWindow = new Window(name,5, testBoxes,"test");
        testDie = new Die(Colour.YELLOW);
        testDie2=new Die(Colour.RED);

        testDie.setValue(Value.SIX);
        testDie2.setValue(Value.FIVE);
        assertTrue(testWindow.controlAllBoundBox(0,2,testDie));
        assertFalse(testWindow.controlAllBoundBox(0,2,testDie2));
        assertFalse(testWindow.controlAllBoundBox(0,0,testDie));
        assertTrue(testWindow.controlAllBoundBox(0,0,testDie2));
    }

    @Test
    public void controlColourBoundBox() {

        testBoxes = new Box[4][5];

        for ( int i = 0; i < 4; i++){

            for ( int j = 0; j < 5; j++){

                testBoxes[i][j]= new Box(Colour.YELLOW);

            }
        }

        name = "Virtus";
        testWindow = new Window(name,5, testBoxes, "test");
        testDie = new Die(Colour.YELLOW);
        testDie2=new Die(Colour.RED);

        testDie.setValue(Value.SIX);
        testDie2.setValue(Value.FIVE);

        assertTrue(testWindow.controlValueBoundBox(0,0,testDie));
        assertFalse(testWindow.controlColourBoundBox(0,0,testDie2));
    }

    @Test
    public void controlValueBoundBox() {

        testBoxes = new Box[4][5];

        for ( int i = 0; i < 4; i++){

            for ( int j = 0; j < 5; j++){

                testBoxes[i][j]= new Box(Value.SIX);

            }
        }

        name = "Virtus";
        testWindow = new Window(name,5, testBoxes,"test");

        testDie = new Die(Colour.YELLOW);
        testDie2=new Die(Colour.RED);

        testDie.setValue(Value.SIX);
        testDie2.setValue(Value.FIVE);

        assertTrue(testWindow.controlValueBoundBox(0,0,testDie));
        assertFalse(testWindow.controlValueBoundBox(0,0,testDie2));
    }
}