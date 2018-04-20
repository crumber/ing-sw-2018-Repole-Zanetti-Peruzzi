package RepoleZanettiPeruzzi.Model;

import java.util.ArrayList;
import java.util.Random;

public class DiceBag {

    private ArrayList<Die> diceBag=new ArrayList<Die>();

    public DiceBag(){
        Die redDie= new Die(Colour.RED);
        Die yellowDie= new Die(Colour.YELLOW);
        Die purpleDie= new Die(Colour.PURPLE);
        Die greenDie= new Die(Colour.GREEN);
        Die blueDie= new Die(Colour.BLUE);

        for(int i=0;i<90;i++){
            if(i<=17) diceBag.add(redDie);
            if(i>17 && i<=35) diceBag.add(yellowDie);
            if(i>35 && i<=53) diceBag.add(purpleDie);
            if(i>53 && i<=71) diceBag.add(greenDie);
            if(i>71 && i<=89) diceBag.add(blueDie);
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


