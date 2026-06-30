package repolezanettiperuzzi.controller;

import org.junit.Test;

import static org.junit.Assert.*;

public class RmiChosenWindowRequestTest {

    @Test
    public void decodesChosenWindowName() {

        RmiChosenWindowRequest request = new RmiChosenWindowRequest("Aurorae-Magnificus");

        assertEquals("Aurorae Magnificus",request.getWindowName());
    }
}
