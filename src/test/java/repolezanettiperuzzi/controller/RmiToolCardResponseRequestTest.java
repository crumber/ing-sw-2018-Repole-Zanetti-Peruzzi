package repolezanettiperuzzi.controller;

import org.junit.Test;

import static org.junit.Assert.*;

public class RmiToolCardResponseRequestTest {

    @Test
    public void storesCardNumberAndDecodesResponse() {

        RmiToolCardResponseRequest request = new RmiToolCardResponseRequest(8,"move-1-2");

        assertEquals(8,request.getCardNumber());
        assertEquals("move 1 2",request.getResponse());
    }
}
