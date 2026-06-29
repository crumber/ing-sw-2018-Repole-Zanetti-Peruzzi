package repolezanettiperuzzi.controller;

import org.junit.Test;

import static org.junit.Assert.*;

public class SocketInitRequestTest {

    @Test
    public void mapsInitParametersToNamedFields() {

        SocketClientMessage message = SocketClientMessage.parse("ale init secret socket cli 1234");
        SocketInitRequest request = SocketInitRequest.from(message);

        assertEquals("secret",request.getPassword());
        assertEquals("socket",request.getConnection());
        assertEquals("cli",request.getUi());
        assertEquals(1234,request.getPort());
    }
}
