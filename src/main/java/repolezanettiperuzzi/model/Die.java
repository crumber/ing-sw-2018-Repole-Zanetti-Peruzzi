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
     * imposta il valore del dado
     * @param value valore che si vuole assegnare al dado
     */
    public void setValue(Value value) {

        this.valueDie=value;

    }

    /**
     *
     * @return restituisce una stringa che rapprenseta il dado prima una lettera che indica il colore e poi un numero per il valore
     */
    public String toString(){

        String res="";

        switch(getColourDie()){

            case YELLOW:
                res = "Y";
                break;
            case RED:
                res = "R";
                break;
            case BLUE:
                res = "B";
                break;
            case PURPLE:
                res = "P";
                break;
            default:
                res = "G";
                break;

        }

        switch(getValueDie()){

            case ONE:
                res+="1";
                break;
            case TWO:
                res+="2";
                break;
            case THREE:
                res+="3";
                break;
            case FOUR:
                res+="4";
                break;
            case FIVE:
                res+="5";
                break;
            default:
                res+="6";
                break;

        }

        return res;
    }
}