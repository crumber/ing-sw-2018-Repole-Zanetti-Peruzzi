package repolezanettiperuzzi.model;

import org.junit.Test;

import static org.junit.Assert.*;

//testa la classe Player
public class PlayerTest {

    Player player;

    // testa che funzionino i get sugli attributi del player
    @Test
    public void getNamePortAddressConnectionScore() {

        player=new Player("topolino","RMI","CLI","127.0.0.1",8008);

        Box[][] testBoxes = new Box[4][5];

        for ( int i = 0; i < 4; i++){

            for ( int j = 0; j < 5; j++){

                testBoxes[i][j]= new Box(Colour.BLUE);

            }
        }

        String name = "testWindow2";
        Window tempWindow2 = new Window(name,5, testBoxes,"test1");
        player.setWindow(tempWindow2);

        Player playerCopy=new Player(player);
        playerCopy.updateScore(123);

        assertEquals(123,playerCopy.getScore());
        assertEquals("topolino",playerCopy.getName());
        assertEquals("testWindow2",playerCopy.getWindow().getName());
        assertEquals("RMI",playerCopy.getConnection());
        assertEquals(8008,playerCopy.getPort());
        assertEquals("127.0.0.1",playerCopy.getAddress());

    }

    //testa che funzioni l'inizializzazione del prametro che indica se il player ha gia inserito il dado
    @Test
    public void setGetInserDieInThisTurn() {

        player=new Player("pippo","RMI","CLI","127.0.0.1",8008);
        player.setInsertDieInThisTurn(true);

        assertEquals(true,player.getInsertDieInThisTurn());

    }

    // test che controlla che funzioni l'inizializzazione del colore segnreto
    @Test
    public void testSetGetSecretColour() {

        player=new Player("pippo","RMI","CLI","127.0.0.1",8008);

        player.setSecretColour(Colour.RED);

        assertEquals(Colour.RED,player.getSecretColour());
    }

    //testa che vengano ridotti i fv
    @Test
    public void testReduceFavorTokens() {

        player=new Player("pippo","RMI","CLI","127.0.0.1",8008);

        player.setFavorTokens(12);
        assertEquals(12,player.getFavorTokens());

        player.reduceFavorTokens(1);
        assertEquals(11,player.getFavorTokens());
    }

    //testa che venga incrementato/resettato il turno
    @Test
    public void testIncrTurnResetTurn() {

        player = new Player("pippo","RMI","CLI","127.0.0.1",8008);

        player.incrTurn();
        assertEquals(1,player.getTurn());

        player.resetTurn();
        assertEquals(0,player.getTurn());
    }

    //tetsa che copia il player
    @Test
    public void testCopy() {

        player=new Player("pippo","RMI","CLI","127.0.0.1",8008);

        Box[][] testBoxes = new Box[4][5];

        for ( int i = 0; i < 4; i++){

            for ( int j = 0; j < 5; j++){

                testBoxes[i][j]= new Box(Colour.RED);

            }
        }

        String name = "testWindow";
        Window tempWindow = new Window(name,5, testBoxes,"test");
        player.setWindow(tempWindow);

        Player playerCopy=player.copy();

        assertEquals("pippo",playerCopy.getName());
        assertEquals("testWindow",playerCopy.getWindow().getName());
    }

    // testa la last scene
    @Test
    public void testCheckLastScene(){

        player=new Player("naruto","RMI","CLI","127.0.0.1",8008);

        player.setLastScene("faseGame");
        assertTrue(player.checkLastScene("faseGame"));
        assertEquals("faseGame", player.getLastScene());
        assertFalse(player.checkLastScene("login"));

    }

    //tetsa il live status
    @Test
    public void testLiveStatus(){

        player=new Player("naruto","RMI","CLI","127.0.0.1",8008);

        player.setLiveStatus(true);
        assertTrue(player.getLiveStatus());
        player.setLiveStatus(false);
        assertFalse(player.getLiveStatus());
        assertTrue(!player.getLiveStatus());

    }

    //testa il tipo di connessione
    @Test
    public void testSetTypeConnection(){

        player=new Player("Sasuke","","","",0);
        assertNotEquals("RMI",player.getConnection());
        assertNotEquals("CLI",player.getUI());
        assertNotEquals("127.0.0.1",player.getAddress());
        assertNotEquals(8008,player.getPort());

        player.setConnection("RMI");
        player.setUI("CLI");
        player.setAddress("127.0.0.1");
        player.setPort(8008);

        assertEquals("RMI",player.getConnection());
        assertEquals("CLI",player.getUI());
        assertEquals("127.0.0.1",player.getAddress());
        assertEquals(8008,player.getPort());
    }
}