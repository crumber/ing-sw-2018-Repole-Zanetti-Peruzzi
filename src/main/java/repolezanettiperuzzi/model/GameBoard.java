package repolezanettiperuzzi.model;
import repolezanettiperuzzi.model.publiccards.PublicCard;
import repolezanettiperuzzi.model.toolcards.ToolCard;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Classe che modellizza lo schema del gioco
 * @author Giampiero Repole
 * @author Andrea Zanetti
 * @author Alessandro Peruzzi
 */
public class GameBoard {

    private ArrayList<Player> players;
    private ArrayList<Die> diceDraft;
    private PublicCard[] publicCards;
    private ToolCard[] toolCards;
    private DiceBag diceBag;
    private RoundTrack roundTrack;
    private ArrayList<Window> windowsPool;
    private HashMap<Player,ArrayList<Window>> playersWindowsChoices;
    private int fetchPlayersToCheck;
    private int fetchReadyPlayers;
    private boolean gameLocked; //indica se possono ancora aggiungersi giocatori al gioco
    private int nPlayers;
    private int[] costToolCard = new int[3];

    /**
     * Costruttore della classe
     */
    public GameBoard(){

        players = new ArrayList<>();
        diceDraft = new ArrayList<>();
        publicCards = new PublicCard[3];
        toolCards = new ToolCard[3];
        diceBag = new DiceBag();
        roundTrack = new RoundTrack();
        nPlayers=0;
        fetchReadyPlayers = 0;
        gameLocked = false;

        for(int i=0;i<3;i++){

            costToolCard[i]=1;

        }

    }

    /**
     * Aggiunge un giocatore alla lista di giocatori che hanno aderito alla partita
     * @param playerName Nome del giocatore
     * @param connection Tipo di connessione del giocatore
     * @param UI Tipo di interfaccia
     * @param address Indirizzo ip del giocatore
     * @param port Porta del client
     */
    public void addPlayer(String playerName, String connection, String UI, String address, int port){

        players.add(new Player(playerName, connection, UI, address, port));
        this.nPlayers++;

    }

    /**
     * Riduce di 1 il numero di player
     */
    public void reduceNPlayers(){

        nPlayers-=1;

    }

    /**
     * Rimuove un giocatore dall'ArrayList
     * @param playerIndex Indice del giocatore all'interno dell'ArrayList
     */
    public void removePlayer(int playerIndex){

        this.players.remove(playerIndex);
        this.reduceNPlayers();

    }

    /**
     * @return Numero di giocatori
     */
    public int getNPlayers(){
        return this.nPlayers;
    }

    /**
     * Deep clone dell'ArrayList di giocatori
     * @return Copia dell'ArrayList di giocatori
     */
    public List<Player> getPlayersCopy(){
        ArrayList<Player> copy = new ArrayList<>(players.size());
        for (Player p : players) copy.add(p.copy());
        return copy;
    }

    /**
     * Restituisce i giocatori che sono ancora onLine
     * @return Numero di giocatori onLine
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
     * Restituisce un dado dalla posizione desiderata del draft
     * @param posDie Posizione sul draft
     * @return Dado selezionato
     */
    public Die getDieDraft(int posDie) {

        if(posDie>=diceDraft.size() || posDie<0){

            return null;

        }

        return diceDraft.get(posDie);

    }

    /**
     * Inserisce un ArrayList di dadi nel draft
     * @param diceDraft ArrayList di dadi
     */
    //metodo pericoloso che può cambiare il rep dall'esterno in un attimo, meglio toglierlo se non serve
    public void setDiceDraft(List<Die> diceDraft) {

        this.diceDraft = (ArrayList<Die>) diceDraft;

    }

    /**
     * Inserisce un dado nella posizione desiderata del draft
     * @param posDie Posizione desiderata
     * @param d Dado da inserire
     */
    public void setDieDraft(int posDie, Die d){

        diceDraft.set(posDie,d);

    }

    /**
     * Sposta un dado dal draft al sacchetto dei dadi
     * @param posDieDraft Posizione del dado sul draft
     */
    public void putDieInBag(int posDieDraft) {

        diceBag.setDieInBag(getDieDraft(posDieDraft));

    }

    /**
     * Rimuove un dado dal draft
     * @param posDieDraft Posizione del dado sul draft
     */
    public void removeDieFromDraft(int posDieDraft){

        diceDraft.remove(posDieDraft);

    }

    /**
     * Prende un dado dal sacchetto
     * @return Dado pescato
     */
    public Die takeDieFromBag(){

        return diceBag.takeDie();

    }

    /**
     * Aggiunge un dado al draft
     * @param d Dado da aggiungere
     */
    public void addDieToDraft(Die d){

        diceDraft.add(d);

    }

    /**
     * @return Dimensione del draft
     */
    public int getSizeDraft(){

        return diceDraft.size();

    }

    /**
     * Prende un dado dalla RoundTrack
     * @param whichRound Round nel quale si vuole prendere il dado
     * @param whichDieRound Posizione del dado che si vuole prendere
     * @return Dado preso
     */
    public Die getDieFromRoundTrack(int whichRound, int whichDieRound){

        return roundTrack.getDieRoundTrack(whichRound,whichDieRound);

    }

    /**
     * Inserisce un dado nella RoundTrack
     * @param whichRound Round attuale
     * @param whichDieRound Posizione nella quale si vuole inserire il dado
     * @param d Dado che si vuole inserire
     */
    public void setDieToRoundTrack(int whichRound, int whichDieRound, Die d){

        roundTrack.setDieOnRoundTrack(whichRound,whichDieRound,d);

    }

    /**
     * Aggiunge alla RoundTrack i dadi rimasti nel Draft alla fine del round
     */
    public void addDiceToRoundTrack(){

        ArrayList<Die> remainingDice=new ArrayList<>();

        for(int i=0;i<this.getSizeDraft();i++){

            remainingDice.add(this.getDieDraft(i));

        }

        roundTrack.addDice(remainingDice);
        diceDraft.clear();

    }

    /**
     * Restituisce il costo di una ToolCard
     * @param whichToolCard Intero che indica la ToolCard di cui si vuole conoscere il costo
     * @return Intero che indica il costo
     */
    public int getCostToolCard(int whichToolCard) {

        return costToolCard[whichToolCard];

    }

    /**
     *
     * @return Il roundtrack
     */
    public RoundTrack getRoundTrack(){

        return this.roundTrack;

    }

    /**
     * Imposta il costo di una ToolCard a 2 dopo che è stata utilizzata una volta
     * @param whichToolCard Intero che indica la ToolCard di cui si vuole modificare il costo
     */
    public void setCostToolCard(int whichToolCard) {

        costToolCard[whichToolCard]=2;

    }

    /**
     * Prende la ToolCard desiderata
     * @param whichToolCard Indice della ToolCard desiderata
     * @return ToolCard desiderata
     */
    public ToolCard getToolCard(int whichToolCard) {

        return toolCards[whichToolCard];

    }

    /**
     * Prende la PublicCard desiderata
     * @param whichPublicCard Indice della PublicCard desiderata
     * @return PublicCard desiderata
     */
    public PublicCard getPublicCards(int whichPublicCard) {

        return publicCards[whichPublicCard];

    }

    /**
     * Restituisce l'ID della ToolCard selezionata
     * @param whichToolCard Indice della ToolCard
     * @return Intero che rappresenta la ToolCard
     */
    public int getId(int whichToolCard){

        return toolCards[whichToolCard].getId();

    }

    /**
     * Restituisce il giocatore selezionato nell'ArrayList
     * @param nPlayer Indice del giocatore selezionato
     * @return Giocatore selezionato
     */
    public Player getPlayer(int nPlayer){
        return players.get(nPlayer);
    }

    /**
     * Per selezionare un giocatore tramite il nome
     * @param playerID Nome del giocatore che si vuole selezionare
     * @return Giocatore selezionato
     */
    public synchronized Player getPlayerByName(String playerID){
        for(int i = 0; i<players.size(); i++){
            if(players.get(i).getName().equals(playerID)){
                return players.get(i);
            }
        }
        return null;
    }

    /**
     * Inserisce una ToolCard nella GameBoard
     * @param toolCard ToolCard da inserire
     * @param i Indice in cui si vuole inserire
     */
    public void setToolCards(ToolCard toolCard, int i) {

        this.toolCards[i]=toolCard;

    }

    /**
     * Ritorna il pool delle Window utile al FetchState
     * @return Pool di Window attuale
     */
    public ArrayList<Window> getWindowsPool(){
        return this.windowsPool;
    }

    /**
     * Setta l'attributo locale prendendo come parametro un windowsPool
     * @param windowsPool Pool di Windows che verra settato nell'attributo locale
     */
    public void setWindowsPool(ArrayList<Window> windowsPool){
        this.windowsPool = windowsPool;
    }

    /**
     * Inizializza l'attributo playersWindowsChoices chiamato nella prima doAction del FetchState
     */
    public void initPlayersWindowsChoices(){
        this.playersWindowsChoices = new HashMap<>();
    }

    /**
     * Inserisce una entry nella Hash Map delle finestre associate ai singoli giocatori
     * @param player Giocatore da inserire come chiave
     * @param windows Windows tra cui puo' scegliere il giocatore
     */
    public void putPlayersWindowsChoices(Player player, ArrayList<Window> windows){
        this.playersWindowsChoices.put(player, windows);
    }

    /**
     * Ritorna le Windows tra cui puo' scegliere un determinato giocatore
     * @param player Giocatore della quale si vogliono ottenere le Window
     * @return Windows tra cui può scegliere il player
     */
    public ArrayList<Window> getPlayersWindowsChoices(Player player){
        return this.playersWindowsChoices.get(player);
    }

    /**
     * Imposta il numero di giocatori da controllare che siano online allo scadere del timer del FetchState
     * @param nPlayers Numero di giocatori
     */
    public void setFetchPlayersToCheck(int nPlayers){
        this.fetchPlayersToCheck = nPlayers;
    }

    /**
     * Ritorna il numero di giocatori da controllare che siano online allo scadere del timer del FetchState
     * @return Numero di giogcaori da controllare
     */
    public int getFetchPlayersToCheck(){
        return this.fetchPlayersToCheck;
    }

    /**
     * Incrementa il numero di giocatori pronti ad iniziare la partita con una Window associata e che abbiano caricato
     * la view per il turno
     */
    public void incrFetchReadyPlayers(){
        this.fetchReadyPlayers++;
    }

    /**
     * Ritorna il numero di giocatori pronti ad iniziare la partita con una Window associata e che abbiano caricato
     * la view per il turno
     * @return Numero di giocatori pronti ad iniziare la partita con una Window associata e che abbiano caricato
     * la view per il turno
     */
    public int getFetchReadyPlayers(){
        return this.fetchReadyPlayers;
    }

    /**
     * Inserisce una PublicCard nella GameBoard
     * @param publicCard PublicCard che si vuole inserire
     * @param i Indice in cui si vuole inserire
     */
    public void setPublicCards(PublicCard publicCard, int i) {

        this.publicCards[i]=publicCard;
    }

    /**
     * @return Copia dell'ArrayList di giocatori
     */
    public List<Player> getPlayers() {

        return (ArrayList<Player>) this.players.clone();

    }

    /**
     * @return Ritorna se il è game locked o no
     */
    public boolean isGameLocked(){
        return this.gameLocked;
    }


    /**
     * Imposta il valore a true del game locked
     */
    public void setGameLocked(){
        this.gameLocked = true;
    }

    /**
     * @return Ritorna la stringa che rappresenta il draft: ogni dado formato da una lettera che rappresenta il colore e un numero per il valore e ogni dado è separato dall'altro da _
     */
    //B3_Y1_G6...
    public String toStringDraft(){

        StringBuilder res= new StringBuilder();

        for(int i = 0; i<diceDraft.size(); i++){

            res.append(diceDraft.get(i).toString());
            if(i<(diceDraft.size()-1)) {
                res.append("_");
            }

        }

        return res.toString();
    }

    /**
     * Richiama il metodo toString della round track
     * @return Ritorna la stringa che rappresenta il round track
     */
    public String toStringRoundTrack(){

        return this.roundTrack.toString();

    }

    /**
     * @return Ritorna una stringa che rappresenta le tool card:titolo_id_descrizione e ogni carta separata da *
     */
    public String toStringToolCards(){

        StringBuilder res= new StringBuilder();

        for(int i=0; i<this.toolCards.length; i++){

            res.append(toolCards[i].getTitle());
            res.append("_");
            res.append(toolCards[i].getId());
            res.append("_");
            res.append(toolCards[i].getDescription().replace(" ","-"));
            res.append("_");
            res.append(costToolCard[i]);
            if(i<(this.toolCards.length-1)) {
                res.append("*");
            }

        }

        return res.toString();

    }

    /**
     * @return Ritorna una stringa che rappresenta le public card: titolo_descrizione_punti e ogni carta separata da *
     */
    public String toStringPublicCards(){

        StringBuilder res= new StringBuilder();

        for(int i=0; i<this.publicCards.length; i++){

            res.append(publicCards[i].getTitle());
            res.append("_");
            res.append(publicCards[i].getDescription().replace(" ","-"));
            res.append("_");
            res.append(publicCards[i].getValue());
            if(i<(this.publicCards.length-1)){
                res.append("*");
            }

        }

        return res.toString();
    }
}
