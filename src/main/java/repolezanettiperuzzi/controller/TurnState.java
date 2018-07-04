package repolezanettiperuzzi.controller;

import org.json.simple.parser.ParseException;
import repolezanettiperuzzi.model.Player;
import repolezanettiperuzzi.model.actions.*;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

public class TurnState extends ControllerState {

    private Controller controller;
    public static boolean isSendTurn = false;

    @Override
    public void doAction(Controller controller) throws IOException, ParseException {

        BeginTurn beginTurn = new BeginTurn();

        this.controller=controller;

        if(BeginTurn.getCurrentTurn()==0 && BeginTurn.getNumPlayedTurn()==0){

            BeginTurn.resetCurrentPlayer();

        }

        if(!controller.board.getPlayer(BeginTurn.getCurrentPlayer()).getLiveStatus()){

            this.passToNextTurn(controller.board.getPlayer(BeginTurn.getCurrentPlayer()));

        }


        if(BeginTurn.getNumPlayedTurn()==0){

            for(int i=0; i<controller.board.getNPlayers();i++){

                this.updateView(controller.board.getPlayer(i));

            }

        }

        if((!BeginTurn.controlTurn(controller.board.getPlayer(BeginTurn.getCurrentPlayer())))||(!controller.board.getPlayer(BeginTurn.getCurrentPlayer()).getLiveStatus())) {

            System.out.println(true);
            this.passToNextTurn(controller.board.getPlayer(BeginTurn.getCurrentPlayer()));
            return;

        }

        if(!isSendTurn) {
            notifyPlayerTurn();
            isSendTurn = true;
        }

        beginTurn.doAction(controller.board.getPlayer(BeginTurn.getCurrentPlayer()),controller.board);

    }

    public void notifyPlayerTurn() throws IOException {

        Player actualPlayer = controller.board.getPlayer(BeginTurn.getCurrentPlayer());

        if(!controller.isTimerOn()){

            controller.setTimer("playerTurn");

        }

        for(Player player : controller.board.getPlayers()) {

            if (player.getConnection().equals("Socket")&&player.getLiveStatus()) {

                try (Socket socket = new Socket(player.getAddress(), player.getPort())) {

                    HandlerControllerSocket handler = new HandlerControllerSocket(controller, socket);
                    handler.notifyOnBeginTurn(actualPlayer.getName(), controller.getCurrentTime());

                }

            } else if (player.getConnection().equals("RMI")&&player.getLiveStatus()) {


            }

        }
    }

    public void insertDie(Player player, String message) throws IOException {

        if(BeginTurn.controlTurn(player)) {

            int code;

            InsertDieWithCheckAction insert = new InsertDieWithCheckAction();
            code = insert.doAction(player,controller.board,new CreateListForInsertDieAction().doAction(message));

            if(code<0){

                String error = new WhichErrorAction().doAction(code);

                if(player.getConnection().equals("Socket")){

                    try (Socket socket = new Socket(player.getAddress(), player.getPort())) {
                        System.out.println(error);
                        HandlerControllerSocket handler = new HandlerControllerSocket(controller, socket);
                        handler.sendActionError(error);

                    }
                }else if(player.getConnection().equals("RMI")){


                }

            }else{

                this.updateView(player);

            }

        }else{

            if(player.getConnection().equals("Socket")){

                try (Socket socket = new Socket(player.getAddress(), player.getPort())) {
                    System.out.println("notYourTurn");
                    HandlerControllerSocket handler = new HandlerControllerSocket(controller, socket);
                    handler.sendNotYourTurn();

                }
            }else if(player.getConnection().equals("RMI")){


            }

        }


    }

    //da usare quando il giocatore richiede di utilizzare una carta
    public void useCardRequest(Player player, int numCard) throws IOException {

        if(BeginTurn.controlTurn(player)) {

            CheckCostToolCardAction check = new CheckCostToolCardAction();
            int checkCost = check.checkCostToolCard(controller.board,player,numCard);

            if(checkCost>=0) {

                ParametersRequestCardAction action = new ParametersRequestCardAction();
                String requestParameters = action.doAction(controller.board, numCard);

                //solo per la carta 7
                if(requestParameters.equals("requestCard NOTHING")){

                    UseCardAction cardAction = new UseCardAction();
                    cardAction.doAction(player,controller.board,numCard,new ArrayList<>());
                    this.updateView(player);
                    return;

                }

                if (player.getConnection().equals("Socket")) {

                    try (Socket socket = new Socket(player.getAddress(), player.getPort())) {

                        HandlerControllerSocket handler = new HandlerControllerSocket(controller, socket);
                        handler.sendParametersForToolCard(requestParameters);

                    }

                } else if (player.getConnection().equals("RMI")) {


                }

            }else {

                String error = (new WhichErrorAction()).doAction(checkCost);

                if (player.getConnection().equals("Socket")) {

                    try (Socket socket = new Socket(player.getAddress(), player.getPort())) {

                        HandlerControllerSocket handler = new HandlerControllerSocket(controller, socket);
                        handler.sendActionError(error);

                    }

                } else if (player.getConnection().equals("RMI")) {


                }

            }

        }else{

            if(player.getConnection().equals("Socket")){

                try (Socket socket = new Socket(player.getAddress(), player.getPort())) {

                    HandlerControllerSocket handler = new HandlerControllerSocket(controller, socket);
                    handler.sendNotYourTurn();

                }
            }else if(player.getConnection().equals("RMI")){


            }
        }

    }

    public void useCard(Player player, int numCard, String parameters) throws IOException {

        CreateListForCardAction list = new CreateListForCardAction();
        UseCardAction action = new UseCardAction();
        String[] mode = parameters.split(" ");
        int code;

        String message;

        WhichErrorAction error = new WhichErrorAction();

        if(numCard==11){

            if(mode[0].equals("preEffect")) {

                parameters = parameters.substring(10);

                code = action.doActionPreEffect(player,controller.board,numCard,list.doAction(parameters,controller.board,numCard));

                if(code==11){

                    ParametersRequestCardAction secondRequest = new ParametersRequestCardAction();
                    message = secondRequest.doAction();

                }else{

                    message = error.doAction(code);

                }


            }else{

                code = action.doAction(player,controller.board,numCard,list.doAction(parameters,controller.board,numCard));

                if(code<0){

                    message = error.doAction(code);

                }else{

                    this.updateView(player);
                    return;

                }

            }

        }else{

            code = action.doAction(player,controller.board,numCard,list.doAction(parameters,controller.board,numCard));

            if(code<0){

                message = error.doAction(code);

            }else{


                this.updateView(player);
                return;

            }

        }

        if(player.getConnection().equals("Socket")){

            try (Socket socket = new Socket(player.getAddress(), player.getPort())) {

                HandlerControllerSocket handler = new HandlerControllerSocket(controller,socket);
                handler.sendActionError(message);
            }

        }


    }

    public void updateView(Player player) throws IOException {

        if(player.getConnection().equals("Socket")&&(player.getLiveStatus())){

            Socket socket = new Socket(player.getAddress(),player.getPort());
            HandlerControllerSocket handler = new HandlerControllerSocket(controller,socket);
            handler.sendUpdateView(gameToString());

        }else if(player.getConnection().equals("RMI")&&(player.getLiveStatus())){


        }
    }

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

    public void passToNextTurn(Player player) throws IOException, ParseException {

        controller.board.getPlayer(BeginTurn.getCurrentPlayer()).setInsertDieInThisTurn(false);
        controller.board.getPlayer(BeginTurn.getCurrentPlayer()).setUsedCardInThisTurn(false);
        isSendTurn = false;

        for(int i=0; i<controller.board.getNPlayers();i++){

            this.updateView(controller.board.getPlayer(i));

        }

        BeginTurn.nextTurnParameters(controller.board,player);

        if(BeginTurn.getNumPlayedTurn()==controller.board.getNPlayers()){

            BeginTurn.resetNumPlayedTurn();
            controller.setState(new EndRoundState());

        }else{

            controller.setState(new TurnState());

        }



    }

}
