package repolezanettiperuzzi.model.actions;


import repolezanettiperuzzi.model.Player;


public class BeginTurn extends Action{


    public void doAction(Player player){

        player.incrTurn();

    }

//inserire due metodi che richiamano rispettivamente le azioni UseCard e InsertDie in modo da poterle attivare soltanto quando è il turno del giocatore passato come parametro




}
