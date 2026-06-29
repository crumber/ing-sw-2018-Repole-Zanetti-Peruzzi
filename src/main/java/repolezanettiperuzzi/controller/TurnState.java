package repolezanettiperuzzi.controller;

import org.json.simple.parser.ParseException;
import repolezanettiperuzzi.domain.ActionResult;
import repolezanettiperuzzi.model.Player;
import repolezanettiperuzzi.application.actions.*;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Classe che rappresenta lo stato del turno del player
 * @author Giampiero Repole
 */
public class TurnState extends ControllerState {

    private Controller controller;
    private TurnStateTracker turnStateTracker = new TurnStateTracker();

    /**
     * Fa iniziare il turno del player
     * @param controller Controller
     * @throws IOException Fallimento o interruzione delle operazioni I/O
     * @throws ParseException Errore durante l'analisi
     */
    @Override
    public void doAction(Controller controller) throws IOException, ParseException {

        BeginTurn beginTurn = new BeginTurn();

        setController(controller);

        if(BeginTurn.getCurrentTurn()==0 && BeginTurn.getNumPlayedTurn()==0){

            BeginTurn.resetCurrentPlayer();

        }

        if(!controller.board.getPlayer(BeginTurn.getCurrentPlayer()).getLiveStatus()){

            this.passToNextTurn(controller.board.getPlayer(BeginTurn.getCurrentPlayer()));

        }


        if(BeginTurn.getNumPlayedTurn()==0 && !turnStateTracker.isTurnNotificationSent()){

            for(int i=0; i<controller.board.getNPlayers();i++){

                this.updateView(controller.board.getPlayer(i));

            }

        }

        if(((!BeginTurn.controlTurn(controller.board.getPlayer(BeginTurn.getCurrentPlayer())))||(!controller.board.getPlayer(BeginTurn.getCurrentPlayer()).getLiveStatus()))&&!turnStateTracker.isToolCard8Active()) {

            this.passToNextTurn(controller.board.getPlayer(BeginTurn.getCurrentPlayer()));
            return;

        }

        if(!turnStateTracker.isTurnNotificationSent()) {
            notifyPlayerTurn();
            turnStateTracker.markTurnNotificationSent();
        }

        beginTurn.doAction(controller.board.getPlayer(BeginTurn.getCurrentPlayer()),controller.board);

    }

    public void setController(Controller controller){
        this.controller = controller;
        this.turnStateTracker = controller.getSession().getTurnStateTracker();
    }

    /**
     * Notifica l'inizio del turno al player
     * @throws IOException Fallimento o interruzione delle operazioni I/O
     */
    public void notifyPlayerTurn() throws IOException {

        Player actualPlayer = controller.board.getPlayer(BeginTurn.getCurrentPlayer());

        if(!controller.isTimerOn()){

            controller.setTimer("playerTurn");

        }

        for(Player player : controller.board.getPlayers()) {

            sendBeginTurnNotification(player, actualPlayer);

        }
    }

    private void sendBeginTurnNotification(Player player, Player actualPlayer) throws IOException {

        if (player.isSocketConnection()&&player.getLiveStatus()) {

            try (Socket socket = new Socket(player.getAddress(), player.getPort())) {

                HandlerControllerSocket handler = new HandlerControllerSocket(controller, socket);
                handler.notifyOnBeginTurn(actualPlayer.getName(), controller.getCurrentTime());

            }

        } else if (player.isRmiConnection()&&player.getLiveStatus()) {

            controller.getHandlerRMI().notifyOnBeginTurn(player.getName(), actualPlayer.getName(), controller.getCurrentTime());

        }
    }

    /**
     * Inserimento del dado
     * @param player Player che vuole inserire il dado
     * @param message Messaggio da parte dal client
     * @throws IOException Fallimento o interruzione delle operazioni I/O
     */
    public void insertDie(Player player, String message) throws IOException {

        if(BeginTurn.controlTurn(player)) {

            InsertDieWithCheckAction insert = new InsertDieWithCheckAction();
            ActionResult result = insert.doActionResult(player,controller.board,new CreateListForInsertDieAction().doAction(message));

            if(result.isError()){

                sendActionError(player, result);

            }else{

                this.updateView(player);

            }

        }else{

            sendNotYourTurn(player, false);

        }


    }

    /**
     * Richiesta attivazione tool card
     * @param player Player che vuole usare la carta
     * @param numCard Numero posizione carta nella game board
     * @throws IOException Fallimento o interruzione delle operazioni I/O
     */
    //da usare quando il giocatore richiede di utilizzare una carta
    public void useCardRequest(Player player, int numCard) throws IOException {

        if(BeginTurn.controlTurn(player)) {

            CheckCostToolCardAction check = new CheckCostToolCardAction();
            ActionResult checkCost = check.checkCostToolCardResult(controller.board,player,numCard);

            if(!checkCost.isError()) {

                ParametersRequestCardAction action = new ParametersRequestCardAction();
                String requestParameters = action.doAction(controller.board, numCard);

                //solo per la carta 7
                if(requestParameters.equals("requestCard NOTHING")){


                    UseCardAction cardAction = new UseCardAction();
                    ActionResult cardResult = cardAction.doActionResult(player,controller.board,numCard,new ArrayList<>());

                    if(cardResult.isError()){

                        sendActionError(player, cardResult);

                    }else{

                        this.updateView(player);

                    }

                    return;

                }

                sendParametersForToolCard(player, requestParameters);

            }else {

                sendActionError(player, checkCost);

            }

        }else{

            sendNotYourTurn(player, true);
        }

    }

    /**
     * Richiesta attivazione tool card
     * @param player Player che vuole usare la carta
     * @param numCard Numero posizione carta nella game board
     * @param parameters Parametri per l'attivazione della carta
     * @throws IOException Fallimento o interruzione delle operazioni I/O
     */
    public void useCard(Player player, int numCard, String parameters) throws IOException {

        System.out.println(numCard+" "+parameters);
        CreateListForCardAction list = new CreateListForCardAction();
        UseCardAction action = new UseCardAction();
        String[] mode = parameters.split(" ");
        ActionResult result;

        String message;

        WhichErrorAction error = new WhichErrorAction();

        if(controller.board.getToolCard(numCard).getId()==11){


            if(mode[0].equals("preEffect")) {


                parameters = parameters.substring(10);

                result = action.doActionPreEffectResult(player,controller.board,numCard,list.doAction(parameters,controller.board,numCard));


                if(result == ActionResult.FLUX_REMOVER_SECOND_STEP_REQUIRED){

                    ParametersRequestCardAction secondRequest = new ParametersRequestCardAction();
                    message = secondRequest.doAction();
                    this.updateView(player);

                }else{

                    message = error.doAction(result);

                }


            }else{

                result = action.doActionResult(player,controller.board,numCard,list.doAction(parameters,controller.board,numCard));

                if(result.isError()){

                    message = error.doAction(result);

                }else{

                    this.updateView(player);
                    return;

                }


            }

        }else{

            result = action.doActionResult(player,controller.board,numCard,list.doAction(parameters,controller.board,numCard));
            System.out.println(result.getCode());

            if(result.isError()){

                message = error.doAction(result);

            }else{

                if(controller.board.getToolCard(numCard).getId()==8){

                    turnStateTracker.activateToolCard8();

                }

                this.updateView(player);
                return;

            }

        }

        sendActionError(player, message);


    }

    private void sendActionError(Player player, ActionResult result) throws IOException {

        sendActionError(player, new WhichErrorAction().doAction(result));
    }

    private void sendActionError(Player player, String message) throws IOException {

        if(player.isSocketConnection()){

            try (Socket socket = new Socket(player.getAddress(), player.getPort())) {

                HandlerControllerSocket handler = new HandlerControllerSocket(controller,socket);
                handler.sendActionError(message);
            }

        } else if(player.isRmiConnection()){

            controller.getHandlerRMI().sendActionError(player.getName(), message);

        }
    }

    private void sendParametersForToolCard(Player player, String requestParameters) throws IOException {

        if (player.isSocketConnection()) {

            try (Socket socket = new Socket(player.getAddress(), player.getPort())) {

                HandlerControllerSocket handler = new HandlerControllerSocket(controller, socket);
                handler.sendParametersForToolCard(requestParameters);

            }

        } else if (player.isRmiConnection()) {

            controller.getHandlerRMI().sendCardParameters(player.getName(), requestParameters);

        }
    }

    private void sendNotYourTurn(Player player, boolean notifyRmi) throws IOException {

        if(player.isSocketConnection()){

            try (Socket socket = new Socket(player.getAddress(), player.getPort())) {

                HandlerControllerSocket handler = new HandlerControllerSocket(controller, socket);
                handler.sendNotYourTurn();

            }
        }else if(player.isRmiConnection() && notifyRmi){

            controller.getHandlerRMI().sendNotYourTurn(player.getName());

        }
    }

    /**
     * Aggiornamento view
     * @param player Player
     * @throws IOException Fallimento o interruzione delle operazioni I/O
     */
    public void updateView(Player player) throws IOException {

        if(player.getLiveStatus()){

            sendUpdateView(player);

        }
    }

    private void sendUpdateView(Player player) throws IOException {

        if(player.isSocketConnection()){

            Socket socket = new Socket(player.getAddress(),player.getPort());
            HandlerControllerSocket handler = new HandlerControllerSocket(controller,socket);
            handler.sendUpdateView(gameToString());

        }else if(player.isRmiConnection()){

            controller.getHandlerRMI().updateView(player.getName());

        }
    }

    public void notifyStatusToPlayers() throws IOException {

        if(controller.board.getPlayersOnline()>1) {

            for (int i = 0; i < controller.board.getNPlayers(); i++) {
                Player player = controller.board.getPlayer(i);

                if (shouldSendStatusUpdate(player)) { //solo GUI perche' altrimenti per la CLI mi blocca il flusso di updateView()

                    sendUpdateView(player);

                }
            }
        }else if(controller.board.getPlayersOnline()==1){

            for(int i = 0; i < controller.board.getNPlayers(); i++){

                Player player = controller.board.getPlayer(i);

                notifyLivePlayerBeforeEndGame(player);
            }
        }
    }

    private void notifyLivePlayerBeforeEndGame(Player player) throws IOException {

        if(player.getLiveStatus() && sendWinBeforeEndGameNotification(player)){

            System.exit(0);

        }
    }

    private boolean sendWinBeforeEndGameNotification(Player player) throws IOException {

        if(player.isSocketConnection()){

            Socket socket = new Socket(player.getAddress(), player.getPort());
            HandlerControllerSocket handler = new HandlerControllerSocket(controller, socket);
            handler.notifyWinBeforeEndGame();
            return true;

        }else if(player.isRmiConnection()){

            controller.getHandlerRMI().notifyOnWinBeforeEndGame(player.getName());
            return true;

        }

        return false;
    }

    private boolean shouldSendStatusUpdate(Player player) {

        if(!player.getLiveStatus()){

            return false;

        }

        if(player.isSocketConnection()){

            return player.usesGui() || (player.usesCli() && !player.getName().equals(BeginTurn.getCurrentPlayer()));

        }

        return player.isRmiConnection() && player.usesGui();
    }

    /**
     * Creazione e invio della stringa che rappresenta la situazione del gioco
     * @return Stringa che rappresenta la situazione del gioco
     */
    private String gameToString(){

        StringBuilder res = new StringBuilder();

        res.append(controller.board.getNPlayers());
        res.append("+");

        for (Player player: controller.board.getPlayers()){

            res.append(player.getName());
            res.append("*");
            res.append(player.getSecretColour());
            res.append("*");
            res.append(player.getWindow().getName().replace(" ", "-"));
            res.append("*");
            res.append(player.getWindow().getFTokens());
            res.append("*");
            res.append(player.getWindow().toString().replace(" ","_"));
            res.append("*");
            res.append(player.getFavorTokens());
            res.append("*");
            res.append(player.getLiveStatus());
            res.append("+");

        }

        res.append(BeginRound.getRound());
        res.append(BeginTurn.getCurrentTurn());
        res.append("+");
        res.append(controller.board.toStringDraft());
        res.append("+");
        res.append(controller.board.toStringRoundTrack());
        res.append("+");
        res.append(controller.board.toStringToolCards());
        res.append("+");
        res.append(controller.board.toStringPublicCards());

        return res.toString();
    }

    /**
     * Passaggio del turno
     * @param player Player
     * @throws IOException Fallimento o interruzione delle operazioni I/O
     * @throws ParseException Errore durante l'analisi
     */
    public void passToNextTurn(Player player) throws IOException, ParseException {

        controller.board.getPlayer(BeginTurn.getCurrentPlayer()).setInsertDieInThisTurn(false);
        controller.board.getPlayer(BeginTurn.getCurrentPlayer()).setUsedCardInThisTurn(false);
        turnStateTracker.resetTurnNotification();

        for(int i=0; i<controller.board.getNPlayers();i++){

            this.updateView(controller.board.getPlayer(i));

        }

        turnStateTracker.resetToolCard8();

        BeginTurn.nextTurnParameters(controller.board,player);

        if(BeginTurn.getNumPlayedTurn()==controller.board.getNPlayers()){

            BeginTurn.resetNumPlayedTurn();
            controller.setState(new EndRoundState());

        }else{

            controller.setState(new TurnState());

        }
    }

}
