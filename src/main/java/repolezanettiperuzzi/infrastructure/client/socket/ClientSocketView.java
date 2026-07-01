package repolezanettiperuzzi.infrastructure.client.socket;

import repolezanettiperuzzi.shared.dto.GameBoardClient;
import repolezanettiperuzzi.shared.dto.WindowClient;

import java.util.ArrayList;

/**
 * View callbacks used by the client socket coordinator.
 */
public interface ClientSocketView {

    void enterWaitingRoom();

    void refreshWaitingRoom(int setTimer, String[] players);

    void showPlayerAlreadyOnlineAlert();

    void showWrongPwdAlert();

    void showGameAlreadyStarted();

    void showAlready4Players();

    void enterChooseWindow();

    void viewWindows(ArrayList<WindowClient> windows, int currentTime);

    void viewOneWindow(WindowClient window, int currentTime);

    void enterGame();

    void showWinOnChooseWindowAlert();

    void notYourTurn();

    void viewError(String error);

    void notifyTurn(String actualPlayer, int currentTime);

    void updateView(GameBoardClient board);

    void receiveCardParameters(String parameters);

    void receiveRanking(String score);

    void showWinBeforeEndGameAlert();

    void shutdownClient();
}
