package repolezanettiperuzzi.model.toolcards;

import repolezanettiperuzzi.model.*;

import java.util.List;

public abstract class ToolCard {

    int id;
    int resultOfAction;

    public int getId() {

        return id;

    }

    public int check(GameBoard board, Player player, List<Integer> parameterForCard) {

        return  resultOfAction;

    }

    public int checkMoveOneDie(GameBoard board, Player player, int xStart, int yStart, int xEnd, int yEnd){

        int numProblem;

        if(xStart<0 || xStart>3 || yStart<0 || yStart>4 || xEnd<0 || xEnd>3 || yEnd<0 || yEnd>4){

            numProblem=-1;

        } else if (!player.getWindow().thereIsDie(xStart, yStart)) {

            numProblem=-2;

        } else{

            Die dTemp=player.getWindow().removeDie(xStart,yStart);

            if (player.getWindow().thereIsDie(xEnd, yEnd)) {

                numProblem=-3;

            } else if (!player.getWindow().controlAdjacences(xEnd, yEnd)) {

                numProblem=-4;

            }else{

                numProblem=1;
            }

            player.getWindow().insertDie(dTemp,xStart,yStart,"both");
        }

        return numProblem;
    }

    public int checkMoveTwoDice(GameBoard board, Player player, int x1Start, int y1Start, int x1End, int y1End, int x2Start, int y2Start, int x2End, int y2End){

        int numProblem;

        if(checkMoveOneDie(board,player,x1Start,y1Start,x1End,y1End)!=1){

            numProblem=checkMoveOneDie(board,player,x1Start,y1Start,x1End,y1End);

        } else{

            Die dTemp=player.getWindow().removeDie(x1Start,y1Start);

            if (!player.getWindow().controlAllBoundBox(x1End, y1End, dTemp)) {

                player.getWindow().insertDie(dTemp,x1Start,y1Start,"both");
                numProblem=-7;

            }else if(player.getWindow().controlAllBoundAdjacences(dTemp,x1End,y1End)){

                player.getWindow().insertDie(dTemp,x1Start,y1Start,"both");
                numProblem=-25;

            } else {

                player.getWindow().insertDie(dTemp,x1End,y1End,"both");

                if(x2Start<0 || x2Start>3 || y2Start<0 || y2Start>4 || x2End<0 || x2End>3 || y2End<0 || y2End>4){

                    player.getWindow().insertDie(dTemp,x1Start,y1Start,"both");
                    player.getWindow().removeDie(x1End,y1End);
                    numProblem=-1;

                } else if (!player.getWindow().thereIsDie(x2Start, y2Start)) {

                    player.getWindow().insertDie(dTemp,x1Start,y1Start,"both");
                    player.getWindow().removeDie(x1End,y1End);
                    numProblem=-17;

                } else{

                    Die d2Temp=player.getWindow().removeDie(x2Start,y2Start);

                    if (player.getWindow().thereIsDie(x2End, y2End)) {

                        numProblem=-18;

                    } else if (!player.getWindow().controlAdjacences(x2End, y2End)) {

                        numProblem=-19;

                    } else if (!player.getWindow().controlAllBoundBox(x2End, y2End, d2Temp)) {

                        numProblem=-20;

                    }else if(player.getWindow().controlAllBoundAdjacences(d2Temp,x2End,y2End)){

                        numProblem=-26;

                    } else {

                        numProblem=1;

                    }

                    player.getWindow().insertDie(dTemp,x1Start,y1Start,"both");
                    player.getWindow().removeDie(x1End,y1End);
                    player.getWindow().insertDie(d2Temp,x2Start,y2Start,"both");
                }
            }
        }

        return numProblem;
    }

    public int checkDieOnDraft(GameBoard board, Player player, int posDieOnDraft){

        int numProblem;

        if (board.getDieDraft(posDieOnDraft) == null) {

            numProblem=-9;

        } else {

            numProblem=1;

        }

        return numProblem;
    }

    public int checkDieOnRoundTrack(GameBoard board, Player player, int whichRound, int whichDieRound){

        int numProblem;

        if(board.getDieFromRoundTrack(whichRound,whichDieRound)==null){

            numProblem=-21;

        }else {

            numProblem=1;

        }

        return numProblem;
    }

    public void effect(GameBoard board, Player player, List<Integer> parameterForCard){

    }

    //only for card 11
    public int preEffect(GameBoard board, Player player, List<Integer> parameterForCard){

        return resultOfAction;

    }

    //only for card 11
    public int checkPreEffect(GameBoard board, Player player, List<Integer> parameterForCard){

        return resultOfAction;

    }

}
