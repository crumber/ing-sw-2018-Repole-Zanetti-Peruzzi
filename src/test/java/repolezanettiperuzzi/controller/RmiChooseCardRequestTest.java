package repolezanettiperuzzi.controller;

import org.junit.Test;

import static org.junit.Assert.*;

public class RmiChooseCardRequestTest {

    @Test
    public void mapsChooseCardParameterToNamedField() {

        RmiChooseCardRequest request = new RmiChooseCardRequest(2);

        assertEquals(2,request.getCardNumber());
    }
}
