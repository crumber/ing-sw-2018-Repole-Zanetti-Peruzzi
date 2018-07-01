package repolezanettiperuzzi.model.toolcards;

import org.junit.Test;
import repolezanettiperuzzi.model.*;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class RunningPliersTest {

    private RunningPliers testRunningPliers=new RunningPliers();
    private GameBoard board=new GameBoard();
    private Player player;
    private ArrayList<Integer> parameterforcard=new ArrayList<>();
    private Box[][] boardBoxes;

    @Test
    public void effect() {

        boardBoxes=new Box[4][5];

        for ( int i = 0; i < 4; i++){

            for ( int j = 0; j < 5; j++){

                if(i==0 && j==0){

                    boardBoxes[i][j]=new Box(Value.ONE);

                }else{

                    boardBoxes[i][j]=new Box(Value.FOUR);

                }

            }
        }

        Die die=new Die(Colour.BLUE);
        Window windowTest=new Window("test",4,boardBoxes,"test");
        windowTest.insertDie(die,0,0,"none");

        board.addPlayer("ASH","boh","boh","120.11.101.01",1234);
        board.getPlayer(0).setWindow(windowTest);

        Die d1=new Die(Colour.YELLOW);
        d1.setValue(Value.FOUR);

        board.addDieToDraft(d1);

        parameterforcard.add(0);
        parameterforcard.add(1);
        parameterforcard.add(1);

        board.getPlayer(0).resetTurn();
        board.getPlayer(0).incrTurn();

        assertEquals(-30,testRunningPliers.check(board,board.getPlayer(0),parameterforcard));

        board.getPlayer(0).resetTurn();
        board.getPlayer(0).setInsertDieInThisTurn(false);

        assertEquals(-31,testRunningPliers.check(board,board.getPlayer(0),parameterforcard));

        board.getPlayer(0).setInsertDieInThisTurn(true);

        assertEquals(1,testRunningPliers.check(board,board.getPlayer(0),parameterforcard));
    }

    @Test
    public void check() {

        boardBoxes=new Box[4][5];

        for ( int i = 0; i < 4; i++){

            for ( int j = 0; j < 5; j++){

                if(i==0 && j==0){

                    boardBoxes[i][j]=new Box(Value.SIX);

                }else{

                    boardBoxes[i][j]=new Box(Value.THREE);

                }

            }
        }

        Die die=new Die(Colour.PURPLE);
        die.setValue(Value.SIX);
        Window windowTest=new Window("test",4,boardBoxes,"test");
        windowTest.insertDie(die,0,0,"none");

        board.addPlayer("ASH","boh","boh","120.11.101.01",1234);
        board.getPlayer(0).setWindow(windowTest);

        Die d1=new Die(Colour.GREEN);
        d1.setValue(Value.THREE);

        board.addDieToDraft(d1);

        parameterforcard.add(0);
        parameterforcard.add(1);
        parameterforcard.add(1);

        testRunningPliers.effect(board,board.getPlayer(0),parameterforcard);
        assertTrue(board.getPlayer(0).getWindow().thereIsDie(1,1));

    }
}