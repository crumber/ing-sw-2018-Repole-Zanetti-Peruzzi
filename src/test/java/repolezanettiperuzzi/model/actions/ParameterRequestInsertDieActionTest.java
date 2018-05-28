package repolezanettiperuzzi.model.actions;

import org.junit.Test;
import repolezanettiperuzzi.model.Colour;
import repolezanettiperuzzi.model.Die;
import repolezanettiperuzzi.model.GameBoard;

import static org.junit.Assert.*;

public class ParameterRequestInsertDieActionTest {

    ParameterRequestInsertDieAction testParameterRequestInsertDieAction=new ParameterRequestInsertDieAction();
    GameBoard board=new GameBoard();
    Die d=new Die(Colour.RED);
    Die d1=new Die(Colour.BLUE);

    @Test
    public void testDoAction() {

        board.addDieToDraft(d);
        board.addDieToDraft(d1);

        assertEquals("Which die on draft would you put in your window? (choose from 0 to 1) ?\n" +
                "where would you put this die (which row (from 0 to 3) and which column (from 0 to 4).. answer like this 2 3) ?\n",testParameterRequestInsertDieAction.doAction(board));
    }
}