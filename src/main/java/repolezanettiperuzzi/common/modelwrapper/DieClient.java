package repolezanettiperuzzi.common.modelwrapper;

import java.io.Serializable;

public class DieClient implements Serializable {

    private final ColourClient COLOURDIE;
    private ValueClient valueDie;

    public DieClient(ColourClient c){

        this.COLOURDIE=c;
        this.valueDie= ValueClient.ONE;

    }

    public void rollDie(){

        this.valueDie=valueDie.randomValue();

    }

    public ColourClient getColourDie() {

        return COLOURDIE;

    }

    public ValueClient getValueDie() {

        return valueDie;

    }

    public void setValue(ValueClient value) {

        this.valueDie=value;

    }
}