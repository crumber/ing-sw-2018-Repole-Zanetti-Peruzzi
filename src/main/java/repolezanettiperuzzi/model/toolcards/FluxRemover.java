package repolezanettiperuzzi.model.toolcards;

import repolezanettiperuzzi.model.Die;
import repolezanettiperuzzi.model.GameBoard;
import repolezanettiperuzzi.model.RealPlayer;

import java.util.ArrayList;
import java.util.List;

public class FluxRemover extends ToolCard {

    int id=11;
    List<Object> resultOfAction= new ArrayList<>();

    public int getId() {
        return id;
    }

    public List<Object> check(GameBoard boar, int posDieDraft) {

        if(boar.getDieDraft(posDieDraft)==null){

                resultOfAction.add(-1);
                resultOfAction.add("there isn't die in this position on draft");

        }else{

            resultOfAction.add(1);
        }

        return resultOfAction;
    }

    public boolean effect(GameBoard board, int posDieDraft){

        board.putDieInBag(posDieDraft); //put die in the bag
        board.removeDieFromDraft(posDieDraft); //removed die from draft
        Die newDie= board.takeDieFromBag(); // take another die from bag

        /*

        BISOGNA CHIEDERE AL PLAYER CHE VALORE VUOLE PER QUEL DADO SI POTREBBE AVVISARE IL CONTROLLER CHE IL GIOCATORE DEVE FARE STA COSA E FARLA GESTIRE FUORI DALLA CARTA

         */

        board.addDieToDraft(newDie); // add new die to draft
        return true;

    }
}
