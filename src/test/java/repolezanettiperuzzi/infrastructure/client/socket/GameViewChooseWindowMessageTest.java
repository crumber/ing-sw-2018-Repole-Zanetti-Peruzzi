package repolezanettiperuzzi.infrastructure.client.socket;

import org.junit.Test;
import repolezanettiperuzzi.shared.dto.WindowClient;

import static org.junit.Assert.*;

public class GameViewChooseWindowMessageTest {

    @Test
    public void mapsChooseWindowPayloadToWindowsAndCurrentTime() {

        GameViewSocketMessage message = GameViewSocketMessage.parse("chooseWindow aurora 5 Y-R 0-1 _ industria 4 B-G 2-3 _ 30");
        GameViewChooseWindowMessage chooseWindowMessage = GameViewChooseWindowMessage.from(message);

        assertEquals(30,chooseWindowMessage.getCurrentTime());
        assertEquals(2,chooseWindowMessage.getWindows().size());
        assertWindow(chooseWindowMessage.getWindows().get(0),"aurora",5,new String[][]{{"YELLOW","RED"},{"0","1"}});
        assertWindow(chooseWindowMessage.getWindows().get(1),"industria",4,new String[][]{{"BLUE","GREEN"},{"2","3"}});
    }

    @Test
    public void returnsCopyOfWindowsList() {

        GameViewSocketMessage message = GameViewSocketMessage.parse("chooseWindow aurora 5 Y-R _ 30");
        GameViewChooseWindowMessage chooseWindowMessage = GameViewChooseWindowMessage.from(message);

        chooseWindowMessage.getWindows().clear();

        assertEquals(1,chooseWindowMessage.getWindows().size());
    }

    private void assertWindow(WindowClient window, String name, int favorTokens, String[][] expectedBoxes){
        assertEquals(name,window.getName());
        assertEquals(favorTokens,window.getFTokens());
        for(int i = 0; i < expectedBoxes.length; i++){
            for(int j = 0; j < expectedBoxes[i].length; j++){
                assertEquals(expectedBoxes[i][j],window.getBoardBox()[i][j].toString());
            }
        }
    }
}
