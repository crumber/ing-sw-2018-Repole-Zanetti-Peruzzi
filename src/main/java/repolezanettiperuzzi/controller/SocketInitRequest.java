package repolezanettiperuzzi.controller;

/**
 * Parameters carried by the socket init command.
 */
class SocketInitRequest {

    private final String password;
    private final String connection;
    private final String ui;
    private final int port;

    private SocketInitRequest(String password, String connection, String ui, int port){
        this.password=password;
        this.connection=connection;
        this.ui=ui;
        this.port=port;
    }

    static SocketInitRequest from(SocketClientMessage message){
        return new SocketInitRequest(
                message.getParameter(0),
                message.getParameter(1),
                message.getParameter(2),
                Integer.parseInt(message.getParameter(3))
        );
    }

    String getPassword(){
        return password;
    }

    String getConnection(){
        return connection;
    }

    String getUi(){
        return ui;
    }

    int getPort(){
        return port;
    }
}
