package repolezanettiperuzzi.model.publiccards;

import repolezanettiperuzzi.model.Window;

public class ColorVariety implements PublicCard {

    @Override
    public int effect(Window finalWindow){
        return 10;
    }
}