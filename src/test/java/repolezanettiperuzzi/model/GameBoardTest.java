package repolezanettiperuzzi.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class GameBoardTest {

    private GameBoard gB;

    @Test
    public void addPlayer(){
        gB = new GameBoard(3);
        gB.addPlayer("Lorenzo");
        gB.addPlayer("Bubu");
        gB.addPlayer("Attila");
        assertEquals(3,  gB.getNPlayers());
    }
}
