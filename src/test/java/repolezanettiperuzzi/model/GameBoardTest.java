package repolezanettiperuzzi.model;

import org.junit.Test;
import repolezanettiperuzzi.model.publiccards.DeepShades;
import repolezanettiperuzzi.model.publiccards.LightShades;
import repolezanettiperuzzi.model.publiccards.PublicCard;
import repolezanettiperuzzi.model.publiccards.RowShadeVariety;
import repolezanettiperuzzi.model.toolcards.*;

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
        assertNull(boardTest.getDieDraft(-1));
        assertNull(boardTest.getDieDraft(boardTest.getSizeDraft()+1));

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

        assertEquals(Lathekin.class,boardTest.getToolCard(0).getClass());
        assertEquals(GlazingHammer.class,boardTest.getToolCard(1).getClass());

    }

    @Test
    public void testGetPublicCards() {

        boardTest=new GameBoard();
        PublicCard cardPublic=new RowShadeVariety();

        boardTest.setPublicCards(cardPublic,0);
        assertEquals(RowShadeVariety.class,boardTest.getPublicCards(0).getClass());

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

        ArrayList<Player> players=(ArrayList<Player>)boardTest.getPlayersCopy();

        assertEquals("tom",players.get(0).getName());

    }

    @Test
    public void testRemovePlayer(){

        boardTest=new GameBoard();

        boardTest.addPlayer("tom","RMI","CLI","127.0.0.1",8008);
        boardTest.addPlayer("jerry","RMI","CLI","127.0.0.1",8008);
        assertEquals(2,boardTest.getNPlayers());
        boardTest.removePlayer(0);
        assertEquals(1,boardTest.getNPlayers());
        assertEquals("jerry",boardTest.getPlayer(0).getName());

    }

    @Test
    public void testPlayersOnLine(){

        boardTest=new GameBoard();
        boardTest.addPlayer("tom","RMI","CLI","127.0.0.1",8008);
        boardTest.getPlayer(0).setLiveStatus(true);
        assertEquals(1,boardTest.getPlayersOnline());
        boardTest.getPlayer(0).setLiveStatus(false);
        assertEquals(0,boardTest.getPlayersOnline());


        assertEquals("tom",boardTest.getPlayerByName("tom").getName());
        assertNull(boardTest.getPlayerByName("lode"));


    }

   @Test
   public void testWindowPool(){

       boardTest=new GameBoard();
       Box[][] testBoxes = new Box[4][5];

       for ( int i = 0; i < 4; i++){

           for ( int j = 0; j < 5; j++){

               testBoxes[i][j]= new Box(Colour.RED);

           }
       }

       String name = "testWindow";
       Window tempWindow = new Window(name,5, testBoxes,"test");

       boardTest.addPlayer("jerry","RMI","CLI","127.0.0.1",8008);
       Box[][] testBoxes2 = new Box[4][5];

       for ( int i = 0; i < 4; i++){

           for ( int j = 0; j < 5; j++){

               testBoxes2[i][j]= new Box(Colour.BLUE);

           }
       }

       String name2 = "testWindow2";
       Window tempWindow2 = new Window(name2,5, testBoxes2,"test2");

       ArrayList<Window> windows=new ArrayList<>();
       windows.add(tempWindow);
       windows.add(tempWindow2);

       boardTest.setWindowsPool(windows);
       assertEquals("testWindow",boardTest.getWindowsPool().get(0).getName());
   }

   @Test
    public void testPlayerWindowChoices(){

        boardTest=new GameBoard();
        boardTest.initPlayersWindowsChoices();

        Player player1=new Player("jerry","RMI","CLI","127.0.0.1",8008);
       Box[][] testBoxes = new Box[4][5];

       for ( int i = 0; i < 4; i++){

           for ( int j = 0; j < 5; j++){

               testBoxes[i][j]= new Box(Colour.RED);

           }
       }

       String name = "testWindow";
       Window tempWindow = new Window(name,5, testBoxes,"test");

       Box[][] testBoxes2 = new Box[4][5];

       for ( int i = 0; i < 4; i++){

           for ( int j = 0; j < 5; j++){

               testBoxes2[i][j]= new Box(Colour.BLUE);

           }
       }

       String name2 = "testWindow2";
       Window tempWindow2 = new Window(name2,5, testBoxes2,"test2");

       ArrayList<Window> windows=new ArrayList<>();
       windows.add(tempWindow);
       windows.add(tempWindow2);

       boardTest.putPlayersWindowsChoices(player1,windows);
       assertEquals("testWindow",boardTest.getPlayersWindowsChoices(player1).get(0).getName());
       assertEquals("testWindow2",boardTest.getPlayersWindowsChoices(player1).get(1).getName());

   }

   @Test
    public void testFetchPlayer(){

        boardTest=new GameBoard();
        boardTest.setFetchPlayersToCheck(3);
        assertEquals(3,boardTest.getFetchPlayersToCheck());
        boardTest.incrFetchReadyPlayers();
        assertEquals(1,boardTest.getFetchReadyPlayers());

   }

   @Test
    public void testGameLocked(){

        boardTest=new GameBoard();
        boardTest.setGameLocked();
        assertTrue(boardTest.isGameLocked());

   }

   @Test
    public void testToString(){

        boardTest=new GameBoard();

        Die d1=new Die(Colour.RED);
        Die d2=new Die(Colour.RED);
        Die d3=new Die(Colour.YELLOW);
        Die d4=new Die(Colour.GREEN);
        Die d5=new Die(Colour.GREEN);
        Die d6=new Die(Colour.PURPLE);
        Die d7=new Die(Colour.YELLOW);
        Die d8=new Die(Colour.BLUE);

        boardTest.addDieToDraft(d1);
        boardTest.addDieToDraft(d2);
        boardTest.addDiceToRoundTrack();

        boardTest.addDieToDraft(d3);
        boardTest.addDieToDraft(d4);
        boardTest.addDiceToRoundTrack();

        boardTest.addDieToDraft(d5);
        boardTest.addDieToDraft(d6);
        boardTest.addDiceToRoundTrack();

        boardTest.addDieToDraft(d7);
        boardTest.addDieToDraft(d8);

        assertEquals("Y1_B1",boardTest.toStringDraft());
        assertEquals("1R1_R1-2Y1_G1-3G1_P1",boardTest.toStringRoundTrack());

        CopperFoilBurnisher cardTool1=new CopperFoilBurnisher();
        cardTool1.setDescription("ciao");
        cardTool1.setTitle("CIAO");
        EglomiseBrush cardTool2=new EglomiseBrush();
        cardTool2.setDescription("mondo");
        cardTool2.setTitle("MONDO");
        GlazingHammer cardTool3=new GlazingHammer();
        cardTool3.setDescription("Terra");
        cardTool3.setTitle("TERRA");

        RowShadeVariety cardPublic1=new RowShadeVariety();
        DeepShades cardPublic2=new DeepShades();
        LightShades cardPublic3=new LightShades();

        cardPublic1.setDescription("fortissimo");
        cardPublic1.setTitle("FORTISSIMO");
        cardPublic1.setValue(10);
        cardPublic2.setDescription("pump it");
        cardPublic2.setTitle("PUMP IT");
        cardPublic2.setValue(1000);
        cardPublic3.setDescription("it");
        cardPublic3.setTitle("IT");
        cardPublic3.setValue(1);

        boardTest.setToolCards(cardTool1,0);
        boardTest.setToolCards(cardTool2,1);
        boardTest.setToolCards(cardTool3,2);
        boardTest.setPublicCards(cardPublic1,0);
        boardTest.setPublicCards(cardPublic2,1);
        boardTest.setPublicCards(cardPublic3,2);

        assertEquals("FORTISSIMO_fortissimo_10*PUMP IT_pump-it_1000*IT_it_1",boardTest.toStringPublicCards());
        assertEquals("CIAO_3_ciao_1*MONDO_2_mondo_1*TERRA_7_Terra_1",boardTest.toStringToolCards());

   }

}