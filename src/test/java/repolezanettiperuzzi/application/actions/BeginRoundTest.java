package repolezanettiperuzzi.application.actions;

import org.junit.Test;
import repolezanettiperuzzi.model.GameBoard;

import static org.junit.Assert.*;

//test della begin round
public class BeginRoundTest {

    BeginRound testBeginRound=new BeginRound();

    //testo che svolga l'azione in modo corretto
    @Test
    public void doAction() {

        assertEquals(0,testBeginRound.getRound());
        assertEquals(0,testBeginRound.getIndex());
        testBeginRound.increaseIndex();
        testBeginRound.increaseRound();
        assertEquals(1,testBeginRound.getRound());
        assertEquals(1,testBeginRound.getIndex());
        testBeginRound.resetIndex();
        assertEquals(0,testBeginRound.getIndex());

        GameBoard board=new GameBoard();
        board.addPlayer("jobs","asd","ert","jsiji",12334);
        board.addPlayer("bill","asd","ert","jsiji",12334);

        testBeginRound.doAction(board);
        assertEquals(2,testBeginRound.getRound());
        assertEquals(5,board.getSizeDraft());

    }

    @Test
    public void sessionRoundTrackersAreIndependent() {

        BeginRound firstSession = new BeginRound(new RoundTracker());
        BeginRound secondSession = new BeginRound(new RoundTracker());

        firstSession.increaseIndex();
        firstSession.increaseRound();

        assertEquals(1,firstSession.getIndex());
        assertEquals(1,firstSession.getRound());

        assertEquals(0,secondSession.getIndex());
        assertEquals(0,secondSession.getRound());
    }

    @Test
    public void defaultRoundTrackersAreIndependent() {

        BeginRound firstSession = new BeginRound();
        BeginRound secondSession = new BeginRound();

        firstSession.increaseIndex();
        firstSession.increaseRound();

        assertEquals(1,firstSession.getIndex());
        assertEquals(1,firstSession.getRound());

        assertEquals(0,secondSession.getIndex());
        assertEquals(0,secondSession.getRound());
    }

    @Test
    public void doActionUsesSessionRoundTracker() {

        BeginRound beginRound = new BeginRound(new RoundTracker());
        GameBoard board=new GameBoard();
        board.addPlayer("jobs","asd","ert","jsiji",12334);
        board.addPlayer("bill","asd","ert","jsiji",12334);

        beginRound.doAction(board);

        assertEquals(1,beginRound.getRound());
        assertEquals(5,board.getSizeDraft());
    }

    @Test(expected = IllegalArgumentException.class)
    public void requiresRoundTrackerForSessionConstructor() {

        new BeginRound(null);
    }

}
