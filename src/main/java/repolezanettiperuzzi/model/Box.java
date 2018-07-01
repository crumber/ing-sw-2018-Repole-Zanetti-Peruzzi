package repolezanettiperuzzi.model;

/**
 * Classe che definisce una casella della window
 * @author Giampiero Repole
 * @author Alessandro Peruzzi
 * @author Andrea Zanetti
 */
public class Box {

    protected Die die;
    private final Colour BOUNDCOLOUR ;
    private final Value BOUNDVALUE ;

    /**
     * Costruttore della classe per creare una casella senza vincoli
     */
    public Box (){
        this.BOUNDCOLOUR=null;
        this.BOUNDVALUE=null;
    }

    /**
     * Costruttore della classe per creare una casella con vincolo di colore
     * @param c vincolo di colore
     */
    public Box(Colour c) {
        this.BOUNDCOLOUR = c;
        this.BOUNDVALUE = null;
    }

    /**
     * Costruttore della classe per creare una casella con vincolo di valore
     * @param v vincolo di valore
     */
    public Box(Value v){
        this.BOUNDVALUE=v;
        this.BOUNDCOLOUR=null;
    }

    /**
     * @return vincolo di colore della casella
     */
    public Colour getBoundColour(){

        return this.BOUNDCOLOUR;

    }

    /**
     * @return vincolo di valore della casella
     */
    public Value getBoundValue(){

        return this.BOUNDVALUE;

    }

    /**
     * Inserisce un dado nella casella
     * @param dice dado da inserire
     * @param restriction tipo di restrizione
     * @return vero se il dado viene inserito correttamente, falso altrimenti
     */
    public boolean setDie(Die dice,String restriction){

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

    /**
     * Rimuove il dado dalla casella
     * @return dado rimosso dalla casella
     */
    public Die removeDie() {

        Die removed= die;
        this.die=null;
        return removed;

    }

    /**
     * Controlla che il dado da inserire rispetti i vincoli della casella
     * @param dice dado da verificare
     * @return vero se rispetta i vincoli, falso altrimenti
     */
    public boolean controlBounds(Die dice){

        return ((this.getBoundColour() == null) && (this.getBoundValue() == null)) ||
                ((null != this.getBoundValue()) && (this.getBoundValue().equals(dice.getValueDie()))) ||
                ((null != this.getBoundColour()) && (this.getBoundColour().equals(dice.getColourDie())));

    }

    /**
     * Controlla soltanto il vincolo di valore
     * @param dice dado da verificare
     * @return vero se la casella non ha vincoli o se il dado ne rispetta i vincoli, falso altrimenti
     */
    public boolean controlValue(Die dice){

        return ((this.getBoundValue() == null)) || (this.getBoundValue().equals(dice.getValueDie()));

    }

    /**
     * Controlla soltanto il vincolo di colore
     * @param dice dado da verificare
     * @return vero se la casella non ha vincoli o se il dado ne rispetta i vincoli, falso altrimenti
     */
    public boolean controlColour(Die dice){

        return (this.getBoundColour() == null) || (this.getBoundColour().equals(dice.getColourDie()));

    }

    @Override
    public String toString(){
        String res = "";

        if(this.die==null) {

            if (getBoundColour() != null) {
                switch (getBoundColour()) {
                    case YELLOW:
                        res = "Y";
                        break;
                    case RED:
                        res = "R";
                        break;
                    case BLUE:
                        res = "B";
                        break;
                    case PURPLE:
                        res = "P";
                        break;
                    default:
                        res = "G";
                        break;
                }
            } else if (getBoundValue() != null) {
                res = getBoundValue().getNumber() + "";
            } else {
                res = "0";
            }

        }else{

           res = die.toString();

        }

        return res;
    }


}