package repolezanettiperuzzi.common.modelwrapper;

import java.io.Serializable;

public class DieClient implements Serializable {

    private final ColourClient COLOURDIE;
    private ValueClient valueDie;

    public DieClient(ColourClient c){

        this.COLOURDIE=c;
        this.valueDie= ValueClient.ONE;

    }

    public DieClient(String colorValue){
        String colour = colorValue.charAt(0)+"";
        String value = colorValue.charAt(1)+"";

        switch (colour){
            case "P":
                this.COLOURDIE = ColourClient.PURPLE;
                break;
            case "Y":
                this.COLOURDIE = ColourClient.YELLOW;
                break;
            case "B":
                this.COLOURDIE = ColourClient.BLUE;
                break;
            case "R":
                this.COLOURDIE = ColourClient.RED;
                break;
            case "G":
                this.COLOURDIE = ColourClient.GREEN;
                break;
            default:
                this.COLOURDIE = ColourClient.PURPLE;
        }

        switch (value){
            case "1":
                this.valueDie = ValueClient.ONE;
                break;
            case "2":
                this.valueDie = ValueClient.TWO;
                break;
            case "3":
                this.valueDie = ValueClient.THREE;
                break;
            case "4":
                this.valueDie = ValueClient.FOUR;
                break;
            case "5":
                this.valueDie = ValueClient.FIVE;
                break;
            case "6":
                this.valueDie = ValueClient.SIX;
                break;
        }
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

    public String toString() {

        String res = "";

        switch (getColourDie()) {

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

        switch (getValueDie()) {

            case ONE:
                res += "1";
                break;
            case TWO:
                res += "2";
                break;
            case THREE:
                res += "3";
                break;
            case FOUR:
                res += "4";
                break;
            case FIVE:
                res += "5";
                break;
            default:
                res += "6";
                break;

        }

        return res;
    }
}