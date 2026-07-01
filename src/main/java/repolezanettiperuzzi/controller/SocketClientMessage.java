package repolezanettiperuzzi.controller;

import java.util.Arrays;

/**
 * Parsed command received from a socket client.
 */
class SocketClientMessage {

    private final String playerId;
    private final SocketClientAction action;
    private final String[] parameters;

    private SocketClientMessage(String playerId, SocketClientAction action, String[] parameters){
        this.playerId=playerId;
        this.action=action;
        this.parameters=parameters;
    }

    static SocketClientMessage parse(String message){
        String[] line = message.split(" ");
        String[] parameters = Arrays.copyOfRange(line,2,line.length);

        return new SocketClientMessage(line[0], SocketClientAction.fromWireValue(line[1]), parameters);
    }

    String getPlayerId(){
        return playerId;
    }

    SocketClientAction getAction(){
        return action;
    }

    String getParameter(int index){
        return parameters[index];
    }

    int getParameterCount(){
        return parameters.length;
    }
}
