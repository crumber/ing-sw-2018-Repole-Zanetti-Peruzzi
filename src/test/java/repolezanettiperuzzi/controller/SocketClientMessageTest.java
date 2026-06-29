package repolezanettiperuzzi.controller;

import org.junit.Test;

import static org.junit.Assert.*;

public class SocketClientMessageTest {

    @Test
    public void parsesPlayerActionAndParameters() {

        SocketClientMessage message = SocketClientMessage.parse("ale insertDie 1 2 3");

        assertEquals("ale",message.getPlayerId());
        assertEquals("insertDie",message.getAction());
        assertEquals(3,message.getParameterCount());
        assertEquals("1",message.getParameter(0));
        assertEquals("2",message.getParameter(1));
        assertEquals("3",message.getParameter(2));
    }

    @Test
    public void parsesMessageWithoutParameters() {

        SocketClientMessage message = SocketClientMessage.parse("ale waitingOk");

        assertEquals("ale",message.getPlayerId());
        assertEquals("waitingOk",message.getAction());
        assertEquals(0,message.getParameterCount());
    }
}
