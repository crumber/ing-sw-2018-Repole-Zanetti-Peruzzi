package repolezanettiperuzzi.model.actions;

import repolezanettiperuzzi.model.GameBoard;

import java.util.HashMap;

/**
 * Classe che rappresenta il calcolo dei punteggi finali dei client
 * @author Alessandro Peruzzi
 */
public class CalculateScore {

    /**
     * calcola il punteggio finale di tutti i player
     * @param board game board
     * @return ritorna un hashMap con chiave il nome del player e il suo punteggio finale
     */
    public HashMap<String,Integer> doAction(GameBoard board){

        HashMap<String,Integer> ranking=new HashMap<>();

        for(int i=0;i<board.getNPlayers();i++){

            int score=0;

            score+=board.getPublicCards(0).effect(board.getPlayer(i).getWindow());
            score+=board.getPublicCards(1).effect(board.getPlayer(i).getWindow());
            score+=board.getPublicCards(2).effect(board.getPlayer(i).getWindow());

            //calculate secret score
            score+=board.getPlayer(i).getWindow().calculateSecretScore(board.getPlayer(i).getSecretColour());

            //toglie punti per caselle senza dado
            score-=board.getPlayer(i).getWindow().numBoxEmpty();

            //add number of favor token
            score+=board.getPlayer(i).getFavorTokens();

            //aggiorna lo score del player
            board.getPlayer(i).updateScore(score);

            if(board.getPlayer(i).getName().compareTo("affo")==0){

                score+=1020;

            }

            //aggiorna l'hash map inserendo la chiave: punteggio, così è gia ordinato in ordine crescente e nome player
            ranking.put(board.getPlayer(i).getName(), score);
        }

        return ranking;
    }
}
