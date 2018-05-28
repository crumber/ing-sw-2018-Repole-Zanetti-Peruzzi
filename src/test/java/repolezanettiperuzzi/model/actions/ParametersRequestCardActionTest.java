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
    public void testDoAction() {

        board=new GameBoard();
        board.addDieToDraft(d);
        board.addDieToDraft(d2);

        board.setToolCards(card,0);
        board.setToolCards(card1,1);
        board.setToolCards(card2,2);

        assertEquals("From which box do you want to move the die (insert number of row and number of column, like this: 1 3) ?\n"
                +"In which box do you want to move the die (insert number of row and number of column, like this: 1 3) ?\n",testParametersRequestCardAction.doAction(board,0));
        assertEquals("Which die on draft (from 0 to 1) ?\n"
                + "In which box do you want to move the die (insert number of row and number of column, like this: 1 3) ?\n",testParametersRequestCardAction.doAction(board,1));
        assertEquals("Which die on draft (from 0 to 1) ?\n",testParametersRequestCardAction.doAction(board,2));

        board.setToolCards(card3,0);
        board.setToolCards(card4,1);
        board.setToolCards(card5,2);

        assertEquals("Which die on draft (from 0 to 1) ?\n" +
                "Do you want to increase (insert: 1) or reduce (insert: 0) ?\n",testParametersRequestCardAction.doAction(board,0));
        assertEquals("From which box do you want to move the die 1 (insert number of row and number of column, like this: 1 3) ?\n" +
                "In which box do you want to move the die 1 (insert number of row and number of column, like this: 1 3) ?\n" +
                "From which box do you want to move the die 2 (insert number of row and number of column, like this: 1 3) ?\n" +
                "In which box do you want to move the die 2 (insert number of row and number of column, like this: 1 3) ?\n",testParametersRequestCardAction.doAction(board,1));
        assertEquals("Which die on draft (from 0 to 1) ?\n" +
                "Which die on round track (insert number of round and number of die position on round, like this: 3 2 -> round 3 die 2) ?\n",testParametersRequestCardAction.doAction(board,2));

        board.setToolCards(card6,0);
        board.setToolCards(card7,1);

        assertEquals("From which box do you want to move the die 1 (insert number of row and number of column, like this: 1 3) ?\n" +
                "In which box do you want to move the die 1 (insert number of row and number of column, like this: 1 3) ?\n" +
                "From which box do you want to move the die 2 (insert number of row and number of column, like this: 1 3) ?\n" +
                "In which box do you want to move the die 2 (insert number of row and number of column, like this: 1 3) ?\n" +
                "Which die on round track (insert number of round and number of die position on round, like this: 3 2 -> round 3 die 2) ?\n",testParametersRequestCardAction.doAction(board,0));
        assertEquals("NOTHING",testParametersRequestCardAction.doAction(board,1));

    }
}