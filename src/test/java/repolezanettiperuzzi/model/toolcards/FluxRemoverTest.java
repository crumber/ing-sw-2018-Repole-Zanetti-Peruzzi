package repolezanettiperuzzi.model.toolcards;

import org.junit.Test;
import repolezanettiperuzzi.model.*;

import java.util.ArrayList;

import static org.junit.Assert.*;

//testa la tool card 11
public class FluxRemoverTest {

    private FluxRemover testPublicCard=new FluxRemover();
    private GameBoard board=new GameBoard();
    private Player player;
    private ArrayList<Integer> parameterforcard=new ArrayList<>();

    @Test
    public void testCheck() {

        Die die1=new Die(Colour.RED);

        board.addDieToDraft(die1);

        //testo errore -11 dovuto a valore inserito non compreso tra 1 e 6(per numero mag a 6)
        parameterforcard.add(1234);

        assertEquals(-11,testPublicCard.check(board,player,parameterforcard));

        //testo errore -11 dovuto a valore inserito non compreso tra 1 e 6 (per numero inf a 0)
        parameterforcard.add(0,-1234);

        assertEquals(-11,testPublicCard.check(board,player,parameterforcard));

        parameterforcard.add(0,4);

        assertEquals(1,testPublicCard.check(board,player,parameterforcard));

    }

    @Test
    public void testCheckPreEffect() {

        Die dieTest=new Die(Colour.BLUE);
        board.addDieToDraft(dieTest);

        parameterforcard.add(0);

        assertEquals(1,testPublicCard.checkPreEffect(board,player,parameterforcard));

    }

    @Test
    public void testPreEffect() {

        Die dieTest=new Die(Colour.BLUE);
        Die die1=new Die(Colour.RED);
        board.addDieToDraft(dieTest);
        board.addDieToDraft(die1);

        parameterforcard.add(0);

        assertEquals(1,testPublicCard.checkPreEffect(board,player,parameterforcard));

        assertEquals(11, testPublicCard.preEffect(board,player,parameterforcard));

        assertEquals(2,board.getSizeDraft());
        assertEquals(die1,board.getDieDraft(0));
    }

    @Test
    public void testEffect() {

        Die dieTest=new Die(Colour.BLUE);
        Die die1=new Die(Colour.RED);
        board.addDieToDraft(dieTest);
        board.addDieToDraft(die1);

        parameterforcard.add(5);

        testPublicCard.effect(board,player,parameterforcard);

        assertEquals(Value.FIVE,board.getDieDraft(board.getSizeDraft()-1).getValueDie());

    }
}