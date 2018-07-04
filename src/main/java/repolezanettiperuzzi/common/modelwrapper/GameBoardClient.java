package repolezanettiperuzzi.common.modelwrapper;

import repolezanettiperuzzi.model.publiccards.PublicCard;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Classe che modellizza lo schema del gioco nel client
 * @author Giampiero Repole
 * @author Andrea Zanetti
 * @author Alessandro Peruzzi
 */
public class GameBoardClient implements Serializable {

    private ArrayList<PlayerClient> players;
    private ArrayList<DieClient> diceDraft;
    private ArrayList<PublicCardClient> publicCards;
    private ArrayList<ToolCardClient> toolCards;
    private RoundTrackClient roundTrack;
    private int round;
    private int turn;
    private int nPlayers;
    private int[] costToolCard = new int[3];

    /**
     * Costruttore della classe
     */
    public GameBoardClient(){

        players = new ArrayList<>();
        diceDraft = new ArrayList<>();
        publicCards = new ArrayList<>();
        toolCards = new ArrayList<>();
        roundTrack = new RoundTrackClient();
        round=0;
        nPlayers=0;

        for(int i=0;i<3;i++){

            costToolCard[i]=1;

        }

    }

    /**
     *
     * @return La dimensione del roundtrack
     */
    public int sizeRoundTrack(){

        return roundTrack.sizeRoundTrack();

    }

    /**
     *
     * @return True se il roundtrack è vuoto
     */
    public boolean isEmptyRoundTrack(){

        return roundTrack.isEmptyRoundTrack();

    }

    /**
     *
     * @param whichRound Intero che indica quale round del roundtrack
     * @return Ritorna il numero di dadi nel round del roundtrack
     */
    public int sizeDiceRoundTrack(int whichRound){

        return roundTrack.sizeRound(whichRound);

    }

    /**
     *
     * @return Numero di player
     */
    public int getNPlayers(){
        return this.nPlayers;
    }

    /**
     * Aggiunge un giocatore alla lista di giocatori che hanno aderito alla partita
     * @param playerName Nome del giocatore
     */
    public void addPlayer(String playerName){

        players.add(new PlayerClient(playerName));
        this.nPlayers++;

    }

    /**
     * Aggiunge una public card alla game board
     * @param title Titolo della carta
     * @param description Descrizione della carta
     * @param value Valore della carta
     */
    public void addPublicCard(String title, String description, int value){
        publicCards.add(new PublicCardClient(title, description, value));
    }

    /**
     * Aggiunge una tool card alla game board
     * @param title Titolo della carta
     * @param description Descrizione della carta
     * @param id Id della carta
     * @param favorTokens Numero favor token
     */
    public void addToolCard(String title, String description, int id, int favorTokens){
        toolCards.add(new ToolCardClient(title, description, id, favorTokens));
    }

    /**
     *
     * @return ArrayList con tutte le public card della game board
     */
    public ArrayList<PublicCardClient> getPublicCards(){
        return this.publicCards;
    }

    /**
     *
     * @return ArrayList con tutte le tool card della game board
     */
    public ArrayList<ToolCardClient> getToolCards(){
        return this.toolCards;
    }

    /**
     *
     * @return Numero player Online
     */
    public int getPlayersOnline(){
        int nPlayersOnline = 0;
        for(int i = 0; i<this.nPlayers; i++){
            if(players.get(i).getLiveStatus()){
                nPlayersOnline++;
            }
        }
        return nPlayersOnline;
    }

    /**
     *
     * @param posDie Posizione del dado nel draft
     * @return Il dado presente nella posizione del draft selezionata
     */
    public DieClient getDieDraft(int posDie) {

        if(posDie>=diceDraft.size()){

            return null;

        }

        return diceDraft.get(posDie);

    }

    /**
     * Rimuove il dado dal draft
     * @param posDieDraft Posizione del dado nel draft
     */
    public void removeDieFromDraft(int posDieDraft){

        diceDraft.remove(posDieDraft);

    }

    /**
     * Aggiunge il dado al draft
     * @param d Dado da aggiungere al draft
     */
    public void addDieToDraft(DieClient d){

        diceDraft.add(d);

    }

    /**
     *
     * @return La dimensione del draft
     */
    public int getSizeDraft(){

        return diceDraft.size();

    }

    /**
     *
     * @param whichRound Indica il round del roundtrack
     * @param whichDieRound Indica quale dado nel round del roundtrack
     * @return Il dado presente nel round del roundtrack
     */
    public DieClient getDieFromRoundTrack(int whichRound, int whichDieRound){

        return roundTrack.getDieRoundTrack(whichRound,whichDieRound);

    }

    /**
     *
     * @param whichToolCard Indica quale tool card
     * @return Il costo della tool card
     */
    public int getCostToolCard(int whichToolCard) {

        return costToolCard[whichToolCard];

    }

    /**
     *
     * @return Il valore del round
     */
    public int getRound() {

        return round;

    }

    /**
     *
     * @param nPlayer Indica quale player
     * @return Il player
     */
    public PlayerClient getPlayer(int nPlayer){
        return players.get(nPlayer);
    }

    /**
     *
     * @param playerID Stringa che rappresenta il nome del player
     * @return Il player cercato
     */
    public PlayerClient getPlayerByName(String playerID){
        for(int i = 0; i<players.size(); i++){
            if(players.get(i).getName().equals(playerID)){
                return players.get(i);
            }
        }
        return null;
    }

    /**
     *
     * @return Clone della lista di player
     */
    public List<PlayerClient> getPlayers() {

        return (ArrayList<PlayerClient>) this.players.clone();

    }

    /**
     *
     * @return Il roundtrack
     */
    public RoundTrackClient getRoundTrack(){

        return this.roundTrack;

    }

    /**
     * Inizializza il round
     * @param round Valore round
     */
    public void setRound(int round){

        this.round=round;

    }

    /**
     * Inizializza il turno
     * @param turn Valore turno
     */
    public void setTurn(int turn){

        this.turn=turn;

    }

    /**
     *
     * @return Il valore del turno
     */
    public int getTurn(){

        return this.turn;

    }
}
