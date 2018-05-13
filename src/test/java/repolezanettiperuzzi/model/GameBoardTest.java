package repolezanettiperuzzi.model;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GameBoardTest {

    private GameBoard boardTest;
    private Player gamer;

    @Test
    public void addPlayer() {

        boardTest=new GameBoard();
        gamer=mock(Player.class);
        when(gamer.getName()).thenReturn("Pippo");

        boardTest.addPlayer(gamer.getName());

        assertEquals("Pippo",gamer.getName());
    }

    @Test
    public void getNPlayers() {

        boardTest=new GameBoard();
        gamer=mock(Player.class);
        when(gamer.getName()).thenReturn("Pippo");
        Player gamer1=mock(Player.class);
        when(gamer1.getName()).thenReturn("Topolino");

        boardTest.addPlayer(gamer.getName());
        boardTest.addPlayer(gamer1.getName());

        assertEquals(2,boardTest.getNPlayers());
    }

    @Test
    public void getDieDraft() {

        boardTest=new GameBoard();
        ArrayList<Die> diceForDraft= new ArrayList<>();
        Die die1=new Die(Colour.RED);
        Die die2=new Die(Colour.RED);
        diceForDraft.add(die1);
        diceForDraft.add(die2);

        boardTest.setDiceDraft(diceForDraft);
        Die dieTest=boardTest.getDieDraft(0);

        assertEquals(dieTest,die1);
    }

    @Test
    public void setDieDraft() {

        boardTest=new GameBoard();
        ArrayList<Die> diceForDraft= new ArrayList<>();
        Die die1=new Die(Colour.RED);
        Die die2=new Die(Colour.RED);
        diceForDraft.add(die1);
        diceForDraft.add(die2);

        boardTest.setDiceDraft(diceForDraft);
        Die dieTest=new Die(Colour.GREEN);
        boardTest.setDieDraft(0,dieTest);

        assertEquals(dieTest,boardTest.getDieDraft(0));
    }

    @Test
    public void putDieInBag() {

        boardTest=new GameBoard();
        int okay=0;
        ArrayList<Die> diceForDraft= new ArrayList<>();
        Die die1=new Die(Colour.RED);
        Die die2=new Die(Colour.RED);
        diceForDraft.add(die1);
        diceForDraft.add(die2);

        boardTest.setDiceDraft(diceForDraft);
        boardTest.putDieInBag(0);
        boardTest.removeDieFromDraft(0);

        for(int i=0;i<91;i++){

            Die dieTest=boardTest.takeDieFromBag();
            if(dieTest==die1){

                okay=1;
                break;

            }
        }

        assertEquals(1,okay);
    }


    @Test
    public void addDieToDraft() {

        boardTest = new GameBoard();
        ArrayList<Die> diceForDraft = new ArrayList<>();
        Die die1 = new Die(Colour.RED);
        Die die2 = new Die(Colour.RED);
        Die dieTest = new Die(Colour.GREEN);
        diceForDraft.add(die1);
        diceForDraft.add(die2);

        boardTest.setDiceDraft(diceForDraft);
        boardTest.addDieToDraft(dieTest);

        assertEquals(3, boardTest.getSizeDraft());
    }

    @Test
    public void removeDieFromDraft(){
        boardTest = new GameBoard();
        ArrayList<Die> diceForDraft = new ArrayList<>();
        Die die1 = new Die(Colour.RED);
        Die die2 = new Die(Colour.RED);
        diceForDraft.add(die1);
        diceForDraft.add(die2);
        boardTest.setDiceDraft(diceForDraft);

        boardTest.removeDieFromDraft(0);
        assertEquals(1,boardTest.getSizeDraft());
        assertEquals(die2,boardTest.getDieDraft(0));

    }

    @Test
    public void getDieFromRoundTrack() {

        boardTest = new GameBoard();
        ArrayList<Die> diceForDraft = new ArrayList<>();
        Die die1 = new Die(Colour.RED);
        Die die2 = new Die(Colour.RED);
        diceForDraft.add(die1);
        diceForDraft.add(die2);
        boardTest.setDiceDraft(diceForDraft);
        boardTest.addDiceToRoundTrack();

        assertEquals(0,boardTest.getSizeDraft());

        assertEquals(die2,boardTest.getDieFromRoundTrack(0,1));


    }

    @Test
    public void setDieToRoundTrack() {

        boardTest = new GameBoard();
        ArrayList<Die> diceForDraft = new ArrayList<>();
        Die die1 = new Die(Colour.RED);
        Die die2 = new Die(Colour.RED);
        Die testDie=new Die(Colour.BLUE);
        diceForDraft.add(die1);
        diceForDraft.add(die2);
        boardTest.setDiceDraft(diceForDraft);
        boardTest.addDiceToRoundTrack();
        boardTest.setDieToRoundTrack(0,0,testDie);

        assertEquals(testDie,boardTest.getDieFromRoundTrack(0,0));
    }

    @Test
    public void getCostToolCard() {

        boardTest = new GameBoard();

        assertEquals(1,boardTest.getCostToolCard(1));

        boardTest.setCostToolCard(1);

        assertEquals(2,boardTest.getCostToolCard(1));

    }

    @Test
    public void getToolCards() {


    }

    @Test
    public void getPublicCards() {


    }

    @Test
    public void getId() {


    }

    @Test
    public void getRound() {

        boardTest = new GameBoard();
        boardTest.incrRound();

        assertEquals(2,boardTest.getRound());
    }


    @Test
    public void getPlayer() {


    }
}