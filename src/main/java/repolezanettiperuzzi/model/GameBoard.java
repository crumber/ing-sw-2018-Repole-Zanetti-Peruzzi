package repolezanettiperuzzi.model;
import repolezanettiperuzzi.model.publiccards.PublicCard;
import repolezanettiperuzzi.model.toolcards.ToolCard;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GameBoard {
    private ArrayList<Player> players;
    private ArrayList<Die> diceDraft;
    private PublicCard[] publicCards;
    private ToolCard[] toolCards;
    private DiceBag diceBag;
    private RoundTrack roundTrack;
    private int round;
    private int nPlayers;
    private int[] costToolCard = new int[3];

    public GameBoard(){

        players = new ArrayList<>();
        diceDraft = new ArrayList<>();
        publicCards = new PublicCard[3];
        toolCards = new ToolCard[3];
        diceBag = new DiceBag();
        roundTrack = new RoundTrack();
        round=0;
        nPlayers=0;

        for(int i=0;i<3;i++){

            costToolCard[i]=1;

        }

    }

    public void addPlayer(String playerName, String connection, String UI, String address, int port){

        players.add(new Player(playerName, connection, UI, address, port));
        this.nPlayers++;

    }

    public int getNPlayers(){
        return this.nPlayers;
    }

    //deep clone
    public ArrayList<Player> getPlayersCopy(){
        ArrayList<Player> copy = new ArrayList<Player>(players.size());
        for (Player p : players) copy.add(p.copy());
        return copy;
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

    public Die getDieDraft(int posDie) {

        if(posDie>=diceDraft.size()){

            return null;

        }

        return diceDraft.get(posDie);

    }

    //metodo pericolo che può cambiare il rep dall'esterno in un attimo, meglio toglierlo se non serve
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

    public int getSizeDraft(){

        return diceDraft.size();

    }

    public Die getDieFromRoundTrack(int whichRound, int whichDieRound){

        return roundTrack.getDieRoundTrack(whichRound,whichDieRound);

    }

    public void setDieToRoundTrack(int whichRound, int whichDieRound, Die d){

        roundTrack.setDieOnRoundTrack(whichRound,whichDieRound,d);

    }

    public void addDiceToRoundTrack(){

        ArrayList<Die> remainingDice=new ArrayList<>();

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

    public ToolCard getToolCards(int whichToolCard) {

        return toolCards[whichToolCard];

    }

    public PublicCard getPublicCards(int whichPublicCard) {

        return publicCards[whichPublicCard];

    }

    public int getId(int whichToolCard){

        return toolCards[whichToolCard].getId();

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

    public Player getPlayer(int nPlayer){
        return players.get(nPlayer);
    }

    public Player getPlayerByName(String playerID){
        for(int i = 0; i<players.size(); i++){
            if(players.get(i).getName().equals(playerID)){
                return players.get(i);
            }
        }
        return null;
    }

    public void setToolCards(ToolCard toolCard, int i) {

        this.toolCards[i]=toolCard;

    }

    public void setPublicCards(PublicCard publicCard, int i) {

        this.publicCards[i]=publicCard;
    }

    public List<Player> getPlayers() {

        return (ArrayList<Player>) this.players.clone();

    }
}
