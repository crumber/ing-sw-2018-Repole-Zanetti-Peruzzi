package repolezanettiperuzzi.client;

import java.io.IOException;

/**
 * Actions exposed by the client coordinator to CLI and JavaFX presentation code.
 */
public interface ClientViewActions {

    void onLogin(String username, String pwd, String conn, String UI, String serverIp) throws IOException, InterruptedException;

    void notifyOnExit(String typeView) throws IOException;

    void waitingRoomLoaded() throws IOException;

    void chooseWindowSceneLoaded() throws IOException;

    void gameLoaded() throws IOException;

    void sendInsertDie(int draftPos, int xWindowPos, int yWindowPos) throws IOException;

    void sendChooseCard(int numCard) throws IOException;

    void sendResponseToolCard(int nCard, String response) throws IOException;

    void sendChosenWindow(String windowName) throws IOException;

    void sendEndTurn() throws IOException;

    String getUsername();
}
