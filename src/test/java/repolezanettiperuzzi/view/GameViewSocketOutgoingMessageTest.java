package repolezanettiperuzzi.view;

import org.junit.Test;

import static org.junit.Assert.*;

public class GameViewSocketOutgoingMessageTest {

    @Test
    public void buildsClientSocketWireMessages() {

        assertEquals("ale init secret socket cli 1234",GameViewSocketOutgoingMessage.init("ale","secret","socket","cli",1234));
        assertEquals("ale waitingOk",GameViewSocketOutgoingMessage.waitingRoomLoaded("ale"));
        assertEquals("ale chooseWindowOk",GameViewSocketOutgoingMessage.chooseWindowSceneLoaded("ale"));
        assertEquals("ale gameOk",GameViewSocketOutgoingMessage.gameSceneLoaded("ale"));
        assertEquals("ale exit game",GameViewSocketOutgoingMessage.exit("ale","game"));
        assertEquals("ale insertDie 1 2 3",GameViewSocketOutgoingMessage.insertDie("ale",1,2,3));
        assertEquals("ale chooseCard 2",GameViewSocketOutgoingMessage.chooseCard("ale",2));
        assertEquals("ale responseToolCard 8 move-1-2",GameViewSocketOutgoingMessage.responseToolCard("ale",8,"move-1-2"));
        assertEquals("ale chosenWindow Aurorae-Magnificus",GameViewSocketOutgoingMessage.chosenWindow("ale","Aurorae-Magnificus"));
        assertEquals("ale endTurn",GameViewSocketOutgoingMessage.endTurn("ale"));
    }
}
