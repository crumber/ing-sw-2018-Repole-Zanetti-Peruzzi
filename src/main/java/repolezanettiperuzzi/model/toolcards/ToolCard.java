package repolezanettiperuzzi.model.toolcards;

import repolezanettiperuzzi.model.*;

import java.util.List;

/**
 * Classe astratta che rappresenta la generica tool card
 * @author Alessandro Peruzzi
 */
public abstract class ToolCard {

    protected int id;
    protected int resultOfAction;
    private String title;
    private String description;

    /**
     * imposta la descrizione
     * @param description descrizione
     */
    public void setDescription(String description){
        this.description = description;
    }

    /**
     * imposta il titolo
     * @param title titolo
     */
    public void setTitle(String title){
        this.title = title;
    }

    /**
     *
     * @return ritorna una stringa: la descrizione della carta
     */
    public String getDescription(){
        return this.description;
    }

    /**
     *
     * @return ritorna una stringa: il titolo
     */
    public String getTitle(){
        return this.title;
    }

    /**
     *
     * @return ritorna l'id
     */
    public int getId() {

        return this.id;

    }

    public abstract int check(GameBoard board, Player player, List<Integer> parameterForCard);

    /**
     *
     * @param board game board
     * @param player player che attiva la carta
     * @param xStart riga da cui muovere il dado
     * @param yStart colonna da cui muovere il dado
     * @param xEnd riga in cui muovere il dado
     * @param yEnd colonna in cui muovere il dado
     * @return 1 se i controlli sono andati a buon fine sennò un valore negativo che indica l'errore
     */
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

    /**
     * svolge i controlli sul movimento di due dadi
     * @param board è la game board
     * @param player indica il player che vuole attivare la carta
     * @param x1Start indica da quale riga muovere il dado 1
     * @param y1Start indica da quale colonna muovere il dado 1
     * @param x1End indica in quale riga muovere il dado 1
     * @param y1End indica in quale colonna muovere il dado 1
     * @param x2Start indica da quale riga muovere il dado 2
     * @param y2Start indica da quale colonna muovere il dado 2
     * @param x2End indica in quale riga muovere il dado 2
     * @param y2End indica in quale colonna muovere il dado 2
     * @return ritorna 1 se i controlli sono andati bene sennò un valore negativo che indica l'errore
     */
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

    /**
     * svolge i controlli sulla selezione del dado del draft
     * @param board è la game board
     * @param player indica il player che vuole attivare la carta
     * @param posDieOnDraft indica quale dado del draft
     * @return ritorna 1 se i controlli sono andati bene sennò un valore negativo che indica l'errore
     */
    public int checkDieOnDraft(GameBoard board, Player player, int posDieOnDraft){

        int numProblem;

        if (board.getDieDraft(posDieOnDraft) == null) {

            numProblem=-9;

        } else {

            numProblem=1;

        }

        return numProblem;
    }

    /**
     * svolge i controlli sulla selezione del dado del roundtrack
     * @param board è la game board
     * @param player indica il player che vuole attivare la carta
     * @param whichRound indica quale round del roundtrack
     * @param whichDieRound quale dado tra quelli del round scelto
     * @return ritorna 1 se i controlli sono andati bene sennò un valore negativo che indica l'errore
     */
    public int checkDieOnRoundTrack(GameBoard board, Player player, int whichRound, int whichDieRound){

        int numProblem;

        if(board.getDieFromRoundTrack(whichRound,whichDieRound)==null){

            numProblem=-21;

        }else {

            numProblem=1;

        }

        return numProblem;
    }

    /**
     * attiva l'effetto della carta
     * @param board è la game board
     * @param player indica il player che vuole attivare la carta
     * @param parameterForCard è una lista di interi che rappresentano i vari valori dei parametri per l'attivazione della carta scelti dal client
     */
    public abstract void effect(GameBoard board, Player player, List<Integer> parameterForCard);


}
