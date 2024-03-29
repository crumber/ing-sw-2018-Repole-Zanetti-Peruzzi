package repolezanettiperuzzi.model;

import repolezanettiperuzzi.common.DynamicPath;
import repolezanettiperuzzi.model.actions.InitializeGame;
import repolezanettiperuzzi.model.publiccards.FactoryPublicCard;
import repolezanettiperuzzi.model.publiccards.PublicCard;
import repolezanettiperuzzi.model.toolcards.FactoryToolCard;
import repolezanettiperuzzi.model.toolcards.ToolCard;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

/**
 * Classe che modellizza i mazzi di ToolCards e di PublicCards
 * @author Giampiero Repole
 * @author Alessandro Peruzzi
 * @author Andrea Zanetti
 */
public class Deck {

    private ArrayList<PublicCard> publicCardsDeck;
    private ArrayList<ToolCard> toolCardsDeck;

    int nPublicCards;
    int nToolCards;

    /**
     * Costruttore della classe
     * @param publicCardsFolder Percorso della cartella in cui si trovano i file che contengono
     *                          le informazioni sulle PublicCards
     * @param toolCardsFolder Percorso della cartella in cui si trovano i file che contengono
     *                        le informazioni sulle ToolCards
     * @throws IOException Se il file non viene correttamente aperto
     */
    public Deck(String publicCardsFolder, String toolCardsFolder) throws IOException{

        DynamicPath dP = new DynamicPath("");
        if(dP.isJar()) {
            URI publicUri = null;
            URI toolUri = null;
            try {
                publicUri = InitializeGame.class.getResource("/"+publicCardsFolder).toURI();
                toolUri = InitializeGame.class.getResource("/"+toolCardsFolder).toURI();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }

            FileSystem publicFileSystem = FileSystems.newFileSystem(publicUri, Collections.<String, Object>emptyMap());
            Path publicPath = publicFileSystem.getPath("/"+publicCardsFolder);
            Stream<Path> publicWalk = Files.walk(publicPath, 1);
            Iterator<Path> publicIt = publicWalk.iterator();

            int localNPublicCards = 0;
            publicCardsDeck = new ArrayList<>();

            publicIt.next();
            ArrayList<String> sortedPaths = sortPaths(publicIt);
            Collections.sort(sortedPaths.subList(0, sortedPaths.size()));

            for (int i = 0; i<sortedPaths.size(); i++) {

                BufferedReader bR = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(sortedPaths.get(i))));
                String title = bR.readLine();
                bR.readLine();
                int value = Integer.parseInt(bR.readLine());
                bR.readLine();
                publicCardsDeck.add(FactoryPublicCard.getPublicCard(bR.readLine()));
                bR.readLine();
                String description = bR.readLine();
                publicCardsDeck.get(i).setTitle(title);
                publicCardsDeck.get(i).setDescription(description);
                publicCardsDeck.get(i).setValue(value);
                bR.close();
                localNPublicCards++;
            }

            this.nPublicCards = localNPublicCards;

            publicFileSystem.close();
            publicWalk.close();

            FileSystem toolFileSystem = FileSystems.newFileSystem(toolUri, Collections.<String, Object>emptyMap());
            Path toolPath = toolFileSystem.getPath("/"+toolCardsFolder);
            Stream<Path> toolWalk = Files.walk(toolPath, 1);
            Iterator<Path> toolIt = toolWalk.iterator();

            int localNToolCards = 0;
            toolCardsDeck = new ArrayList<>();

            toolIt.next();
            sortedPaths = sortPaths(toolIt);
            Collections.sort(sortedPaths.subList(0, sortedPaths.size()));

            for (int i = 0; i<sortedPaths.size(); i++) {

                BufferedReader bR = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(sortedPaths.get(i))));
                String title = bR.readLine();
                String color = bR.readLine();
                bR.readLine();
                toolCardsDeck.add(FactoryToolCard.getToolCard(bR.readLine()));
                bR.readLine();
                String description = bR.readLine();
                toolCardsDeck.get(i).setTitle(title);
                toolCardsDeck.get(i).setDescription(description);
                bR.close();
                localNToolCards++;
            }


            this.nToolCards = localNToolCards;

            toolFileSystem.close();
            toolWalk.close();

        } else {
            nPublicCards = new File(new DynamicPath(publicCardsFolder).getPathNoFile()).list().length-1;    //-1, a differenza delle tool cards. sembra ci sia un file nascoto in piu' qui
            publicCardsDeck = new ArrayList<>(nPublicCards);

            String addZero;

            for (int i = 0; i < nPublicCards; i++) {
                addZero = i<9 ? "0" : "";
                ArrayList<String> lines = (ArrayList<String>) Files.readAllLines(Paths.get(new DynamicPath(publicCardsFolder + "/pc" + addZero+(i + 1) + ".txt").getPathNoFile()));
                publicCardsDeck.add(FactoryPublicCard.getPublicCard(lines.get(4)));
                publicCardsDeck.get(i).setTitle(lines.get(0));
                publicCardsDeck.get(i).setDescription(lines.get(6));
                publicCardsDeck.get(i).setValue(Integer.parseInt(lines.get(2)));
            }

            nToolCards = new File(new DynamicPath(toolCardsFolder).getPathNoFile()).list().length;
            toolCardsDeck = new ArrayList<>(nToolCards);

            for (int i = 0; i < nToolCards; i++) {
                addZero = i<9 ? "0" : "";
                ArrayList<String> lines = (ArrayList<String>) Files.readAllLines(Paths.get(new DynamicPath(toolCardsFolder + "/tc" + addZero+(i + 1) + ".txt").getPathNoFile()));
                toolCardsDeck.add(FactoryToolCard.getToolCard(lines.get(3)));
                toolCardsDeck.get(i).setTitle(lines.get(0));
                toolCardsDeck.get(i).setDescription(lines.get(5));
            }
        }

    }

    /**
     * @return Mazzo di PublicCards
     */
    public List<PublicCard> getPublicCardsDeck(){

        return (ArrayList<PublicCard>) publicCardsDeck.clone();

    }

    /**
     * Pesca casualmente una PublicCard dal mazzo
     * @return PublicCard pescata
     */
    public PublicCard drawPublicCard(){
        //shuffle cards and take first card in the ArrayList
        Collections.shuffle(this.publicCardsDeck);
        return this.publicCardsDeck.remove(0);

    }

    /**
     * Pesca casualmente una ToolCard dal mazzo
     * @return ToolCard pescata
     */
    public ToolCard drawToolCard() {
        //mischia le carte e pesca la prima dell'arrayList rimuovendola
        Collections.shuffle(this.toolCardsDeck);
        return this.toolCardsDeck.remove(0);

    }

    /**
     * Serve un metodo per ordinare perche' i file che leggo dalla cartella sono in ordine decrescente
     * @param it Iteratore sui path
     * @return Lista di percorsi ordinati in ordine crescente
     */
    public ArrayList<String> sortPaths(Iterator<Path> it){
        ArrayList<String> pathsList = new ArrayList<>();
        while(it.hasNext()){
            String path = it.next().toString();
            pathsList.add(path);
        }
        return pathsList;
    }
}
