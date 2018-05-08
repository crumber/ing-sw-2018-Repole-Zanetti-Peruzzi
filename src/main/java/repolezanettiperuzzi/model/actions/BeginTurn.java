package repolezanettiperuzzi.model.actions;

import repolezanettiperuzzi.model.RealPlayer;

public class BeginTurn extends Action{

    public void doAction(RealPlayer player){

        //increment player's turn

        player.incrTurn();

        //inserire timer

    }

//inserire due metodi che richiamano rispettivamente le azioni UseCard e InsertDie in modo da poterle attivare soltanto quando è il turno del giocatore passato come parametro




}
