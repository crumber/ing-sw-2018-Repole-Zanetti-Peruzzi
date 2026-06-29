package repolezanettiperuzzi.application.actions;

import org.junit.Test;
import repolezanettiperuzzi.domain.ActionResult;
import repolezanettiperuzzi.model.Box;
import repolezanettiperuzzi.model.Colour;
import repolezanettiperuzzi.model.GameBoard;
import repolezanettiperuzzi.model.Window;

import static org.junit.Assert.*;

//testo la classe Checkcosttoolcardaction
public class CheckCostToolCardActionTest {

    CheckCostToolCardAction testCheckCostToolCardAction=new CheckCostToolCardAction();
    GameBoard board=new GameBoard();
    int whichToolCard=0;

    //testo che svolga l'azione in modo corretto e ritorni i codici giusti di errore
    @Test
    public void testCheckCostToolCard() {

        Box[][] boxes = new Box[4][5];

        for ( int i = 0; i < 4; i++){

            for ( int j = 0; j < 5; j++){

                boxes[i][j]= new Box(Colour.RED);

            }
        }

        String name = "window";
        Window tempWindow = new Window(name,0, boxes,"test");

        board.addPlayer("jerry","RMI","CLI","127.0.0.1",8008);
        board.getPlayer(0).setWindow(tempWindow);

        assertEquals(ActionResult.NOT_ENOUGH_FAVOR_TOKENS,testCheckCostToolCardAction.checkCostToolCardResult(board,board.getPlayer(0),whichToolCard));
        assertEquals(-8,testCheckCostToolCardAction.checkCostToolCard(board,board.getPlayer(0),whichToolCard));

        board.getPlayer(0).setFavorTokens(123);

        assertEquals(ActionResult.SUCCESS,testCheckCostToolCardAction.checkCostToolCardResult(board,board.getPlayer(0),whichToolCard));
        assertEquals(1,testCheckCostToolCardAction.checkCostToolCard(board,board.getPlayer(0),whichToolCard));

        board.getPlayer(0).setUsedCardInThisTurn(true);
        assertEquals(ActionResult.ALREADY_USED_TOOL_CARD,testCheckCostToolCardAction.checkCostToolCardResult(board,board.getPlayer(0),whichToolCard));
        assertEquals(-29,testCheckCostToolCardAction.checkCostToolCard(board,board.getPlayer(0),whichToolCard));
    }
}
