package repolezanettiperuzzi.view;

import repolezanettiperuzzi.shared.dto.BoxClient;
import repolezanettiperuzzi.shared.dto.ColourClient;
import repolezanettiperuzzi.shared.dto.ValueClient;
import repolezanettiperuzzi.shared.dto.WindowClient;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Parser for serialized client socket window payloads.
 */
class GameViewWindowPayloadParser {

    private GameViewWindowPayloadParser(){
    }

    static ParsedWindow parseWindow(String[] tokens, int startIndex){
        ArrayList<ArrayList<String>> boxesList = new ArrayList<>();
        int i = startIndex;
        String windowName = tokens[i];
        i++;
        int favorToken = Integer.parseInt(tokens[i]);
        i++;
        while(!tokens[i].equals("_")){
            boxesList.add(new ArrayList<>(Arrays.asList(tokens[i].split("-"))));
            i++;
        }

        return new ParsedWindow(new WindowClient(windowName, favorToken, arrayListToMatrix(boxesList)), i+1);
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

    static class ParsedWindow {

        private final WindowClient window;
        private final int nextIndex;

        private ParsedWindow(WindowClient window, int nextIndex){
            this.window=window;
            this.nextIndex=nextIndex;
        }

        WindowClient getWindow(){
            return window;
        }

        int getNextIndex(){
            return nextIndex;
        }
    }
}
