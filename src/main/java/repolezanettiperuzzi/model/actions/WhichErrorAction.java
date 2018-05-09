package repolezanettiperuzzi.model.actions;

import repolezanettiperuzzi.model.GameBoard;

public class WhichErrorAction {

    public String doAction(GameBoard board, int numError){

        String error;

        switch (numError){

            //TRUE AND SPECIAL STRING FOR TOOL CARD 11

            case 1:{

                error="not error";

                break;

            }

            case 11:{

                int posNewDie = board.getSizeDraft() - 1 ;
                error=("Choose value of die in position "+posNewDie +" ( the value have to be between 1 and 6 )\n");

                break;
            }

            //ERROR LIST

            case -1:{

                error="You can't move this die because your starting/final position doesn't exist!!!!";

                break;
            }

            case -2:{

                error="You can't move this die because you have chosen an empty box";

                break;
            }

            case -3:{

                error="You can't move this die into this position because there's a die inside";

                break;
            }

            case -4:{

                error="You can't move this die in this position because there isn't at least one dice next to it";

                break;
            }

            case -5:{

                error="You can't move this die in this position because it doesn't respect the box's colour restriction";

                break;
            }

            case -6:{

                error="You can't move this die in this position because it doesn't respect box's value restriction";

                break;
            }

            case -7:{

                error="You can't move this die in this position box because it doesn't respect box's restriction";

                break;
            }

            case -8:{

                error="You don't have enough favor tokens!";

                break;
            }

            case -9:{

                error="You have chosen an empty position on the draft!";

                break;
            }

            case -10:{

                error="You can't move this die in this position because there is at least one die next to it!";

                break;
            }

            case -11:{

                error="Wrong number! You have to choose a value between 1 and 6!";

                break;
            }

            case -12:{

                error="You can't use this card because this is not the second turn of this round!";

                break;
            }

            case -13:{

                error="You can't use this card because you have already inserted a die!";

                break;
            }

            case -14:{

                error="Your choice does not exist, 1 for increase 0 for decrease!!!";

                break;
            }

            case -15:{

                error="You can't decrease this value because it has already the minimum one";

                break;
            }

            case -16:{

                error="You can't increase value because it has already the maximum one";

                break;
            }

            case -17:{

                error="You can't move die 2 because you have chosen an empty box!";

                break;
            }

            case -18:{

                error="You can't move die 2 in this position because there's a die inside!";

                break;
            }

            case -19:{

                error="You can't move die 2 in this position because there isn't at least one dice next to it";

                break;
            }

            case -20:{

                error="You can't move die 2 in this position because it doesn't respect box's restriction!";

                break;
            }

            case -21:{

                error="There isn't die on round track in the position you have chosen!";

                break;
            }

            case -22:{

                error="You don't move dice because they don't have the same colour of die on round track!";

                break;
            }

            case -23:{

                error="You don't put this die in a final position because there is another one with the same color near this position";

                break;
            }

            case -24:{

                error="You don't put this die in a final position because there is another one with the same value next to this position";

                break;
            }

            case -25:{

                error="You don't put this die in a final position because there is another one with the same color or value next to this position!";

                break;
            }

            case -26:{

                error="You don't put die 2 in a final position because there is another one with the same color or value next to this position!";

                break;
            }

            case -27:{

                error="You can't put this die here, you need to select a boundary position!";

                break;
            }

            default:{

                error=" MANCA LA NUMERO 8 DI CARTA FORSE HA ALTRI ERRORI..";
                break;
            }
        }

        return error;
    }
}
