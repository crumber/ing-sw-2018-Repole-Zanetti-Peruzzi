package repolezanettiperuzzi.common.modelwrapper;

public class PublicCardClient {

    private String title;
    private String description;
    private int value;

    public PublicCardClient(String title, String description, int value){
        this.title = title;
        this.description = description;
        this.value = value;
    }

    public String getTitle(){
        return this.title;
    }

    public String getDescription(){
        return this.description;
    }

    public int getValue(){
        return this.value;
    }
}
