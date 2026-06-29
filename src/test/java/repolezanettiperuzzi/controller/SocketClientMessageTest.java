package repolezanettiperuzzi.controller;

import org.junit.Test;

import static org.junit.Assert.*;

public class SocketClientMessageTest {

    @Test
    public void parsesPlayerActionAndParameters() {

        SocketClientMessage message = SocketClientMessage.parse("ale insertDie 1 2 3");

        assertEquals("ale",message.getPlayerId());
        assertEquals(SocketClientAction.INSERT_DIE,message.getAction());
        assertEquals(3,message.getParameterCount());
        assertEquals("1",message.getParameter(0));
        assertEquals("2",message.getParameter(1));
        assertEquals("3",message.getParameter(2));
    }

    @Test
    public void parsesMessageWithoutParameters() {

        SocketClientMessage message = SocketClientMessage.parse("ale waitingOk");

        assertEquals("ale",message.getPlayerId());
        assertEquals(SocketClientAction.WAITING_OK,message.getAction());
        assertEquals(0,message.getParameterCount());
    }

    @Test
    public void mapsUnknownActionsWithoutFailing() {

        SocketClientMessage message = SocketClientMessage.parse("ale doesNotExist");

        assertEquals("ale",message.getPlayerId());
        assertEquals(SocketClientAction.UNKNOWN,message.getAction());
        assertEquals(0,message.getParameterCount());
    }
}
