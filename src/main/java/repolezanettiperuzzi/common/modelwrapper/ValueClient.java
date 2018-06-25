package repolezanettiperuzzi.common.modelwrapper;

import java.io.Serializable;
import java.util.Random;

public enum ValueClient implements Serializable{

    ONE(1),
    TWO(2),
    THREE(3),
    FOUR(4),
    FIVE(5),
    SIX(6);

    private int value;
    public static final int VALUEMAX=6;

    private ValueClient(int value){

        this.value=value;

    }

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

    public int getNumber() {

        return value;

    }

    public ValueClient randomValue() {

        Random random= new Random();
        int numRandom= random.nextInt(VALUEMAX);

        return intToValue(numRandom);

    }

}