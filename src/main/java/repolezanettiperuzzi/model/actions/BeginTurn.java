package repolezanettiperuzzi.model.actions;


import repolezanettiperuzzi.model.GameBoard;
import repolezanettiperuzzi.model.Player;


public class BeginTurn {

    private static int currentTurn = 0;

    private static int numPlayedTurn = 0;

    private static int currentPlayer = 0;

    private GameBoard board;

    private Player player;



    public void doAction(Player player, GameBoard board){

        this.board=board;

        this.player=player;

        if(currentTurn==0) {


            //se nessuno ha ancora giocato il proprio turno allora prendo l'indice del primo giocatore di questo round
            if (numPlayedTurn == 0) {

                currentPlayer = BeginRound.getIndex();

            }

        }


    }

    public static void incrTurn(Player player){

        player.incrTurn();

    }

    public static boolean controlTurn(Player player){

        return player.getTurn() == currentTurn ;

    }

    public static int getCurrentPlayer(){

        return currentPlayer;
    }

    public static int getCurrentTurn(){

        return currentTurn;

    }

    public static void resetCurrentTurn(){

        currentTurn=0;

    }

    public static int getNumPlayedTurn(){

        return numPlayedTurn;

    }

    public static void nextTurnParameters(GameBoard board,Player player){

        if(currentTurn==0) {

            //System.out.println("aumento");
            //se l'indice del giocatore è arrivato all'ultimo elemento dell'arraylist di giocatori e non tutti i giocatori hanno giocato il loro turno inizializzo l'indice a zero
            if (currentPlayer == board.getNPlayers() - 1 && numPlayedTurn < board.getNPlayers() - 1) {

                currentPlayer = 0;

            } else {

                currentPlayer++;

            }

            incrTurn(player);

            numPlayedTurn++;

            if (numPlayedTurn == (board.getNPlayers()-1)) {

                currentTurn++;
                resetNumPlayerTurn();

            }


        }else if(currentTurn==1){

            //System.out.println("diminuisco");

            if(currentPlayer == 0 && numPlayedTurn < board.getNPlayers()-1){

                currentPlayer = board.getNPlayers() - 1;

            } else {

                currentPlayer--;

            }

            numPlayedTurn++;


        }
    }

    public static void resetNumPlayerTurn() {

        numPlayedTurn=0;

    }



}
