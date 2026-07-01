package repolezanettiperuzzi.view;

import repolezanettiperuzzi.shared.dto.BoxClient;
import repolezanettiperuzzi.shared.dto.ColourClient;
import repolezanettiperuzzi.shared.dto.ValueClient;
import repolezanettiperuzzi.shared.dto.WindowClient;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Payload carried by the client socket chooseWindow message.
 */
class GameViewChooseWindowMessage {

    private final ArrayList<WindowClient> windows;
    private final int currentTime;

    private GameViewChooseWindowMessage(ArrayList<WindowClient> windows, int currentTime){
        this.windows=windows;
        this.currentTime=currentTime;
    }

    static GameViewChooseWindowMessage from(GameViewSocketMessage message){
        ArrayList<WindowClient> windows = new ArrayList<>();
        String[] tokens = message.getTokens();
        int i = 1;
        while(i < tokens.length-1){
            ArrayList<ArrayList<String>> boxesList = new ArrayList<>();
            String windowName = tokens[i];
            i++;
            int favorToken = Integer.parseInt(tokens[i]);
            i++;
            while(!tokens[i].equals("_")){
                boxesList.add(new ArrayList<>(Arrays.asList(tokens[i].split("-"))));
                i++;
            }
            windows.add(new WindowClient(windowName, favorToken, arrayListToMatrix(boxesList)));
            i++;
        }

        return new GameViewChooseWindowMessage(windows, Integer.parseInt(tokens[i]));
    }

    ArrayList<WindowClient> getWindows(){
        return new ArrayList<>(windows);
    }

    int getCurrentTime(){
        return currentTime;
    }

    private static BoxClient[][] arrayListToMatrix(ArrayList<ArrayList<String>> chosenWindows){
        int n = chosenWindows.size();
        int m = chosenWindows.get(0).size();
        BoxClient[][] boxMatrix = new BoxClient[n][m];
        for(int i = 0; i < n; i++){
            for(int j = 0; j < m; j++){
                switch (chosenWindows.get(i).get(j)){
                    case "Y":
                        boxMatrix[i][j] = new BoxClient(ColourClient.YELLOW);
                        break;
                    case "R":
                        boxMatrix[i][j] = new BoxClient(ColourClient.RED);
                        break;
                    case "P":
                        boxMatrix[i][j] = new BoxClient(ColourClient.PURPLE);
                        break;
                    case "G":
                        boxMatrix[i][j] = new BoxClient(ColourClient.GREEN);
                        break;
                    case "B":
                        boxMatrix[i][j] = new BoxClient(ColourClient.BLUE);
                        break;
                    case "0":
                        boxMatrix[i][j] = new BoxClient();
                        break;
                    case "1":
                        boxMatrix[i][j] = new BoxClient(ValueClient.ONE);
                        break;
                    case "2":
                        boxMatrix[i][j] = new BoxClient(ValueClient.TWO);
                        break;
                    case "3":
                        boxMatrix[i][j] = new BoxClient(ValueClient.THREE);
                        break;
                    case "4":
                        boxMatrix[i][j] = new BoxClient(ValueClient.FOUR);
                        break;
                    case "5":
                        boxMatrix[i][j] = new BoxClient(ValueClient.FIVE);
                        break;
                    case "6":
                        boxMatrix[i][j] = new BoxClient(ValueClient.SIX);
                        break;
                }
            }
        }
        return boxMatrix;
    }
}
