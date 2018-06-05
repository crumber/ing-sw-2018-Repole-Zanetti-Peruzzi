package repolezanettiperuzzi.controller;

import org.json.simple.JSONArray;
import org.json.simple.parser.ParseException;

import java.io.FileWriter;
import java.io.IOException;

public class ResetGameState extends ControllerState{
    @Override
    public void doAction(Controller controller) {
        cleanJson("gamedata/playersinfo.json");
    }

    //method to clean the json file every time the game restarts
    private void cleanJson(String jsonPath){
        JSONArray jsonArr = new JSONArray();
        try (FileWriter file = new FileWriter(jsonPath)) {
            file.write(jsonArr.toJSONString());
            file.close();
        } catch (IOException e) {
            System.out.println("Cannot write on file");
        }
    }
}
