package repolezanettiperuzzi.model.toolcards;

import java.util.ArrayList;
import java.util.List;

public class RunningPliers extends ToolCard {

    int id=8;
    List<Object> resultOfAction= new ArrayList<>();
    List<Object> requestForToolCard = new ArrayList<>();

    public int getId() {
        return id;
    }

    @Override
    public List<Object> requestCard(){

        return  requestForToolCard;

    }

}
