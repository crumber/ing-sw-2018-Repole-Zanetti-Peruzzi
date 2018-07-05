package repolezanettiperuzzi.model.actions;

import org.junit.Test;
import repolezanettiperuzzi.model.*;
import repolezanettiperuzzi.model.toolcards.FluxRemover;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class UseCardActionTest {

    UseCardAction testUseCardAction=new UseCardAction();
    GameBoard board=new GameBoard();
    int whichToolCard=0;
    List<Integer> parameterForCard=new ArrayList<>();
    FluxRemover card1=new FluxRemover();
    Die d=new Die(Colour.YELLOW);
    Die d1=new Die(Colour.RED);
    Die d2=new Die(Colour.BLUE);

    @Test
    public void testDoAction() {

        board.setToolCards(card1,0);
        board.addDieToDraft(d);
        board.addDieToDraft(d1);
        board.addDieToDraft(d2);
        board.addPlayer("kim","RMI","CLI","127.0.0.1",8008);

        parameterForCard.add(7);

        assertEquals(-11,testUseCardAction.doAction(board.getPlayer(0),board,whichToolCard,parameterForCard));

        parameterForCard.add(0,4);
        testUseCardAction.doAction(board.getPlayer(0),board,whichToolCard,parameterForCard);

        assertEquals(Value.FOUR,board.getDieDraft(board.getSizeDraft()-1).getValueDie());
        assertEquals(2,board.getCostToolCard(0));

        testUseCardAction.doAction(board.getPlayer(0),board,whichToolCard,parameterForCard);
        assertEquals(2,board.getCostToolCard(0));


    }

    @Test
    public void testDoActionPreEffect() {

        board.setToolCards(card1,0);
        board.addDieToDraft(d);
        board.addDieToDraft(d1);
        board.addDieToDraft(d2);
        board.addPlayer("trumpino","RMI","CLI","127.0.0.1",8008);

        parameterForCard.add(4);

        assertEquals(-9,testUseCardAction.doActionPreEffect(board.getPlayer(0),board,whichToolCard,parameterForCard));

        parameterForCard.add(0,2);

        assertEquals(11,testUseCardAction.doActionPreEffect(board.getPlayer(0),board,whichToolCard,parameterForCard));
    }
}