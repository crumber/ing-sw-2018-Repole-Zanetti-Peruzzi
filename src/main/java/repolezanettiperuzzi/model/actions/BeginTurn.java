package repolezanettiperuzzi.model.actions;


import repolezanettiperuzzi.model.GameBoard;
import repolezanettiperuzzi.model.Player;


public class BeginTurn {

    private static int currentTurn = 0;

    private static int numPlayedTurn = 0;

    private static int currentPlayer = 0;

    public void doAction(Player player, GameBoard board){

        //se nessuno ha ancora giocato il proprio turno allora prendo l'indice del primo giocatore di questo round
        if(numPlayedTurn==0){

            currentPlayer=BeginRound.getIndex();

        }

        //se l'indice del giocatore è arrivato all'ultimo elemento dell'arraylist di giocatori e non tutti i giocatori hanno giocato il loro turno inizializzo l'indice a zero
        if(currentPlayer==board.getNPlayers()-1 && numPlayedTurn<board.getNPlayers()-1){

           currentPlayer=0;

        }else{

            currentPlayer++;

        }

        this.incrTurn(player);

        numPlayedTurn++;

        if(numPlayedTurn==board.getNPlayers()-1){

            currentTurn++;

        }

    }

    public void incrTurn(Player player){

        player.incrTurn();

    }

    public static boolean controlTurn(Player player, GameBoard board){

        return player.getTurn() == currentTurn ;

    }

    public static int getCurrentPlayer(){

        return currentPlayer;
    }

    public static int getCurrentTurn(){

        return currentTurn;

    }









//inserire due metodi che richiamano rispettivamente le azioni UseCard e InsertDie in modo da poterle attivare soltanto quando è il turno del giocatore passato come parametro




}
