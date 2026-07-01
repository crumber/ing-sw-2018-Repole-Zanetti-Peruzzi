package repolezanettiperuzzi.view;

import repolezanettiperuzzi.infrastructure.client.socket.GameViewSocketMessage;

import org.junit.Test;

import static org.junit.Assert.*;

public class GameViewUpdatedPlayersMessageTest {

    @Test
    public void mapsUpdatedPlayersPayloadToNamedFields() {

        GameViewSocketMessage message = GameViewSocketMessage.parse("updatedplayers 30 ale giampi");
        GameViewUpdatedPlayersMessage updatedPlayersMessage = GameViewUpdatedPlayersMessage.from(message);

        assertEquals(30,updatedPlayersMessage.getTimer());
        assertArrayEquals(new String[]{"ale","giampi"},updatedPlayersMessage.getPlayers());
    }

    @Test
    public void returnsCopyOfPlayers() {

        GameViewSocketMessage message = GameViewSocketMessage.parse("updatedplayers 30 ale");
        GameViewUpdatedPlayersMessage updatedPlayersMessage = GameViewUpdatedPlayersMessage.from(message);

        String[] players = updatedPlayersMessage.getPlayers();
        players[0] = "changed";

        assertArrayEquals(new String[]{"ale"},updatedPlayersMessage.getPlayers());
    }
}
