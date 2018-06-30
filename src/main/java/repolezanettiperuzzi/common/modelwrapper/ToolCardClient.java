package repolezanettiperuzzi.common.modelwrapper;

public class ToolCardClient {
    private String title;
    private String description;
    private int id;
    private int favorTokens;

    public ToolCardClient(String title, String description, int id, int favorTokens){
        this.title = title;
        this.description = description;
        this.id = id;
        this.favorTokens = favorTokens;
    }

    public String getTitle(){
        return this.title;
    }

    public String getDescription(){
        return this.description;
    }

    public int getId(){
        return this.id;
    }

    public int getFavorTokens(){
        return this.favorTokens;
    }
}
