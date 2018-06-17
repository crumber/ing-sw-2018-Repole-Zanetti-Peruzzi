package repolezanettiperuzzi.model.actions;


import repolezanettiperuzzi.model.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.logging.*;

public class InitializeGame{

    private ArrayList<Colour> privateObjective;
    private ArrayList<Window> windows;
    private Box[][] board;
    private final Logger LOGGER = Logger.getLogger(InitializeGame.class.getName());


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


    public void doAction(GameBoard board) throws IOException {

        //assign private objective for each Player
        this.assignPrivateObjective(board);

        //create windows from files dynamically
        this.createWindows();

        //initialize deck with tool cards and public cards
        Deck startDeck = null;
        try {

            startDeck = new Deck("cards/publiccards","cards/toolcards");

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



    private void assignPrivateObjective(GameBoard board){

        Collections.shuffle(this.privateObjective);

        for(int i = 0 ; i < board.getNPlayers(); i++){

            Player p = board.getPlayer(i);
            p.setSecretColour(this.privateObjective.remove(0));

        }

    }


    private void createWindows() throws IOException {

        int nWindows = Objects.requireNonNull(new File("cards/gamemaps").list()).length;

        //System.out.println("NUMERO WINDOWS "+nWindows);

        for (int i = 0; i < nWindows; i++) {

            ArrayList<String> lines = (ArrayList<String>) Files.readAllLines(Paths.get("cards/gamemaps" + "/gm" + (i + 1) + ".txt"));
            String name = lines.get(0);
            int tokens = Integer.parseInt(lines.get(1));

            for (int j = 2; j < 6; j++) {

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

            this.windows.add(new Window(name,tokens,this.board,"gm" + ( i + 1 )));

        }

    }

    public List<Window> getWindows(){

        return this.windows;

    }

}
