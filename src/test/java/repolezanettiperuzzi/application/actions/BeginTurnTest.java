package repolezanettiperuzzi.application.actions;

import org.junit.Before;
import org.junit.Test;
import repolezanettiperuzzi.model.GameBoard;
import repolezanettiperuzzi.model.Player;

import static org.junit.Assert.*;

//test sulla classe begin turn
public class BeginTurnTest {

    BeginTurn testBeginTurn=new BeginTurn();

    @Before
    public void setUp() {
        BeginRound.resetIndex();
        BeginRound.resetRound();
        BeginTurn.resetCurrentTurn();
        BeginTurn.resetNumPlayedTurn();
        BeginTurn.resetCurrentPlayer();
    }

    //testo che svolga l'azione in modo corretto
    @Test
    public void doAction() {

        BeginRound.resetIndex();
        BeginTurn.resetCurrentTurn();
        BeginTurn.resetNumPlayedTurn();
        BeginTurn.resetCurrentPlayer();


        GameBoard gameBoard=new GameBoard();
        gameBoard.addPlayer("ale","sda","rere","13521.122",12421);

        assertEquals(0,BeginTurn.getCurrentPlayer());
        assertEquals(0,BeginTurn.getCurrentTurn());
        assertEquals(0,BeginTurn.getNumPlayedTurn());

        testBeginTurn.doAction(gameBoard.getPlayer(BeginTurn.getCurrentPlayer()),gameBoard);

        assertEquals(0,BeginTurn.getCurrentPlayer());
        assertEquals(0,BeginTurn.getCurrentTurn());
        assertTrue(BeginTurn.controlTurn(gameBoard.getPlayer(0)));

    }

    //test sulla creazione dei parametri per il turno successivo
    @Test
    public void nextTurnParameters() {

        BeginTurn.resetCurrentTurn();
        BeginTurn.resetNumPlayedTurn();
        BeginTurn.resetCurrentPlayer();

        GameBoard gameBoard=new GameBoard();
        gameBoard.addPlayer("ale","sda","rere","13521.122",12421);
        gameBoard.addPlayer("fede","assa","rerereff","65.21.8788",5335);
        gameBoard.addPlayer("lele","sda","rere","13521.122",12421);
        gameBoard.addPlayer("ywyw","assa","rerereff","65.21.8788",5335);

        BeginRound.increaseIndex();
        BeginTurn.resetCurrentPlayer();

        //faccio un'andata e un ritorno
        assertTrue(BeginTurn.controlTurn(gameBoard.getPlayer(1)));
        BeginTurn.nextTurnParameters(gameBoard,gameBoard.getPlayer(BeginTurn.getCurrentPlayer()));
        assertEquals(1,gameBoard.getPlayer(1).getTurn());
        assertEquals(0,BeginTurn.getCurrentTurn());

        assertTrue(BeginTurn.controlTurn(gameBoard.getPlayer(2)));
        BeginTurn.nextTurnParameters(gameBoard,gameBoard.getPlayer(BeginTurn.getCurrentPlayer()));
        assertEquals(1,gameBoard.getPlayer(2).getTurn());
        assertEquals(0,BeginTurn.getCurrentTurn());

        assertTrue(BeginTurn.controlTurn(gameBoard.getPlayer(3)));
        BeginTurn.nextTurnParameters(gameBoard,gameBoard.getPlayer(BeginTurn.getCurrentPlayer()));
        assertEquals(1,gameBoard.getPlayer(3).getTurn());
        assertEquals(0,BeginTurn.getCurrentTurn());

        assertTrue(BeginTurn.controlTurn(gameBoard.getPlayer(0)));
        BeginTurn.nextTurnParameters(gameBoard,gameBoard.getPlayer(BeginTurn.getCurrentPlayer()));
        assertEquals(1,gameBoard.getPlayer(0).getTurn());
        assertEquals(1,BeginTurn.getCurrentTurn());

        assertTrue(BeginTurn.controlTurn(gameBoard.getPlayer(0)));
        BeginTurn.nextTurnParameters(gameBoard,gameBoard.getPlayer(BeginTurn.getCurrentPlayer()));
        assertEquals(1,gameBoard.getPlayer(0).getTurn());
        assertEquals(1,BeginTurn.getCurrentTurn());

        assertTrue(BeginTurn.controlTurn(gameBoard.getPlayer(3)));
        BeginTurn.nextTurnParameters(gameBoard,gameBoard.getPlayer(BeginTurn.getCurrentPlayer()));
        assertEquals(1,gameBoard.getPlayer(3).getTurn());
        assertEquals(1,BeginTurn.getCurrentTurn());

        assertTrue(BeginTurn.controlTurn(gameBoard.getPlayer(2)));
        BeginTurn.nextTurnParameters(gameBoard,gameBoard.getPlayer(BeginTurn.getCurrentPlayer()));
        assertEquals(1,gameBoard.getPlayer(2).getTurn());
        assertEquals(1,BeginTurn.getCurrentTurn());

        assertTrue(BeginTurn.controlTurn(gameBoard.getPlayer(1)));
        BeginTurn.nextTurnParameters(gameBoard,gameBoard.getPlayer(BeginTurn.getCurrentPlayer()));
        assertEquals(1,gameBoard.getPlayer(1).getTurn());
        assertEquals(1,BeginTurn.getCurrentTurn());


    }

    @Test
    public void nextTurnParametersFollowsForwardThenBackwardOrder() {

        GameBoard gameBoard=new GameBoard();
        gameBoard.addPlayer("ale","sda","rere","13521.122",12421);
        gameBoard.addPlayer("fede","assa","rerereff","65.21.8788",5335);
        gameBoard.addPlayer("lele","sda","rere","13521.122",12421);
        gameBoard.addPlayer("ywyw","assa","rerereff","65.21.8788",5335);

        BeginRound.increaseIndex();
        BeginTurn.resetCurrentPlayer();

        int[] expectedPlayers = {1, 2, 3, 0, 0, 3, 2, 1};
        int[] expectedTurns = {0, 0, 0, 0, 1, 1, 1, 1};

        for(int i=0; i<expectedPlayers.length; i++){

            assertEquals(expectedPlayers[i],BeginTurn.getCurrentPlayer());
            assertEquals(expectedTurns[i],BeginTurn.getCurrentTurn());
            assertTrue(BeginTurn.controlTurn(gameBoard.getPlayer(expectedPlayers[i])));

            BeginTurn.nextTurnParameters(gameBoard,gameBoard.getPlayer(BeginTurn.getCurrentPlayer()));
        }

        assertEquals(1,BeginTurn.getCurrentTurn());
        assertEquals(gameBoard.getNPlayers(),BeginTurn.getNumPlayedTurn());

        for(int i=0; i<gameBoard.getNPlayers(); i++){

            assertEquals(1,gameBoard.getPlayer(i).getTurn());

        }
    }

    @Test
    public void sessionTurnTrackersAreIndependent() {

        BeginTurn firstSession = new BeginTurn(new TurnTracker());
        BeginTurn secondSession = new BeginTurn(new TurnTracker());
        GameBoard firstBoard = new GameBoard();
        GameBoard secondBoard = new GameBoard();

        firstBoard.addPlayer("ale","sda","rere","13521.122",12421);
        firstBoard.addPlayer("fede","assa","rerereff","65.21.8788",5335);
        secondBoard.addPlayer("lele","sda","rere","13521.122",12421);
        secondBoard.addPlayer("ywyw","assa","rerereff","65.21.8788",5335);

        firstSession.resetSessionCurrentPlayer(1);
        firstSession.nextSessionTurnParameters(firstBoard,firstBoard.getPlayer(firstSession.getSessionCurrentPlayer()));

        assertEquals(0,firstSession.getSessionCurrentTurn());
        assertEquals(1,firstSession.getSessionNumPlayedTurn());
        assertEquals(0,firstSession.getSessionCurrentPlayer());
        assertTrue(firstSession.controlSessionTurn(firstBoard.getPlayer(0)));

        assertEquals(0,secondSession.getSessionCurrentTurn());
        assertEquals(0,secondSession.getSessionNumPlayedTurn());
        assertEquals(0,secondSession.getSessionCurrentPlayer());
        assertTrue(secondSession.controlSessionTurn(secondBoard.getPlayer(0)));
    }

    @Test
    public void defaultTurnTrackersAreIndependent() {

        BeginTurn firstSession = new BeginTurn();
        BeginTurn secondSession = new BeginTurn();
        GameBoard firstBoard = new GameBoard();
        GameBoard secondBoard = new GameBoard();

        firstBoard.addPlayer("ale","sda","rere","13521.122",12421);
        firstBoard.addPlayer("fede","assa","rerereff","65.21.8788",5335);
        secondBoard.addPlayer("lele","sda","rere","13521.122",12421);
        secondBoard.addPlayer("ywyw","assa","rerereff","65.21.8788",5335);

        firstSession.resetSessionCurrentPlayer(1);
        firstSession.nextSessionTurnParameters(firstBoard,firstBoard.getPlayer(firstSession.getSessionCurrentPlayer()));

        assertEquals(1,firstSession.getSessionNumPlayedTurn());
        assertEquals(0,secondSession.getSessionNumPlayedTurn());
        assertEquals(0,secondSession.getSessionCurrentPlayer());
        assertTrue(secondSession.controlSessionTurn(secondBoard.getPlayer(0)));
    }

    @Test(expected = IllegalArgumentException.class)
    public void requiresTurnTrackerForSessionConstructor() {

        new BeginTurn(null);
    }

}
