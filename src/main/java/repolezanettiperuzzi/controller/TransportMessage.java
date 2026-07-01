package repolezanettiperuzzi.controller;

/**
 * Helpers for command-prefixed transport messages.
 */
class TransportMessage {

    private TransportMessage(){
    }

    static String payload(String message){
        return message.split(" ")[1];
    }
}
