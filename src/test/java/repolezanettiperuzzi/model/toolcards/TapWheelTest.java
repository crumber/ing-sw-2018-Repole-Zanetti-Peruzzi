package repolezanettiperuzzi.model.toolcards;

import org.junit.Test;
import repolezanettiperuzzi.model.*;

import java.util.ArrayList;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TapWheelTest {

    private TapWheel testPublicCard=new TapWheel();
    private GameBoard board=new GameBoard();
    private Player player;
    private ArrayList<Integer> parameterforcard=new ArrayList<>();
    private Box[][] boardBoxes;

    @Test
    public void testCheck() {

        boardBoxes=new Box[4][5];

        for ( int i = 0; i < 4; i++){

            for ( int j = 0; j < 5; j++){

                if((i==0 && j==0) || (i==1 && j==1)){

                    boardBoxes[i][j] = new Box(Colour.RED);

                }else if((i==2 && j==2) ||(i==3 && j==3)){

                    boardBoxes[i][j] = new Box(Colour.BLUE);

                }else if(j==0){

                    boardBoxes[i][j] = new Box(Value.ONE);

                }else if(j==1){

                    boardBoxes[i][j] = new Box(Value.TWO);

                }else if(j==2){

                    boardBoxes[i][j] = new Box(Value.THREE);

                }else if(j==3) {

                    boardBoxes[i][j] = new Box(Value.FOUR);

                }else{

                    boardBoxes[i][j] = new Box(Value.SIX);

                }

            }
        }

        Die die1=new Die(Colour.RED);
        Die die2=new Die(Colour.RED);
        Die die3=new Die(Colour.BLUE);

        die2.setValue(Value.TWO);
        die3.setValue(Value.THREE);

        Window windowTest=new Window("test",4,boardBoxes,"test");
        windowTest.insertDie(die1,0,0,"colour");
        windowTest.insertDie(die2,1,1,"colour");

        player=mock(Player.class);
        when(player.getWindow()).thenReturn(windowTest);

        Die die4=new Die(Colour.RED);
        Die die5=new Die(Colour.BLUE);

        board.addDieToDraft(die4);
        board.addDieToDraft(die5);
        board.addDiceToRoundTrack();

        Die die6=new Die(Colour.PURPLE);
        Die die7=new Die(Colour.BLUE);
        Die die8=new Die(Colour.GREEN);
        Die die9=new Die(Colour.RED);

        board.addDieToDraft(die6);
        board.addDieToDraft(die7);
        board.addDieToDraft(die8);
        board.addDieToDraft(die9);
        board.addDiceToRoundTrack();

        parameterforcard.add(0);
        parameterforcard.add(0);
        parameterforcard.add(2);
        parameterforcard.add(0);
        parameterforcard.add(1);
        parameterforcard.add(1);
        parameterforcard.add(3);
        parameterforcard.add(1);
        parameterforcard.add(1);
        parameterforcard.add(3);

        assertEquals(1,testPublicCard.check(board,player,parameterforcard));

        //testo errore su move one die dovuto alla posizione non esistente nella window
        parameterforcard.add(3,1234);

        assertEquals(-1,testPublicCard.check(board,player,parameterforcard));

        //testo che mi ritorni errore -21 dovuto al fatto che non esiste un dado nella posizione scelta del round track
        parameterforcard.clear();
        parameterforcard.add(0);
        parameterforcard.add(0);
        parameterforcard.add(2);
        parameterforcard.add(0);
        parameterforcard.add(1);
        parameterforcard.add(1);
        parameterforcard.add(3);
        parameterforcard.add(1);
        parameterforcard.add(1);
        parameterforcard.add(1234);

        assertEquals(-21,testPublicCard.check(board,player,parameterforcard));

        //testo che mi ritorni errore -22 dovuto al fatto che i dadi sono di un colore diverso da quello scelto nel round track
        parameterforcard.clear();
        parameterforcard.add(0);
        parameterforcard.add(0);
        parameterforcard.add(2);
        parameterforcard.add(0);
        parameterforcard.add(1);
        parameterforcard.add(1);
        parameterforcard.add(3);
        parameterforcard.add(1);
        parameterforcard.add(1);
        parameterforcard.add(2);

        assertEquals(-22,testPublicCard.check(board,player,parameterforcard));

        //testo errore -22 dovuto al diverso colore dei 2 dadi da muovere scelti

        windowTest.insertDie(die3,3,1,"none");

        parameterforcard.clear();
        parameterforcard.add(0);
        parameterforcard.add(0);
        parameterforcard.add(2);
        parameterforcard.add(0);
        parameterforcard.add(3);
        parameterforcard.add(1);
        parameterforcard.add(0);
        parameterforcard.add(2);
        parameterforcard.add(1);
        parameterforcard.add(2);

        assertEquals(-22,testPublicCard.check(board,player,parameterforcard));

    }

    @Test
    public void testEffect() {

        boardBoxes=new Box[4][5];

        for ( int i = 0; i < 4; i++){

            for ( int j = 0; j < 5; j++){

                if((i==0 && j==0) || (i==1 && j==1)){

                    boardBoxes[i][j] = new Box(Colour.YELLOW);

                }else if(j==0){

                    boardBoxes[i][j] = new Box(Value.THREE);

                }else if(j==1){

                    boardBoxes[i][j] = new Box(Value.FOUR);

                }else{

                    boardBoxes[i][j] = new Box(Value.SIX);

                }

            }
        }

        Die die1=new Die(Colour.YELLOW);
        die1.setValue(Value.THREE);

        Die die2=new Die(Colour.YELLOW);
        die2.setValue(Value.FOUR);

        Window windowTest=new Window("test",4,boardBoxes,"test");
        windowTest.insertDie(die1,0,0,"colour");
        windowTest.insertDie(die2,1,1,"colour");

        player=mock(Player.class);
        when(player.getWindow()).thenReturn(windowTest);

        Die die4=new Die(Colour.RED);
        Die die5=new Die(Colour.YELLOW);

        board.addDieToDraft(die4);
        board.addDieToDraft(die5);
        board.addDiceToRoundTrack();

        parameterforcard.add(0);
        parameterforcard.add(0);
        parameterforcard.add(2);
        parameterforcard.add(0);
        parameterforcard.add(1);
        parameterforcard.add(1);
        parameterforcard.add(3);
        parameterforcard.add(1);
        parameterforcard.add(0);
        parameterforcard.add(1);

        assertEquals(1,testPublicCard.check(board,player,parameterforcard));

        testPublicCard.effect(board,player,parameterforcard);

        assertEquals(die1,player.getWindow().getDieFromBoardBox(2,0));
        assertEquals(die2,player.getWindow().getDieFromBoardBox(3,1));
        assertFalse(player.getWindow().thereIsDie(0,0));
        assertFalse(player.getWindow().thereIsDie(1,1));
    }
}