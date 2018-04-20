package RepoleZanettiPeruzzi.Model;

import java.util.Random;

public enum Value {
    ONE(1),
    TWO(2),
    THREE(3),
    FOUR(4),
    FIVE(5),
    SIX(6);

    private int value;
    public static final int valueMax=6;

     private Value(int value){
        this.value=value;
    }

    public int getNumber() {
        return value;
    }

    public Value randomValue() {
        Random random= new Random();
        Value realValue;
        int numRandom= random.nextInt(valueMax);

        switch (numRandom) {
            case 1:
                realValue = ONE;
                break;
            case 2:
                realValue = TWO;
                break;
            case 3:
                realValue = THREE;
                break;
            case 4:
                realValue = FOUR;
                break;
            case 5:
                realValue = FIVE;
                break;
            default:
                realValue = SIX;
                break;
        }
        return realValue;
    }
}

