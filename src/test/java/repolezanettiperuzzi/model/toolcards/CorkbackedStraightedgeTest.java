package repolezanettiperuzzi.model.toolcards;

import org.junit.Test;
import repolezanettiperuzzi.model.*;

import java.util.ArrayList;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CorkbackedStraightedgeTest {

    private CorkbackedStraightedge testPublicCard=new CorkbackedStraightedge();
    private GameBoard board=new GameBoard();
    private Player player;
    private ArrayList<Integer> parameterforcard=new ArrayList<>();
    private Box[][] boardBoxes;

    @Test
    public void getId() {

        assertEquals(9,testPublicCard.getId());

    }

    @Test
    public void check() {

        boardBoxes=new Box[4][5];


        for ( int i = 0; i < 4; i++) {

            for (int j = 0; j < 5; j++) {

                if (i == 0 && j == 0) {

                    boardBoxes[i][j] = new Box(Colour.GREEN);

                } else {

                    boardBoxes[i][j] = new Box(Colour.BLUE);
                }
            }
        }

        Die die1=new Die(Colour.GREEN);
        Window windowTest=new Window("test",4,boardBoxes,"test");
        windowTest.insertDie(die1,0,0,"colour");

        player=mock(Player.class);
        when(player.getWindow()).thenReturn(windowTest);

        Die die2=new Die(Colour.BLUE);
        board.addDieToDraft(die2);

        parameterforcard.add(0);
        parameterforcard.add(3);
        parameterforcard.add(3);

        assertEquals(1,testPublicCard.check(board,player,parameterforcard));

        parameterforcard.add(2,133);
        assertEquals(-1,testPublicCard.check(board,player,parameterforcard));

        parameterforcard.add(1,0);
        parameterforcard.add(2,1);

        assertEquals(-10,testPublicCard.check(board,player,parameterforcard));

        parameterforcard.add(1,0);
        parameterforcard.add(2,0);

        assertEquals(-3,testPublicCard.check(board,player,parameterforcard));

        windowTest.removeDie(0,0);

        assertEquals(-7,testPublicCard.check(board,player,parameterforcard));

        parameterforcard.add(0,12);

        assertEquals(-9,testPublicCard.check(board,player,parameterforcard));

    }

    @Test
    public void effect() {

        boardBoxes=new Box[4][5];


        for ( int i = 0; i < 4; i++) {

            for (int j = 0; j < 5; j++) {

                boardBoxes[i][j] = new Box(Colour.PURPLE);

            }
        }

        Die die1=new Die(Colour.PURPLE);
        Window windowTest=new Window("test",4,boardBoxes,"test");

        player=mock(Player.class);
        when(player.getWindow()).thenReturn(windowTest);

        board.addDieToDraft(die1);

        parameterforcard.add(0);
        parameterforcard.add(3);
        parameterforcard.add(3);

        assertEquals(1,testPublicCard.check(board,player,parameterforcard));

        testPublicCard.effect(board,player,parameterforcard);

        assertEquals(die1,windowTest.getDieFromBoardBox(3,3));
        assertEquals(0,board.getSizeDraft());

    }
}