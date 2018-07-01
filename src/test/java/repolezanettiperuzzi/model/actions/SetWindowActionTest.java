package repolezanettiperuzzi.model.actions;

import org.junit.Test;
import repolezanettiperuzzi.model.Box;
import repolezanettiperuzzi.model.Colour;
import repolezanettiperuzzi.model.Player;
import repolezanettiperuzzi.model.Window;

import static org.junit.Assert.*;

public class SetWindowActionTest {

    SetWindowAction testsSetWindowAction=new SetWindowAction();

    @Test
    public void doAction() {

        Player player=new Player("mondiali","boh","forse","si",10101);

        Box[][] testBoxes = new Box[4][3];

        for ( int i = 0; i < 4; i++){

            for ( int j = 0; j < 3; j++){

                testBoxes[i][j]= new Box(Colour.RED);

            }
        }

        String name = "testWindow";
        Window tempWindow = new Window(name,5, testBoxes,"test");

        testsSetWindowAction.doAction(player,tempWindow);

        assertEquals("testWindow",player.getWindow().getName());
        assertEquals(5,player.getWindow().getFTokens());
        assertEquals(5,player.getFavorTokens());


    }
}