package repolezanettiperuzzi.application.actions;

import org.junit.Test;
import repolezanettiperuzzi.model.GameBoard;

import static org.junit.Assert.*;

//test sulla classe begin turn
public class BeginTurnTest {

    BeginTurn testBeginTurn=new BeginTurn();

    //testo che svolga l'azione in modo corretto
    @Test
    public void doAction() {

        GameBoard gameBoard=new GameBoard();
        gameBoard.addPlayer("ale","sda","rere","13521.122",12421);

        assertEquals(0,testBeginTurn.getSessionCurrentPlayer());
        assertEquals(0,testBeginTurn.getSessionCurrentTurn());
        assertEquals(0,testBeginTurn.getSessionNumPlayedTurn());

        testBeginTurn.doAction(gameBoard.getPlayer(testBeginTurn.getSessionCurrentPlayer()),gameBoard);

        assertEquals(0,testBeginTurn.getSessionCurrentPlayer());
        assertEquals(0,testBeginTurn.getSessionCurrentTurn());
        assertTrue(testBeginTurn.controlSessionTurn(gameBoard.getPlayer(0)));

    }

    //test sulla creazione dei parametri per il turno successivo
    @Test
    public void nextTurnParameters() {

        BeginTurn beginTurn = new BeginTurn();
        GameBoard gameBoard=new GameBoard();
        gameBoard.addPlayer("ale","sda","rere","13521.122",12421);
        gameBoard.addPlayer("fede","assa","rerereff","65.21.8788",5335);
        gameBoard.addPlayer("lele","sda","rere","13521.122",12421);
        gameBoard.addPlayer("ywyw","assa","rerereff","65.21.8788",5335);

        beginTurn.resetSessionCurrentPlayer(1);

        //faccio un'andata e un ritorno
        assertTrue(beginTurn.controlSessionTurn(gameBoard.getPlayer(1)));
        beginTurn.nextSessionTurnParameters(gameBoard,gameBoard.getPlayer(beginTurn.getSessionCurrentPlayer()));
        assertEquals(1,gameBoard.getPlayer(1).getTurn());
        assertEquals(0,beginTurn.getSessionCurrentTurn());

        assertTrue(beginTurn.controlSessionTurn(gameBoard.getPlayer(2)));
        beginTurn.nextSessionTurnParameters(gameBoard,gameBoard.getPlayer(beginTurn.getSessionCurrentPlayer()));
        assertEquals(1,gameBoard.getPlayer(2).getTurn());
        assertEquals(0,beginTurn.getSessionCurrentTurn());

        assertTrue(beginTurn.controlSessionTurn(gameBoard.getPlayer(3)));
        beginTurn.nextSessionTurnParameters(gameBoard,gameBoard.getPlayer(beginTurn.getSessionCurrentPlayer()));
        assertEquals(1,gameBoard.getPlayer(3).getTurn());
        assertEquals(0,beginTurn.getSessionCurrentTurn());

        assertTrue(beginTurn.controlSessionTurn(gameBoard.getPlayer(0)));
        beginTurn.nextSessionTurnParameters(gameBoard,gameBoard.getPlayer(beginTurn.getSessionCurrentPlayer()));
        assertEquals(1,gameBoard.getPlayer(0).getTurn());
        assertEquals(1,beginTurn.getSessionCurrentTurn());

        assertTrue(beginTurn.controlSessionTurn(gameBoard.getPlayer(0)));
        beginTurn.nextSessionTurnParameters(gameBoard,gameBoard.getPlayer(beginTurn.getSessionCurrentPlayer()));
        assertEquals(1,gameBoard.getPlayer(0).getTurn());
        assertEquals(1,beginTurn.getSessionCurrentTurn());

        assertTrue(beginTurn.controlSessionTurn(gameBoard.getPlayer(3)));
        beginTurn.nextSessionTurnParameters(gameBoard,gameBoard.getPlayer(beginTurn.getSessionCurrentPlayer()));
        assertEquals(1,gameBoard.getPlayer(3).getTurn());
        assertEquals(1,beginTurn.getSessionCurrentTurn());

        assertTrue(beginTurn.controlSessionTurn(gameBoard.getPlayer(2)));
        beginTurn.nextSessionTurnParameters(gameBoard,gameBoard.getPlayer(beginTurn.getSessionCurrentPlayer()));
        assertEquals(1,gameBoard.getPlayer(2).getTurn());
        assertEquals(1,beginTurn.getSessionCurrentTurn());

        assertTrue(beginTurn.controlSessionTurn(gameBoard.getPlayer(1)));
        beginTurn.nextSessionTurnParameters(gameBoard,gameBoard.getPlayer(beginTurn.getSessionCurrentPlayer()));
        assertEquals(1,gameBoard.getPlayer(1).getTurn());
        assertEquals(1,beginTurn.getSessionCurrentTurn());


    }

    @Test
    public void nextTurnParametersFollowsForwardThenBackwardOrder() {

        BeginTurn beginTurn = new BeginTurn();
        GameBoard gameBoard=new GameBoard();
        gameBoard.addPlayer("ale","sda","rere","13521.122",12421);
        gameBoard.addPlayer("fede","assa","rerereff","65.21.8788",5335);
        gameBoard.addPlayer("lele","sda","rere","13521.122",12421);
        gameBoard.addPlayer("ywyw","assa","rerereff","65.21.8788",5335);

        beginTurn.resetSessionCurrentPlayer(1);

        int[] expectedPlayers = {1, 2, 3, 0, 0, 3, 2, 1};
        int[] expectedTurns = {0, 0, 0, 0, 1, 1, 1, 1};

        for(int i=0; i<expectedPlayers.length; i++){

            assertEquals(expectedPlayers[i],beginTurn.getSessionCurrentPlayer());
            assertEquals(expectedTurns[i],beginTurn.getSessionCurrentTurn());
            assertTrue(beginTurn.controlSessionTurn(gameBoard.getPlayer(expectedPlayers[i])));

            beginTurn.nextSessionTurnParameters(gameBoard,gameBoard.getPlayer(beginTurn.getSessionCurrentPlayer()));
        }

        assertEquals(1,beginTurn.getSessionCurrentTurn());
        assertEquals(gameBoard.getNPlayers(),beginTurn.getSessionNumPlayedTurn());

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
