package repolezanettiperuzzi.model.actions;

import repolezanettiperuzzi.model.Colour;
import repolezanettiperuzzi.model.Deck;
import repolezanettiperuzzi.model.GameBoard;
import repolezanettiperuzzi.model.Player;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class InitializeGame extends Action{

    private ArrayList<Colour> privateObjective;


    public InitializeGame(){

        this.privateObjective = new ArrayList<>(5);
        this.privateObjective.add(0,Colour.RED);
        this.privateObjective.add(1,Colour.BLUE);
        this.privateObjective.add(2,Colour.YELLOW);
        this.privateObjective.add(3,Colour.GREEN);
        this.privateObjective.add(4,Colour.PURPLE);

    }


    public void doAction(GameBoard board) {

        //assign private objective for each Player
        this.assignPrivateObjective(board);

        //TODO assegnare 2 window ad ogni giocatore e la plancia vetrata
        //aspetta azione di Ale

        //assign for each player the tokens that belong to him
        this.assignFlavorTokens(board);


        //initialize deck with tool cards and public cards

        Deck startDeck = null;
        try {
            startDeck = new Deck("cards/publiccards","cards/toolcards");
        } catch (IOException e) {
            e.printStackTrace();
        }


        for(int i = 0; i < 3; i++){

            board.setPublicCards(startDeck.drawPublicCard(),i);

        }

        for(int i = 0; i < 3; i++){

            board.setToolCards(startDeck.drawToolCard(),i);

        }

    }


    private void assignPrivateObjective(GameBoard board){

        Collections.shuffle(this.privateObjective);

        for(int i=0 ; i<board.getNPlayers(); i++){

            Player p = board.getPlayer(i);
            p.setSecretColour(this.privateObjective.remove(0));

        }

    }

    private void assignFlavorTokens(GameBoard board){

        for(int i=0 ; i<board.getNPlayers(); i++){

            Player p = board.getPlayer(i);
            p.setFlavorTokens(board.getPlayer(i).getWindow().getFTokens());

        }
    }

    private void createWindow(GameBoard board){


    }


}
