package repolezanettiperuzzi.controller;

/**
 * Text encoding shared by socket and RMI command parameters.
 */
class TransportText {

    private TransportText(){
    }

    static String decodeSpaces(String value){
        return value.replace("-", " ");
    }
}
