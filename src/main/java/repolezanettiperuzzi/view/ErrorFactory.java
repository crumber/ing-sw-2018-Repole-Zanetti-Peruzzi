package repolezanettiperuzzi.view;

public class ErrorFactory {
    //ritorna messaggio di errore prendendo il codice errore
    public static String getErrorMessage(String error) {

        switch (error) {

            case "startingFinalPositionNotExist": {

                return "You can't move this die because your starting/final position not exist!!!!";
            }
            case "notMoveChosenEmptyBox": {

                return "You can't move this die because you have chosen an empty box!!!";

            }
            case "notMoveThereIsDieInside": {

                return "You can't move this die into this position because there's a die inside!!";
            }
            case "notMoveNotDiceNextToIt": {

                return "You can't move this die in this position because there isn't at least one dice next to it!!";

            }
            case "notMoveNotRespectColourRestriction": {

                return "You can't move this die in this position because it doesn't respect the box's colour restriction!!";

            }
            case "notMoveNotRespectValueRestriction": {

                return "You can't move this die in this position because it doesn't respect the box's value restriction!!";

            }
            case "notMoveRespectRestriction": {

                return "You can't move this die in this position box because it doesn't respect box's restriction!!";

            }
            case "notNaveFavorTokens": {

                return "You don't have enough favor tokens!";

            }
            case "emptyPositionDraft": {

                return "You have chosen an empty position on the draft!";

            }
            case "notMoveThereisDieNextToIt": {

                return "You can't move this die in this position because there is at least one die next to it!";

            }
            case "wrongNumber": {

                return "Wrong number! You have to choose a value between 1 and 6!";

            }
            case "notUseCardIsNotSecondTurn": {

                return "You can't use this card because this is not the second turn of this round!";

            }
            case "notUseCardHaveAlreadyInsertedDie": {

                return "You can't use this card because you have already inserted a die!";

            }
            case "choiceNotExist": {

                return "Your choice does not exist, 1 for increase 0 for decrease!!!";

            }
            case "notDecreaseIsMinimum": {

                return "You can't decrease this value because it has already the minimum one!!";

            }
            case "notIncreaseIsMaximum": {

                return "You can't increase value because it has already the maximum one!!";

            }
            case "notMoveDie2BoxEmpty": {

                return "You can't move die 2 because you have chosen an empty box!";

            }
            case "notMoveDie2NotEmptyBox": {

                return "You can't move die 2 in this position because there's a die inside!";

            }
            case "notMoveDie2NotDiceNextToIt": {

                return "You can't move die 2 in this position because there isn't at least one dice next to it";

            }
            case "notMoveDie2NotRespectRestriction": {

                return "You can't move die 2 in this position because it doesn't respect box's restriction!";

            }
            case "notDieOnRoundTrack": {

                return "There isn't die on round track in the position you have chosen!";

            }
            case "notMoveDiceNotSameColourRoundTrack": {

                return "You don't move dice because they don't have the same colour of die on round track!";

            }
            case "notPutThereIsDieSameColorNear": {

                return "You don't put this die in a final position because there is another one with the same color near this position";

            }
            case "notPutThereIsDieSameValueNear": {

                return "You don't put this die in a final position because there is another one with the same value next to this position";

            }
            case "notPutDieThereIsDieSameColorValue": {

                return "You don't put this die in a final position because there is another one with the same color or value next to this position!";

            }
            case "notPutDie2ThereIsSameColorValue": {

                return "You don't put die 2 in a final position because there is another one with the same color or value next to this position!";

            }
            case "notPutDieHereNotBoundaryPosition": {

                return "You can't put this die here, you need to select a boundary position!";

            }
            case "alreadyInsertedDie": {

                return "You already inserted a die in this turn!";

            }
            case "alreadyUsedToolCard": {

                return "You already used a tool card in this turn!!";

            }
            case "notFirstTurn": {

                return "Not is your first turn!";

            }
            default: {

                return "You don't insert die in this turn!!";

            }
        }
    }
}
