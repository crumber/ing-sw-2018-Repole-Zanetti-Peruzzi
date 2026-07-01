package repolezanettiperuzzi.view;

import org.junit.Test;
import repolezanettiperuzzi.shared.dto.WindowClient;

import static org.junit.Assert.*;

public class GameViewShowWindowMessageTest {

    @Test
    public void mapsShowWindowPayloadToWindowAndCurrentTime() {

        GameViewSocketMessage message = GameViewSocketMessage.parse("showWindow aurora 5 Y-R 0-1 _ 30");
        GameViewShowWindowMessage showWindowMessage = GameViewShowWindowMessage.from(message);

        assertEquals(30,showWindowMessage.getCurrentTime());
        assertWindow(showWindowMessage.getWindow(),"aurora",5,new String[][]{{"YELLOW","RED"},{"0","1"}});
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
