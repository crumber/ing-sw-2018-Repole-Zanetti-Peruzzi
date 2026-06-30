package repolezanettiperuzzi.controller;

import org.junit.Test;

import static org.junit.Assert.*;

public class SocketToolCardResponseRequestTest {

    @Test
    public void mapsToolCardResponseParametersToNamedFields() {

        SocketClientMessage message = SocketClientMessage.parse("ale responseToolCard 8 move-1-2");
        SocketToolCardResponseRequest request = SocketToolCardResponseRequest.from(message);

        assertEquals(8,request.getCardNumber());
        assertEquals("move 1 2",request.getResponse());
    }
}
