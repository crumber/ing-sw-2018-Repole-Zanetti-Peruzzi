package repolezanettiperuzzi.model;
import repolezanettiperuzzi.model.publiccards.PublicCard;
import repolezanettiperuzzi.model.toolcards.ToolCard;

import java.io.IOException;
import java.util.ArrayList;

public class GameBoard {
    ArrayList<RealPlayer> players;
    ArrayList<Die> diceDraft;
    PublicCard publicCards[];
    ToolCard toolCards[];
    DiceBag diceBag;
    RoundTrack roundTrack;
    int round;
    int turn;
    int nPlayers;

    public GameBoard(int nPlayers){
        players = new ArrayList<RealPlayer>(nPlayers);
        diceDraft = new ArrayList<Die>();
        publicCards = new PublicCard[3];
        toolCards = new ToolCard[3];
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

    public void shuffleCards() throws IOException {
        Deck deck = new Deck("cards/publiccards", "cards/toolcards");

        //il metodo draw non pesca ancora una carta casuale ma ne crea na sempre dello stesso tipo
        for(int i = 0; i < 3; i++){
            publicCards[i] = deck.drawPublicCard();
        }

        for(int i = 0; i < 3; i++){
            toolCards[i] = deck.drawToolCard();
        }
    }

    public Die getDieDraft(int posDie) {

        return diceDraft.get(posDie);

    }

    public void setDiceDraft(ArrayList<Die> diceDraft) {

        this.diceDraft = diceDraft;

    }

    public void setDieDraft(int posDie, Die d){

        diceDraft.set(posDie,d);

    }

    public void putDieInBag(int posDieDraft) {

        diceBag.setDieInBag(getDieDraft(posDieDraft));

    }

    public void removeDieFromDraft(int posDieDraft){

        diceDraft.remove(posDieDraft);

    }

    public Die takeDieFromBag(){

        return diceBag.takeDie();

    }

    public void addDieToDraft(Die d){

        diceDraft.add(d);

    }

    public int sizeDraft(){

        return diceDraft.size();

    }

    public Die getDieFromRoundTrack(int whichRound, int whichDieRound){

        return roundTrack.getDieRoundTrack(whichRound,whichDieRound);

    }

    public void setDieToRoundTrack(int whichRound, int whichDieRound, Die d){

        roundTrack.setDieOnRoundTrack(whichRound,whichDieRound,d);

    }
}
