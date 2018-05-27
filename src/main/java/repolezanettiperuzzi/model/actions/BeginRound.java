package repolezanettiperuzzi.model.actions;

import repolezanettiperuzzi.model.GameBoard;
import repolezanettiperuzzi.model.Player;

public class BeginRound extends Action {

    private static int playerIndex = 0;

    public void doAction(GameBoard board) {

        board.incrRound();

        incrIndex();

        //FARE PARTIRE IL TIMER PER QUESTO ROUND DALLA CLASSE TIMER CHE DOBBIAMO ANCORA CREARE

        //si suppone che il draft si stato pulito alla fine del turno precedente
        //in futuro si potrebbe voler aspettare l'input da parte del giocatore che dovrebbe pescare i dadi
        //per questo round in modo da rendere la cosa un po' piu' fedele rispetto alle regole del gioco
        for (int i = 0; i<((board.getNPlayers()*2)+1); i++) {

            board.addDieToDraft(board.takeDieFromBag());

        }

    }


    public static void incrIndex(){

        playerIndex++;

    }

    public static int getIndex(){

        return playerIndex;

    }

    public static void resetIndex(){

        playerIndex=0;

    }

}
