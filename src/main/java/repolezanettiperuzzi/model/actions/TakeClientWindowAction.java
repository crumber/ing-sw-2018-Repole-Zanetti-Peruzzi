package repolezanettiperuzzi.model.actions;

import repolezanettiperuzzi.model.Window;

import java.util.ArrayList;

public class TakeClientWindowAction {

    public Window doAction(ArrayList<Window> clientWindows, String numWindow){

        String[] clientAnswersArray= numWindow.split(" ");
        int whichWindow=Integer.parseInt(clientAnswersArray[0]);

        return clientWindows.get(whichWindow);
    }
}
