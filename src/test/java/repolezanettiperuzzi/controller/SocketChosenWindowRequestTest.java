package repolezanettiperuzzi.controller;

import org.junit.Test;

import static org.junit.Assert.*;

public class SocketChosenWindowRequestTest {

    @Test
    public void mapsChosenWindowParameterToDecodedName() {

        SocketClientMessage message = SocketClientMessage.parse("ale chosenWindow Aurorae-Magnificus");
        SocketChosenWindowRequest request = SocketChosenWindowRequest.from(message);

        assertEquals("Aurorae Magnificus",request.getWindowName());
    }
}
