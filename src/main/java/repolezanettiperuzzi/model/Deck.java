package repolezanettiperuzzi.model;

import repolezanettiperuzzi.model.publiccards.FactoryPublicCard;
import repolezanettiperuzzi.model.publiccards.MediumShades;
import repolezanettiperuzzi.model.publiccards.PublicCard;
import repolezanettiperuzzi.model.toolcards.CopperFoilBurnisher;
import repolezanettiperuzzi.model.toolcards.FactoryToolCard;
import repolezanettiperuzzi.model.toolcards.ToolCard;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;


public class Deck {

    private ArrayList<PublicCard> publicCardsDeck;
    private ArrayList<ToolCard> toolCardsDeck;

    int nPublicCards;
    int nToolCards;

    public Deck(String publicCardsFolder, String toolCardsFolder) throws IOException{
        //faccio -1 perche' conta anche il file .DS_Store

        nPublicCards = new File(publicCardsFolder).list().length-1;
        publicCardsDeck = new ArrayList<>(nPublicCards);

        for(int i = 0; i < nPublicCards; i++){
            ArrayList<String> lines = (ArrayList<String>) Files.readAllLines(Paths.get(publicCardsFolder + "/pc" + (i + 1) + ".txt"));
            publicCardsDeck.add(FactoryPublicCard.getPublicCard(lines.get(2)));
        }

        nToolCards = new File(toolCardsFolder).list().length-1;
        toolCardsDeck = new ArrayList<>(nToolCards);

        for( int i=0 ; i<nToolCards; i++){
            ArrayList<String> lines = (ArrayList<String>) Files.readAllLines(Paths.get(toolCardsFolder + "/tc" + (i + 1) + ".txt"));
            toolCardsDeck.add(FactoryToolCard.getToolCard(lines.get(3)));
        }

    }

    public ArrayList<PublicCard> getPublicCardsDeck(){

        return (ArrayList<PublicCard>) publicCardsDeck.clone();

    }

    public PublicCard drawPublicCard(){
        //shuffle cards and take first card in the ArrayList
        Collections.shuffle(this.publicCardsDeck);
        return this.publicCardsDeck.remove(0);

    }

    public ToolCard drawToolCard() {
        //mischia le carte e pesca la prima dell'arrayList rimuovendola
        Collections.shuffle(this.toolCardsDeck);
        return this.toolCardsDeck.remove(0);

    }
}
