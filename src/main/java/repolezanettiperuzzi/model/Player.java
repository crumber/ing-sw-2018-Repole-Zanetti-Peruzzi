package repolezanettiperuzzi.model;

public class Player{

    private String name;
    private Colour secretColour;
    private boolean insertDieInThisTurn;
    private int flavorTokens;
    private Window window;
    private int turn;
    private int score;
    private String connection; //RMI o Socket
    private String UI; //CLI o GUI
    private String address;
    private int port;

    public Player(String name, String connection, String UI, String address, int port){
        this.name = name;
        this.score = 0;
        this.connection = connection;
        this.UI = UI;
        this.turn = 0;
        this.address = address;
        this.port = port;
    }

    //copy constructor
    public Player(Player p){
        this.name = p.name;
        this.secretColour = p.secretColour;
        this.insertDieInThisTurn = p.insertDieInThisTurn;
        this.flavorTokens = p.flavorTokens;
        this.window = p.window.copy();
        this.turn = p.turn;
        this.score = p.score;
        this.connection = p.connection;
        this.UI = p.UI;
        this.address = p.address;
        this.port = p.port;
    }

    public void useToolCard(){
        //chiama una Action useToolCard
    }

    public void takeDieFromDraft(){
        //prende il dado attraverso una Action
    }

    public void takeDiceFromBag(){
        //prende il dado dal sacchetto attraverso una Action
    }

    public void rollDice(){
        //lancia i dadi presi dal sacchetto
    }

    public void passTurn(){
        //chiamo una Action PassTurn
    }

    public void updateScore(int points){

        score+=points;

    }

    public Window getWindow(){

        return this.window;

    }

    public void setWindow(Window window){

        this.window = window;

    }

    public int getFlavorTokens() {

        return flavorTokens;

    }

    public String getConnection(){
        return connection;
    }

    public String getAddress(){
        return address;
    }

    public int getPort(){
        return port;
    }

    public void setFlavorTokens(int flavorTokens){

        this.flavorTokens=flavorTokens;

    }

    public String getName() {

        return name;

    }

    public boolean getInserDieInThisTurn(){

        return insertDieInThisTurn;
    }

    public Colour getSecretColour() {

        return secretColour;

    }

    public void setSecretColour(Colour colour){

        this.secretColour=colour;

    }

    public int getTurn() {

        return turn;

    }

    public void reduceFlavorTokens(int reduction) {

        flavorTokens -= reduction;

    }

    //vedi incrRound dentro GameBoard per il metovo per cui non ho creato una setTurn
    public void incrTurn(){
        this.turn++;
    }

    //Il primo turno di un giocatore si ha quando turn = 1 . Qui nella reset lo imposto a 0 perchè questo
    //metodo viene chiamato solo dalla Azione BeginRound. Quindi all'inizio del Round tutti i turni vengono
    //impostati a 0 tramite questo metodo e quando sta per iniziare il primo turno vengono tutti incrementati di 1
    //tramite la incrTurn(). Così chi dovra saltare il turno successivo per via di una carta tool basterà incrementare
    //il suo turno di 1 portandolo quindi ad un eventuale turno 3 che non potra' mai giocare quel Round (per via di controlli
    // sui turni che verranno fatti).
    public void resetTurn(){
        this.turn = 0;
    }

    //deep clone method
    public Player copy(){
        return new Player(this);
    }


}
