package repolezanettiperuzzi.controller;

import org.junit.Test;

import static org.junit.Assert.*;

public class TransportTextTest {

    @Test
    public void decodesHyphensAsSpaces() {

        assertEquals("Aurorae Magnificus",TransportText.decodeSpaces("Aurorae-Magnificus"));
    }
}
