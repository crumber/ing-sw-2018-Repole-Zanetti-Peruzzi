package repolezanettiperuzzi.model.actions;

import org.junit.Test;
import repolezanettiperuzzi.model.GameBoard;

import static org.junit.Assert.*;

//test della begin round
public class BeginRoundTest {

    BeginRound testBeginRound=new BeginRound();

    //testo che svolga l'azione in modo corretto
    @Test
    public void doAction() {

        assertEquals(0,BeginRound.getRound());
        assertEquals(0,BeginRound.getIndex());
        BeginRound.increaseIndex();
        BeginRound.increaseRound();
        assertEquals(1,BeginRound.getRound());
        assertEquals(1,BeginRound.getIndex());
        BeginRound.resetIndex();
        assertEquals(0,BeginRound.getIndex());

        GameBoard board=new GameBoard();
        board.addPlayer("jobs","asd","ert","jsiji",12334);
        board.addPlayer("bill","asd","ert","jsiji",12334);

        testBeginRound.doAction(board);
        assertEquals(5,board.getSizeDraft());

    }

}