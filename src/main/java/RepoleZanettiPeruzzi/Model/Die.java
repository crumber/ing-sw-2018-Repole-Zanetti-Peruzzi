package RepoleZanettiPeruzzi.Model;

public class Die {

    private Colour COLOURDIE;
    private Value valueDie;

    public Die (Colour c){

        this.COLOURDIE=c;
        this.valueDie=Value.ONE;
    }

    public void rollDie(){
        this.valueDie=valueDie.randomValue();
    }

    public Colour getCOLOURDIE() {
        return COLOURDIE;
    }

    public Value getValueDie() {
        return valueDie;
    }
}