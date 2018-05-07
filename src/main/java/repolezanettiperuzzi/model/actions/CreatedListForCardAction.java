package repolezanettiperuzzi.model.actions;

import repolezanettiperuzzi.model.GameBoard;
import repolezanettiperuzzi.model.RealPlayer;

import java.util.ArrayList;
import java.util.List;

public class CreatedListForCardAction {

    public List<Object> doAction (String clientAnswers, GameBoard board, RealPlayer player, int whichToolCard){

        List<Object> parameterForCard=new ArrayList<>();
        String[] clientAnswersArray= clientAnswers.split(" ");
        int idToolCard= board.getToolCards(whichToolCard).getId();

        parameterForCard.add(board);
        parameterForCard.add(player);

        if(idToolCard==1){

            for(int i=0; i<2;i++){

                parameterForCard.add(Integer.parseInt(clientAnswersArray[i]));

            }
        }

        if(idToolCard==2 || idToolCard==3){

            for(int i=0; i<4;i++){

                parameterForCard.add(Integer.parseInt(clientAnswersArray[i]));

            }
        }

        if(idToolCard==4){

            for(int i=0; i<8;i++){

                parameterForCard.add(Integer.parseInt(clientAnswersArray[i]));

            }
        }

        if(idToolCard==5 || idToolCard==9){

            for(int i=0; i<3;i++){

                parameterForCard.add(Integer.parseInt(clientAnswersArray[i]));

            }
        }

        if(idToolCard==6 || idToolCard==10){

            for(int i=0; i<1;i++){

                parameterForCard.add(Integer.parseInt(clientAnswersArray[i]));

            }
        }

        if(idToolCard==12){

            for(int i=0; i<10;i++){

                parameterForCard.add(Integer.parseInt(clientAnswersArray[i]));

            }
        }

        //carta numero 7 non ha bisogno altro oltre board e player mentre carta numero 11 e 8 da fare

        return parameterForCard;
    }
}
