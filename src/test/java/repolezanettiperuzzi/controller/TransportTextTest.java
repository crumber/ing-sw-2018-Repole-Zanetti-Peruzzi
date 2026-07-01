package repolezanettiperuzzi.controller;

import org.junit.Test;

import static org.junit.Assert.*;

public class TransportTextTest {

    @Test
    public void decodesHyphensAsSpaces() {

        assertEquals("Aurorae Magnificus",TransportText.decodeSpaces("Aurorae-Magnificus"));
    }

    @Test
    public void encodesSpacesAsHyphens() {

        assertEquals("Aurorae-Magnificus",TransportText.encodeSpaces("Aurorae Magnificus"));
    }
}
