package repolezanettiperuzzi.model.toolcards;

import org.junit.Test;
import repolezanettiperuzzi.model.Colour;
import repolezanettiperuzzi.model.Die;
import repolezanettiperuzzi.model.GameBoard;
import repolezanettiperuzzi.model.Player;

import java.util.ArrayList;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GlazingHammerTest {

    private GlazingHammer testPublicCard=new GlazingHammer();
    private GameBoard board=new GameBoard();
    private Player player;
    private ArrayList<Integer> parameterForCard =new ArrayList<>();

    @Test
    public void testCheck() {

        player=mock(Player.class);

        when(player.getTurn()).thenReturn(1);
        when(player.getInsertDieInThisTurn()).thenReturn(true);

        assertEquals(-28,testPublicCard.check(board,player, parameterForCard));

        when(player.getTurn()).thenReturn(1);
        when(player.getInsertDieInThisTurn()).thenReturn(false);

        assertEquals(1,testPublicCard.check(board,player, parameterForCard));

        when(player.getTurn()).thenReturn(0);

        assertEquals(-12,testPublicCard.check(board,player, parameterForCard));

    }

    @Test
    public void testEffect() {

        player=mock(Player.class);
        when(player.getTurn()).thenReturn(1);
        when(player.getInsertDieInThisTurn()).thenReturn(false);

        Die die1=new Die(Colour.RED);
        Die die2=new Die(Colour.RED);

        board.addDieToDraft(die1);
        board.addDieToDraft(die2);

        testPublicCard.effect(board,player, parameterForCard);

        assertEquals(2, board.getSizeDraft());
    }
}