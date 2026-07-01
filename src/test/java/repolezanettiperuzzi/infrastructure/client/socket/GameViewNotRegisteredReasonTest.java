package repolezanettiperuzzi.infrastructure.client.socket;

import org.junit.Test;

import static org.junit.Assert.*;

public class GameViewNotRegisteredReasonTest {

    @Test
    public void mapsKnownReasonsFromWireValues() {

        assertEquals(GameViewNotRegisteredReason.ALREADY_ONLINE,GameViewNotRegisteredReason.fromWireValue("alreadyonline"));
        assertEquals(GameViewNotRegisteredReason.WRONG_PASSWORD,GameViewNotRegisteredReason.fromWireValue("wrongpwd"));
        assertEquals(GameViewNotRegisteredReason.GAME_ALREADY_STARTED,GameViewNotRegisteredReason.fromWireValue("gameAlreadyStarted"));
        assertEquals(GameViewNotRegisteredReason.ALREADY_FOUR_PLAYERS,GameViewNotRegisteredReason.fromWireValue("already4Players"));
    }

    @Test
    public void mapsUnknownReasonWithoutFailing() {

        assertEquals(GameViewNotRegisteredReason.UNKNOWN,GameViewNotRegisteredReason.fromWireValue("maintenance"));
    }
}
