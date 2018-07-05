package repolezanettiperuzzi.model.actions;

import repolezanettiperuzzi.model.GameBoard;

/**
 * Classe che rappresenta la generazione dei codici per le domande delle tool card
 * @author Alessandro Peruzzi
 */
public class ParametersRequestCardAction{

    /**
     *
     * @return ritorna una stringa con il codice per la seconda richiesta da fare al client (vale solo per la carta 11)
     */
    public String doAction(){

        return "requestCard dieValue ";

    }

    /**
     *
     * @param board game board
     * @param whichToolCard intero che indica quale tool card presente nella game board
     * @return ritorna una stringa che contiene i codici per le domande da fare al client relativi alla tool cart scelta
     */
    public String doAction(GameBoard board, int whichToolCard){

        String requestForToolCard;
        int id=board.getToolCard(whichToolCard).getId();
        String card11="card11 ";
        String typeMess="requestCard ";
        String start="startPos ";
        String end="endPos ";
        String draft="dieDraft ";
        String round="dieRoundTrack ";
        String incrDecr="incrDecrDie ";

        if(id==2 || id==3){

            requestForToolCard=start+end;

        }else if(id==9 || id==8){

            requestForToolCard=draft+end;

        }else if(id==11){

            requestForToolCard=card11;

        }else if(id==6 || id==10){

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

        requestForToolCard = requestForToolCard.replace(" ","-");
        return typeMess+requestForToolCard;
    }
}
