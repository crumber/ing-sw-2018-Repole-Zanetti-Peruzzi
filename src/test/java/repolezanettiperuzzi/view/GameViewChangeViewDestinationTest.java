package repolezanettiperuzzi.view;

import org.junit.Test;

import static org.junit.Assert.*;

public class GameViewChangeViewDestinationTest {

    @Test
    public void mapsKnownDestinationFromWireValue() {

        assertEquals(GameViewChangeViewDestination.CHOOSE_WINDOW,GameViewChangeViewDestination.fromWireValue("chooseWindow"));
    }

    @Test
    public void mapsUnknownDestinationWithoutFailing() {

        assertEquals(GameViewChangeViewDestination.UNKNOWN,GameViewChangeViewDestination.fromWireValue("lobby"));
    }
}
