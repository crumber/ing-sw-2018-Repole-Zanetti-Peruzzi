package repolezanettiperuzzi.common.modelwrapper;

import java.io.Serializable;

/**
 * Classe che definisce una casella della window lato client
 * @author Giampiero Repole
 * @author Alessandro Peruzzi
 * @author Andrea Zanetti
 */
public class BoxClient implements Serializable {

    protected DieClient die;
    private final ColourClient BOUNDCOLOUR ;
    private final ValueClient BOUNDVALUE ;

    /**
     * Costruttore della classe per creare una casella senza vincoli
     */
    public BoxClient(){
        this.BOUNDCOLOUR=null;
        this.BOUNDVALUE=null;
    }

    /**
     * Costruttore della classe per creare una casella con vincolo di colore
     * @param c vincolo di colore
     */
    public BoxClient(ColourClient c) {
        this.BOUNDCOLOUR = c;
        this.BOUNDVALUE = null;
    }

    /**
     * Costruttore della classe per creare una casella con vincolo di valore
     * @param v vincolo di valore
     */
    public BoxClient(ValueClient v){
        this.BOUNDVALUE=v;
        this.BOUNDCOLOUR=null;
    }

    /**
     * @return Vincolo di colore della casella
     */
    public ColourClient getBoundColour(){

        return this.BOUNDCOLOUR;

    }

    /**
     * @return Vincolo di valore della casella
     */
    public ValueClient getBoundValue(){

        return this.BOUNDVALUE;

    }

    /**
     *
     * @return La stringa che rappresenta la box
     */
    public String toString(){
        if(this.BOUNDCOLOUR!=null){
            return this.BOUNDCOLOUR.toString();
        } else if(this.BOUNDVALUE!=null){
            return this.BOUNDVALUE.getNumber()+"";
        } else {
            return "0";
        }
    }

    /**
     * Inizializza il dado della box
     * @param die Dado
     */
    public void setDieNoRestricion(DieClient die){
        this.die = die;
    }

    /**
     * Inserisce un dado nella casella
     * @param dice Dado da inserire
     * @param restriction Tipo di restrizione
     * @return Vero se il dado viene inserito correttamente, falso altrimenti
     */
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

    /**
     * Rimuove il dado dalla casella
     * @return Dado rimosso dalla casella
     */
    public DieClient removeDie() {

        DieClient removed= die;
        this.die=null;
        return removed;

    }

    /**
     * Controlla che il dado da inserire rispetti i vincoli della casella
     * @param dice Dado da verificare
     * @return Vero se rispetta i vincoli, falso altrimenti
     */
    public boolean controlBounds(DieClient dice){

        return ((this.getBoundColour() == null) && (this.getBoundValue() == null)) ||
                ((null != this.getBoundValue()) && (this.getBoundValue().equals(dice.getValueDie()))) ||
                ((null != this.getBoundColour()) && (this.getBoundColour().equals(dice.getColourDie())));

    }

    /**
     * Controlla soltanto il vincolo di valore
     * @param dice Dado da verificare
     * @return Vero se la casella non ha vincoli o se il dado ne rispetta i vincoli, falso altrimenti
     */
    // if there aren't bound value or value dice is equals bound value return true else return false
    public boolean controlValue(DieClient dice){

        return ((this.getBoundValue() == null)) || (this.getBoundValue().equals(dice.getValueDie()));

    }

    /**
     * Controlla soltanto il vincolo di colore
     * @param dice Dado da verificare
     * @return Vero se la casella non ha vincoli o se il dado ne rispetta i vincoli, falso altrimenti
     */
    // if there aren't bound colour or colour dice is equals bound colour return true else return false
    public boolean controlColour(DieClient dice){

        return (this.getBoundColour() == null) || (this.getBoundColour().equals(dice.getColourDie()));

    }

    /**
     *
     * @return True se è presente il dado nella box sennò false
     */
    public boolean thereIsDie(){

        return this.die != null;

    }

    /**
     *
     * @return Il dado presente nella box
     */
    public DieClient getDie(){

        return this.die;
    }

}