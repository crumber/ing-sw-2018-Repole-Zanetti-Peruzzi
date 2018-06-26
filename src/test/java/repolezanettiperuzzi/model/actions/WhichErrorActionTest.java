/*package repolezanettiperuzzi.model.actions;

import org.junit.Test;
import repolezanettiperuzzi.model.Colour;
import repolezanettiperuzzi.model.Die;
import repolezanettiperuzzi.model.GameBoard;
import repolezanettiperuzzi.model.Player;

import static org.junit.Assert.*;

public class WhichErrorActionTest {

    GameBoard board=new GameBoard();
    int numError;
    String error;
    WhichErrorAction testWhichErrorAction=new WhichErrorAction();

    @Test
    public void testDoAction() {

        numError=-111;
        Die d=new Die(Colour.RED);
        Die d1=new Die(Colour.RED);
        Die d2=new Die(Colour.RED);
        board=new GameBoard();
        board.addDieToDraft(d);
        board.addDieToDraft(d1);
        board.addDieToDraft(d2);
        error=testWhichErrorAction.doAction(board,numError);
        assertEquals("error MANCA LA NUMERO 8 DI CARTA FORSE HA ALTRI ERRORI..",error);

        numError=-1;
        error=testWhichErrorAction.doAction(board,numError);
        assertEquals("error You can't move this die because your starting/final position doesn't exist!!!!",error);

        numError=-2;
        error=testWhichErrorAction.doAction(board,numError);
        assertEquals("error You can't move this die because you have chosen an empty box",error);

        numError=-3;
        error=testWhichErrorAction.doAction(board,numError);
        assertEquals("error You can't move this die into this position because there's a die inside",error);

        numError=-4;
        error=testWhichErrorAction.doAction(board,numError);
        assertEquals("error You can't move this die in this position because there isn't at least one dice next to it",error);

        numError=-5;
        error=testWhichErrorAction.doAction(board,numError);
        assertEquals("error You can't move this die in this position because it doesn't respect the box's colour restriction",error);

        numError=-6;
        error=testWhichErrorAction.doAction(board,numError);
        assertEquals("error You can't move this die in this position because it doesn't respect box's value restriction",error);

        numError=-7;
        error=testWhichErrorAction.doAction(board,numError);
        assertEquals("error You can't move this die in this position box because it doesn't respect box's restriction",error);

        numError=-8;
        error=testWhichErrorAction.doAction(board,numError);
        assertEquals("error You don't have enough favor tokens!",error);

        numError=-9;
        error=testWhichErrorAction.doAction(board,numError);
        assertEquals("error You have chosen an empty position on the draft!",error);

        numError=-10;
        error=testWhichErrorAction.doAction(board,numError);
        assertEquals("error You can't move this die in this position because there is at least one die next to it!",error);

        numError=-11;
        error=testWhichErrorAction.doAction(board,numError);
        assertEquals("error Wrong number! You have to choose a value between 1 and 6!",error);

        numError=-12;
        error=testWhichErrorAction.doAction(board,numError);
        assertEquals("error You can't use this card because this is not the second turn of this round!",error);

        numError=-13;
        error=testWhichErrorAction.doAction(board,numError);
        assertEquals("error You can't use this card because you have already inserted a die!",error);

        numError=-14;
        error=testWhichErrorAction.doAction(board,numError);
        assertEquals("error Your choice does not exist, 1 for increase 0 for decrease!!!",error);

        numError=-15;
        error=testWhichErrorAction.doAction(board,numError);
        assertEquals("error You can't decrease this value because it has already the minimum one",error);

        numError=-16;
        error=testWhichErrorAction.doAction(board,numError);
        assertEquals("error You can't increase value because it has already the maximum one",error);

        numError=-17;
        error=testWhichErrorAction.doAction(board,numError);
        assertEquals("error You can't move die 2 because you have chosen an empty box!",error);

        numError=-18;
        error=testWhichErrorAction.doAction(board,numError);
        assertEquals("error You can't move die 2 in this position because there's a die inside!",error);

        numError=-19;
        error=testWhichErrorAction.doAction(board,numError);
        assertEquals("error You can't move die 2 in this position because there isn't at least one dice next to it",error);

        numError=-20;
        error=testWhichErrorAction.doAction(board,numError);
        assertEquals("error You can't move die 2 in this position because it doesn't respect box's restriction!",error);

        numError=-21;
        error=testWhichErrorAction.doAction(board,numError);
        assertEquals("error There isn't die on round track in the position you have chosen!",error);

        numError=-22;
        error=testWhichErrorAction.doAction(board,numError);
        assertEquals("error You don't move dice because they don't have the same colour of die on round track!",error);

        numError=-23;
        error=testWhichErrorAction.doAction(board,numError);
        assertEquals("error You don't put this die in a final position because there is another one with the same color near this position",error);

        numError=-24;
        error=testWhichErrorAction.doAction(board,numError);
        assertEquals("error You don't put this die in a final position because there is another one with the same value next to this position",error);

        numError=-25;
        error=testWhichErrorAction.doAction(board,numError);
        assertEquals("error You don't put this die in a final position because there is another one with the same color or value next to this position!",error);

        numError=-26;
        error=testWhichErrorAction.doAction(board,numError);
        assertEquals("error You don't put die 2 in a final position because there is another one with the same color or value next to this position!",error);

        numError=-27;
        error=testWhichErrorAction.doAction(board,numError);
        assertEquals("error You can't put this die here, you need to select a boundary position!",error);
    }
}*/