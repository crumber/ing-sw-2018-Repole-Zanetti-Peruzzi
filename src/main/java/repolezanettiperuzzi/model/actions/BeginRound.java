package repolezanettiperuzzi.model.actions;

import repolezanettiperuzzi.model.GameBoard;

public class BeginRound {

    private static int firstPlayerIndex = 0;

    public void doAction(GameBoard board) {

        board.incrRound(); //increase Round number

        //add die to draft based on number of players
        for (int i = 0; i<((board.getNPlayers()*2)+1); i++) {

            board.addDieToDraft(board.takeDieFromBag());

        }

    }


    public static void increaseIndex(){

        firstPlayerIndex++;

    }

    public static int getIndex(){

        return firstPlayerIndex;

    }

    public static void resetIndex(){

        firstPlayerIndex=0;

    }

}
