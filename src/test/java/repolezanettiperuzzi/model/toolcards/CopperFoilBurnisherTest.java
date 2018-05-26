package repolezanettiperuzzi.model.toolcards;

import org.junit.Test;
import repolezanettiperuzzi.model.*;
import repolezanettiperuzzi.model.publiccards.PublicCard;

import java.util.ArrayList;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CopperFoilBurnisherTest {

    private CopperFoilBurnisher testPublicCard=new CopperFoilBurnisher();
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

                }else if(i==1 && j==1){

                    boardBoxes[i][j]=new Box(Colour.BLUE);

                }else {

                    boardBoxes[i][j] = new Box(Value.FIVE);

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
        parameterforcard.add(1);
        parameterforcard.add(1);

        player=mock(Player.class);
        when(player.getWindow()).thenReturn(windowTest);

        assertEquals(-5,testPublicCard.check(board,player,parameterforcard));

        parameterforcard.clear();
        parameterforcard.add(0);
        parameterforcard.add(0);
        parameterforcard.add(0);
        parameterforcard.add(2);

        assertEquals(1,testPublicCard.check(board,player,parameterforcard));

        parameterforcard.add(3,123);

        assertEquals(-1,testPublicCard.check(board,player,parameterforcard));

        parameterforcard.clear();
        parameterforcard.add(0);
        parameterforcard.add(2);
        parameterforcard.add(0);
        parameterforcard.add(3);

        assertEquals(-2,testPublicCard.check(board,player,parameterforcard));

        parameterforcard.add(1,0);

        assertEquals(-4,testPublicCard.check(board,player,parameterforcard));

        parameterforcard.clear();
        parameterforcard.add(0);
        parameterforcard.add(0);
        parameterforcard.add(0);
        parameterforcard.add(1);

        assertEquals(-3,testPublicCard.check(board,player,parameterforcard));

        parameterforcard.add(3,2);
        windowTest.removeDie(0,1);
        Die die3=new Die(Colour.RED);
        die3.setValue(Value.FIVE);
        windowTest.insertDie(die3,0,1,"both");

        assertEquals(-23,testPublicCard.check(board,player,parameterforcard));

    }

    @Test
    public void testGetId() {

        assertEquals(3,testPublicCard.getId());

    }

    @Test
    public void testEffect() {

        boardBoxes=new Box[4][5];

        for ( int i = 0; i < 4; i++){

            for ( int j = 0; j < 5; j++){

                if(i==0 && j==0){

                    boardBoxes[i][j] = new Box(Colour.RED);

                }else{

                    boardBoxes[i][j] = new Box(Value.SIX);

                }
            }
        }

        Die die1=new Die(Colour.RED);
        Die die2=new Die(Colour.YELLOW);
        die2.setValue(Value.SIX);

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
        assertEquals(null,player.getWindow().getDieFromBoardBox(0,0));

    }
}