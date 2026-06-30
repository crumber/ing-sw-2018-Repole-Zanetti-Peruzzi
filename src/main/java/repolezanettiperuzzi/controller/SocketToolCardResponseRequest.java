package repolezanettiperuzzi.controller;

/**
 * Parameters carried by the socket responseToolCard command.
 */
class SocketToolCardResponseRequest {

    private final int cardNumber;
    private final String response;

    private SocketToolCardResponseRequest(int cardNumber, String response){
        this.cardNumber=cardNumber;
        this.response=response;
    }

    static SocketToolCardResponseRequest from(SocketClientMessage message){
        return new SocketToolCardResponseRequest(
                Integer.parseInt(message.getParameter(0)),
                message.getParameter(1).replace("-", " ")
        );
    }

    int getCardNumber(){
        return cardNumber;
    }

    String getResponse(){
        return response;
    }
}
