package repolezanettiperuzzi.model.actions;

import org.junit.Test;
import repolezanettiperuzzi.model.Colour;
import repolezanettiperuzzi.model.Die;
import repolezanettiperuzzi.model.GameBoard;
import repolezanettiperuzzi.model.Player;

import static org.junit.Assert.*;

public class EndRoundTest {

    EndRound testEndRound= new EndRound();

    @Test
    public void doAction() {

        GameBoard gameBoard=new GameBoard();

        gameBoard.addPlayer("name1","boh","ui","where",123434);
        gameBoard.addPlayer("name2","boh","ui","where",122431);
        gameBoard.addPlayer("name3","boh","ui","where",656665);
        gameBoard.addPlayer("name4","boh","ui","where",323223);

        gameBoard.getPlayer(0).incrTurn();
        gameBoard.getPlayer(1).incrTurn();
        gameBoard.getPlayer(2).incrTurn();
        gameBoard.getPlayer(3).incrTurn();
        gameBoard.getPlayer(0).incrTurn();

        Die die1=new Die(Colour.RED);
        Die die2=new Die(Colour.GREEN);
        Die die3=new Die(Colour.BLUE);
        Die die4=new Die(Colour.YELLOW);

        gameBoard.addDieToDraft(die1);
        gameBoard.addDieToDraft(die2);
        gameBoard.addDieToDraft(die3);
        gameBoard.addDieToDraft(die4);

        assertEquals(4,gameBoard.getSizeDraft());

        assertEquals(2,gameBoard.getPlayer(0).getTurn());
        assertEquals(1,gameBoard.getPlayer(1).getTurn());
        assertEquals(1,gameBoard.getPlayer(2).getTurn());
        assertEquals(1,gameBoard.getPlayer(3).getTurn());

        BeginRound.increaseIndex();
        BeginRound.increaseIndex();
        BeginRound.increaseIndex();
        BeginRound.increaseIndex();

        testEndRound.doAction(gameBoard);

        assertEquals(0,gameBoard.getSizeDraft());
        assertEquals(0,gameBoard.getPlayer(0).getTurn());
        assertEquals(0,gameBoard.getPlayer(1).getTurn());
        assertEquals(0,gameBoard.getPlayer(2).getTurn());
        assertEquals(0,gameBoard.getPlayer(3).getTurn());

        assertEquals(die2,gameBoard.getDieFromRoundTrack(0,1));

        Die die5=new Die(Colour.YELLOW);

        gameBoard.addDieToDraft(die5);
        testEndRound.doAction(gameBoard);

        assertEquals(0,gameBoard.getSizeDraft());
        assertEquals(0,gameBoard.getPlayer(0).getTurn());
        assertEquals(0,gameBoard.getPlayer(1).getTurn());
        assertEquals(0,gameBoard.getPlayer(2).getTurn());
        assertEquals(0,gameBoard.getPlayer(3).getTurn());
        assertEquals(die5,gameBoard.getDieFromRoundTrack(1,0));




    }
}