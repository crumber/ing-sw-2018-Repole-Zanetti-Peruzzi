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
}
