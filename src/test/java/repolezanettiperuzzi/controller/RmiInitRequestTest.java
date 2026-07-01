package repolezanettiperuzzi.controller;

import org.junit.Test;

import static org.junit.Assert.*;

public class RmiInitRequestTest {

    @Test
    public void mapsInitParametersToNamedFields() {

        RmiInitRequest request = new RmiInitRequest("ale","secret","RMI","GUI");

        assertEquals("ale",request.getUsername());
        assertEquals("secret",request.getPassword());
        assertEquals("RMI",request.getConnection());
        assertEquals("GUI",request.getUi());
    }
}
