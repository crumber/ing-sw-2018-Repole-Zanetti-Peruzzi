package repolezanettiperuzzi.controller;

/**
 * Parsed command received from a socket client.
 */
class SocketClientMessage {

    private final String playerId;
    private final String action;
    private final String[] parameters;

    private SocketClientMessage(String playerId, String action, String[] parameters){
        this.playerId=playerId;
        this.action=action;
        this.parameters=parameters;
    }

    static SocketClientMessage parse(String message){
        String[] line = message.split(" ");
        String[] parameters = new String[line.length-2];

        for(int i = 0; i<parameters.length; i++){
            parameters[i] = line[i+2];
        }

        return new SocketClientMessage(line[0], line[1], parameters);
    }

    String getPlayerId(){
        return playerId;
    }

    String getAction(){
        return action;
    }

    String getParameter(int index){
        return parameters[index];
    }

    int getParameterCount(){
        return parameters.length;
    }
}
