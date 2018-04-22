package RepoleZanettiPeruzzi.Model;

public class ProxyPlayer implements Player {
    private RealPlayer realPlayer;

    public ProxyPlayer(String name){
        //ottieni l'istanza del RealPlayer con il nome che ci interessa dal controllore
    }

    @Override
    public void useToolCard(){
        realPlayer.useToolCard();
    }

    @Override
    public void takeDieFromDraft(){
        realPlayer.takeDieFromDraft();
    }

    @Override
    public void takeDiceFromBag(){
        realPlayer.takeDiceFromBag();
    }

    @Override
    public void rollDice(){
        realPlayer.rollDice();
    }

    @Override
    public void passTurn(){
        realPlayer.passTurn();
    }
}
