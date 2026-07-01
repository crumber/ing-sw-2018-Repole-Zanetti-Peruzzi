package repolezanettiperuzzi.view;

import repolezanettiperuzzi.infrastructure.client.socket.GameViewSocketMessage;

import org.junit.Test;
import repolezanettiperuzzi.shared.dto.ColourClient;
import repolezanettiperuzzi.shared.dto.GameBoardClient;

import static org.junit.Assert.*;

public class GameViewUpdateViewMessageTest {

    @Test
    public void mapsUpdateViewPayloadToClientBoard() {

        GameViewSocketMessage message = GameViewSocketMessage.parse(
                "updateView 1+ale*RED*aurora*5*Y-R_0-1*3*true+12+B2_R5++tool-one_1_move-left_2*tool-two_2_move-right_1*tool-three_3_move-up_1+pub-one_desc-one_5*pub-two_desc-two_4*pub-three_desc-three_3"
        );
        GameBoardClient board = GameViewUpdateViewMessage.from(message).getBoard();

        assertEquals(1,board.getNPlayers());
        assertEquals(1,board.getRound());
        assertEquals(2,board.getTurn());
        assertEquals(2,board.getSizeDraft());
        assertEquals("B2",board.getDieDraft(0).toString());
        assertEquals("R5",board.getDieDraft(1).toString());
        assertTrue(board.isEmptyRoundTrack());

        assertEquals("ale",board.getPlayer(0).getName());
        assertEquals(ColourClient.RED,board.getPlayer(0).getSecretColour());
        assertEquals("aurora",board.getPlayer(0).getWindow().getName());
        assertEquals(3,board.getPlayer(0).getFavorTokens());
        assertTrue(board.getPlayer(0).getLiveStatus());

        assertEquals("tool-one",board.getToolCards().get(0).getTitle());
        assertEquals("move left",board.getToolCards().get(0).getDescription());
        assertEquals(1,board.getToolCards().get(0).getId());
        assertEquals(2,board.getToolCards().get(0).getFavorTokens());

        assertEquals("pub-one",board.getPublicCards().get(0).getTitle());
        assertEquals("desc one",board.getPublicCards().get(0).getDescription());
        assertEquals(5,board.getPublicCards().get(0).getValue());
    }
}
