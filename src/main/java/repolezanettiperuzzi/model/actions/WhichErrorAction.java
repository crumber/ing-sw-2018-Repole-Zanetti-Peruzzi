package repolezanettiperuzzi.model.actions;

public class WhichErrorAction {

    public String doAction(int numError){

        String error;
        String typeMess="error ";

        switch (numError){

            //ERROR LIST

            case -1:{

                error="startingFinalPositionNotExist"; //You can't move this die because your starting/final position not exist!!!!

                break;
            }

            case -2:{

                error="notMoveChosenEmptyBox"; //You can't move this die because you have chosen an empty box

                break;
            }

            case -3:{

                error="notMoveThereIsDieInside"; //You can't move this die into this position because there's a die inside

                break;
            }

            case -4:{

                error="notMoveNotDiceNextToIt"; //You can't move this die in this position because there isn't at least one dice next to it

                break;
            }

            case -5:{

                error="notMoveNotRespectColourRestriction"; //You can't move this die in this position because it doesn't respect the box's colour restriction

                break;
            }

            case -6:{

                error="notMoveNotRespectValueRestriction"; //You can't move this die in this position because it doesn't respect the box's value restriction

                break;
            }

            case -7:{

                error="notMoveRespectRestriction"; //You can't move this die in this position box because it doesn't respect box's restriction

                break;
            }

            case -8:{

                error="notNaveFavorTokens"; //You don't have enough favor tokens!

                break;
            }

            case -9:{

                error="emptyPositionDraft"; //You have chosen an empty position on the draft!

                break;
            }

            case -10:{

                error="notMoveThereisDieNextToIt"; //You can't move this die in this position because there is at least one die next to it!

                break;
            }

            case -11:{

                error="wrongNumber"; //Wrong number! You have to choose a value between 1 and 6!

                break;
            }

            case -12:{

                error="notUseCardIsNotSecondTurn"; //You can't use this card because this is not the second turn of this round!

                break;
            }

            case -13:{

                error="notUseCardHaveAlreadyInsertedDie"; //You can't use this card because you have already inserted a die!

                break;
            }

            case -14:{

                error="choiceNotExist"; //Your choice does not exist, 1 for increase 0 for decrease!!!

                break;
            }

            case -15:{

                error="notDecreaseIsMinimum";  //You can't decrease this value because it has already the minimum one

                break;
            }

            case -16:{

                error="notIncreaseIsMaximum";  //You can't increase value because it has already the maximum one

                break;
            }

            case -17:{

                error="notMoveDie2BoxEmpty";  //You can't move die 2 because you have chosen an empty box!

                break;
            }

            case -18:{

                error="notMoveDie2NotEmptyBox"; //You can't move die 2 in this position because there's a die inside!

                break;
            }

            case -19:{

                error="notMoveDie2NotDiceNextToIt"; //You can't move die 2 in this position because there isn't at least one dice next to it

                break;
            }

            case -20:{

                error="notMoveDie2NotRespectRestriction"; //You can't move die 2 in this position because it doesn't respect box's restriction!

                break;
            }

            case -21:{

                error="notDieOnRoundTrack";  //There isn't die on round track in the position you have chosen!

                break;
            }

            case -22:{

                error="notMoveDiceNotSameColourRoundTrack"; //You don't move dice because they don't have the same colour of die on round track!

                break;
            }

            case -23:{

                error="notPutThereIsDieSameColorNear"; //You don't put this die in a final position because there is another one with the same color near this position

                break;
            }

            case -24:{

                error="notPutThereIsDieSameValueNear"; //You don't put this die in a final position because there is another one with the same value next to this position

                break;
            }

            case -25:{

                error="notPutDieThereIsDieSameColorValue"; //You don't put this die in a final position because there is another one with the same color or value next to this position!

                break;
            }

            case -26:{

                error="notPutDie2ThereIsSameColorValue"; //You don't put die 2 in a final position because there is another one with the same color or value next to this position!

                break;
            }

            case -27:{

                error="notPutDieHereNotBoundaryPosition"; //You can't put this die here, you need to select a boundary position!

                break;
            }

            case -28:{

                error="alreadyInsertedDie"; //You already inserted a die in this turn!

                break;
            }

            case -29:{

                error="alreadyUsedToolCard"; //You already used a tool card in this turn!!
                break;
            }

            case -30:{

                error="notFirstTurn"; //Not is your first turn!
                break;
            }

            default:{

                error="notInsertDieInTurn"; //You don't insert die in this turn!!
                break;
            }
        }

        return typeMess+error;
    }
}
