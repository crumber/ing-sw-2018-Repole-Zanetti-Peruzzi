package repolezanettiperuzzi.model.actions;

import org.junit.Test;
import repolezanettiperuzzi.model.Colour;
import repolezanettiperuzzi.model.Die;
import repolezanettiperuzzi.model.GameBoard;
import repolezanettiperuzzi.model.Player;

import static org.junit.Assert.*;

//test classe whicherroractiontest
public class WhichErrorActionTest {

    GameBoard board=new GameBoard();
    int numError;
    String error;
    WhichErrorAction testWhichErrorAction=new WhichErrorAction();

    //testo che ritorni il codice d'errore corretto
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

        numError=-1;
        error=testWhichErrorAction.doAction(numError);
        assertEquals("error startingFinalPositionNotExist",error);

        numError=-2;
        error=testWhichErrorAction.doAction(numError);
        assertEquals("error notMoveChosenEmptyBox",error);

        numError=-3;
        error=testWhichErrorAction.doAction(numError);
        assertEquals("error notMoveThereIsDieInside",error);

        numError=-4;
        error=testWhichErrorAction.doAction(numError);
        assertEquals("error notMoveNotDiceNextToIt",error);

        numError=-5;
        error=testWhichErrorAction.doAction(numError);
        assertEquals("error notMoveNotRespectColourRestriction",error);

        numError=-6;
        error=testWhichErrorAction.doAction(numError);
        assertEquals("error notMoveNotRespectValueRestriction",error);

        numError=-7;
        error=testWhichErrorAction.doAction(numError);
        assertEquals("error notMoveRespectRestriction",error);

        numError=-8;
        error=testWhichErrorAction.doAction(numError);
        assertEquals("error notNaveFavorTokens",error);

        numError=-9;
        error=testWhichErrorAction.doAction(numError);
        assertEquals("error emptyPositionDraft",error);

        numError=-10;
        error=testWhichErrorAction.doAction(numError);
        assertEquals("error notMoveThereisDieNextToIt",error);

        numError=-11;
        error=testWhichErrorAction.doAction(numError);
        assertEquals("error wrongNumber",error);

        numError=-12;
        error=testWhichErrorAction.doAction(numError);
        assertEquals("error notUseCardIsNotSecondTurn",error);

        numError=-13;
        error=testWhichErrorAction.doAction(numError);
        assertEquals("error notUseCardHaveAlreadyInsertedDie",error);

        numError=-14;
        error=testWhichErrorAction.doAction(numError);
        assertEquals("error choiceNotExist",error);

        numError=-15;
        error=testWhichErrorAction.doAction(numError);
        assertEquals("error notDecreaseIsMinimum",error);

        numError=-16;
        error=testWhichErrorAction.doAction(numError);
        assertEquals("error notIncreaseIsMaximum",error);

        numError=-17;
        error=testWhichErrorAction.doAction(numError);
        assertEquals("error notMoveDie2BoxEmpty",error);

        numError=-18;
        error=testWhichErrorAction.doAction(numError);
        assertEquals("error notMoveDie2NotEmptyBox",error);

        numError=-19;
        error=testWhichErrorAction.doAction(numError);
        assertEquals("error notMoveDie2NotDiceNextToIt",error);

        numError=-20;
        error=testWhichErrorAction.doAction(numError);
        assertEquals("error notMoveDie2NotRespectRestriction",error);

        numError=-21;
        error=testWhichErrorAction.doAction(numError);
        assertEquals("error notDieOnRoundTrack",error);

        numError=-22;
        error=testWhichErrorAction.doAction(numError);
        assertEquals("error notMoveDiceNotSameColourRoundTrack",error);

        numError=-23;
        error=testWhichErrorAction.doAction(numError);
        assertEquals("error notPutThereIsDieSameColorNear",error);

        numError=-24;
        error=testWhichErrorAction.doAction(numError);
        assertEquals("error notPutThereIsDieSameValueNear",error);

        numError=-25;
        error=testWhichErrorAction.doAction(numError);
        assertEquals("error notPutDieThereIsDieSameColorValue",error);

        numError=-26;
        error=testWhichErrorAction.doAction(numError);
        assertEquals("error notPutDie2ThereIsSameColorValue",error);

        numError=-27;
        error=testWhichErrorAction.doAction(numError);
        assertEquals("error notPutDieHereNotBoundaryPosition",error);

        numError=-28;
        error=testWhichErrorAction.doAction(numError);
        assertEquals("error alreadyInsertedDie",error);

        numError=-29;
        error=testWhichErrorAction.doAction(numError);
        assertEquals("error alreadyUsedToolCard",error);

        numError=-30;
        error=testWhichErrorAction.doAction(numError);
        assertEquals("error notFirstTurn",error);

        numError=-31;
        error=testWhichErrorAction.doAction(numError);
        assertEquals("error notInsertDieInTurn",error);
    }
}

