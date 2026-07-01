package repolezanettiperuzzi.controller;

import org.junit.Test;

import static org.junit.Assert.*;

public class TransportMessageTest {

    @Test
    public void extractsPayloadAfterCommandName() {

        assertEquals("parameters",TransportMessage.payload("requestCard parameters"));
    }
}
