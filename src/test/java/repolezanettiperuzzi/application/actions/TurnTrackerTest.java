package repolezanettiperuzzi.application.actions;

import org.junit.Test;
import repolezanettiperuzzi.model.GameBoard;

import static org.junit.Assert.*;

public class TurnTrackerTest {

    @Test
    public void startsFromFirstPlayerInFirstTurn() {

        TurnTracker tracker = new TurnTracker();

        assertEquals(0,tracker.getCurrentPlayer());
        assertEquals(0,tracker.getCurrentTurn());
        assertEquals(0,tracker.getNumPlayedTurn());
    }

    @Test
    public void followsForwardThenBackwardOrder() {

        TurnTracker tracker = new TurnTracker();
        GameBoard gameBoard = new GameBoard();
        gameBoard.addPlayer("ale","sda","rere","13521.122",12421);
        gameBoard.addPlayer("fede","assa","rerereff","65.21.8788",5335);
        gameBoard.addPlayer("lele","sda","rere","13521.122",12421);
        gameBoard.addPlayer("ywyw","assa","rerereff","65.21.8788",5335);

        tracker.resetCurrentPlayer(1);

        int[] expectedPlayers = {1, 2, 3, 0, 0, 3, 2, 1};
        int[] expectedTurns = {0, 0, 0, 0, 1, 1, 1, 1};

        for(int i=0; i<expectedPlayers.length; i++){

            assertEquals(expectedPlayers[i],tracker.getCurrentPlayer());
            assertEquals(expectedTurns[i],tracker.getCurrentTurn());
            assertTrue(tracker.controlTurn(gameBoard.getPlayer(expectedPlayers[i])));

            tracker.nextTurnParameters(gameBoard,gameBoard.getPlayer(tracker.getCurrentPlayer()));
        }

        assertEquals(1,tracker.getCurrentTurn());
        assertEquals(gameBoard.getNPlayers(),tracker.getNumPlayedTurn());

        for(int i=0; i<gameBoard.getNPlayers(); i++){

            assertEquals(1,gameBoard.getPlayer(i).getTurn());

        }
    }
}
