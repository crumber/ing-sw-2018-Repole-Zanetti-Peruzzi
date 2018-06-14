package repolezanettiperuzzi.model;

/**
 * Classe che modellizza un dado
 * @author Alessandro Peruzzi
 * @author Giampiero Repole
 * @author Andrea Zanetti
 */
public class Die {

    private final Colour COLOURDIE;
    private Value valueDie;

    /**
     * Costruttore della classe
     * @param c colore del dado
     */
    public Die (Colour c){

        this.COLOURDIE=c;
        this.valueDie=Value.ONE;

    }

    /**
     * Tira il dado e gli assegna un valore casuale da 1 a 6
     */
    public void rollDie(){

        this.valueDie=valueDie.randomValue();

    }

    /**
     * @return colore del dado
     */
    public Colour getColourDie() {

        return COLOURDIE;

    }

    /**
     * @return valore del dado
     */
    public Value getValueDie() {

        return valueDie;

    }

    /**
     * Setta il valore del dado
     * @param value valore che si vuole assegnare al dado
     */
    public void setValue(Value value) {

        this.valueDie=value;

    }
}