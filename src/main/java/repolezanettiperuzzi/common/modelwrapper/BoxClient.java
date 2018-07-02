package repolezanettiperuzzi.common.modelwrapper;

import java.io.Serializable;

public class BoxClient implements Serializable {

    protected DieClient die;
    private final ColourClient BOUNDCOLOUR ;
    private final ValueClient BOUNDVALUE ;

    public BoxClient(){
        this.BOUNDCOLOUR=null;
        this.BOUNDVALUE=null;
    }

    public BoxClient(ColourClient c) {
        this.BOUNDCOLOUR = c;
        this.BOUNDVALUE = null;
    }

    public BoxClient(ValueClient v){
        this.BOUNDVALUE=v;
        this.BOUNDCOLOUR=null;
    }

    public ColourClient getBoundColour(){

        return this.BOUNDCOLOUR;

    }

    public ValueClient getBoundValue(){

        return this.BOUNDVALUE;

    }

    public String toString(){
        if(this.BOUNDCOLOUR!=null){
            return this.BOUNDCOLOUR.toString();
        } else if(this.BOUNDVALUE!=null){
            return this.BOUNDVALUE.getNumber()+"";
        } else {
            return "0";
        }
    }

    public void setDieNoRestricion(DieClient die){
        this.die = die;
    }

    public boolean setDie(DieClient dice,String restriction){

        if(restriction.compareTo("both")==0){

            if(controlBounds(dice)){

                this.die=dice;
                return true;
            }

        }else if(restriction.compareTo("none")==0){

                this.die=dice;
                return true;


        }else if(restriction.compareTo("value")==0){

            if(controlValue(dice)){

                this.die=dice;
                return true;

            }

        }else if(restriction.compareTo("colour")==0){

           if(controlColour(dice)){

               this.die=dice;
               return true;

           }
        }

        return false;

    }

    public DieClient removeDie() {

        DieClient removed= die;
        this.die=null;
        return removed;

    }

    public boolean controlBounds(DieClient dice){

        return ((this.getBoundColour() == null) && (this.getBoundValue() == null)) ||
                ((null != this.getBoundValue()) && (this.getBoundValue().equals(dice.getValueDie()))) ||
                ((null != this.getBoundColour()) && (this.getBoundColour().equals(dice.getColourDie())));

    }

    // if there aren't bound value or value dice is equals bound value return true else return false
    public boolean controlValue(DieClient dice){

        return ((this.getBoundValue() == null)) || (this.getBoundValue().equals(dice.getValueDie()));

    }

    // if there aren't bound colour or colour dice is equals bound colour return true else return false
    public boolean controlColour(DieClient dice){

        return (this.getBoundColour() == null) || (this.getBoundColour().equals(dice.getColourDie()));

    }

}