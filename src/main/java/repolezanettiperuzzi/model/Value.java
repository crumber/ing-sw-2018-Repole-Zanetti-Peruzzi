package repolezanettiperuzzi.model;

import java.util.Random;

/**
 * Classe che modellizza i valori assunti da un dado
 * @author Alessandro Peruzzi
 * @author Giampiero Repole
 */
public enum Value {

    ONE(1),
    TWO(2),
    THREE(3),
    FOUR(4),
    FIVE(5),
    SIX(6);

    private int value;
    public static final int VALUEMAX=6;

    /**
     * Costruttore della classe
     * @param value valore scelto
     */
    Value(int value){

        this.value=value;

    }

    /**
     * Trasforma un intero nel Value corrispondente
     * @param val intero che si vuole trasformare
     * @return Value corrispondente all'intero
     */
    public static Value intToValue(int val){

        Value valueNum;

        if (val==1) {

            valueNum = ONE;

        }else if(val==2) {

            valueNum = TWO;

        }else if(val==3) {

            valueNum = THREE;

        }else if(val==4) {

            valueNum = FOUR;

        }else if(val==5) {

            valueNum = FIVE;

        }else{

            valueNum=SIX;

        }

        return valueNum;
    }

    /**
     * @return intero corrispondente al Value
     */
    public int getNumber() {

        return value;

    }

    /**
     * @return Value scelto randomicamente
     */
    public Value randomValue() {

        Random random= new Random();
        int numRandom= random.nextInt(VALUEMAX);

        return intToValue(numRandom);

    }

}