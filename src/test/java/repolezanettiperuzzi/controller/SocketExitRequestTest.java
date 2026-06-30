package repolezanettiperuzzi.controller;

import org.junit.Test;

import static org.junit.Assert.*;

public class SocketExitRequestTest {

    @Test
    public void mapsExitSceneToNamedValue() {

        SocketClientMessage message = SocketClientMessage.parse("ale exit waitingRoom");
        SocketExitRequest request = SocketExitRequest.from(message);

        assertEquals(SocketExitScene.WAITING_ROOM,request.getScene());
    }

    @Test
    public void mapsUnknownExitSceneWithoutFailing() {

        SocketClientMessage message = SocketClientMessage.parse("ale exit someScene");
        SocketExitRequest request = SocketExitRequest.from(message);

        assertEquals(SocketExitScene.UNKNOWN,request.getScene());
    }
}
