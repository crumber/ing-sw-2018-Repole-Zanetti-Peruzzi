package repolezanettiperuzzi.model.actions;

import org.junit.Test;
import repolezanettiperuzzi.model.Box;
import repolezanettiperuzzi.model.Colour;
import repolezanettiperuzzi.model.Window;

import java.util.ArrayList;

import static org.junit.Assert.*;

//tetso la classe taketwocardwindowaction
public class TakeTwoCardWindowActionTest {

    TakeTwoCardWindowAction testTakeTwoCardWindow= new TakeTwoCardWindowAction();

    //testo che svolga correttamente l'azione
    @Test
    public void doAction() {

        ArrayList<Window> windows= new ArrayList<>();

        Box [][] testBoxes = new Box[4][5];

        for ( int i = 0; i < 4; i++){

            for ( int j = 0; j < 5; j++){

                testBoxes[i][j]= new Box(Colour.RED);

            }
        }

        String name = "1";
        Window tempWindow = new Window(name,5, testBoxes,"test");

        Box [][] testBoxes2 = new Box[4][5];

        for ( int i = 0; i < 4; i++){

            for ( int j = 0; j < 5; j++){

                testBoxes2[i][j]= new Box(Colour.RED);

            }
        }

        String name2 = "2";
        Window tempWindow2 = new Window(name2,5, testBoxes2,"test");

        Box [][] testBoxes3 = new Box[4][5];

        for ( int i = 0; i < 4; i++){

            for ( int j = 0; j < 5; j++){

                testBoxes3[i][j]= new Box(Colour.RED);

            }
        }

        String name3 = "3";
        Window tempWindow3 = new Window(name3,5, testBoxes3,"test");

        Box [][] testBoxes4 = new Box[4][5];

        for ( int i = 0; i < 4; i++){

            for ( int j = 0; j < 5; j++){

                testBoxes4[i][j]= new Box(Colour.RED);

            }
        }

        String name4 = "4";
        Window tempWindow4 = new Window(name4,5, testBoxes4,"test");

        windows.add(tempWindow);
        windows.add(tempWindow2);
        windows.add(tempWindow3);
        windows.add(tempWindow4);

        ArrayList<Window> windowCard1=(ArrayList<Window>) testTakeTwoCardWindow.doAction(windows);

        assertEquals(4,windowCard1.size());

    }
}