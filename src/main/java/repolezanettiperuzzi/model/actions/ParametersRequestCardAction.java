package repolezanettiperuzzi.model.actions;

import repolezanettiperuzzi.model.GameBoard;

public class ParametersRequestCardAction extends Action{

    //manca la 8 la 7 va in nothing perche non ha bisogno di niente

    public String doAction(GameBoard board, int whichToolCard){

        String requestForToolCard;
        int id=board.getToolCards(whichToolCard).getId();

        if(id==2 || id==3){

            requestForToolCard="From which box do you want to move the die (insert number of row and number of column, like this: 1 3) ?\n"
                    + "In which box do you want to move the die (insert number of row and number of column, like this: 1 3) ?\n";

        }else if(id==9){

            int maxChooseDraftDie = board.getSizeDraft()-1;
            requestForToolCard="Which die on draft (from 0 to " +maxChooseDraftDie +") ?\n"
                    + "In which box do you want to move the die (insert number of row and number of column, like this: 1 3) ?\n";

        }else if(id==6 || id==11 || id==10){

            int maxChooseDraftDie = board.getSizeDraft()-1;
            requestForToolCard="Which die on draft (from 0 to " + maxChooseDraftDie + ") ?\n";

        }else if(id==1){

            int maxChooseDraftDie = board.getSizeDraft()-1;
            requestForToolCard="Which die on draft (from 0 to " + maxChooseDraftDie + ") ?\n"
                    + "Do you want to increase (insert: 1) or reduce (insert: 0) ?\n";

        }else if(id==4) {

            requestForToolCard="From which box do you want to move the die 1 (insert number of row and number of column, like this: 1 3) ?\n" +
                    "In which box do you want to move the die 1 (insert number of row and number of column, like this: 1 3) ?\n" +
                    "From which box do you want to move the die 2 (insert number of row and number of column, like this: 1 3) ?\n" +
                    "In which box do you want to move the die 2 (insert number of row and number of column, like this: 1 3) ?\n";

        }else if(id==5) {

            int maxChooseDraftDie = board.getSizeDraft()-1;
            requestForToolCard="Which die on draft (from 0 to " + maxChooseDraftDie+ ") ?\n" +
                    "Which die on round track (insert number of round and number of die position on round, like this: 3 2 -> round 3 die 2) ?\n";

        }else if(id==12) {

            requestForToolCard="From which box do you want to move the die 1 (insert number of row and number of column, like this: 1 3) ?\n" +
                    "In which box do you want to move the die 1 (insert number of row and number of column, like this: 1 3) ?\n" +
                    "From which box do you want to move the die 2 (insert number of row and number of column, like this: 1 3) ?\n" +
                    "In which box do you want to move the die 2 (insert number of row and number of column, like this: 1 3) ?\n" +
                    "Which die on round track (insert number of round and number of die position on round, like this: 3 2 -> round 3 die 2) ?\n";

        }else{

            requestForToolCard="NOTHING";

        }

        return requestForToolCard;
    }
}
