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


    }

    public int check(GameBoard board, Player player, List<Integer> parameterForCard){

      return 0;

    }

}
