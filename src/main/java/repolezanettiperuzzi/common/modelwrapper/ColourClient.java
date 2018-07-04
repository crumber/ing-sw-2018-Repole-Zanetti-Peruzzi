package repolezanettiperuzzi.common.modelwrapper;

import java.io.Serializable;

/**
 * Classe che modellizza i colori dei dadi lato client
 * @author Alessandro Peruzzi
 * @author Andrea Zanetti
 * @author Giampiero Repole
 */
public enum ColourClient implements Serializable {

    YELLOW,
    RED,
    PURPLE,
    GREEN,
    BLUE;

    /**
     * Converte la stringa in colourClient
     * @param colour Stringa con scritto il colore in inglese tutto in maiuscolo
     * @return ColourClient associato alla stringa passata
     */
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