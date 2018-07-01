package repolezanettiperuzzi.controller;

import org.json.simple.parser.ParseException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import repolezanettiperuzzi.model.GameBoard;

import java.io.IOException;

import static org.junit.Assert.*;

public class FetchStateTest {

    private Controller controller;
    private GameBoard board;

    @Before
    public void setUp() throws Exception {

        this.board = new GameBoard();
        for(int i =0; i<4; i++){

            this.board.addPlayer("prova"+i,"Socket","GUI","192.168.1."+i+1,80);

        }
        this.controller = new Controller(board.getPlayers(),board);

    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void doActionTest() throws IOException, ParseException {

        FetchState state = new FetchState();
        this.controller.setState(state);
//        assertNull(this.board.getWindowsPool());
        state.doAction(this.controller);
        assertNotNull(this.board.getWindowsPool());

    }

    @Test
    public void sendWindowsTest() throws IOException, ParseException {

        /*FetchState state = new FetchState();
        controller.setState(state);
        state.sendWindows(controller.board.getPlayer(0));
        assertEquals("chooseWindowRoom",controller.board.getPlayer(0).getLastScene());
        assertTrue(controller.isTimerOn());*/

    }

    @Test
    public void getWindows() {
    }

    @Test
    public void setChosenWindowOnTimer() {
    }

    @Test
    public void setChosenWindow() {
    }

    @Test
    public void checkConnectedPlayers() {
    }

    @Test
    public void readyToPlay() {
    }
}