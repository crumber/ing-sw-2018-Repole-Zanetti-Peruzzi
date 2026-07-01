package repolezanettiperuzzi.view;

import org.junit.Test;

import static org.junit.Assert.*;

public class GameViewSocketCommandTest {

    @Test
    public void exposesClientCommandWireValues() {

        assertEquals("init",GameViewSocketCommand.INIT.getWireValue());
        assertEquals("waitingOk",GameViewSocketCommand.WAITING_OK.getWireValue());
        assertEquals("chooseWindowOk",GameViewSocketCommand.CHOOSE_WINDOW_OK.getWireValue());
        assertEquals("gameOk",GameViewSocketCommand.GAME_OK.getWireValue());
        assertEquals("exit",GameViewSocketCommand.EXIT.getWireValue());
        assertEquals("insertDie",GameViewSocketCommand.INSERT_DIE.getWireValue());
        assertEquals("chooseCard",GameViewSocketCommand.CHOOSE_CARD.getWireValue());
        assertEquals("responseToolCard",GameViewSocketCommand.RESPONSE_TOOL_CARD.getWireValue());
        assertEquals("chosenWindow",GameViewSocketCommand.CHOSEN_WINDOW.getWireValue());
        assertEquals("endTurn",GameViewSocketCommand.END_TURN.getWireValue());
    }
}
