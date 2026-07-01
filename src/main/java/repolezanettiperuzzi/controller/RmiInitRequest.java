package repolezanettiperuzzi.controller;

/**
 * Parameters carried by the RMI init command.
 */
class RmiInitRequest {

    private final String username;
    private final String password;
    private final String connection;
    private final String ui;

    RmiInitRequest(String username, String password, String connection, String ui){
        this.username=username;
        this.password=password;
        this.connection=connection;
        this.ui=ui;
    }

    String getUsername(){
        return username;
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
}
