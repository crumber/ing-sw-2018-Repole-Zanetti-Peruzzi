package repolezanettiperuzzi.model;

public class Die {

    private final Colour COLOURDIE;
    private Value valueDie;

    public Die (Colour c){

        this.COLOURDIE=c;
        this.valueDie=Value.ONE;

    }

    public void rollDie(){

        this.valueDie=valueDie.randomValue();

    }

    public Colour getColourDie() {

        return COLOURDIE;

    }

    public Value getValueDie() {

        return valueDie;

    }
}