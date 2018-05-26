package repolezanettiperuzzi.model.toolcards;

import org.junit.Test;
import repolezanettiperuzzi.model.Colour;
import repolezanettiperuzzi.model.Die;
import repolezanettiperuzzi.model.GameBoard;
import repolezanettiperuzzi.model.Player;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class FluxBrushTest {


    private FluxBrush testPublicCard=new FluxBrush();
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

        Die dietest=new Die(Colour.BLUE);
        board.addDieToDraft(dietest);
        parameterforcard.add(0);
        testPublicCard.effect(board,player,parameterforcard);

        assertEquals(dietest,board.getDieDraft(0));

    }
}