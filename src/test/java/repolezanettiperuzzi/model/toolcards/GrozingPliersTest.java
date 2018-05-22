package repolezanettiperuzzi.model.toolcards;

import org.junit.Test;
import repolezanettiperuzzi.model.*;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class GrozingPliersTest {

    private GrozingPliers testPublicCard=new GrozingPliers();
    private GameBoard board=new GameBoard();
    private Player player;
    private ArrayList<Integer> parameterforcard=new ArrayList<>();

    @Test
    public void check() {

        Die die1=new Die(Colour.RED);
        die1.setValue(Value.FIVE);
        board.addDieToDraft(die1);

        parameterforcard.add(0);
        parameterforcard.add(1);

        assertEquals(1,testPublicCard.check(board,player,parameterforcard));

        die1.setValue(Value.SIX);
        board.setDieDraft(0,die1);

        assertEquals(-16,testPublicCard.check(board,player,parameterforcard));

        parameterforcard.add(1,0);
        die1.setValue(Value.FIVE);
        board.setDieDraft(0,die1);

        assertEquals(1,testPublicCard.check(board,player,parameterforcard));

        die1.setValue(Value.ONE);
        board.setDieDraft(0,die1);

        assertEquals(-15,testPublicCard.check(board,player,parameterforcard));

        parameterforcard.add(1,23);

        assertEquals(-14,testPublicCard.check(board,player,parameterforcard));

        parameterforcard.clear();
        parameterforcard.add(1);
        parameterforcard.add(0);

        assertEquals(-9,testPublicCard.check(board,player,parameterforcard));
    }

    @Test
    public void effect() {

        Die die1=new Die(Colour.RED);
        die1.setValue(Value.FOUR);
        board.addDieToDraft(die1);

        parameterforcard.add(0);
        parameterforcard.add(0);

        testPublicCard.effect(board,player,parameterforcard);
        assertEquals(Value.THREE,board.getDieDraft(0).getValueDie());

        parameterforcard.add(1,1);

        testPublicCard.effect(board,player,parameterforcard);
        assertEquals(Value.FOUR,board.getDieDraft(0).getValueDie());

    }
}