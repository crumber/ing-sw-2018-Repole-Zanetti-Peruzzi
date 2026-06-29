package repolezanettiperuzzi.domain;

import org.junit.Test;

import static org.junit.Assert.*;

public class ActionResultTest {

    @Test
    public void testMapsLegacyCodesToNamedResults() {

        assertEquals(ActionResult.SUCCESS, ActionResult.fromCode(1));
        assertEquals(ActionResult.FLUX_REMOVER_SECOND_STEP_REQUIRED, ActionResult.fromCode(11));
        assertEquals(ActionResult.ALREADY_INSERTED_DIE, ActionResult.fromCode(-28));
        assertEquals(ActionResult.DIE_NOT_INSERTED_IN_TURN, ActionResult.fromCode(-111));
    }

    @Test
    public void testBuildsClientErrorMessage() {

        assertEquals("error alreadyInsertedDie", ActionResult.ALREADY_INSERTED_DIE.toClientErrorMessage());
        assertEquals("error notInsertDieInTurn", ActionResult.fromErrorCode(11).toClientErrorMessage());
    }
}
