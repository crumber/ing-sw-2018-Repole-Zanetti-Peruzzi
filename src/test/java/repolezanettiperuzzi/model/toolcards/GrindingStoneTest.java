package repolezanettiperuzzi.model.toolcards;

import org.junit.Test;
import repolezanettiperuzzi.model.*;

import java.util.ArrayList;

import static org.junit.Assert.*;

//testa la tool card 10
public class GrindingStoneTest {

    private ToolCard testPublicCard=new GrindingStone();
    private GameBoard board=new GameBoard();
    private Player player;
    private ArrayList<Integer> parameterforcard=new ArrayList<>();

    @Test
    public void testCheck() {

        Die die1=new Die(Colour.RED);
        board.addDieToDraft(die1);

        parameterforcard.add(0);
        assertEquals(1,testPublicCard.check(board,player,parameterforcard));

    }

    @Test
    public void testEffect() {

        Die die1=new Die(Colour.RED);

        Die die2=new Die(Colour.RED);
        die2.setValue(Value.TWO);

        Die die3=new Die(Colour.RED);
        die3.setValue(Value.THREE);

        Die die4=new Die(Colour.RED);
        die4.setValue(Value.FOUR);

        Die die5=new Die(Colour.RED);
        die5.setValue(Value.FIVE);

        Die die6=new Die(Colour.RED);
        die6.setValue(Value.SIX);

        board.addDieToDraft(die1);
        board.addDieToDraft(die2);
        board.addDieToDraft(die3);
        board.addDieToDraft(die4);
        board.addDieToDraft(die5);
        board.addDieToDraft(die6);

        parameterforcard.add(5);
        testPublicCard.effect(board,player,parameterforcard);
        assertEquals(Value.ONE,board.getDieDraft(parameterforcard.get(0)).getValueDie());

        parameterforcard.add(0,4);
        testPublicCard.effect(board,player,parameterforcard);
        assertEquals(Value.TWO,board.getDieDraft(parameterforcard.get(0)).getValueDie());

        parameterforcard.add(0,3);
        testPublicCard.effect(board,player,parameterforcard);
        assertEquals(Value.THREE,board.getDieDraft(parameterforcard.get(0)).getValueDie());

        parameterforcard.add(0,2);
        testPublicCard.effect(board,player,parameterforcard);
        assertEquals(Value.FOUR,board.getDieDraft(parameterforcard.get(0)).getValueDie());

        parameterforcard.add(0,1);
        testPublicCard.effect(board,player,parameterforcard);
        assertEquals(Value.FIVE,board.getDieDraft(parameterforcard.get(0)).getValueDie());

        parameterforcard.add(0,0);
        testPublicCard.effect(board,player,parameterforcard);
        assertEquals(Value.SIX,board.getDieDraft(parameterforcard.get(0)).getValueDie());
    }
}