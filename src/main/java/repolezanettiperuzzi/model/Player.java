package repolezanettiperuzzi.model;

public class  Player {

    private String name;
    private Colour secretColour;
    private boolean insertDieInThisTurn;
    private int flavorTokens;
    private Window window;
    private int turn;
    private int score=0;
    private String connection; //RMI o Socket
    private String UI; //CLI o GUI

    public Player(String name){
        this.name = name;
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

    public int getFlavorTokens() {

        return flavorTokens;

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
}
