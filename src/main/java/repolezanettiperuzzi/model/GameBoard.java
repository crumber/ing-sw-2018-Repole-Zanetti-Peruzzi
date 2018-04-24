package repolezanettiperuzzi.model;
import java.util.ArrayList;

public class GameBoard {
    ArrayList<RealPlayer> players;
    ArrayList<Die> diceDraft;
    //PublicCard publicCards[];
    //ToolCard toolCards[];
    DiceBag diceBag;
    RoundTrack roundTrack;
    int round;
    int turn;
    int nPlayers;

    public GameBoard(int nPlayers){
        players = new ArrayList<RealPlayer>(nPlayers);
        diceDraft = new ArrayList<Die>();
        //publicCards = new PublicCard[3];
        //toolCards = new ToolCard[3];
        diceBag = new DiceBag();
        roundTrack = new RoundTrack();
        this.nPlayers = nPlayers;
    }

    public void addPlayer(String playerName){
        players.add(new RealPlayer(playerName));
    }

    public int getNPlayers(){
        return this.nPlayers;
    }

    public void shuffleCards(){
        /*Deck deck = new Deck("carte.txt");

        for(int i = 0; i < 3; i++){
            publicCards[i] = deck.drawPublicCard();
        }

        for(int i = 0; i < 3; i++){
            toolCards[i] = deck.drawToolCard();
        }*/
    }

}
