package repolezanettiperuzzi.controller;

import org.junit.Test;
import repolezanettiperuzzi.application.actions.BeginRound;
import repolezanettiperuzzi.application.actions.BeginTurn;
import repolezanettiperuzzi.application.actions.EndRound;
import repolezanettiperuzzi.model.GameBoard;

import static org.junit.Assert.*;

public class ControllerTest {

    @Test
    public void createsGameSession() {

        GameBoard board = new GameBoard();
        Controller controller = new Controller(board.getPlayers(),board);

        assertNotNull(controller.getSession());
        assertNotNull(controller.getSession().getRoundTracker());
        assertNotNull(controller.getSession().getTurnTracker());
        assertNotNull(controller.getSession().getTurnStateTracker());
    }

    @Test
    public void keepsGameSessionIndependentPerController() {

        GameBoard firstBoard = new GameBoard();
        GameBoard secondBoard = new GameBoard();
        Controller firstController = new Controller(firstBoard.getPlayers(),firstBoard);
        Controller secondController = new Controller(secondBoard.getPlayers(),secondBoard);

        firstController.getSession().getRoundTracker().increaseRound();
        firstController.getSession().getTurnStateTracker().markTurnNotificationSent();

        assertEquals(1,firstController.getSession().getRoundTracker().getRound());
        assertTrue(firstController.getSession().getTurnStateTracker().isTurnNotificationSent());

        assertEquals(0,secondController.getSession().getRoundTracker().getRound());
        assertFalse(secondController.getSession().getTurnStateTracker().isTurnNotificationSent());
    }

    @Test
    public void createsFlowActionsBoundToControllerSession() {

        GameBoard board = new GameBoard();
        board.addPlayer("ale","sda","rere","13521.122",12421);
        board.addPlayer("fede","assa","rerereff","65.21.8788",5335);
        Controller controller = new Controller(board.getPlayers(),board);

        BeginRound beginRound = controller.createBeginRoundAction();
        BeginTurn beginTurn = controller.createBeginTurnAction();
        EndRound endRound = controller.createEndRoundAction();

        beginRound.doAction(board);
        beginTurn.resetSessionCurrentPlayer(1);
        beginTurn.nextSessionTurnParameters(board,board.getPlayer(beginTurn.getSessionCurrentPlayer()));

        assertEquals(1,controller.getCurrentRound());
        assertEquals(0,controller.getCurrentTurn());
        assertEquals(0,controller.getCurrentPlayerIndex());
        assertEquals(1,controller.getNumPlayedTurn());

        endRound.doAction(board);

        assertEquals(1,controller.getFirstPlayerIndex());
    }

    @Test
    public void exposesSessionBackedTurnCommands() {

        GameBoard board = new GameBoard();
        board.addPlayer("ale","sda","rere","13521.122",12421);
        board.addPlayer("fede","assa","rerereff","65.21.8788",5335);
        board.addPlayer("lele","sda","rere","13521.122",12421);
        Controller controller = new Controller(board.getPlayers(),board);

        controller.getSession().getRoundTracker().increaseIndex();
        controller.resetCurrentPlayer();

        assertEquals(1,controller.getCurrentPlayerIndex());
        assertSame(board.getPlayer(1),controller.getCurrentPlayer());
        assertTrue(controller.isCurrentPlayerTurn(board.getPlayer(1)));

        controller.nextTurnParameters(controller.getCurrentPlayer());

        assertEquals(2,controller.getCurrentPlayerIndex());
        assertEquals(1,controller.getNumPlayedTurn());
        assertTrue(controller.isCurrentPlayerTurn(board.getPlayer(2)));

        controller.resetCurrentTurn();
        controller.resetNumPlayedTurn();

        assertEquals(0,controller.getCurrentTurn());
        assertEquals(0,controller.getNumPlayedTurn());
    }
}
