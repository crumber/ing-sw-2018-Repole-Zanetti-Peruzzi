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

    /**
     * Costruttore della classe
     *
     * @param name       nome del giocatore
     * @param connection connessione del giocatore (socket o RMI)
     * @param UI         interfaccia
     * @param address    indirizzo ip del client
     * @param port       porta del client
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
     *
     * @param p giocatore da copiare
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
     * @param points punti del giocatore
     */
    public void updateScore(int points) {

        score += points;

    }

    /**
     * @return punti attuali del giocatore
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
     * Setta la Window scelta dal giocatore per la partita attuale
     *
     * @param window Window scelta
     */
    public void setWindow(Window window) {

        this.window = window;

    }

    /**
     * Controlla che il giocatore abbia già giocato la scena attuale
     *
     * @param sceneName scena attuale
     * @return vero se l'ha gia giocata, falso altrimenti
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
     * Setta l'ultima scena giocata dal Player
     *
     * @param sceneName scena giocata
     */
    public void setLastScene(String sceneName) {
        this.lastScene = sceneName;
    }

    /**
     * @return vero se il giocatore è online, falso altrimenti
     */
    public boolean getLiveStatus() {
        return this.liveStatus;
    }

    /**
     * Setta lo stato di connessione del giocatore
     *
     * @param status vero se Online, falso se OffLine
     */
    public void setLiveStatus(boolean status) {
        this.liveStatus = status;
    }

    /**
     * Imposta il tipo di connessione scelta dal giocatore
     *
     * @param connection connessione scelta
     */
    public void setConnection(String connection) {
        this.connection = connection;
    }

    /**
     * Imposta il tipo di interfaccia scelta dal giocatore
     *
     * @param UI interfaccia scelta
     */
    public void setUI(String UI) {
        this.UI = UI;
    }

    /**
     * Imposta l'indirizzo IP del client
     *
     * @param address indirizzo IP
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Imposta la porta per comunicare col client
     *
     * @param port porta dle client
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
     * @return connessione del giocatore
     */
    public String getConnection() {
        return connection;
    }

    /**
     * @return user interface del giocatore
     */
    public String getUI(){
            return UI;
    }

    /**
     * @return indirizzo IP del client
     */
    public String getAddress(){
        return address;
    }

    /**
     * @return porta del client
     */
    public int getPort(){
        return port;
    }

    /**
     * @return nome del giocatore
     */
    public String getName() {

        return name;

    }

    /**
     * Indica se il giocatore ha inserito o meno un dado nel turno di gioco attuale
     * @param trueOrFalse vero se ha inserito un dado, falso altrimenti
     */
    public void setInsertDieInThisTurn(boolean trueOrFalse) {

         insertDieInThisTurn=trueOrFalse;

    }

    /**
     * @return boolean che indica se il giocatore ha inserito o meno un dado nel turno di gioco attuale
     */
    public boolean getInsertDieInThisTurn(){

        return insertDieInThisTurn;

    }

    /**
     * @return colore che rappresenta l'obitettivo privato del giocatore
     */
    public Colour getSecretColour() {

        return secretColour;

    }

    /**
     * Imposta l'obiettivo privato del giocatore
     * @param colour obiettivo privato
     */
    public void setSecretColour(Colour colour){

        this.secretColour=colour;

    }

    /**
     * @return il turno attuale del giocatore
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
     * incrementa il turno del giocatore
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
     * @return copia del giocatore
     */
    public Player copy(){
        return new Player(this);
    }


}
