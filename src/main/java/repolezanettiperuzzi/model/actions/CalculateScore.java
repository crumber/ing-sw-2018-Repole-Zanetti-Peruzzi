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
     * @return ritorna una stringa nome_punteggio inordine di chi ha il punteggio maggiore
     */
    public String doAction(GameBoard board){

        int [] possInRanking= new int[board.getNPlayers()];
        String ranking="";

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

            board.getPlayer(i).setScore(score);
            possInRanking[i]=i;

        }

        for(int i=0;i<board.getNPlayers();i++){

            for(int j=0; j<board.getNPlayers();j++){

                if(board.getPlayer(i).getScore()<board.getPlayer(j).getScore()){

                    int temp=possInRanking[i];
                    possInRanking[i]=possInRanking[j];
                    possInRanking[j]=temp;

                }
            }
        }

        for (int i=0; i<board.getNPlayers();i++){

            ranking+=(board.getPlayer(possInRanking[i]).getName() +"_" +board.getPlayer(possInRanking[i]).getScore() +"_");

        }

        return ranking;
    }
}
