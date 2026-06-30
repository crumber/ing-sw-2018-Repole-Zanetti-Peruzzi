package repolezanettiperuzzi.controller;

import org.junit.Test;

import static org.junit.Assert.*;

public class RmiInsertDieRequestTest {

    @Test
    public void storesInsertDieCoordinatesAsNamedFields() {

        RmiInsertDieRequest request = new RmiInsertDieRequest(1,2,3);

        assertEquals(1,request.getDraftPosition());
        assertEquals(2,request.getXPosition());
        assertEquals(3,request.getYPosition());
    }

    @Test
    public void keepsTurnStateParameterFormat() {

        RmiInsertDieRequest request = new RmiInsertDieRequest(1,2,3);

        assertEquals("1 2 3",request.toTurnStateParameter());
    }
}
