package repolezanettiperuzzi.model.actions;

import com.sun.org.apache.xpath.internal.operations.String;
import repolezanettiperuzzi.model.GameBoard;
import repolezanettiperuzzi.model.RealPlayer;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UseCardAction {

    public List<Object> doAction(RealPlayer gamer, GameBoard board, int whichToolCard, List<Object> parameterForCard){

        List<Object> resultOfAction = new ArrayList<>();

        if(gamer.getFlavorTokens()>=board.getCostToolCard(whichToolCard)){

            switch (board.getId(whichToolCard)){

                case 1: {

                   // fare check se va a buon fine faccio attiva

                    break;
                }

                case 2: {

                    break;
                }

                case 3: {

                    break;
                }

                case 4: {

                    break;
                }

                case 5: {

                    break;
                }

                case 6: {

                    break;
                }

                case 7: {

                    break;
                }

                case 8: {

                    break;
                }

                case 9: {

                    break;
                }

                case 10: {

                    break;
                }

                case 11: {

                    break;
                }
                default: {

                    break;
                }
            }


        }
        else{

            resultOfAction.add(-1);
            resultOfAction.add("You don't have enough flavor tokens!");

        }

        return resultOfAction;
    }
}
