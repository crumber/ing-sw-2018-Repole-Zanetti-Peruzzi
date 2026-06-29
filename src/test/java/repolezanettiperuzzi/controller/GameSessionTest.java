package repolezanettiperuzzi.controller;

import org.junit.Test;

import static org.junit.Assert.*;

public class GameSessionTest {

    @Test
    public void createsSessionTrackers() {

        GameSession session = new GameSession();

        assertNotNull(session.getRoundTracker());
        assertNotNull(session.getTurnTracker());
        assertNotNull(session.getTurnStateTracker());
    }

    @Test
    public void keepsSessionStateIndependent() {

        GameSession firstSession = new GameSession();
        GameSession secondSession = new GameSession();

        firstSession.getRoundTracker().increaseRound();
        firstSession.getTurnStateTracker().markTurnNotificationSent();

        assertEquals(1,firstSession.getRoundTracker().getRound());
        assertTrue(firstSession.getTurnStateTracker().isTurnNotificationSent());

        assertEquals(0,secondSession.getRoundTracker().getRound());
        assertFalse(secondSession.getTurnStateTracker().isTurnNotificationSent());
    }
}
