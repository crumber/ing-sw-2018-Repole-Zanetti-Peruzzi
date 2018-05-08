package repolezanettiperuzzi.model.actions;

import repolezanettiperuzzi.model.GameBoard;
import repolezanettiperuzzi.model.RealPlayer;

import java.util.ArrayList;
import java.util.List;

public class CreatedListForCardAction extends Action{

    public List<Object> doAction (String clientAnswers, GameBoard board, RealPlayer player, int whichToolCard){

        List<Object> parameterForCard=new ArrayList<>();
        String[] clientAnswersArray= clientAnswers.split(" ");
        int idToolCard= board.getToolCards(whichToolCard).getId();

        // for all card
        parameterForCard.add(board);
        parameterForCard.add(player);

        //which die on draft and increment or decrement (1 or 0)
        if(idToolCard==1){

            for(int i=0; i<2;i++){

                parameterForCard.add(Integer.parseInt(clientAnswersArray[i]));

            }
        }

        //add initial position (x,y) and final position (x2, y2)
        if(idToolCard==2 || idToolCard==3){

            for(int i=0; i<4;i++){

                parameterForCard.add(Integer.parseInt(clientAnswersArray[i]));

            }
        }

        //add initial position (x1,y1) die 1, final position (x2, y2) die 1, add initial position (x3,y3) die 2 and final position (x4, y4) die 2
        if(idToolCard==4){

            for(int i=0; i<8;i++){

                parameterForCard.add(Integer.parseInt(clientAnswersArray[i]));

            }
        }

        //add which die on draft and which die on track round (which turn and which turn's die)
        if(idToolCard==5 || idToolCard==9){

            for(int i=0; i<3;i++){

                parameterForCard.add(Integer.parseInt(clientAnswersArray[i]));

            }
        }

        //add which die on draft
        if(idToolCard==6 || idToolCard==10){

            for(int i=0; i<1;i++){

                parameterForCard.add(Integer.parseInt(clientAnswersArray[i]));

            }
        }

        //add initial position (x1,y1) die 1, final position (x2, y2) die 1, add initial position (x3,y3) die 2 , final position (x4, y4) die 2 and which die on track round (which turn and which turn's die)
        if(idToolCard==12){

            for(int i=0; i<10;i++){

                parameterForCard.add(Integer.parseInt(clientAnswersArray[i]));

            }
        }

        //carta numero 7 non ha bisogno altro oltre board e player mentre carta numero 11 e 8 da fare

        return parameterForCard;
    }
}
