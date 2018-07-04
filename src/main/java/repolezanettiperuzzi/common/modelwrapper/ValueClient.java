package repolezanettiperuzzi.common.modelwrapper;

import java.io.Serializable;
import java.util.Random;

/**
 * Classe che modellizza il valore del dado nel client
 * @author Andrea Zanetti
 * @author Giampiero Repole
 * @author Alessandro Peruzzi
 */
public enum ValueClient implements Serializable{

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
     * @param value Valore scelto
     */
    private ValueClient(int value){

        this.value=value;

    }

    /**
     * Trasforma un intero nel Value corrispondente
     * @param val Intero che si vuole trasformare
     * @return Value corrispondente all'intero
     */
    public static ValueClient intToValue(int val){

        ValueClient valueNum;

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
     * @return Intero corrispondente al Value
     */
    public int getNumber() {

        return value;

    }

    /**
     * @return Value scelto randomicamente
     */
    public ValueClient randomValue() {

        Random random= new Random();
        int numRandom= random.nextInt(VALUEMAX);

        return intToValue(numRandom);

    }

}