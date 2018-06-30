package repolezanettiperuzzi.common.modelwrapper;

import java.io.Serializable;

public enum ColourClient implements Serializable {

    YELLOW,
    RED,
    PURPLE,
    GREEN,
    BLUE;

    public static ColourClient getColour(String colour){
        switch(colour){
            case "PURPLE":
                return ColourClient.PURPLE;
            case "GREEN":
                return ColourClient.GREEN;
            case "YELLOW":
                return ColourClient.YELLOW;
            case "RED":
                return ColourClient.RED;
            case "BLUE":
                return ColourClient.BLUE;
        }
        return null;
    }

}