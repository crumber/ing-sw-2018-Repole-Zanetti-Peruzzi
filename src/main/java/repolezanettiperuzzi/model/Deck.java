package repolezanettiperuzzi.model;

import repolezanettiperuzzi.model.publiccards.FactoryPublicCard;
import repolezanettiperuzzi.model.publiccards.MediumShades;
import repolezanettiperuzzi.model.publiccards.PublicCard;
import repolezanettiperuzzi.model.toolcards.CopperFoilBurnisher;
import repolezanettiperuzzi.model.toolcards.ToolCard;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Deck {

    private ArrayList<PublicCard> publicCardsDeck;
    int nCards;

    public Deck(String publicCardsFolder, String toolCardsFolder) throws IOException{
        //faccio -1 perche' conta anche il file .DS_Store

        nCards = new File(publicCardsFolder).list().length-1;
        publicCardsDeck = new ArrayList<PublicCard>(nCards);

        for(int i = 0; i < nCards; i++){
            ArrayList<String> lines = (ArrayList<String>) Files.readAllLines(Paths.get(Paths.get(publicCardsFolder + "/pc" + (i + 1)) + ".txt"));
            publicCardsDeck.add(FactoryPublicCard.getPublicCard(lines.get(2)));
        }

    }

    public ArrayList<PublicCard> getPublicCardsDeck(){
        return (ArrayList<PublicCard>) publicCardsDeck.clone();
    }

    public PublicCard drawPublicCard(){
        return new MediumShades();
    }

    public ToolCard drawToolCard() {
        return new CopperFoilBurnisher();
    }

}
