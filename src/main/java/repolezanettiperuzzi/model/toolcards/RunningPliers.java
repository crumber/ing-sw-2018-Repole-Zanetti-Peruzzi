package repolezanettiperuzzi.model.toolcards;

import repolezanettiperuzzi.model.GameBoard;
import repolezanettiperuzzi.model.Player;

import java.util.ArrayList;
import java.util.List;

public class RunningPliers extends ToolCard {

    public RunningPliers(){

        id=8;

    }

    @Override
    public void effect(GameBoard board, Player player, List<Integer> parameterForCard){

        player.setInsertDieInThisTurn(false);
        player.incrTurn();

    }

    public int check(GameBoard board, Player player, List<Integer> parameterForCard){

        if(player.getTurn()!=0){

            resultOfAction=-30;

        }else if(!player.getInsertDieInThisTurn()){

            resultOfAction=-31;

        }else{

            resultOfAction=1;
        }

      return resultOfAction;
    }

}
