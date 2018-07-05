package repolezanettiperuzzi.model;

/**
 * Classe che modellizza il giocatore
 * @author Andrea Zanetti
 * @author Giampiero Repole
 * @author Alessandro Peruzzi
 */
public class Player {

    private String name;
    private Colour secretColour;
    private boolean insertDieInThisTurn;
    private int favorTokens;
    private Window window;
    private int turn;
    private int score;
    private String connection; //RMI o Socket
    private String UI; //CLI o GUI
    private String address;
    private int port;
    private String lastScene;
    private boolean liveStatus;
    private boolean usedCardInThisTurn;

    /**
     * Costruttore della classe
     *
     * @param name       Nome del giocatore
     * @param connection Connessione del giocatore (socket o RMI)
     * @param UI         Interfaccia
     * @param address    Indirizzo ip del client
     * @param port       Porta del client
     */
    public Player(String name, String connection, String UI, String address, int port) {
        this.name = name;
        this.score = 0;
        this.connection = connection;
        this.UI = UI;
        this.turn = 0;
        this.address = address;
        this.port = port;
        this.lastScene = "login";
        this.liveStatus = true;
    }

    /**
     * Costruttore della classe che crea una copia di un altro giocatore
     * @param p Giocatore da copiare
     */
    public Player(Player p) {
        this.name = p.name;
        this.secretColour = p.secretColour;
        this.insertDieInThisTurn = p.insertDieInThisTurn;
        this.favorTokens = p.favorTokens;
        this.window = p.window.copy();
        this.turn = p.turn;
        this.score = p.score;
        this.connection = p.connection;
        this.UI = p.UI;
        this.address = p.address;
        this.port = p.port;
        this.lastScene = p.lastScene;
        this.liveStatus = p.liveStatus;
    }

    /**
     * Aggiorna il punteggio del giocatore
     *
     * @param points Punti del giocatore
     */
    public void updateScore(int points) {

        score += points;

    }

    /**
     * Assegna il punteggio al player
     * @param score Punteggio da assegnare al player
     */
    public void setScore(int score){

        this.score=score;

    }

    /**
     * Imposta a vero o falso l'attributo che indica se il giocatore ha usato o meno una carta in questo turno
     * @param trueOrFalse True se ha gia usato una carta false se non ha ancora usato una carta
     */
    public void setUsedCardInThisTurn(boolean trueOrFalse){

        usedCardInThisTurn=trueOrFalse;

    }

    /**
     * @return Vero se il giocatore ha gia usato una carta in questo turno
     */
    public boolean getUseCardInThisTurn(){

        return usedCardInThisTurn;

    }


    /**
     * @return Punti attuali del giocatore
     */
    public int getScore() {

        return score;

    }

    /**
     * @return Window del giocatore
     */
    public Window getWindow() {

        return this.window;

    }

    /**
     * Imposta la Window scelta dal giocatore per la partita attuale
     *
     * @param window Window scelta
     */
    public void setWindow(Window window) {

        this.window = window;
        this.setFavorTokens(window.getFTokens());


    }

    /**
     * Controlla che il giocatore abbia già giocato la scena attuale
     *
     * @param sceneName Scena attuale
     * @return Vero se l'ha gia giocata, falso altrimenti
     */
    public boolean checkLastScene(String sceneName) {
        return this.lastScene.equals(sceneName);
    }

    /**
     * @return Stringa che rappresenta l'ultima scena giocata dal Player
     */
    public String getLastScene() {
        return this.lastScene;
    }

    /**
     * Imposta l'ultima scena giocata dal Player
     *
     * @param sceneName Scena giocata
     */
    public void setLastScene(String sceneName) {
        this.lastScene = sceneName;
    }

    /**
     * @return Vero se il giocatore è online, falso altrimenti
     */
    public boolean getLiveStatus() {
        return this.liveStatus;
    }

    /**
     * Imposta lo stato di connessione del giocatore
     *
     * @param status Vero se Online, falso se OffLine
     */
    public void setLiveStatus(boolean status) {
        this.liveStatus = status;
    }

    /**
     * Imposta il tipo di connessione scelta dal giocatore
     *
     * @param connection Connessione scelta
     */
    public void setConnection(String connection) {
        this.connection = connection;
    }

    /**
     * Imposta il tipo di interfaccia scelta dal giocatore
     *
     * @param UI Interfaccia scelta
     */
    public void setUI(String UI) {
        this.UI = UI;
    }

    /**
     * Imposta l'indirizzo IP del client
     *
     * @param address Indirizzo IP
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Imposta la porta per comunicare col client
     *
     * @param port Porta dle client
     */
    public void setPort(int port) {
        this.port = port;
    }

    /**
     * @return FavorTokens ancora a disposizione del giocatore
     */
    public int getFavorTokens() {

        return favorTokens;

    }

    /**
     * Imposta i FavorTokens che spettano al giocatore relativamente alla Window che ha scelto
     *
     * @param flavorTokens FavorTokens del giocatore
     */
    public void setFavorTokens(int flavorTokens) {

        this.favorTokens = flavorTokens;

    }

    /**
     * @return Connessione del giocatore
     */
    public String getConnection() {
        return connection;
    }

    /**
     * @return User interface del giocatore
     */
    public String getUI(){
            return UI;
    }

    /**
     * @return Indirizzo IP del client
     */
    public String getAddress(){
        return address;
    }

    /**
     * @return Porta del client
     */
    public int getPort(){
        return port;
    }

    /**
     * @return Nome del giocatore
     */
    public String getName() {

        return name;

    }

    /**
     * Indica se il giocatore ha inserito o meno un dado nel turno di gioco attuale
     * @param trueOrFalse Vero se ha inserito un dado, falso altrimenti
     */
    public void setInsertDieInThisTurn(boolean trueOrFalse) {

        insertDieInThisTurn=trueOrFalse;

    }

    /**
     * @return Boolean che indica se il giocatore ha inserito o meno un dado nel turno di gioco attuale
     */
    public boolean getInsertDieInThisTurn(){

        return insertDieInThisTurn;

    }

    /**
     * @return Colore che rappresenta l'obitettivo privato del giocatore
     */
    public Colour getSecretColour() {

        return secretColour;

    }

    /**
     * Imposta l'obiettivo privato del giocatore
     * @param colour Obiettivo privato
     */
    public void setSecretColour(Colour colour){

        this.secretColour=colour;

    }

    /**
     * @return Il turno attuale del giocatore
     */
    public int getTurn() {

        return turn;

    }

    /**
     * Riduce i FavorTokens del giocatore a seguito dell'utilizzo di una ToolCard
     * @param reduction FavorTokens utilizzati
     */
    public void reduceFavorTokens(int reduction) {

        favorTokens -= reduction;

    }

    /**
     * Incrementa il turno del giocatore
     */
    public void incrTurn(){
        this.turn++;
    }

    /**
     * Resetta il turno del giocatore
     */
    //Il primo turno di un giocatore si ha quando turn = 1 . Qui nella reset lo imposto a 0 perchè questo
    //metodo viene chiamato solo dalla Azione BeginRound. Quindi all'inizio del Round tutti i turni vengono
    //impostati a 0 tramite questo metodo e quando sta per iniziare il primo turno vengono tutti incrementati di 1
    //tramite la incrTurn(). Così chi dovra saltare il turno successivo per via di una carta tool basterà incrementare
    //il suo turno di 1 portandolo quindi ad un eventuale turno 3 che non potra' mai giocare quel Round (per via di controlli
    // sui turni che verranno fatti).
    public void resetTurn(){
        this.turn = 0;
    }

    /**
     * @return Copia del giocatore
     */
    public Player copy(){
        return new Player(this);
    }


}
