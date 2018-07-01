package repolezanettiperuzzi.model.actions;

import org.junit.Test;
import repolezanettiperuzzi.model.GameBoard;
import repolezanettiperuzzi.model.Player;

import static org.junit.Assert.*;

public class BeginTurnTest {

    BeginTurn testBeginTurn=new BeginTurn();

    @Test
    public void doAction() {

        BeginTurn.resetCurrentTurn();
        BeginTurn.resetNumPlayedTurn();
        BeginTurn.resetCurrentPlayer();

        GameBoard gameBoard=new GameBoard();
        gameBoard.addPlayer("ale","sda","rere","13521.122",12421);

        assertEquals(0,BeginTurn.getCurrentPlayer());
        assertEquals(0,BeginTurn.getCurrentTurn());
        assertEquals(0,BeginTurn.getNumPlayedTurn());

        testBeginTurn.doAction(gameBoard.getPlayer(0),gameBoard);

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

        //faccio un'andata e un ritorno
        BeginTurn.nextTurnParameters(gameBoard,gameBoard.getPlayer(0));
        assertEquals(1,gameBoard.getPlayer(0).getTurn());
        assertEquals(0,BeginTurn.getCurrentTurn());

        BeginTurn.nextTurnParameters(gameBoard,gameBoard.getPlayer(1));
        assertEquals(1,gameBoard.getPlayer(1).getTurn());
        assertEquals(0,BeginTurn.getCurrentTurn());

        BeginTurn.nextTurnParameters(gameBoard,gameBoard.getPlayer(2));
        assertEquals(1,gameBoard.getPlayer(2).getTurn());
        assertEquals(0,BeginTurn.getCurrentTurn());

        BeginTurn.nextTurnParameters(gameBoard,gameBoard.getPlayer(3));
        assertEquals(1,gameBoard.getPlayer(3).getTurn());
        assertEquals(1,BeginTurn.getCurrentTurn());

        BeginTurn.nextTurnParameters(gameBoard,gameBoard.getPlayer(0));
        assertEquals(1,gameBoard.getPlayer(0).getTurn());
        assertEquals(1,BeginTurn.getCurrentTurn());

        BeginTurn.nextTurnParameters(gameBoard,gameBoard.getPlayer(1));
        assertEquals(1,gameBoard.getPlayer(1).getTurn());
        assertEquals(1,BeginTurn.getCurrentTurn());

        BeginTurn.nextTurnParameters(gameBoard,gameBoard.getPlayer(2));
        assertEquals(1,gameBoard.getPlayer(2).getTurn());
        assertEquals(1,BeginTurn.getCurrentTurn());

        BeginTurn.nextTurnParameters(gameBoard,gameBoard.getPlayer(3));
        assertEquals(1,gameBoard.getPlayer(3).getTurn());
        assertEquals(1,BeginTurn.getCurrentTurn());


    }

}