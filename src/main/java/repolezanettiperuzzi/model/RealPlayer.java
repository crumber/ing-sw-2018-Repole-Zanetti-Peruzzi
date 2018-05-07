package repolezanettiperuzzi.model;

public class RealPlayer implements Player {

    private String name;
    private Colour secretColour;
    private boolean insertDieInThisTurn;
    private int flavorTokens;
    private Window window;
    private int turn;

    public RealPlayer(String name){
        this.name = name;
    }

    @Override
    public void useToolCard(){
        //chiama una Action useToolCard
    }

    @Override
    public void takeDieFromDraft(){
        //prende il dado attraverso una Action
    }

    @Override
    public void takeDiceFromBag(){
        //prende il dado dal sacchetto attraverso una Action
    }

    @Override
    public void rollDice(){
        //lancia i dadi presi dal sacchetto
    }

    @Override
    public void passTurn(){
        //chiamo una Action PassTurn
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
