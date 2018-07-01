package repolezanettiperuzzi.model;

import org.junit.Test;

import static org.junit.Assert.*;

public class WindowTest {

    private Window testWindow;
    private Box[][] testBoxes;
    private String name;
    private Die testDie,testDie2;


    @Test
    public void testTestInsertDie(){

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
    public void testMoveDie() {
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
    public void  testThereIsDie(){

        testBoxes = new Box[10][10];

        for ( int i = 0; i < 10; i++){

            for ( int j = 0; j < 10; j++){

                testBoxes[i][j]= new Box();

            }
        }

        name = "testThereIsDie";
        testWindow = new Window(name,5, testBoxes,"test");
        assertFalse(testWindow.thereIsDie(0,0));
        assertFalse(testWindow.thereIsDie(-1,0));
        assertFalse(testWindow.thereIsDie(0,-1));
        assertFalse(testWindow.thereIsDie(100,0));
        assertFalse(testWindow.thereIsDie(0,100));

    }


    @Test
    public void testGetNameFToken() {

        testBoxes = new Box[4][5];

        for ( int i = 0; i < 4; i++){

            for ( int j = 0; j < 5; j++){

                testBoxes[i][j]= new Box();

            }
        }

        name = "Virtus";
        testWindow = new Window(name,5, testBoxes,"test");

        assertEquals("Virtus",testWindow.getName());
        assertEquals(5,testWindow.getFTokens());
        assertTrue(testWindow.isEmpty());
        assertEquals(20,testWindow.numBoxEmpty());
    }

    @Test
    public void testControlAdjacences() {

        testBoxes = new Box[4][5];

        for ( int i = 0; i < 4; i++){

            for ( int j = 0; j < 5; j++){

                testBoxes[i][j]= new Box();

            }
        }

        name = "Virtus";
        testWindow = new Window(name,5, testBoxes, "test");
        testDie=new Die(Colour.RED);

        assertFalse(testWindow.controlAdjacencies(1,1));

        testWindow.insertDie(testDie,0,1,"both");
        assertTrue(testWindow.controlAdjacencies(1,1));

        testWindow.moveDie(0,1,0,0,"both");
        assertTrue(testWindow.controlAdjacencies(1,1));

        testWindow.moveDie(0,0,0,2,"both");
        assertTrue(testWindow.controlAdjacencies(1,1));

        testWindow.moveDie(0,2,2,1,"both");
        assertTrue(testWindow.controlAdjacencies(1,1));

        testWindow.moveDie(2,1,2,0,"both");
        assertTrue(testWindow.controlAdjacencies(1,1));

        testWindow.moveDie(2,0,2,2,"both");
        assertTrue(testWindow.controlAdjacencies(1,1));

        testWindow.moveDie(2,2,1,0,"both");
        assertTrue(testWindow.controlAdjacencies(1,1));

        testWindow.moveDie(1,0,1,2,"both");
        assertTrue(testWindow.controlAdjacencies(1,1));

    }

    @Test
    public void testControlAllBoundAdjacences() {

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

        assertFalse(testWindow.controlAllBoundAdjacencies(testDie,2,4));

        testWindow.insertDie(d1,1,3,"both");

        assertFalse(testWindow.controlAllBoundAdjacencies(testDie,2,3));

        testDie.setValue(Value.THREE);

        assertTrue(testWindow.controlAllBoundAdjacencies(testDie,2,3));

        testWindow.moveDie(1,3,3,3,"both");
        assertTrue(testWindow.controlAllBoundAdjacencies(testDie,2,3));

        testWindow.moveDie(3,3,2,2,"both");
        assertTrue(testWindow.controlAllBoundAdjacencies(testDie,2,3));

        testWindow.moveDie(2,2,2,4,"both");
        assertTrue(testWindow.controlAllBoundAdjacencies(testDie,2,3));


        testWindow.insertDie(d,0,1,"both");

        assertTrue(testWindow.controlAllBoundAdjacencies(testDie,1,1));

        testWindow.moveDie(0,1,2,1,"both");
        assertTrue(testWindow.controlAllBoundAdjacencies(testDie,1,1));

        testWindow.moveDie(2,1,1,0,"both");
        assertTrue(testWindow.controlAllBoundAdjacencies(testDie,1,1));

        testWindow.moveDie(1,0,1,2,"both");
        assertTrue(testWindow.controlAllBoundAdjacencies(testDie,1,1));
    }

    @Test
    public void testControlColourBoundAdjacencies(){

        testBoxes = new Box[4][5];

        for ( int i = 0; i < 4; i++){

            for ( int j = 0; j < 5; j++){

                testBoxes[i][j]= new Box(Value.ONE);

            }
        }
        name = "Virt";
        testWindow = new Window(name,5, testBoxes,"test");

        testDie = new Die(Colour.RED);

        assertFalse(testWindow.controlColourBoundAdjacencies(testDie,0,4));
        assertFalse(testWindow.controlColourBoundAdjacencies(testDie,0,3));

        Die d=new Die(Colour.RED);
        testWindow.insertDie(d,0,4,"none");
        assertTrue(testWindow.controlColourBoundAdjacencies(testDie,0,3));

        Die d1=new Die(Colour.BLUE);

        testWindow.insertDie(d1,2,4,"none");
        assertFalse(testWindow.controlColourBoundAdjacencies(testDie,2,3));

    }

    @Test
    public void testControlValueBoundAdjacencies(){

        testBoxes = new Box[4][5];

        for ( int i = 0; i < 4; i++){

            for ( int j = 0; j < 5; j++){

                testBoxes[i][j]= new Box(Colour.RED);

            }
        }
        name = "Virman";
        testWindow = new Window(name,8, testBoxes,"test");

        testDie = new Die(Colour.RED);
        testDie.setValue(Value.TWO);

        assertFalse(testWindow.controlValueBoundAdjacencies(testDie,3,0));
        assertFalse(testWindow.controlValueBoundAdjacencies(testDie,2,0));

        Die d=new Die(Colour.RED);
        testWindow.insertDie(d,3,0,"none");
        assertFalse(testWindow.controlValueBoundAdjacencies(testDie,2,0));

        d.setValue(Value.TWO);

        testWindow.insertDie(d,3,3,"none");
        assertTrue(testWindow.controlValueBoundAdjacencies(testDie,2,3));


        testDie.setValue(Value.THREE);
        assertFalse(testWindow.controlValueBoundAdjacencies(testDie,0,4));
        assertFalse(testWindow.controlValueBoundAdjacencies(testDie,0,3));

        testWindow.insertDie(d,2,4,"none");
        assertFalse(testWindow.controlValueBoundAdjacencies(testDie,2,3));

        d.setValue(Value.THREE);

        testWindow.insertDie(d,0,4,"none");
        assertTrue(testWindow.controlValueBoundAdjacencies(testDie,0,3));

    }

    @Test
    public void testCalculeteSecretScore() {
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

        assertEquals(6,testWindow.calculateSecretScore(Colour.YELLOW));
        assertEquals(0,testWindow.calculateSecretScore(Colour.BLUE));


    }

    @Test
    public void testControlAllBoundBox() {

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
    public void testControlColourBoundBox() {

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
    public void testControlValueBoundBox() {

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

    @Test
    public void testCopy() {

        testBoxes = new Box[4][5];

        for ( int i = 0; i < 4; i++){

            for ( int j = 0; j < 5; j++){

                testBoxes[i][j]= new Box(Colour.RED);

            }
        }

        name = "testWindow";
        Window tempWindow = new Window(name,5, testBoxes,"test");

        testDie=new Die(Colour.RED);
        tempWindow.insertDie(testDie,3,3,"none");

        testWindow=new Window(tempWindow.copy());

        assertTrue(testWindow.thereIsDie(3,3));
    }

    @Test
    public void testToString(){

        testBoxes = new Box[4][5];

        for ( int i = 0; i < 4; i++){

            for ( int j = 0; j < 5; j++){

                if(i==0)
                {

                  testBoxes[i][j]= new Box(Colour.RED);

                }else if(j<3){

                    testBoxes[i][j]= new Box(Colour.BLUE);

                }else{

                    testBoxes[i][j]= new Box(Value.SIX);

                }

            }
        }

        name = "testWindow";
        Window tempWindow = new Window(name,5, testBoxes,"test");

        assertEquals("R-R-R-R-R B-B-B-6-6 B-B-B-6-6 B-B-B-6-6",tempWindow.toString());

    }

}