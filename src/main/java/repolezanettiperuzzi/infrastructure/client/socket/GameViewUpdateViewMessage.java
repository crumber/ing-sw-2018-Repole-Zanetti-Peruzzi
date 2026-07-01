package repolezanettiperuzzi.infrastructure.client.socket;
import repolezanettiperuzzi.shared.dto.ColourClient;
import repolezanettiperuzzi.shared.dto.DieClient;
import repolezanettiperuzzi.shared.dto.GameBoardClient;
import repolezanettiperuzzi.shared.dto.WindowClient;

import java.util.ArrayList;

/**
 * Payload carried by the client socket updateView message.
 */
public class GameViewUpdateViewMessage {

    private final GameBoardClient board;

    private GameViewUpdateViewMessage(GameBoardClient board){
        this.board=board;
    }

    public static GameViewUpdateViewMessage from(GameViewSocketMessage message){
        GameBoardClient board = new GameBoardClient();
        String[] boardElems = message.getFirstPayloadToken().split("\\+");
        int numPlayers = Integer.parseInt(boardElems[0]);
        int i;
        for(i = 1; i<(numPlayers+1); i++){
            String[] playerElems = boardElems[i].split("\\*");
            String playerName = playerElems[0];
            board.addPlayer(playerName);
            ColourClient secretColour = ColourClient.stringToColour(playerElems[1]);
            board.getPlayerByName(playerName).setSecretColour(secretColour);
            String windowName = playerElems[2]; // nome della window con i trattini inclusi
            int favorTokens = Integer.parseInt(playerElems[3]);
            String window = playerElems[4].replace("_", " ");
            board.getPlayerByName(playerName).setWindow(new WindowClient(windowName, favorTokens, window));
            board.getPlayerByName(playerName).setFavorTokens(Integer.parseInt(playerElems[5]));
            board.getPlayerByName(playerName).setLiveStatus(Boolean.parseBoolean(playerElems[6]));
        }
        if(boardElems[i].length()==2) {


            board.setRound(Character.getNumericValue(boardElems[i].charAt(0)));
            board.setTurn(Character.getNumericValue(boardElems[i].charAt(1)));


        }else{

            board.setRound(Integer.parseInt(boardElems[i].substring(0,2)));
            board.setTurn(Character.getNumericValue(boardElems[i].charAt(2)));


        }
        i++;

        if(!boardElems[i].equals("")) {
            String[] dice = boardElems[i].split("_");
            for (int j = 0; j < dice.length; j++) {
                board.addDieToDraft(new DieClient(dice[j]));
            }
        }
        i++;

        if(!boardElems[i].equals("")) {
            String[] roundAndDice = boardElems[i].split("-");
            for(int j = 0; j<roundAndDice.length; j++){
                String dice = roundAndDice[j].substring(1);
                String[] die = dice.split("_");
                ArrayList<DieClient> dieRound = new ArrayList<>();

                if(die[0].length()!=0) {
                    for (int k = 0; k < die.length; k++) {

                        dieRound.add(new DieClient(die[k]));

                    }
                }
                board.getRoundTrack().addDice(dieRound);
            }
        }
        i++;

        String[] toolCards = boardElems[i].split("\\*");
        for(int j = 0; j<3; j++) {
            String[] toolCardsElems = toolCards[j].split("_");
            String cardName = toolCardsElems[0];
            int id = Integer.parseInt(toolCardsElems[1]);
            String description = toolCardsElems[2].replace("-", " ");
            int favor = Integer.parseInt(toolCardsElems[3]);
            board.addToolCard(cardName, description, id, favor);
        }
        i++;

        String[] publicCards = boardElems[i].split("\\*");
        for(int j = 0; j<3; j++) {
            String[] publicCardsElems = publicCards[j].split("_");
            String cardName = publicCardsElems[0];
            String description = publicCardsElems[1].replace("-", " ");
            int value = Integer.parseInt(publicCardsElems[2]);
            board.addPublicCard(cardName, description, value);
        }

        return new GameViewUpdateViewMessage(board);
    }

    public GameBoardClient getBoard(){
        return board;
    }
}
