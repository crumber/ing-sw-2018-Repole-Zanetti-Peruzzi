package repolezanettiperuzzi.domain.cards.toolcards;

import org.junit.Test;
import repolezanettiperuzzi.domain.ActionResult;
import repolezanettiperuzzi.model.*;

import java.util.List;

import static org.junit.Assert.*;

public class ToolCardTest {

    private final TestToolCard testToolCard = new TestToolCard();

    @Test
    public void testCheckMoveOneDieResultKeepsLegacyCodeBridge() {

        Window window = createWindowWithAdjacentDice();
        Player player = createPlayer(window);
        GameBoard board = new GameBoard();

        assertEquals(ActionResult.POSITION_OCCUPIED, testToolCard.checkMoveOneDieResult(board, player, 0, 0, 0, 1));
        assertEquals(ActionResult.POSITION_OCCUPIED.getCode(), testToolCard.checkMoveOneDie(board, player, 0, 0, 0, 1));

        assertEquals(ActionResult.SUCCESS, testToolCard.checkMoveOneDieResult(board, player, 0, 0, 0, 2));
        assertTrue(window.thereIsDie(0, 0));
        assertFalse(window.thereIsDie(0, 2));
    }

    @Test
    public void testCheckMoveTwoDiceResultRestoresBoard() {

        Window window = createWindowWithAdjacentDice();
        Player player = createPlayer(window);
        GameBoard board = new GameBoard();

        ActionResult result = testToolCard.checkMoveTwoDiceResult(board, player, 0, 0, 0, 2, 3, 3, 2, 2);

        assertEquals(ActionResult.SECOND_DIE_START_EMPTY, result);
        assertEquals(ActionResult.SECOND_DIE_START_EMPTY.getCode(), testToolCard.checkMoveTwoDice(board, player, 0, 0, 0, 2, 3, 3, 2, 2));
        assertTrue(window.thereIsDie(0, 0));
        assertTrue(window.thereIsDie(0, 1));
        assertFalse(window.thereIsDie(0, 2));
    }

    @Test
    public void testDraftAndRoundTrackResultsKeepLegacyCodeBridge() {

        GameBoard board = new GameBoard();

        assertEquals(ActionResult.EMPTY_DRAFT_POSITION, testToolCard.checkDieOnDraftResult(board, null, 0));
        assertEquals(ActionResult.EMPTY_DRAFT_POSITION.getCode(), testToolCard.checkDieOnDraft(board, null, 0));

        board.addDieToDraft(new Die(Colour.YELLOW));

        assertEquals(ActionResult.SUCCESS, testToolCard.checkDieOnDraftResult(board, null, 0));
        assertEquals(ActionResult.ROUND_TRACK_POSITION_EMPTY, testToolCard.checkDieOnRoundTrackResult(board, null, 0, 0));
        assertEquals(ActionResult.ROUND_TRACK_POSITION_EMPTY.getCode(), testToolCard.checkDieOnRoundTrack(board, null, 0, 0));
    }

    private Window createWindowWithAdjacentDice() {

        Box[][] boxes = new Box[4][5];

        for (int i = 0; i < boxes.length; i++) {
            for (int j = 0; j < boxes[0].length; j++) {
                boxes[i][j] = new Box();
            }
        }

        Window window = new Window("test", 5, boxes, "test");
        Die movingDie = new Die(Colour.RED);
        movingDie.setValue(Value.TWO);
        Die adjacentDie = new Die(Colour.BLUE);
        adjacentDie.setValue(Value.THREE);

        window.insertDie(movingDie, 0, 0, BoxRestriction.NONE);
        window.insertDie(adjacentDie, 0, 1, BoxRestriction.NONE);
        return window;
    }

    private Player createPlayer(Window window) {

        Player player = new Player("player", "Socket", "CLI", "localhost", 0);
        player.setWindow(window);
        return player;
    }

    private static class TestToolCard extends ToolCard {

        @Override
        public int check(GameBoard board, Player player, List<Integer> parameterForCard) {
            return ActionResult.SUCCESS.getCode();
        }

        @Override
        public void effect(GameBoard board, Player player, List<Integer> parameterForCard) {
        }
    }
}
