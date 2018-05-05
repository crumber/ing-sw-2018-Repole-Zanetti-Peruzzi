package repolezanettiperuzzi.model.toolcards;

import repolezanettiperuzzi.model.*;

import java.util.ArrayList;
import java.util.List;

public abstract class ToolCard {

    int id;
    List<Object> resultOfAction = new ArrayList<>();

    public int getId() {
        return id;
    }

}
