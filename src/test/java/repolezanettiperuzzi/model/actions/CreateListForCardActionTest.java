package repolezanettiperuzzi.model.actions;

import org.junit.Test;
import repolezanettiperuzzi.model.GameBoard;
import repolezanettiperuzzi.model.toolcards.*;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class CreateListForCardActionTest {

    CreateListForCardAction testCreatedListForCardAction=new CreateListForCardAction();
    GameBoard board= new GameBoard();
    ToolCard card1=new GrozingPliers(); //id 1
    ToolCard card3=new CopperFoilBurnisher(); //id 3
    ToolCard card4=new Lathekin(); //id 4
    ToolCard card5=new LensCutter(); //id 5
    ToolCard card10=new GrindingStone(); //id 10
    ToolCard card12=new TapWheel(); //id 12
    ToolCard card2=new EglomiseBrush(); //id 2
    ToolCard card6=new FluxBrush(); //id 6
    ToolCard card11=new FluxRemover(); //id 11
    ToolCard card9=new CorkbackedStraightedge(); //id 9
    ToolCard card8=new RunningPliers();

    String stringFromClient;
    ArrayList<Integer> intParameter=new ArrayList<>();

    @Test
    public void testDoAction() {

        board.setToolCards(card1,0);
        board.setToolCards(card3,1);
        board.setToolCards(card4,2);

        stringFromClient="1 2";
        intParameter=(ArrayList<Integer>)testCreatedListForCardAction.doAction(stringFromClient,board,0);

        assertEquals(2,intParameter.size());


        stringFromClient="1 2 3 4";
        intParameter.clear();

        intParameter=(ArrayList<Integer>)testCreatedListForCardAction.doAction(stringFromClient,board,1);

        assertEquals(4,intParameter.size());


        stringFromClient="1 2 3 4 5 6 7 8";
        intParameter.clear();

        intParameter=(ArrayList<Integer>)testCreatedListForCardAction.doAction(stringFromClient,board,2);

        assertEquals(8,intParameter.size());



        board.setToolCards(card5,0);
        board.setToolCards(card10,1);
        board.setToolCards(card12,2);

        stringFromClient="1 2 3";
        intParameter.clear();
        intParameter=(ArrayList<Integer>)testCreatedListForCardAction.doAction(stringFromClient,board,0);

        assertEquals(3,intParameter.size());


        stringFromClient="1";
        intParameter.clear();
        intParameter=(ArrayList<Integer>)testCreatedListForCardAction.doAction(stringFromClient,board,1);

        assertEquals(1,intParameter.size());


        stringFromClient="1 2 3 4 5 6 7 8 9 10";
        intParameter.clear();
        intParameter=(ArrayList<Integer>)testCreatedListForCardAction.doAction(stringFromClient,board,2);

        assertEquals(10,intParameter.size());


        board.setToolCards(card2,0);
        board.setToolCards(card9,1);
        board.setToolCards(card6,2);

        stringFromClient="1 2 3 4";
        intParameter.clear();
        intParameter=(ArrayList<Integer>)testCreatedListForCardAction.doAction(stringFromClient,board,0);

        assertEquals(4,intParameter.size());


        stringFromClient="1 2 3";
        intParameter.clear();
        intParameter=(ArrayList<Integer>)testCreatedListForCardAction.doAction(stringFromClient,board,1);

        assertEquals(3,intParameter.size());


        stringFromClient="1";
        intParameter.clear();
        intParameter=(ArrayList<Integer>)testCreatedListForCardAction.doAction(stringFromClient,board,2);

        assertEquals(1,intParameter.size());


        board.setToolCards(card11,2);

        stringFromClient="1";
        intParameter.clear();
        intParameter=(ArrayList<Integer>)testCreatedListForCardAction.doAction(stringFromClient,board,2);

        assertEquals(1,intParameter.size());

        board.setToolCards(card8,2);

        stringFromClient="1 2 3 4";
        intParameter.clear();
        intParameter=(ArrayList<Integer>)testCreatedListForCardAction.doAction(stringFromClient,board,2);

        assertEquals(3,intParameter.size());

    }
}