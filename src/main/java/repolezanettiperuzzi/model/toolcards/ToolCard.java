package repolezanettiperuzzi.model.toolcards;

import repolezanettiperuzzi.model.*;

import java.util.List;

public abstract class ToolCard {

    protected int id;
    protected int resultOfAction;
    private String title;
    private String description;
    private int value;

    public void setDescription(String description){
        this.description = description;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public String getDescription(){
        return this.description;
    }

    public String getTitle(){
        return this.title;
    }

    public int getId() {

        return this.id;

    }

    public abstract int check(GameBoard board, Player player, List<Integer> parameterForCard);

    public int checkMoveOneDie(GameBoard board, Player player, int xStart, int yStart, int xEnd, int yEnd){

        int numProblem;

        if(xStart<0 || xStart>player.getWindow().numRow()-1 || yStart<0 || yStart>player.getWindow().numColumn()-1 || xEnd<0 || xEnd>player.getWindow().numRow()-1 || yEnd<0 || yEnd>player.getWindow().numColumn()-1){

            numProblem=-1;

        } else if (!player.getWindow().thereIsDie(xStart, yStart)) {

            numProblem=-2;

        } else{

            Die dTemp=player.getWindow().removeDie(xStart,yStart);

            if (player.getWindow().thereIsDie(xEnd, yEnd)) {

                numProblem=-3;

            } else if (!player.getWindow().controlAdjacencies(xEnd, yEnd)) {

                numProblem=-4;

            }else{

                numProblem=1;
            }

            player.getWindow().insertDie(dTemp,xStart,yStart,"none");
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

                player.getWindow().insertDie(dTemp,x1Start,y1Start,"none");
                numProblem=-7;

            }else if(player.getWindow().controlAllBoundAdjacencies(dTemp,x1End,y1End)){

                player.getWindow().insertDie(dTemp,x1Start,y1Start,"none");
                numProblem=-25;

            } else {

                player.getWindow().insertDie(dTemp,x1End,y1End,"none");

                if(x2Start<0 || x2Start>player.getWindow().numRow()-1 || y2Start<0 || y2Start>player.getWindow().numColumn()-1 || x2End<0 || x2End>player.getWindow().numRow()-1 || y2End<0 || y2End>player.getWindow().numColumn()-1){

                    player.getWindow().insertDie(dTemp,x1Start,y1Start,"none");
                    player.getWindow().removeDie(x1End,y1End);
                    numProblem=-1;

                } else if (!player.getWindow().thereIsDie(x2Start, y2Start)) {

                    player.getWindow().insertDie(dTemp,x1Start,y1Start,"none");
                    player.getWindow().removeDie(x1End,y1End);
                    numProblem=-17;

                } else{

                    Die d2Temp=player.getWindow().removeDie(x2Start,y2Start);

                    if (player.getWindow().thereIsDie(x2End, y2End)) {

                        numProblem=-18;

                    } else if (!player.getWindow().controlAdjacencies(x2End, y2End)) {

                        numProblem=-19;

                    } else if (!player.getWindow().controlAllBoundBox(x2End, y2End, d2Temp)) {

                        numProblem=-20;

                    }else if(player.getWindow().controlAllBoundAdjacencies(d2Temp,x2End,y2End)){

                        numProblem=-26;

                    } else {

                        numProblem=1;

                    }

                    player.getWindow().insertDie(dTemp,x1Start,y1Start,"none");
                    player.getWindow().removeDie(x1End,y1End);
                    player.getWindow().insertDie(d2Temp,x2Start,y2Start,"none");
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

    public abstract void effect(GameBoard board, Player player, List<Integer> parameterForCard);


}
