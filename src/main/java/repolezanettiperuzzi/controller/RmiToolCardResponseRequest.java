package repolezanettiperuzzi.controller;

/**
 * Parameters carried by the RMI responseToolCard command.
 */
class RmiToolCardResponseRequest {

    private final int cardNumber;
    private final String response;

    RmiToolCardResponseRequest(int cardNumber, String response){
        this.cardNumber=cardNumber;
        this.response=TransportText.decodeSpaces(response);
    }

    int getCardNumber(){
        return cardNumber;
    }

    String getResponse(){
        return response;
    }
}
