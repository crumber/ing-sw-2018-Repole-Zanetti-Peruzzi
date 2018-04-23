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

    public Die takeDie(){

        int max=diceBag.size();
        Random random= new Random();
        int choose= random.nextInt(max); //return int between 0 and size

        Die chooseDie= diceBag.get(choose);
        diceBag.remove(choose);
        return chooseDie;

    }

    public ArrayList<Die> getDiceBag() {

        return diceBag;

    }
}