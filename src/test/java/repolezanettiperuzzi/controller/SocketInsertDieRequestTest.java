package repolezanettiperuzzi.controller;

import org.junit.Test;

import static org.junit.Assert.*;

public class SocketInsertDieRequestTest {

    @Test
    public void mapsInsertDieParametersToNamedFields() {

        SocketClientMessage message = SocketClientMessage.parse("ale insertDie 1 2 3");
        SocketInsertDieRequest request = SocketInsertDieRequest.from(message);

        assertEquals("1",request.getDraftPosition());
        assertEquals("2",request.getXPosition());
        assertEquals("3",request.getYPosition());
    }

    @Test
    public void keepsTurnStateParameterFormat() {

        SocketClientMessage message = SocketClientMessage.parse("ale insertDie 1 2 3");
        SocketInsertDieRequest request = SocketInsertDieRequest.from(message);

        assertEquals("1 2 3",request.toTurnStateParameter());
    }
}
