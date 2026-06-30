package repolezanettiperuzzi.controller;

/**
 * Parameters carried by the socket chooseCard command.
 */
class SocketChooseCardRequest {

    private final int cardNumber;

    private SocketChooseCardRequest(int cardNumber){
        this.cardNumber=cardNumber;
    }

    static SocketChooseCardRequest from(SocketClientMessage message){
        return new SocketChooseCardRequest(Integer.parseInt(message.getParameter(0)));
    }

    int getCardNumber(){
        return cardNumber;
    }
}
