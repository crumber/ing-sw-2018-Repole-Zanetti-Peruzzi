package repolezanettiperuzzi.controller;

import org.junit.Test;

import static org.junit.Assert.*;

public class SocketChooseCardRequestTest {

    @Test
    public void mapsChooseCardParameterToNamedField() {

        SocketClientMessage message = SocketClientMessage.parse("ale chooseCard 2");
        SocketChooseCardRequest request = SocketChooseCardRequest.from(message);

        assertEquals(2,request.getCardNumber());
    }
}
