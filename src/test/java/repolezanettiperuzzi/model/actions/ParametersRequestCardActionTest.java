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

    ToolCard card3=new CopperFoilBurnisher(); //id 3
    ToolCard card9=new CorkbackedStraightedge(); //id 9
    ToolCard card10=new GrindingStone(); //id 10
    ToolCard card1=new GrozingPliers(); //id 1
    ToolCard card4=new Lathekin(); //id 4
    ToolCard card5=new LensCutter(); //id 5
    ToolCard card12=new TapWheel(); //id 12
    ToolCard card7=new GlazingHammer(); //id 7
    ToolCard card2=new EglomiseBrush(); //id 2
    ToolCard card6=new FluxBrush(); //id 6
    ToolCard card11=new FluxRemover(); //id 11



    @Test
    public void testDoActionForToolCard11() {

        int question=11;

        assertEquals("dieValue ",testParametersRequestCardAction.doAction(board,question));
        assertEquals(null,testParametersRequestCardAction.doAction(board,-1));

    }

    //testo che esca la stringa di codici delle richieste in modo corretto per ogni id
    @Test
    public void testDoAction() {

        board=new GameBoard();
        board.addDieToDraft(d);
        board.addDieToDraft(d2);

        board.setToolCards(card3,0);
        board.setToolCards(card9,1);
        board.setToolCards(card10,2);

        assertEquals("requestCard startPos endPos ",testParametersRequestCardAction.doAction(board,0));
        assertEquals("requestCard dieDraft endPos ",testParametersRequestCardAction.doAction(board,1));
        assertEquals("requestCard dieDraft ",testParametersRequestCardAction.doAction(board,2));

        board.setToolCards(card1,0);
        board.setToolCards(card4,1);
        board.setToolCards(card5,2);

        assertEquals("requestCard dieDraft incrDecrDie ",testParametersRequestCardAction.doAction(board,0));
        assertEquals("requestCard startPos endPos startPos endPos ",testParametersRequestCardAction.doAction(board,1));
        assertEquals("requestCard dieDraft dieRoundTrack ",testParametersRequestCardAction.doAction(board,2));

        board.setToolCards(card12,0);
        board.setToolCards(card7,1);

        assertEquals("requestCard startPos endPos startPos endPos dieRoundTrack ",testParametersRequestCardAction.doAction(board,0));
        assertEquals("requestCard NOTHING",testParametersRequestCardAction.doAction(board,1));


        board.setToolCards(card2,0);
        board.setToolCards(card6,1);
        board.setToolCards(card11,2);
        assertEquals("requestCard startPos endPos ",testParametersRequestCardAction.doAction(board,0));
        assertEquals("requestCard dieDraft ",testParametersRequestCardAction.doAction(board,1));
        assertEquals("requestCard dieDraft ",testParametersRequestCardAction.doAction(board,2));


    }
}