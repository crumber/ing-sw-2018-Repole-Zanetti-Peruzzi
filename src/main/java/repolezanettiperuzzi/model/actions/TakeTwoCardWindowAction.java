package repolezanettiperuzzi.model.actions;

import repolezanettiperuzzi.model.Window;

import java.util.ArrayList;
import java.util.Random;

public class TakeTwoCardWindowAction {

    public ArrayList<Window> doAction(ArrayList<Window> windows){

        ArrayList<Window> clientWindows=new ArrayList<>();
        Random random= new Random();

        for(int i=0;i<2;i++) {

            int which = random.nextInt(windows.size()); //return int between 0 and size

            clientWindows.add(windows.get(which));
            if (which % 2 == 0) {

                clientWindows.add(windows.get(which + 1));
                windows.remove(which + 1);
                windows.remove(which);

            } else {

                clientWindows.add(windows.get(which - 1));
                windows.remove(which);
                windows.remove(which - 1);

            }
        }

        return clientWindows;
    }
}
