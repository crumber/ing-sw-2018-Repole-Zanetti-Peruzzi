package repolezanettiperuzzi.model;

public class Box {

    private Die die;
    private final Colour BOUNDCOLOUR ;
    private final Value BOUNDVALUE ;

    public Box (){
        this.BOUNDCOLOUR=null;
        this.BOUNDVALUE=null;
    }

    public Box(Colour c) {
        this.BOUNDCOLOUR = c;
        this.BOUNDVALUE = null;
    }

    public Box(Value v){
        this.BOUNDVALUE=v;
        this.BOUNDCOLOUR=null;
    }

    public Colour getBoundColour(){

        return this.BOUNDCOLOUR;

    }

    public Value getBoundValue(){

        return this.BOUNDVALUE;

    }

    public void setDie(Die dice){

        if(((this.getBoundColour()==null)&&(this.getBoundValue()==null))||
                ((null != this.getBoundValue())&&(this.getBoundValue().equals(dice.getValueDie())))||
                ((null != this.getBoundColour())&&(this.getBoundColour().equals(dice.getColourDie())))) {

            this.die = dice;
            }
    }

    public Die removeDie() {

        Die removed= die;
        this.die=null;
        return removed;

    }
}