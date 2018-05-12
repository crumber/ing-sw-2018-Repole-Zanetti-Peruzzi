package repolezanettiperuzzi.model.actions;

import repolezanettiperuzzi.model.GameBoard;

import java.util.HashMap;

public class CalculateScore {

    public HashMap<String,Integer> doAction(GameBoard board){

        HashMap<String,Integer> ranking=new HashMap<>();

        for(int i=0;i<board.getNPlayers();i++){

            int score=0;

            score+=board.getPublicCards(0).effect(board.getPlayer(i).getWindow());
            score+=board.getPublicCards(1).effect(board.getPlayer(i).getWindow());
            score+=board.getPublicCards(2).effect(board.getPlayer(i).getWindow());

            //calculate secret score
            score+=board.getPlayer(i).getWindow().calculeteSecretScore(board.getPlayer(i).getSecretColour());

            //aggiorna lo score del player
            board.getPlayer(i).updateScore(score);

            //aggiorna l'hash map inserendo la chiave: punteggio, così è gia ordinato in ordine crescente e nome player
            ranking.put(board.getPlayer(i).getName(), score);
        }

        return ranking;
    }
}
