package repolezanettiperuzzi.model;

import org.junit.Test;
import repolezanettiperuzzi.model.publiccards.PublicCard;
import repolezanettiperuzzi.model.publiccards.RowShadeVariety;
import repolezanettiperuzzi.model.toolcards.GlazingHammer;
import repolezanettiperuzzi.model.toolcards.Lathekin;
import repolezanettiperuzzi.model.toolcards.ToolCard;

import java.util.ArrayList;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GameBoardTest {

    private GameBoard boardTest;
    private Player gamer;

    @Test
    public void testAddPlayer() {

        boardTest=new GameBoard();
        boardTest.addPlayer("Pippo","RMI","CLI","127.0.0.1",8008);
        assertEquals("Pippo",boardTest.getPlayer(0).getName());

    }

    @Test
    public void testGetNPlayers() {

        boardTest=new GameBoard();
        gamer=mock(Player.class);
        when(gamer.getName()).thenReturn("Pippo");
        Player gamer1=mock(Player.class);
        when(gamer1.getName()).thenReturn("Topolino");

        boardTest.addPlayer(gamer.getName(),"RMI","CLI","127.0.0.1",8008);
        boardTest.addPlayer(gamer1.getName(),"RMI","CLI","127.0.0.1",8008);

        assertEquals(2,boardTest.getNPlayers());


    }

    @Test
    public void testGetDieDraft() {

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
    public void testSetDieDraft() {

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
    public void testPutDieInBag() {

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
    public void testAddDieToDraft() {

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
    public void testRemoveDieFromDraft(){
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
    public void testGetDieFromRoundTrack() {

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
    public void testSetDieToRoundTrack() {

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
    public void testGetCostToolCard() {

        boardTest = new GameBoard();

        assertEquals(1,boardTest.getCostToolCard(1));

        boardTest.setCostToolCard(1);

        assertEquals(2,boardTest.getCostToolCard(1));

    }

    @Test
    public void testGetToolCards() {

        boardTest=new GameBoard();
        ToolCard cardTool=new Lathekin();
        ToolCard cardTool1=new GlazingHammer();

        boardTest.setToolCards(cardTool,0);
        boardTest.setToolCards(cardTool1,1);

        assertEquals(4,boardTest.getId(0));
        assertEquals(7,boardTest.getId(1));

        assertEquals(Lathekin.class,boardTest.getToolCards(0).getClass());
        assertEquals(GlazingHammer.class,boardTest.getToolCards(1).getClass());

    }

    @Test
    public void testGetPublicCards() {

        boardTest=new GameBoard();
        PublicCard cardPublic=new RowShadeVariety();

        boardTest.setPublicCards(cardPublic,0);
        assertEquals(RowShadeVariety.class,boardTest.getPublicCards(0).getClass());

    }

    @Test
    public void testGetRound() {

        boardTest = new GameBoard();
        boardTest.incrRound();

        assertEquals(1,boardTest.getRound());
    }

    @Test
    public void testGetPlayers() {

        boardTest=new GameBoard();

        boardTest.addPlayer("pippo","RMI","CLI","127.0.0.1",8008);
        boardTest.addPlayer("topolino","RMI","CLI","127.0.0.1",8008);

        ArrayList<Player> players=(ArrayList<Player>) boardTest.getPlayers();

        assertEquals("pippo",players.get(0).getName());

    }

    @Test
    public void getPlayersCopy() {

        boardTest=new GameBoard();

        boardTest.addPlayer("tom","RMI","CLI","127.0.0.1",8008);
        Box[][] testBoxes = new Box[4][5];

        for ( int i = 0; i < 4; i++){

            for ( int j = 0; j < 5; j++){

                testBoxes[i][j]= new Box(Colour.RED);

            }
        }

        String name = "testWindow";
        Window tempWindow = new Window(name,5, testBoxes,"test");
        boardTest.getPlayer(0).setWindow(tempWindow);

        boardTest.addPlayer("jerry","RMI","CLI","127.0.0.1",8008);
        Box[][] testBoxes2 = new Box[4][5];

        for ( int i = 0; i < 4; i++){

            for ( int j = 0; j < 5; j++){

                testBoxes2[i][j]= new Box(Colour.BLUE);

            }
        }

        String name2 = "testWindow2";
        Window tempWindow2 = new Window(name2,5, testBoxes2,"test2");
        boardTest.getPlayer(1).setWindow(tempWindow2);

        ArrayList<Player> players=boardTest.getPlayersCopy();

        assertEquals("tom",players.get(0).getName());

    }
}