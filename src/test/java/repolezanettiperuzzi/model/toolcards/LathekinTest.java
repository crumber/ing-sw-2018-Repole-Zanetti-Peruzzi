package repolezanettiperuzzi.model.toolcards;

import org.junit.Test;
import repolezanettiperuzzi.model.*;

import java.util.ArrayList;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class LathekinTest {

    private Lathekin testPublicCard=new Lathekin();
    private GameBoard board=new GameBoard();
    private Player player;
    private ArrayList<Integer> parameterforcard=new ArrayList<>();
    private Box[][] boardBoxes;

    @Test
    public void check() {


        boardBoxes=new Box[4][5];


        for ( int i = 0; i < 4; i++){

            for ( int j = 0; j < 5; j++){

                if(i==0 && j<3) {

                    boardBoxes[i][j] = new Box(Colour.RED);

                }else if(i==1 && j<3){

                    boardBoxes[i][j] = new Box(Colour.BLUE);

                }else if(i==2 && j<2){

                    boardBoxes[i][j] = new Box(Value.ONE);

                }else if(i==3 && j<2){

                    boardBoxes[i][j] = new Box(Value.FIVE);

                }else if(i==2 && j>1){

                    boardBoxes[i][j] = new Box(Value.TWO);

                }else{

                    boardBoxes[i][j] = new Box(Value.THREE);
                }
            }
        }

        Die die1=new Die(Colour.RED);
        Die die2=new Die(Colour.RED);
        Die die3=new Die(Colour.BLUE);
        Die die4=new Die(Colour.BLUE);

        die3.setValue(Value.FIVE);
        die2.setValue(Value.FIVE);
        die4.setValue(Value.SIX);

        Window windowTest=new Window("test",4,boardBoxes,"test");
        windowTest.insertDie(die1,0,0,"colour");
        windowTest.insertDie(die2,0,1,"colour");
        windowTest.insertDie(die3,1,0,"colour");
        windowTest.insertDie(die4,1,1,"colour");

        parameterforcard.add(0);
        parameterforcard.add(0);
        parameterforcard.add(2);
        parameterforcard.add(0);
        parameterforcard.add(1);
        parameterforcard.add(0);
        parameterforcard.add(3);
        parameterforcard.add(0);

        player=mock(Player.class);
        when(player.getWindow()).thenReturn(windowTest);

        assertEquals(1,testPublicCard.check(board,player,parameterforcard));

        parameterforcard.add(0,123);

        assertEquals(-1,testPublicCard.check(board,player,parameterforcard));

        parameterforcard.clear();
        parameterforcard.add(0);
        parameterforcard.add(0);
        parameterforcard.add(2);
        parameterforcard.add(0);
        parameterforcard.add(123);
        parameterforcard.add(0);
        parameterforcard.add(3);
        parameterforcard.add(0);

        assertEquals(-1,testPublicCard.check(board,player,parameterforcard));

        parameterforcard.clear();
        parameterforcard.add(0);
        parameterforcard.add(0);
        parameterforcard.add(0);
        parameterforcard.add(2);
        parameterforcard.add(1);
        parameterforcard.add(0);
        parameterforcard.add(3);
        parameterforcard.add(0);

        assertEquals(-25,testPublicCard.check(board,player,parameterforcard));

        parameterforcard.clear();
        parameterforcard.add(0);
        parameterforcard.add(0);
        parameterforcard.add(2);
        parameterforcard.add(2);
        parameterforcard.add(1);
        parameterforcard.add(0);
        parameterforcard.add(3);
        parameterforcard.add(0);

        assertEquals(-7,testPublicCard.check(board,player,parameterforcard));

        parameterforcard.clear();
        parameterforcard.add(0);
        parameterforcard.add(0);
        parameterforcard.add(2);
        parameterforcard.add(1);
        parameterforcard.add(3);
        parameterforcard.add(3);
        parameterforcard.add(2);
        parameterforcard.add(1);

        assertEquals(-17,testPublicCard.check(board,player,parameterforcard));

        parameterforcard.clear();
        parameterforcard.add(0);
        parameterforcard.add(0);
        parameterforcard.add(2);
        parameterforcard.add(1);
        parameterforcard.add(1);
        parameterforcard.add(0);
        parameterforcard.add(2);
        parameterforcard.add(1);

        assertEquals(-18,testPublicCard.check(board,player,parameterforcard));

        parameterforcard.add(7,4);

        assertEquals(-19,testPublicCard.check(board,player,parameterforcard));

        parameterforcard.add(6,0);
        parameterforcard.add(7,2);

        assertEquals(-20,testPublicCard.check(board,player,parameterforcard));

        parameterforcard.clear();
        parameterforcard.add(0);
        parameterforcard.add(0);
        parameterforcard.add(2);
        parameterforcard.add(0);
        parameterforcard.add(1);
        parameterforcard.add(0);
        parameterforcard.add(1);
        parameterforcard.add(2);

        assertEquals(-26,testPublicCard.check(board,player,parameterforcard));
    }

    @Test
    public void effect() {

        boardBoxes=new Box[4][5];


        for ( int i = 0; i < 4; i++){

            for ( int j = 0; j < 5; j++){

                if(i==0) {

                    boardBoxes[i][j] = new Box(Colour.YELLOW);

                }else if(i==1){

                    boardBoxes[i][j] = new Box(Colour.PURPLE);

                }else if(i==2){

                    boardBoxes[i][j] = new Box(Value.ONE);

                }else{

                    boardBoxes[i][j] = new Box(Value.THREE);

                }
            }
        }

        Die die1=new Die(Colour.YELLOW);
        Die die2=new Die(Colour.YELLOW);
        Die die3=new Die(Colour.PURPLE);
        Die die4=new Die(Colour.PURPLE);

        die3.setValue(Value.THREE);
        die2.setValue(Value.THREE);
        die4.setValue(Value.FOUR);

        Window windowTest=new Window("test",4,boardBoxes,"test");
        windowTest.insertDie(die1,0,0,"colour");
        windowTest.insertDie(die2,0,1,"colour");
        windowTest.insertDie(die3,1,0,"colour");
        windowTest.insertDie(die4,1,1,"colour");

        parameterforcard.add(0);
        parameterforcard.add(0);
        parameterforcard.add(2);
        parameterforcard.add(0);
        parameterforcard.add(1);
        parameterforcard.add(0);
        parameterforcard.add(3);
        parameterforcard.add(0);

        player=mock(Player.class);
        when(player.getWindow()).thenReturn(windowTest);

        assertEquals(1,testPublicCard.check(board,player,parameterforcard));

        testPublicCard.effect(board,player,parameterforcard);

        assertTrue(player.getWindow().thereIsDie(2,0));
        assertTrue(player.getWindow().thereIsDie(3,0));
        assertFalse(player.getWindow().thereIsDie(0,0));
        assertFalse(player.getWindow().thereIsDie(1,0));

    }
}