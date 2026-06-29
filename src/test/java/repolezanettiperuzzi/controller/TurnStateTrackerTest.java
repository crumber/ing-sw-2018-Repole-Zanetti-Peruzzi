package repolezanettiperuzzi.controller;

import org.junit.Test;

import static org.junit.Assert.*;

public class TurnStateTrackerTest {

    @Test
    public void tracksTurnNotificationState() {

        TurnStateTracker tracker = new TurnStateTracker();

        assertFalse(tracker.isTurnNotificationSent());

        tracker.markTurnNotificationSent();

        assertTrue(tracker.isTurnNotificationSent());

        tracker.resetTurnNotification();

        assertFalse(tracker.isTurnNotificationSent());
    }

    @Test
    public void tracksToolCard8State() {

        TurnStateTracker tracker = new TurnStateTracker();

        assertFalse(tracker.isToolCard8Active());

        tracker.activateToolCard8();

        assertTrue(tracker.isToolCard8Active());

        tracker.resetToolCard8();

        assertFalse(tracker.isToolCard8Active());
    }
}
