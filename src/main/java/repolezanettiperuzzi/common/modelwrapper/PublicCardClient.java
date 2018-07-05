package repolezanettiperuzzi.common.modelwrapper;

import java.io.Serializable;

/**
 * Classe che modellizza le public card nel client
 * @author Andrea Zanetti
 * @author Giampiero Repole
 * @author Alessandro Peruzzi
 */
public class PublicCardClient implements Serializable{

    private String title;
    private String description;
    private int value;

    /**
     * Costruttore
     * @param title Titolo della carta
     * @param description Descrizione della carta
     * @param value Valore della carta
     */
    public PublicCardClient(String title, String description, int value){
        this.title = title;
        this.description = description;
        this.value = value;
    }

    /**
     *
     * @return Titolo
     */
    public String getTitle(){
        return this.title;
    }

    /**
     *
     * @return Descrizione
     */
    public String getDescription(){
        return this.description;
    }

    /**
     *
     * @return Valore
     */
    public int getValue(){
        return this.value;
    }
}
