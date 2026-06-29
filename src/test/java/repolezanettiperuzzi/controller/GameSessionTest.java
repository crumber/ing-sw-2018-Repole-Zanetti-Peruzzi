package repolezanettiperuzzi.controller;

import org.junit.Test;
import repolezanettiperuzzi.application.actions.BeginRound;
import repolezanettiperuzzi.application.actions.BeginTurn;
import repolezanettiperuzzi.application.actions.EndRound;
import repolezanettiperuzzi.model.GameBoard;

import static org.junit.Assert.*;

public class GameSessionTest {

    @Test
    public void createsSessionTrackers() {

        GameSession session = new GameSession();

        assertNotNull(session.getRoundTracker());
        assertNotNull(session.getTurnTracker());
        assertNotNull(session.getTurnStateTracker());
    }

    @Test
    public void keepsSessionStateIndependent() {

        GameSession firstSession = new GameSession();
        GameSession secondSession = new GameSession();

        firstSession.getRoundTracker().increaseRound();
        firstSession.getTurnStateTracker().markTurnNotificationSent();

        assertEquals(1,firstSession.getRoundTracker().getRound());
        assertTrue(firstSession.getTurnStateTracker().isTurnNotificationSent());

        assertEquals(0,secondSession.getRoundTracker().getRound());
        assertFalse(secondSession.getTurnStateTracker().isTurnNotificationSent());
    }

    @Test
    public void createsBeginRoundActionBoundToSessionTracker() {

        GameSession session = new GameSession();
        BeginRound beginRound = session.createBeginRound();
        GameBoard board = new GameBoard();
        board.addPlayer("ale","sda","rere","13521.122",12421);
        board.addPlayer("fede","assa","rerereff","65.21.8788",5335);

        beginRound.doAction(board);

        assertEquals(1,session.getRoundTracker().getRound());
        assertEquals(1,beginRound.getRound());
        assertEquals(5,board.getSizeDraft());
    }

    @Test
    public void createsBeginTurnActionBoundToSessionTracker() {

        GameSession session = new GameSession();
        BeginTurn beginTurn = session.createBeginTurn();
        GameBoard board = new GameBoard();
        board.addPlayer("ale","sda","rere","13521.122",12421);
        board.addPlayer("fede","assa","rerereff","65.21.8788",5335);

        beginTurn.resetCurrentPlayer(1);
        beginTurn.nextTurnParameters(board,board.getPlayer(beginTurn.getCurrentPlayer()));

        assertEquals(0,session.getTurnTracker().getCurrentPlayer());
        assertEquals(1,session.getTurnTracker().getNumPlayedTurn());
        assertEquals(0,beginTurn.getCurrentPlayer());
        assertEquals(1,beginTurn.getNumPlayedTurn());
    }

    @Test
    public void createsEndRoundActionBoundToSessionTracker() {

        GameSession session = new GameSession();
        EndRound endRound = session.createEndRound();
        GameBoard board = new GameBoard();
        board.addPlayer("ale","sda","rere","13521.122",12421);
        board.addPlayer("fede","assa","rerereff","65.21.8788",5335);

        endRound.doAction(board);

        assertEquals(1,session.getRoundTracker().getIndex());

        endRound.doAction(board);

        assertEquals(0,session.getRoundTracker().getIndex());
    }
}
