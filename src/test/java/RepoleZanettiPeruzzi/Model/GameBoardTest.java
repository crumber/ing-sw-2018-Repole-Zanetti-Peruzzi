package RepoleZanettiPeruzzi.Model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class GameBoardTest {

    GameBoard gB;

    @Test
    public void addPlayer(){
        gB = new GameBoard(3);
        gB.addPlayer("Lorenzo");
        gB.addPlayer("Bubu");
        gB.addPlayer("Attila");
        assertEquals(gB.getNPlayers(), 3);
    }
}
