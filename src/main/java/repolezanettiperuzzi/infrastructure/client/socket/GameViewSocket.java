package repolezanettiperuzzi.infrastructure.client.socket;

import java.io.IOException;
import java.util.function.Consumer;

/**
 * Classe che modella la game view (socket)
 * @author Andrea Zanetti
 */
public class GameViewSocket implements Runnable{

    private ClientSocketMessageServer messageServer;
    private ClientSocketConnection clientConnection;
    private ClientSocketView gameView;

    /**
     * Costruttore
     * @param onReceiveCallback Riferimento all'oggetto remoto del client
     */
    public GameViewSocket(Consumer<String> onReceiveCallback){
        this.messageServer = new ClientSocketMessageServer(onReceiveCallback);
    }

    /**
     * Costruttore
     * @param gameView Game view
     * @throws IOException Fallimento o interruzione delle operazioni I/O
     */
    public GameViewSocket(ClientSocketView gameView, String serverIp) throws IOException {
        this.gameView = gameView;
        this.clientConnection = new ClientSocketConnection(serverIp);
    }

    @Override
    public void run(){
        messageServer.run();
    }

    public void shutdownServer(){
        messageServer.shutdownServer();
    }

    /**
     *  Gestore messaggio
     * @param message Messaggio
     */
    public void handleMessage(String message){
        GameViewSocketMessage socketMessage = GameViewSocketMessage.parse(message);
        switch(socketMessage.getAction()){
            case REGISTERED:
                gameView.enterWaitingRoom();
                break;
            case UPDATED_PLAYERS:
                GameViewUpdatedPlayersMessage updatedPlayersMessage = GameViewUpdatedPlayersMessage.from(socketMessage);
                gameView.refreshWaitingRoom(updatedPlayersMessage.getTimer(), updatedPlayersMessage.getPlayers());
                break;
            case NOT_REGISTERED:
                handleNotRegistered(socketMessage);
                break;
            case CHANGE_VIEW:
                handleChangeView(socketMessage);
                break;
            case CHOOSE_WINDOW:
                GameViewChooseWindowMessage chooseWindowMessage = GameViewChooseWindowMessage.from(socketMessage);
                gameView.viewWindows(chooseWindowMessage.getWindows(), chooseWindowMessage.getCurrentTime());
                break;
            case SHOW_WINDOW:
                GameViewShowWindowMessage showWindowMessage = GameViewShowWindowMessage.from(socketMessage);
                gameView.viewOneWindow(showWindowMessage.getWindow(), showWindowMessage.getCurrentTime());
                break;
            case START_GAME:
                gameView.enterGame();
                break;
            case WIN_CHOOSE_WINDOW:
                gameView.showWinOnChooseWindowAlert();
                break;
            case NOT_YOUR_TURN:
                gameView.notYourTurn();
                break;
            case ERROR:
                gameView.viewError(socketMessage.getFirstPayloadToken());
                break;
            case TURN:
                GameViewTurnMessage turnMessage = GameViewTurnMessage.from(socketMessage);
                gameView.notifyTurn(turnMessage.getActualPlayer(), turnMessage.getCurrentTime());
                break;
            case UPDATE_VIEW:
                GameViewUpdateViewMessage updateViewMessage = GameViewUpdateViewMessage.from(socketMessage);
                gameView.updateView(updateViewMessage.getBoard());
                break;
            case REQUEST_CARD:
                gameView.receiveCardParameters(socketMessage.getFirstPayloadToken());
                break;
            case END_GAME:
                gameView.receiveRanking(socketMessage.getFirstPayloadToken());
                break;
            case WIN_BEFORE_END:
                gameView.showWinBeforeEndGameAlert();
                break;
            case EXIT:
                gameView.shutdownClient();
                break;
        }
    }

    private void handleNotRegistered(GameViewSocketMessage socketMessage){
        GameViewNotRegisteredReason notRegisteredReason = GameViewNotRegisteredReason.fromWireValue(socketMessage.getFirstPayloadToken());
        switch(notRegisteredReason){
            case ALREADY_ONLINE:
                gameView.showPlayerAlreadyOnlineAlert();
                break;
            case WRONG_PASSWORD:
                gameView.showWrongPwdAlert();
                break;
            case GAME_ALREADY_STARTED:
                gameView.showGameAlreadyStarted();
                break;
            case ALREADY_FOUR_PLAYERS:
                gameView.showAlready4Players();
                break;
            case UNKNOWN:
                break;
        }
    }

    private void handleChangeView(GameViewSocketMessage socketMessage){
        GameViewChangeViewDestination changeViewDestination = GameViewChangeViewDestination.fromWireValue(socketMessage.getFirstPayloadToken());
        switch(changeViewDestination){
            case CHOOSE_WINDOW:
                gameView.enterChooseWindow();
                break;
            case UNKNOWN:
                break;
        }
    }

    /**
     * Inizializzazione
     * @param username Username
     * @param pwd Password
     * @param conn Tipo connessione
     * @param UI Interfaccia grafica
     * @param localPort Intero che rappresenta la porta locale
     * @throws IOException Fallimento o interruzione delle operazioni I/O
     */
    public void init(String username, String pwd, String conn, String UI, int localPort) throws IOException {
        sendMessage(GameViewSocketOutgoingMessage.init(username, pwd, conn, UI, localPort));
    }

    /**
     * Caricamento waiting room
     * @param username Nome utente
     * @throws IOException  Fallimento o interruzione delle operazioni I/O
     */
    public void waitingRoomLoaded(String username) throws IOException {
        sendMessage(GameViewSocketOutgoingMessage.waitingRoomLoaded(username));
    }

    /**
     * Caricamento scelta delle window
     * @param username Nome utente
     * @throws IOException  Fallimento o interruzione delle operazioni I/O
     */
    public void chooseWindowSceneLoaded(String username) throws IOException{
        sendMessage(GameViewSocketOutgoingMessage.chooseWindowSceneLoaded(username));
    }

    /**
     * Caricamento scena gioco
     * @param username Nome utente
     * @throws IOException  Fallimento o interruzione delle operazioni I/O
     */
    public void gameSceneLoaded(String username) throws IOException{
        sendMessage(GameViewSocketOutgoingMessage.gameSceneLoaded(username));
    }

    /**
     * Notify uscita
     * @param username Nome utente
     * @param typeView Tipo view
     * @throws IOException  Fallimento o interruzione delle operazioni I/O
     */
    public void notifyOnExit(String username, String typeView) throws IOException {
        sendMessage(GameViewSocketOutgoingMessage.exit(username, typeView));
    }

    /**
     * Invio inserimento dado
     * @param username Nome utente
     * @param draftPos Posizione draft
     * @param xWindowPos Riga
     * @param yWindowPos Colonna
     * @throws IOException  Fallimento o interruzione delle operazioni I/O
     */
    public void sendInsertDie(String username, int draftPos, int xWindowPos, int yWindowPos) throws IOException {
        sendMessage(GameViewSocketOutgoingMessage.insertDie(username, draftPos, xWindowPos, yWindowPos));
    }

    /**
     * Invio scelta della carta
     * @param username Nome player
     * @param numCard Numero carta
     * @throws IOException  Fallimento o interruzione delle operazioni I/O
     */
    public void sendChooseCard(String username, int numCard) throws IOException {

        sendMessage(GameViewSocketOutgoingMessage.chooseCard(username, numCard));

    }

    /**
     * Invio risposte tool card
     * @param username Nome utente
     * @param nCard Numero card
     * @param response Risosta
     * @throws IOException  Fallimento o interruzione delle operazioni I/O
     */
    public void sendResponseToolCard(String username, int nCard, String response) throws IOException {
        sendMessage(GameViewSocketOutgoingMessage.responseToolCard(username, nCard, response));
    }

    /**
     * Invio window scelta
     * @param username Nome utente
     * @param windowName Nome windows
     * @throws IOException  Fallimento o interruzione delle operazioni I/O
     */
    public void sendChosenWindow(String username, String windowName) throws IOException {

        sendMessage(GameViewSocketOutgoingMessage.chosenWindow(username, windowName));
    }

    /**
     *
     * @return Local server port
     */
    public int getLocalServerPort(){
        return messageServer.getLocalServerPort();
    }

    /**
     * Invio fine turno
     * @param username Nome utente
     * @throws IOException  Fallimento o interruzione delle operazioni I/O
     */
    public void sendEndTurn(String username) throws IOException {

        sendMessage(GameViewSocketOutgoingMessage.endTurn(username));

    }

    private void sendMessage(String message) throws IOException {
        clientConnection.sendMessage(message);
    }


}
