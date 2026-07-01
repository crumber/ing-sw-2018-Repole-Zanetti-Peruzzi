package repolezanettiperuzzi.view;

import org.junit.Test;

import static org.junit.Assert.*;

public class GameViewSocketMessageTest {

    @Test
    public void parsesCommandAndTokens() {

        GameViewSocketMessage message = GameViewSocketMessage.parse("turn ale 30");

        assertEquals("turn",message.getCommand());
        assertEquals(3,message.getTokenCount());
        assertEquals("ale",message.getToken(1));
        assertEquals("30",message.getToken(2));
    }

    @Test
    public void returnsCopyOfTokens() {

        GameViewSocketMessage message = GameViewSocketMessage.parse("requestCard move");

        String[] tokens = message.getTokens();
        tokens[0] = "changed";

        assertEquals("requestCard",message.getCommand());
    }
}
