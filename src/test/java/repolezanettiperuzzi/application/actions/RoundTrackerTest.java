package repolezanettiperuzzi.application.actions;

import org.junit.Test;

import static org.junit.Assert.*;

public class RoundTrackerTest {

    @Test
    public void startsAtFirstRoundAndFirstPlayer() {

        RoundTracker tracker = new RoundTracker();

        assertEquals(0,tracker.getIndex());
        assertEquals(0,tracker.getRound());
    }

    @Test
    public void incrementsAndResetsFirstPlayerIndex() {

        RoundTracker tracker = new RoundTracker();

        tracker.increaseIndex();
        tracker.increaseIndex();

        assertEquals(2,tracker.getIndex());

        tracker.resetIndex();

        assertEquals(0,tracker.getIndex());
    }

    @Test
    public void incrementsAndResetsRound() {

        RoundTracker tracker = new RoundTracker();

        tracker.increaseRound();
        tracker.increaseRound();

        assertEquals(2,tracker.getRound());

        tracker.resetRound();

        assertEquals(0,tracker.getRound());
    }
}
