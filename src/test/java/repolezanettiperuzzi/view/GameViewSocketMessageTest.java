package repolezanettiperuzzi.view;

import org.junit.Test;

import static org.junit.Assert.*;

public class GameViewSocketMessageTest {

    @Test
    public void parsesCommandAndTokens() {

        GameViewSocketMessage message = GameViewSocketMessage.parse("turn ale 30");

        assertEquals(GameViewSocketAction.TURN,message.getAction());
        assertEquals(3,message.getTokenCount());
        assertEquals("ale",message.getToken(1));
        assertEquals("ale",message.getFirstPayloadToken());
        assertEquals("30",message.getToken(2));
    }

    @Test
    public void returnsCopyOfTokens() {

        GameViewSocketMessage message = GameViewSocketMessage.parse("requestCard move");

        String[] tokens = message.getTokens();
        tokens[0] = "changed";

        assertEquals(GameViewSocketAction.REQUEST_CARD,message.getAction());
    }

    @Test
    public void mapsUnknownActionsWithoutFailing() {

        GameViewSocketMessage message = GameViewSocketMessage.parse("doesNotExist value");

        assertEquals(GameViewSocketAction.UNKNOWN,message.getAction());
    }
}
