package repolezanettiperuzzi.model.actions;

import repolezanettiperuzzi.model.GameBoard;

public class ParametersRequestCardAction{

    //manca la 8 la 7 va in nothing perche non ha bisogno di niente

    public String doAction(){

        return "dieValue ";

    }

    public String doAction(GameBoard board, int whichToolCard){

        String requestForToolCard;
        int id=board.getToolCard(whichToolCard).getId();
        String typeMess="requestCard ";
        String start="startPos ";
        String end="endPos ";
        String draft="dieDraft ";
        String round="dieRoundTrack ";
        String incrDecr="incrDecrDie ";

        if(id==2 || id==3){

            requestForToolCard=start+end;

        }else if(id==9){

            requestForToolCard=draft+end;

        }else if(id==6 || id==11 || id==10){

            requestForToolCard=draft;

        }else if(id==1){

            requestForToolCard=draft+incrDecr;

        }else if(id==4) {

            requestForToolCard=start+end+start+end;

        }else if(id==5) {

            requestForToolCard=draft+round;

        }else if(id==12) {

            requestForToolCard=start+end+start+end+round;

        }else{

            requestForToolCard="NOTHING";

        }

        return typeMess+requestForToolCard;
    }
}
