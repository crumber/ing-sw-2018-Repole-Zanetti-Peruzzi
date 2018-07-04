package repolezanettiperuzzi.model.actions;

import repolezanettiperuzzi.common.DynamicPath;
import repolezanettiperuzzi.model.*;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.*;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.logging.*;
import java.util.stream.Stream;

/**
 * Classe che rappresenta l'inizializzazione del gioco
 * @author Giampiero Repole
 */
public class InitializeGame{

    private ArrayList<Colour> privateObjective;
    private ArrayList<Window> windows;
    private Box[][] board;
    private final Logger LOGGER = Logger.getLogger(InitializeGame.class.getName());

    /**
     * costruttore
     */
    public InitializeGame(){

        this.privateObjective = new ArrayList<>(5);
        this.privateObjective.add(0,Colour.RED);
        this.privateObjective.add(1,Colour.BLUE);
        this.privateObjective.add(2,Colour.YELLOW);
        this.privateObjective.add(3,Colour.GREEN);
        this.privateObjective.add(4,Colour.PURPLE);
        this.windows = new ArrayList<>();
        this.board = new Box[4][5];

    }

    /**
     * inizializza il deck delle public e delle tool cards
     * @param board game board
     * @throws IOException sintassi errata nella creazione delle windows da file dinamico
     */
    public void doAction(GameBoard board) throws IOException {

        //assign private objective for each Player
        this.assignPrivateObjective(board);

        //create windows from files dynamically
        try {
            this.createWindows();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        //initialize deck with tool cards and public cards
        Deck startDeck = null;
        try {
            startDeck = new Deck("cards/publiccards", "cards/toolcards");

        } catch (IOException e) {

            LOGGER.log(Level.WARNING,"IOException: ", e);

        }


        for(int i = 0; i < 3; i++){

            board.setPublicCards(Objects.requireNonNull(startDeck).drawPublicCard(),i);

        }

        for(int i = 0; i < 3; i++){

            board.setToolCards(Objects.requireNonNull(startDeck).drawToolCard(),i);

        }

    }


    /**
     * assegna i colori segreti ai vari giocatori
     * @param board game board
     */
    private void assignPrivateObjective(GameBoard board){

        Collections.shuffle(this.privateObjective);

        for(int i = 0 ; i < board.getNPlayers(); i++){

            Player p = board.getPlayer(i);
            p.setSecretColour(this.privateObjective.remove(0));

        }

    }

    /**
     * crea le windows usando i metodi createWindowsDebug e createdWindowsJar
     * @throws IOException
     * @throws URISyntaxException
     */
    private void createWindows() throws IOException, URISyntaxException {

        DynamicPath dP = new DynamicPath("");

        Path myPath;

        FileSystem fileSystem = null;
        if (dP.isJar()) {
            URI uri = InitializeGame.class.getResource("/cards/gamemaps").toURI();
            fileSystem = FileSystems.newFileSystem(uri, Collections.<String, Object>emptyMap());
            myPath = fileSystem.getPath("/cards/gamemaps");
        } else {
            myPath = Paths.get(new URI(new DynamicPath("/cards/gamemaps").getPath()));
        }

        Stream<Path> walk = Files.walk(myPath, 1);

        Iterator<Path> it = walk.iterator();
        it.next(); //Salto il primo elemento nella cartella che rappresenta la cartella stessa
        ArrayList<String> sortedPaths = sortPaths(it);
        Collections.sort(sortedPaths.subList(0, sortedPaths.size()));
        for (int i = 0; i<sortedPaths.size(); i++) {

            String addZero = ""; //serve per aggiungere lo 0 davanti ai nomi dei file fino a gm9.txt -> gm09.txt

            if(i<9){
                addZero = "0";
            } else {
                addZero = "";
            }

            String name;
            int tokens;

            if(dP.isJar()){
                BufferedReader bR = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(sortedPaths.get(i))));
                name = bR.readLine();
                tokens = Integer.parseInt(bR.readLine());
                createdWindowsJar(bR);
                bR.close();
            } else {
                ArrayList<String> lines = (ArrayList<String>) Files.readAllLines(Paths.get(sortedPaths.get(i)));
                name = lines.get(0);
                tokens = Integer.parseInt(lines.get(1));
                createWindowsDebug(lines);
            }

            this.windows.add(new Window(name,tokens,this.board,"gm" +addZero+( i + 1 )));

        }

        walk.close();
        if(fileSystem!=null)
            fileSystem.close();

    }

    /**
     * crea le windows
     * @param lines ArrayList di string
     */
    public void createWindowsDebug(ArrayList<String> lines){
        for (int j = 2; j<lines.size(); j++) {

            String[] row = lines.get(j).split(" ");

            for (int k = 0; k < row.length; k++){

                switch(row[k]){

                    case "Y":
                        this.board[j-2][k] = new Box(Colour.YELLOW);
                        break;
                    case "R":
                        this.board[j-2][k] = new Box(Colour.RED);
                        break;
                    case "P":
                        this.board[j-2][k] = new Box(Colour.PURPLE);
                        break;
                    case "G":
                        this.board[j-2][k] = new Box(Colour.GREEN);
                        break;
                    case "B":
                        this.board[j-2][k] = new Box(Colour.BLUE);
                        break;
                    case "1":
                        this.board[j-2][k] = new Box(Value.ONE);
                        break;
                    case "2":
                        this.board[j-2][k] = new Box(Value.TWO);
                        break;
                    case "3":
                        this.board[j-2][k] = new Box(Value.THREE);
                        break;
                    case "4":
                        this.board[j-2][k] = new Box(Value.FOUR);
                        break;
                    case "5":
                        this.board[j-2][k] = new Box(Value.FIVE);
                        break;
                    case "6":
                        this.board[j-2][k] = new Box(Value.SIX);
                        break;
                    default:
                        this.board[j-2][k] = new Box();
                        break;

                }

            }

        }
    }

    /**
     * crea le windows da jar
     * @param bR parametro per leggere da file
     * @throws IOException e
     */
    public void createdWindowsJar(BufferedReader bR) throws IOException {
        String line;
        int j = 0;
        while ((line = bR.readLine())!=null) {

            String[] row = line.split(" ");

            for (int k = 0; k < row.length; k++){

                switch(row[k]){

                    case "Y":
                        this.board[j][k] = new Box(Colour.YELLOW);
                        break;
                    case "R":
                        this.board[j][k] = new Box(Colour.RED);
                        break;
                    case "P":
                        this.board[j][k] = new Box(Colour.PURPLE);
                        break;
                    case "G":
                        this.board[j][k] = new Box(Colour.GREEN);
                        break;
                    case "B":
                        this.board[j][k] = new Box(Colour.BLUE);
                        break;
                    case "1":
                        this.board[j][k] = new Box(Value.ONE);
                        break;
                    case "2":
                        this.board[j][k] = new Box(Value.TWO);
                        break;
                    case "3":
                        this.board[j][k] = new Box(Value.THREE);
                        break;
                    case "4":
                        this.board[j][k] = new Box(Value.FOUR);
                        break;
                    case "5":
                        this.board[j][k] = new Box(Value.FIVE);
                        break;
                    case "6":
                        this.board[j][k] = new Box(Value.SIX);
                        break;
                    default:
                        this.board[j][k] = new Box();
                        break;

                }

            }

            j++;

        }
    }

    /**
     * serve un metodo per ordinare perche' i file che leggo dalla cartella sono in ordine decrescente
     * @param it iteratore sui path
     * @return lista di percorsi ordinati in ordine crescente
     */
    public ArrayList<String> sortPaths(Iterator<Path> it){
        ArrayList<String> pathsList = new ArrayList<>();
        while(it.hasNext()){
            String path = it.next().toString();
            pathsList.add(path);
        }
        return pathsList;
    }

    /**
     *
     * @return lista delle window
     */
    public List<Window> getWindows(){

        return this.windows;

    }

}
