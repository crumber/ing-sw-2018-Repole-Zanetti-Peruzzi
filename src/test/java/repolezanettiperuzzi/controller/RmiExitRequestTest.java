package repolezanettiperuzzi.controller;

import org.junit.Test;

import static org.junit.Assert.*;

public class RmiExitRequestTest {

    @Test
    public void mapsExitSceneToNamedValue() {

        RmiExitRequest request = new RmiExitRequest("waitingRoom");

        assertEquals(RmiExitScene.WAITING_ROOM,request.getScene());
    }

    @Test
    public void mapsUnknownExitSceneWithoutFailing() {

        RmiExitRequest request = new RmiExitRequest("someScene");

        assertEquals(RmiExitScene.UNKNOWN,request.getScene());
    }
}
