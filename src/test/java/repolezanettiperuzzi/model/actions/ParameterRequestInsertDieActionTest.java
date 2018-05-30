package repolezanettiperuzzi.model.actions;

import org.junit.Test;
import repolezanettiperuzzi.model.Colour;
import repolezanettiperuzzi.model.Die;
import repolezanettiperuzzi.model.GameBoard;

import static org.junit.Assert.*;

public class ParameterRequestInsertDieActionTest {

    ParameterRequestInsertDieAction testParameterRequestInsertDieAction=new ParameterRequestInsertDieAction();

    @Test
    public void testDoAction() {

        assertEquals("dieDraft endPos ",testParameterRequestInsertDieAction.doAction());
    }
}