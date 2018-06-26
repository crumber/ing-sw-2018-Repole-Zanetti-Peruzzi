package repolezanettiperuzzi.common.modelwrapper;

public class ToolCardClient {
    private String title;
    private String description;

    public ToolCardClient(String title, String description){
        this.title = title;
        this.description = title;
    }

    public String getTitle(){
        return this.title;
    }

    public String getDescription(){
        return this.description;
    }
}
