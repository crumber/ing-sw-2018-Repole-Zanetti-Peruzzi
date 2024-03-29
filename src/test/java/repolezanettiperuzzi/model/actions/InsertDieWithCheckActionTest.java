package repolezanettiperuzzi.model.actions;

import org.junit.Test;
import repolezanettiperuzzi.model.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

//test classe insertdiewithcheckactiontest
public class InsertDieWithCheckActionTest {

    InsertDieWithCheckAction testInsertDieWithCheckAction=new InsertDieWithCheckAction();
    GameBoard board=new GameBoard();
    Box[][] boxes;
    List<Integer> parameter=new ArrayList<>();
    Die d=new Die(Colour.BLUE);
    Die d1=new Die(Colour.YELLOW);
    Die d2=new Die(Colour.YELLOW);


    //testo che svolga l'azione in modo corretto e ritorni i codici giusti di errore
    @Test
    public void testDoAction() {

        boxes = new Box[4][5];

        for ( int i = 0; i < 4; i++){

            for ( int j = 0; j < 5; j++){

                boxes[i][j]= new Box(Colour.YELLOW);

            }
        }
        String name = "window";
        Window tempWindow = new Window(name,3, boxes,"test");

        board.addPlayer("pumpIt","RMI","CLI","127.0.0.1",8008);
        board.getPlayer(0).setWindow(tempWindow);

        board.addDieToDraft(d1);
        parameter.add(0);
        parameter.add(0);
        parameter.add(1);

        assertEquals(1,testInsertDieWithCheckAction.doAction(board.getPlayer(0),board,parameter));
        board.getPlayer(0).getWindow().removeDie(0,1);

        board.addDieToDraft(d1);
        parameter.clear();
        parameter.add(0);
        parameter.add(2);
        parameter.add(4);

        board.getPlayer(0).setInsertDieInThisTurn(false);
        assertEquals(1,testInsertDieWithCheckAction.doAction(board.getPlayer(0),board,parameter));
        board.getPlayer(0).getWindow().removeDie(2,4);

        board.addDieToDraft(d1);
        parameter.clear();
        parameter.add(0);
        parameter.add(3);
        parameter.add(2);

        board.getPlayer(0).setInsertDieInThisTurn(false);

        assertEquals(1,testInsertDieWithCheckAction.doAction(board.getPlayer(0),board,parameter));
        board.getPlayer(0).getWindow().removeDie(3,2);

        board.addDieToDraft(d1);
        parameter.clear();
        parameter.add(0);
        parameter.add(2);
        parameter.add(0);

        board.getPlayer(0).setInsertDieInThisTurn(false);

        assertEquals(1,testInsertDieWithCheckAction.doAction(board.getPlayer(0),board,parameter));
        board.getPlayer(0).getWindow().removeDie(2,0);


        parameter.clear();
        parameter.add(12);
        parameter.add(11);
        parameter.add(1);

        assertEquals(-28,testInsertDieWithCheckAction.doAction(board.getPlayer(0),board,parameter));
        board.getPlayer(0).setInsertDieInThisTurn(false);
        assertEquals(-9,testInsertDieWithCheckAction.doAction(board.getPlayer(0),board,parameter));


        parameter.add(0,0);
        board.addDieToDraft(d);
        d2.setValue(Value.THREE);
        board.addDieToDraft(d2);

        assertEquals(-1,testInsertDieWithCheckAction.doAction(board.getPlayer(0),board,parameter));
        parameter.add(1,-11);
        assertEquals(-1,testInsertDieWithCheckAction.doAction(board.getPlayer(0),board,parameter));
        parameter.add(1,1);
        parameter.add(2,+11);
        assertEquals(-1,testInsertDieWithCheckAction.doAction(board.getPlayer(0),board,parameter));
        parameter.add(2,-11);
        assertEquals(-1,testInsertDieWithCheckAction.doAction(board.getPlayer(0),board,parameter));

        parameter.clear();
        parameter.add(0);
        parameter.add(1);
        parameter.add(1);

        assertEquals(-27,testInsertDieWithCheckAction.doAction(board.getPlayer(0),board,parameter));


        d1.setValue(Value.TWO);
        board.getPlayer(0).getWindow().insertDie(d1,1,1,"none");

        assertEquals(-3,testInsertDieWithCheckAction.doAction(board.getPlayer(0),board,parameter));


        parameter.add(2,3);

        assertEquals(-4,testInsertDieWithCheckAction.doAction(board.getPlayer(0),board,parameter));


        parameter.clear();
        parameter.add(0);
        parameter.add(1);
        parameter.add(2);

        assertEquals(-7,testInsertDieWithCheckAction.doAction(board.getPlayer(0),board,parameter));


        parameter.add(0,1);
        assertEquals(-25,testInsertDieWithCheckAction.doAction(board.getPlayer(0),board,parameter));


        parameter.clear();
        parameter.add(1);
        parameter.add(0);
        parameter.add(0);

        assertEquals(1,testInsertDieWithCheckAction.doAction(board.getPlayer(0),board,parameter));
        assertEquals(1,board.getSizeDraft());
        assertTrue(board.getPlayer(0).getWindow().thereIsDie(0,0));
    }
}