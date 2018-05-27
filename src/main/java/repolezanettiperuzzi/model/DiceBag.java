package repolezanettiperuzzi.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DiceBag {

    private ArrayList<Die> bag = new ArrayList<>(90);
    private int size;

    public DiceBag(){

        for(int i=0;i<=17;i++){

            bag.add(new Die(Colour.RED));
            bag.add(new Die(Colour.YELLOW));
            bag.add(new Die(Colour.PURPLE));
            bag.add(new Die(Colour.GREEN));
            bag.add(new Die(Colour.BLUE));
        }

        this.size=90;
    }

    public List<Die> takeDice(int numDice){

        ArrayList<Die> diceTaken= new ArrayList<>();
        int max=bag.size();

        for(int i=0;i<numDice;i++) {

            Random random= new Random();
            int choose= random.nextInt(max); //return int between 0 and size
            Die chosenDie= bag.get(choose);
            diceTaken.add(chosenDie);
            bag.remove(choose);
            max--;
            this.size--;
        }

        return diceTaken;

    }

    public Die takeDie(){

        int max=bag.size();
        Random random= new Random();

        int choose= random.nextInt(max); //return int between 0 and size
        Die chosenDie= bag.get(choose);
        bag.remove(choose);
        this.size--;

        return chosenDie;

    }

    public void setDieInBag(Die d) {

        bag.add(d);
        this.size++;

    }

    public int getSize(){

        return this.size;
    }
}