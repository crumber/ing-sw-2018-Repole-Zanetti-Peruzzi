package repolezanettiperuzzi.view.modelwrapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GameBoardClient {

    private ArrayList<PlayerClient> players;
    private ArrayList<DieClient> diceDraft;
    private HashMap<Integer, String> publicCards;
    private HashMap<Integer, String> toolCards;
    private RoundTrackClient roundTrack;
    private int round;
    private int nPlayers;
    private int[] costToolCard = new int[3];

    public GameBoardClient(){

        players = new ArrayList<>();
        diceDraft = new ArrayList<>();
        publicCards = new HashMap<>();
        toolCards = new HashMap<>();
        roundTrack = new RoundTrackClient();
        round=0;
        nPlayers=0;

        for(int i=0;i<3;i++){

            costToolCard[i]=1;

        }

    }

    public int getNPlayers(){
        return this.nPlayers;
    }

    public int getPlayersOnline(){
        int nPlayersOnline = 0;
        for(int i = 0; i<this.nPlayers; i++){
            if(players.get(i).getLiveStatus()){
                nPlayersOnline++;
            }
        }
        return nPlayersOnline;
    }

    /*
    PER ORA NON USATO E NEMMENO FINITO

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
    */

    public DieClient getDieDraft(int posDie) {

        if(posDie>=diceDraft.size()){

            return null;

        }

        return diceDraft.get(posDie);

    }

    //metodo pericolo che può cambiare il rep dall'esterno in un attimo, meglio toglierlo se non serve
    public void setDiceDraft(ArrayList<DieClient> diceDraft) {

        this.diceDraft = diceDraft;

    }

    public void setDieDraft(int posDie, DieClient d){

        diceDraft.set(posDie,d);

    }

    public void removeDieFromDraft(int posDieDraft){

        diceDraft.remove(posDieDraft);

    }

    public void addDieToDraft(DieClient d){

        diceDraft.add(d);

    }

    public int getSizeDraft(){

        return diceDraft.size();

    }

    public DieClient getDieFromRoundTrack(int whichRound, int whichDieRound){

        return roundTrack.getDieRoundTrack(whichRound,whichDieRound);

    }

    public void setDieToRoundTrack(int whichRound, int whichDieRound, DieClient d){

        roundTrack.setDieOnRoundTrack(whichRound,whichDieRound,d);

    }

    public void addDiceToRoundTrack(){

        ArrayList<DieClient> remainingDice=new ArrayList<>();

        for(int i=0;i<this.getSizeDraft();i++){

            remainingDice.add(this.getDieDraft(i));

        }

        roundTrack.addDice(remainingDice);
        diceDraft.clear();

    }

    public int getCostToolCard(int whichToolCard) {

        return costToolCard[whichToolCard];

    }

    public void setCostToolCard(int whichToolCard) {

        costToolCard[whichToolCard]=2;

    }

    public int getRound() {

        return round;

    }

    //Incrementa il round di 1.
    //Non ho creato una setRound perche' non sarebbe mai utile a noi cambiare il numero del round
    //da 1 a 10 e sarebbe anche un metodo pericoloso che potrebbe creare risultati inaspettati.
    public void incrRound(){
        this.round++;
    }

    public PlayerClient getPlayer(int nPlayer){
        return players.get(nPlayer);
    }

    public PlayerClient getPlayerByName(String playerID){
        for(int i = 0; i<players.size(); i++){
            if(players.get(i).getName().equals(playerID)){
                return players.get(i);
            }
        }
        return null;
    }

    public List<PlayerClient> getPlayers() {

        return (ArrayList<PlayerClient>) this.players.clone();

    }
}
