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

        assertEquals(0,testBeginRound.getSessionRound());
        assertEquals(0,testBeginRound.getSessionIndex());
        testBeginRound.increaseSessionIndex();
        testBeginRound.increaseSessionRound();
        assertEquals(1,testBeginRound.getSessionRound());
        assertEquals(1,testBeginRound.getSessionIndex());
        testBeginRound.resetSessionIndex();
        assertEquals(0,testBeginRound.getSessionIndex());

        GameBoard board=new GameBoard();
        board.addPlayer("jobs","asd","ert","jsiji",12334);
        board.addPlayer("bill","asd","ert","jsiji",12334);

        testBeginRound.doAction(board);
        assertEquals(2,testBeginRound.getSessionRound());
        assertEquals(5,board.getSizeDraft());

    }

    @Test
    public void sessionRoundTrackersAreIndependent() {

        BeginRound firstSession = new BeginRound(new RoundTracker());
        BeginRound secondSession = new BeginRound(new RoundTracker());

        firstSession.increaseSessionIndex();
        firstSession.increaseSessionRound();

        assertEquals(1,firstSession.getSessionIndex());
        assertEquals(1,firstSession.getSessionRound());

        assertEquals(0,secondSession.getSessionIndex());
        assertEquals(0,secondSession.getSessionRound());
    }

    @Test
    public void defaultRoundTrackersAreIndependent() {

        BeginRound firstSession = new BeginRound();
        BeginRound secondSession = new BeginRound();

        firstSession.increaseSessionIndex();
        firstSession.increaseSessionRound();

        assertEquals(1,firstSession.getSessionIndex());
        assertEquals(1,firstSession.getSessionRound());

        assertEquals(0,secondSession.getSessionIndex());
        assertEquals(0,secondSession.getSessionRound());
    }

    @Test
    public void doActionUsesSessionRoundTracker() {

        BeginRound beginRound = new BeginRound(new RoundTracker());
        GameBoard board=new GameBoard();
        board.addPlayer("jobs","asd","ert","jsiji",12334);
        board.addPlayer("bill","asd","ert","jsiji",12334);

        beginRound.doAction(board);

        assertEquals(1,beginRound.getSessionRound());
        assertEquals(5,board.getSizeDraft());
    }

    @Test(expected = IllegalArgumentException.class)
    public void requiresRoundTrackerForSessionConstructor() {

        new BeginRound(null);
    }

}
