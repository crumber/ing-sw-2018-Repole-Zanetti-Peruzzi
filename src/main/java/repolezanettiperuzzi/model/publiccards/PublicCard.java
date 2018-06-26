package repolezanettiperuzzi.model.publiccards;

import repolezanettiperuzzi.model.Window;

public abstract class PublicCard {

    private String description;
    private String title;
    private int value;

    public abstract int effect(Window finalWindow);

    public void setDescription(String description){
        this.description = description;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public void setValue(int value){
        this.value = value;
    }

    public String getDescription(){
        return this.description;
    }

    public String getTitle(){
        return this.title;
    }

    public int getValue(){
        return this.value;
    }

}
