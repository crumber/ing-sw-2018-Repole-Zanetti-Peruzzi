package repolezanettiperuzzi.model.toolcards;

import org.junit.Test;
import repolezanettiperuzzi.model.*;

import java.util.ArrayList;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

//testa la tool card 2
public class EglomiseBrushTest {

    private EglomiseBrush testPublicCard=new EglomiseBrush();
    private GameBoard board=new GameBoard();
    private Player player;
    private ArrayList<Integer> parameterforcard=new ArrayList<>();
    private Box[][] boardBoxes;

    @Test
    public void testCheck() {

        boardBoxes=new Box[4][5];


        for ( int i = 0; i < 4; i++){

            for ( int j = 0; j < 5; j++){

                if(i==0 && j==0){

                    boardBoxes[i][j] = new Box(Colour.RED);

                }else if(i==0 && j==2){

                    boardBoxes[i][j]=new Box(Value.ONE);

                }else {

                    boardBoxes[i][j] = new Box(Colour.YELLOW);

                }
            }
        }

        Die die1=new Die(Colour.RED);
        Die die2=new Die(Colour.YELLOW);

        die2.setValue(Value.FIVE);

        Window windowTest=new Window("test",4,boardBoxes,"test");
        windowTest.insertDie(die1,0,0,"colour");
        windowTest.insertDie(die2,0,1,"value");

        parameterforcard.add(0);
        parameterforcard.add(0);
        parameterforcard.add(0);
        parameterforcard.add(2);

        player=mock(Player.class);
        when(player.getWindow()).thenReturn(windowTest);

        assertEquals(1,testPublicCard.check(board,player,parameterforcard));

        parameterforcard.add(1,234);
        assertEquals(-1,testPublicCard.check(board,player,parameterforcard));

        windowTest.removeDie(0,0);
        die1.setValue(Value.FIVE);
        windowTest.insertDie(die1,0,0,"colour");

        parameterforcard.clear();
        parameterforcard.add(0);
        parameterforcard.add(0);
        parameterforcard.add(1);
        parameterforcard.add(1);

        assertEquals(-24,testPublicCard.check(board,player,parameterforcard));

        parameterforcard.add(2,0);
        parameterforcard.add(3,2);

        assertEquals(-6,testPublicCard.check(board,player,parameterforcard));

    }

    @Test
    public void testEffect() {

        boardBoxes=new Box[4][5];


        for ( int i = 0; i < 4; i++){

            for ( int j = 0; j < 5; j++){

                if(i==0 && j==0){

                    boardBoxes[i][j] = new Box(Colour.RED);

                }else{

                    boardBoxes[i][j] = new Box(Colour.YELLOW);

                }
            }
        }

        Die die1=new Die(Colour.RED);
        Die die2=new Die(Colour.YELLOW);
        die2.setValue(Value.TWO);

        Window windowTest=new Window("test",4,boardBoxes,"test");
        windowTest.insertDie(die1,0,0,"colour");
        windowTest.insertDie(die2,0,1,"value");

        parameterforcard.add(0);
        parameterforcard.add(0);
        parameterforcard.add(0);
        parameterforcard.add(2);

        player=mock(Player.class);
        when(player.getWindow()).thenReturn(windowTest);

        testPublicCard.effect(board,player,parameterforcard);

        assertEquals(die1,player.getWindow().getDieFromBoardBox(0,2));

    }
}