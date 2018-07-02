package repolezanettiperuzzi.model.actions;

import org.junit.Test;
import repolezanettiperuzzi.model.GameBoard;
import repolezanettiperuzzi.model.Player;

import static org.junit.Assert.*;

public class BeginTurnTest {

    BeginTurn testBeginTurn=new BeginTurn();

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

}