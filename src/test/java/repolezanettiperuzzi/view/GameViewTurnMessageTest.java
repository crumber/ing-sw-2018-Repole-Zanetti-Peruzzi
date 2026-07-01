package repolezanettiperuzzi.view;

import repolezanettiperuzzi.infrastructure.client.socket.GameViewSocketMessage;

import org.junit.Test;

import static org.junit.Assert.*;

public class GameViewTurnMessageTest {

    @Test
    public void mapsTurnPayloadToNamedFields() {

        GameViewSocketMessage message = GameViewSocketMessage.parse("turn ale 30");
        GameViewTurnMessage turnMessage = GameViewTurnMessage.from(message);

        assertEquals("ale",turnMessage.getActualPlayer());
        assertEquals(30,turnMessage.getCurrentTime());
    }
}
