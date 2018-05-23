package repolezanettiperuzzi.model.toolcards;

import org.junit.Test;
import repolezanettiperuzzi.model.Colour;
import repolezanettiperuzzi.model.Die;
import repolezanettiperuzzi.model.GameBoard;
import repolezanettiperuzzi.model.Player;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class LensCutterTest {

    private LensCutter testPublicCard=new LensCutter();
    private GameBoard board=new GameBoard();
    private Player player;
    private ArrayList<Integer> parameterforcard=new ArrayList<>();

    @Test
    public void check() {

        Die die1=new Die(Colour.RED);
        Die die2=new Die(Colour.BLUE);
        Die die3=new Die(Colour.YELLOW);
        Die die4=new Die(Colour.GREEN);
        Die die5=new Die(Colour.PURPLE);
        Die die6=new Die(Colour.RED);

        board.addDieToDraft(die1);
        board.addDieToDraft(die2);
        board.addDiceToRoundTrack();

        board.addDieToDraft(die3);
        board.addDieToDraft(die4);
        board.addDiceToRoundTrack();

        board.addDieToDraft(die5);
        board.addDieToDraft(die6);

        parameterforcard.add(1);
        parameterforcard.add(1);
        parameterforcard.add(1);

        assertEquals(1,testPublicCard.check(board,player,parameterforcard));

        parameterforcard.add(0,123);
        assertEquals(-9,testPublicCard.check(board,player,parameterforcard));

        parameterforcard.clear();
        parameterforcard.add(1);
        parameterforcard.add(1);
        parameterforcard.add(2);

        assertEquals(-21,testPublicCard.check(board,player,parameterforcard));
    }

    @Test
    public void effect() {

        Die die1=new Die(Colour.RED);
        Die die2=new Die(Colour.BLUE);
        Die die3=new Die(Colour.YELLOW);

        board.addDieToDraft(die1);
        board.addDieToDraft(die2);
        board.addDiceToRoundTrack();

        board.addDieToDraft(die3);

        parameterforcard.add(0);
        parameterforcard.add(0);
        parameterforcard.add(1);

        assertEquals(1,testPublicCard.check(board,player,parameterforcard));

        testPublicCard.effect(board,player,parameterforcard);

        assertEquals(die3,board.getDieFromRoundTrack(0,1));
        assertEquals(1,board.getSizeDraft());
        assertEquals(die2,board.getDieDraft(0));

    }
}