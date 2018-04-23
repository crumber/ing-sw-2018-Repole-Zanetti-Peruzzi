package RepoleZanettiPeruzzi.Model;

import java.util.ArrayList;
import java.util.Random;

public class DiceBag {

    private ArrayList<Die> diceBag=new ArrayList<Die>(90);

    public DiceBag(){

        for(int i=0;i<=17;i++){

            diceBag.add(new Die(Colour.RED));
            diceBag.add(new Die(Colour.YELLOW));
            diceBag.add(new Die(Colour.PURPLE));
            diceBag.add(new Die(Colour.GREEN));
            diceBag.add(new Die(Colour.BLUE));

        }
    }

    public ArrayList<Die> takeDice(int numDice){

        ArrayList<Die> diceTaken= new ArrayList<>();
        int max=diceBag.size();

        for(int i=0;i<numDice;i++) {

            Random random= new Random();
            int choose= random.nextInt(max); //return int between 0 and size
            Die chooseDie= diceBag.get(choose);
            diceTaken.add(chooseDie);
            diceBag.remove(choose);
            max--;

        }

        return diceTaken;

    }

    public ArrayList<Die> getDiceBag() {

        return diceBag;

    }
}