package repolezanettiperuzzi.controller;

import org.junit.Test;
import repolezanettiperuzzi.model.GameBoard;

import java.lang.reflect.Field;

import static org.junit.Assert.*;

public class TurnStateTest {

    @Test
    public void usesControllerSessionTrackerWhenBoundToController() throws Exception {

        GameBoard board = new GameBoard();
        Controller controller = new Controller(board.getPlayers(),board);
        TurnState turnState = new TurnState();

        controller.setStateNoDoAction(turnState);

        assertSame(controller.getSession().getTurnStateTracker(), getTurnStateTracker(turnState));
    }

    @Test
    public void keepsTurnStateTrackerSeparateAcrossControllers() throws Exception {

        GameBoard firstBoard = new GameBoard();
        GameBoard secondBoard = new GameBoard();
        Controller firstController = new Controller(firstBoard.getPlayers(),firstBoard);
        Controller secondController = new Controller(secondBoard.getPlayers(),secondBoard);
        TurnState firstTurnState = new TurnState();
        TurnState secondTurnState = new TurnState();

        firstController.setStateNoDoAction(firstTurnState);
        secondController.setStateNoDoAction(secondTurnState);

        assertSame(firstController.getSession().getTurnStateTracker(), getTurnStateTracker(firstTurnState));
        assertSame(secondController.getSession().getTurnStateTracker(), getTurnStateTracker(secondTurnState));
        assertNotSame(getTurnStateTracker(firstTurnState), getTurnStateTracker(secondTurnState));
    }

    private TurnStateTracker getTurnStateTracker(TurnState turnState) throws NoSuchFieldException, IllegalAccessException {

        Field field = TurnState.class.getDeclaredField("turnStateTracker");
        field.setAccessible(true);
        return (TurnStateTracker) field.get(turnState);
    }
}
