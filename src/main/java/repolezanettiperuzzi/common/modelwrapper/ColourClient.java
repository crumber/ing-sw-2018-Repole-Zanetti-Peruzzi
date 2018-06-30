package repolezanettiperuzzi.common.modelwrapper;

import java.io.Serializable;

public enum ColourClient implements Serializable {

    YELLOW,
    RED,
    PURPLE,
    GREEN,
    BLUE;

    public static ColourClient stringToColour(String colour){

        switch(colour) {

            case "RED":

                return ColourClient.RED;

            case "PURPLE":

                return ColourClient.PURPLE;

            case "YELLOW":

                return ColourClient.YELLOW;

            case "BLUE":

                return ColourClient.BLUE;

            case "GREEN":

                return ColourClient.GREEN;

            default:

                return null;

        }

    }

}