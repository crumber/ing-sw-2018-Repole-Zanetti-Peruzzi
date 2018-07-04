package repolezanettiperuzzi.common.modelwrapper;

/**
 * Classe che modellizza le tool card nel client
 * @author Andrea Zanetti
 * @author Giampiero Repole
 * @author Alessandro Peruzzi
 */
public class ToolCardClient {
    private String title;
    private String description;
    private int id;
    private int favorTokens;

    /**
     * Costruttore
     * @param title Titolo della carta
     * @param description Descrizione della carta
     * @param id Id della carta
     * @param favorTokens Numero favor token della carta, indica il costo di attivazione
     */
    public ToolCardClient(String title, String description, int id, int favorTokens){
        this.title = title;
        this.description = description;
        this.id = id;
        this.favorTokens = favorTokens;
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
     * @return Id
     */
    public int getId(){
        return this.id;
    }

    /**
     *
     * @return Favor token, cioè il costo
     */
    public int getFavorTokens(){
        return this.favorTokens;
    }
}
