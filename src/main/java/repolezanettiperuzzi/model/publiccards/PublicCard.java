package repolezanettiperuzzi.model.publiccards;

import repolezanettiperuzzi.model.Window;

/**
 * Classe astratta che rappresenta una generica public card
 * @author Alessandro Peruzzi
 */
public abstract class PublicCard {

    private String description;
    private String title;
    private int value;

    /**
     *
     * @param finalWindow window su cui controllare il punteggio assegnato dalla public card
     * @return ritorna il punteggio calcolato sulla window passata
     */
    public abstract int effect(Window finalWindow);

    /**
     * imposta la descrizione
     * @param description Stringa che rappresenta la descrizione della carta
     */
    public void setDescription(String description){
        this.description = description;
    }

    /**
     * imposta il titolo
     * @param title Stringa che rappresenta il titolo della carta
     */
    public void setTitle(String title){
        this.title = title;
    }

    /**
     * imposta il valore della carta
     * @param value punteggio che assegna la carta se la window rispetta le condizioni descritte
     */
    public void setValue(int value){
        this.value = value;
    }

    /**
     *
     * @return la descrizione della carta
     */
    public String getDescription(){
        return this.description;
    }

    /**
     *
     * @return il titolo della carta
     */
    public String getTitle(){
        return this.title;
    }

    /**
     *
     * @return il valore della carta
     */
    public int getValue(){
        return this.value;
    }

}
