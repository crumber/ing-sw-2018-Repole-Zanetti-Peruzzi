package repolezanettiperuzzi.model.toolcards;

import repolezanettiperuzzi.model.*;

import java.util.ArrayList;
import java.util.List;

public abstract class ToolCard {

    int id;
    List<Object> resultOfAction = new ArrayList<>();
    List<Object> requestForToolCard = new ArrayList<>();

    public int getId() {
        return id;
    }

    public List<Object> check(List<Object> parameterForCard) {

        return  resultOfAction;

    }

    public void effect(List<Object> parameterForCard){

    }

    public List<Object> requestCard(){

        return  requestForToolCard;

    }

}
