package repolezanettiperuzzi.model.actions;

import org.junit.Test;
import repolezanettiperuzzi.model.Colour;
import repolezanettiperuzzi.model.Die;
import repolezanettiperuzzi.model.GameBoard;
import repolezanettiperuzzi.model.publiccards.PublicCard;
import repolezanettiperuzzi.model.toolcards.*;

import static org.junit.Assert.*;

public class ParametersRequestCardActionTest {

    ParametersRequestCardAction testParametersRequestCardAction=new ParametersRequestCardAction();
    GameBoard board;
    Die d=new Die(Colour.RED);
    Die d2=new Die(Colour.YELLOW);

    ToolCard card=new CopperFoilBurnisher(); //id 3
    ToolCard card1=new CorkbackedStraightedge(); //id 9
    ToolCard card2=new GrindingStone(); //id 10
    ToolCard card3=new GrozingPliers(); //id 1
    ToolCard card4=new Lathekin(); //id 4
    ToolCard card5=new LensCutter(); //id 5
    ToolCard card6=new TapWheel(); //id 12
    ToolCard card7=new GlazingHammer(); //id 7

    @Test
    public void testDoActionForToolCard11() {

        int question=11;

        assertEquals("dieValue ",testParametersRequestCardAction.doAction(question));
        assertEquals(null,testParametersRequestCardAction.doAction(-1));

    }

    @Test
    public void testDoAction() {

        board=new GameBoard();
        board.addDieToDraft(d);
        board.addDieToDraft(d2);

        board.setToolCards(card,0);
        board.setToolCards(card1,1);
        board.setToolCards(card2,2);

        assertEquals("startPos endPos ",testParametersRequestCardAction.doAction(board,0));
        assertEquals("dieDraft endPos ",testParametersRequestCardAction.doAction(board,1));
        assertEquals("dieDraft ",testParametersRequestCardAction.doAction(board,2));

        board.setToolCards(card3,0);
        board.setToolCards(card4,1);
        board.setToolCards(card5,2);

        assertEquals("dieDraft incrDecrDie ",testParametersRequestCardAction.doAction(board,0));
        assertEquals("startPos endPos startPos endPos ",testParametersRequestCardAction.doAction(board,1));
        assertEquals("dieDraft dieRoundTrack ",testParametersRequestCardAction.doAction(board,2));

        board.setToolCards(card6,0);
        board.setToolCards(card7,1);

        assertEquals("startPos endPos startPos endPos dieRoundTrack ",testParametersRequestCardAction.doAction(board,0));
        assertEquals("NOTHING",testParametersRequestCardAction.doAction(board,1));

    }
}