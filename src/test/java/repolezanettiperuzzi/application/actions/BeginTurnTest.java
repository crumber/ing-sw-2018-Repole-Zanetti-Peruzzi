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

        assertEquals(0,testBeginTurn.getCurrentPlayer());
        assertEquals(0,testBeginTurn.getCurrentTurn());
        assertEquals(0,testBeginTurn.getNumPlayedTurn());

        testBeginTurn.doAction(gameBoard.getPlayer(testBeginTurn.getCurrentPlayer()),gameBoard);

        assertEquals(0,testBeginTurn.getCurrentPlayer());
        assertEquals(0,testBeginTurn.getCurrentTurn());
        assertTrue(testBeginTurn.controlTurn(gameBoard.getPlayer(0)));

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

        beginTurn.resetCurrentPlayer(1);

        //faccio un'andata e un ritorno
        assertTrue(beginTurn.controlTurn(gameBoard.getPlayer(1)));
        beginTurn.nextTurnParameters(gameBoard,gameBoard.getPlayer(beginTurn.getCurrentPlayer()));
        assertEquals(1,gameBoard.getPlayer(1).getTurn());
        assertEquals(0,beginTurn.getCurrentTurn());

        assertTrue(beginTurn.controlTurn(gameBoard.getPlayer(2)));
        beginTurn.nextTurnParameters(gameBoard,gameBoard.getPlayer(beginTurn.getCurrentPlayer()));
        assertEquals(1,gameBoard.getPlayer(2).getTurn());
        assertEquals(0,beginTurn.getCurrentTurn());

        assertTrue(beginTurn.controlTurn(gameBoard.getPlayer(3)));
        beginTurn.nextTurnParameters(gameBoard,gameBoard.getPlayer(beginTurn.getCurrentPlayer()));
        assertEquals(1,gameBoard.getPlayer(3).getTurn());
        assertEquals(0,beginTurn.getCurrentTurn());

        assertTrue(beginTurn.controlTurn(gameBoard.getPlayer(0)));
        beginTurn.nextTurnParameters(gameBoard,gameBoard.getPlayer(beginTurn.getCurrentPlayer()));
        assertEquals(1,gameBoard.getPlayer(0).getTurn());
        assertEquals(1,beginTurn.getCurrentTurn());

        assertTrue(beginTurn.controlTurn(gameBoard.getPlayer(0)));
        beginTurn.nextTurnParameters(gameBoard,gameBoard.getPlayer(beginTurn.getCurrentPlayer()));
        assertEquals(1,gameBoard.getPlayer(0).getTurn());
        assertEquals(1,beginTurn.getCurrentTurn());

        assertTrue(beginTurn.controlTurn(gameBoard.getPlayer(3)));
        beginTurn.nextTurnParameters(gameBoard,gameBoard.getPlayer(beginTurn.getCurrentPlayer()));
        assertEquals(1,gameBoard.getPlayer(3).getTurn());
        assertEquals(1,beginTurn.getCurrentTurn());

        assertTrue(beginTurn.controlTurn(gameBoard.getPlayer(2)));
        beginTurn.nextTurnParameters(gameBoard,gameBoard.getPlayer(beginTurn.getCurrentPlayer()));
        assertEquals(1,gameBoard.getPlayer(2).getTurn());
        assertEquals(1,beginTurn.getCurrentTurn());

        assertTrue(beginTurn.controlTurn(gameBoard.getPlayer(1)));
        beginTurn.nextTurnParameters(gameBoard,gameBoard.getPlayer(beginTurn.getCurrentPlayer()));
        assertEquals(1,gameBoard.getPlayer(1).getTurn());
        assertEquals(1,beginTurn.getCurrentTurn());


    }

    @Test
    public void nextTurnParametersFollowsForwardThenBackwardOrder() {

        BeginTurn beginTurn = new BeginTurn();
        GameBoard gameBoard=new GameBoard();
        gameBoard.addPlayer("ale","sda","rere","13521.122",12421);
        gameBoard.addPlayer("fede","assa","rerereff","65.21.8788",5335);
        gameBoard.addPlayer("lele","sda","rere","13521.122",12421);
        gameBoard.addPlayer("ywyw","assa","rerereff","65.21.8788",5335);

        beginTurn.resetCurrentPlayer(1);

        int[] expectedPlayers = {1, 2, 3, 0, 0, 3, 2, 1};
        int[] expectedTurns = {0, 0, 0, 0, 1, 1, 1, 1};

        for(int i=0; i<expectedPlayers.length; i++){

            assertEquals(expectedPlayers[i],beginTurn.getCurrentPlayer());
            assertEquals(expectedTurns[i],beginTurn.getCurrentTurn());
            assertTrue(beginTurn.controlTurn(gameBoard.getPlayer(expectedPlayers[i])));

            beginTurn.nextTurnParameters(gameBoard,gameBoard.getPlayer(beginTurn.getCurrentPlayer()));
        }

        assertEquals(1,beginTurn.getCurrentTurn());
        assertEquals(gameBoard.getNPlayers(),beginTurn.getNumPlayedTurn());

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

        firstSession.resetCurrentPlayer(1);
        firstSession.nextTurnParameters(firstBoard,firstBoard.getPlayer(firstSession.getCurrentPlayer()));

        assertEquals(0,firstSession.getCurrentTurn());
        assertEquals(1,firstSession.getNumPlayedTurn());
        assertEquals(0,firstSession.getCurrentPlayer());
        assertTrue(firstSession.controlTurn(firstBoard.getPlayer(0)));

        assertEquals(0,secondSession.getCurrentTurn());
        assertEquals(0,secondSession.getNumPlayedTurn());
        assertEquals(0,secondSession.getCurrentPlayer());
        assertTrue(secondSession.controlTurn(secondBoard.getPlayer(0)));
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

        firstSession.resetCurrentPlayer(1);
        firstSession.nextTurnParameters(firstBoard,firstBoard.getPlayer(firstSession.getCurrentPlayer()));

        assertEquals(1,firstSession.getNumPlayedTurn());
        assertEquals(0,secondSession.getNumPlayedTurn());
        assertEquals(0,secondSession.getCurrentPlayer());
        assertTrue(secondSession.controlTurn(secondBoard.getPlayer(0)));
    }

    @Test(expected = IllegalArgumentException.class)
    public void requiresTurnTrackerForSessionConstructor() {

        new BeginTurn(null);
    }

}
