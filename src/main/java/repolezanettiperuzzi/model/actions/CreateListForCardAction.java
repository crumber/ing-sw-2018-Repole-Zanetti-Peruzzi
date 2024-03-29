package repolezanettiperuzzi.model.actions;

import repolezanettiperuzzi.model.GameBoard;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe che rappresenta la creazione dei parametri per l'utilizzo di una tool card
 * @author Alessandro Peruzzi
 */
public class CreateListForCardAction {

    /**
     * converte la stringa in una lista di interi
     * @param clientAnswers stringa di risposta del client
     * @param board game board
     * @param whichToolCard intero che indica quale tool card presente nella game board
     * @return ritorna una lista di interi che rappresentano i vari parametri per la carta
     */
    public List<Integer> doAction (String clientAnswers, GameBoard board, int whichToolCard){

        ArrayList<Integer> parameterForCard=new ArrayList<>();
        String[] clientAnswersArray= clientAnswers.split(" ");
        int idToolCard= board.getToolCard(whichToolCard).getId();

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


        //for card 9 and 8: pos die on draft and final position (x,y),  card 5: pos die on draft and pos die on round track (which round and which die on round)
        if(idToolCard==5 || idToolCard==9 || idToolCard==8){

            for(int i=0; i<3;i++){

                parameterForCard.add(Integer.parseInt(clientAnswersArray[i]));

            }
        }

        //add which die on draft (second call 11 tool card add value of die)
        if(idToolCard==6 || idToolCard==10 || idToolCard==11 ){

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

        //carta numero 7 non ha bisogno di qualcosa oltre board e player

        return parameterForCard;
    }
}
