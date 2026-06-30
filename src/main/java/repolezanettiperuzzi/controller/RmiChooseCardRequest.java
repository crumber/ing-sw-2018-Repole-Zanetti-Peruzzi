package repolezanettiperuzzi.controller;

/**
 * Parameters carried by the RMI chooseCard command.
 */
class RmiChooseCardRequest {

    private final int cardNumber;

    RmiChooseCardRequest(int cardNumber){
        this.cardNumber=cardNumber;
    }

    int getCardNumber(){
        return cardNumber;
    }
}
