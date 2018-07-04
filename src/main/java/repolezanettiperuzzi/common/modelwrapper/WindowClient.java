package repolezanettiperuzzi.common.modelwrapper;

import java.io.Serializable;

/**
 * Classe che modellizza le Windows nel client
 * @author Giampiero Repole
 * @author Alessandro Peruzzi
 * @author Andrea Zanetti
 */
public class WindowClient implements Serializable{

    private final String NAME;
    private BoxClient[][] boardBox;
    private final int FAVORTOKENS;

    /**
     * Costruttore
     * @param name Nome della window
     * @param ft Favor token
     * @param board Game board
     */
    public WindowClient(String name, int ft, BoxClient[][] board) {

        this.NAME = name;
        this.FAVORTOKENS = ft;

        this.boardBox = new BoxClient[board.length][board[0].length];

        for(int i = 0; i< board.length; i++) {

            /*for(int j = 0; j<board[0].length; j++){
                System.out.print(board[i][j].toString()+" ");
            }
            System.out.println();*/
            System.arraycopy(board[i], 0, boardBox[i], 0, board[0].length);

        }
        //System.out.println("\n");
    }

    /**
     * Costruttore
     * @param name Nome della window
     * @param ft Favor token
     * @param board Game board
     */
    public WindowClient(String name, int ft, String board){
        this.NAME = name;
        this.FAVORTOKENS = ft;

        String[] boxRows = board.split(" ");
        int rows = boxRows.length;
        int cols = boxRows[0].split("-").length;

        this.boardBox = new BoxClient[rows][cols];

        for(int i = 0; i<rows; i++){
            String[] box = boxRows[i].split("-");
            for(int j = 0; j<cols; j++){
                String bound = box[j].charAt(0)+"";
                switch (bound){
                    case "Y":
                        boardBox[i][j] = new BoxClient(ColourClient.YELLOW);
                        break;
                    case "R":
                        boardBox[i][j] = new BoxClient(ColourClient.RED);
                        break;
                    case "P":
                        boardBox[i][j] = new BoxClient(ColourClient.PURPLE);
                        break;
                    case "G":
                        boardBox[i][j] = new BoxClient(ColourClient.GREEN);
                        break;
                    case "B":
                        boardBox[i][j] = new BoxClient(ColourClient.BLUE);
                        break;
                    case "0":
                        boardBox[i][j] = new BoxClient();
                        break;
                    case "1":
                        boardBox[i][j] = new BoxClient(ValueClient.ONE);
                        break;
                    case "2":
                        boardBox[i][j] = new BoxClient(ValueClient.TWO);
                        break;
                    case "3":
                        boardBox[i][j] = new BoxClient(ValueClient.THREE);
                        break;
                    case "4":
                        boardBox[i][j] = new BoxClient(ValueClient.FOUR);
                        break;
                    case "5":
                        boardBox[i][j] = new BoxClient(ValueClient.FIVE);
                        break;
                    case "6":
                        boardBox[i][j] = new BoxClient(ValueClient.SIX);
                        break;
                }
                if(box[j].length()==3){
                    DieClient die = new DieClient(box[j].substring(1));
                    boardBox[i][j].setDieNoRestricion(die);
                }
            }
        }
    }

    /**
     *
     * @return Clone della "schacchiera"
     */
    public BoxClient[][] getBoardBox(){

        return this.boardBox.clone();

    }

    /**
     *
     * @param d Dado da inserire
     * @param x Indica la riga in cui inserire
     * @param y Indica la colonna in cui inserire
     * @param restriction Tipo di controllo da fare nel muovere il dado
     */
    public void insertDie(DieClient d, int x, int y, String restriction){

        this.boardBox[x][y].setDie(d,restriction);

    }

    /**
     *
     * @param xIn Indica la riga da cui muovere
     * @param yIn Indica la colonna da cui muovere
     * @param xEnd Indica la riga in cui muovere
     * @param yEnd Indica la colonna in cui muovere
     * @param restriction Tipo di controllo da fare nel muovere il dado
     */
    public void moveDie(int xIn,int yIn, int xEnd, int yEnd, String restriction) {

            this.boardBox[xEnd][yEnd].setDie(this.boardBox[xIn][yIn].removeDie(), restriction);

    }

    /**
     *
     * @param x Indica la righa
     * @param y Indica la colonna
     * @return True se è presente il dado nella posizione passata sennò false
     */
    public boolean thereIsDie(int x, int y){

        if(boardBox[x][y].die!=null){

             return true;

        }

        return false;
    }

    /**
     * Rimuove il dado della posizione passata e lo ritorna
     * @param x Indica la righa
     * @param y Indica la colonna
     * @return Il dado rimosso
     */
     public DieClient removeDie(int x, int y){

        if((boardBox[x][y].die!=null)){
            return boardBox[x][y].removeDie();
        }

        else return null;
    }

    /**
     *
     * @return Nome della window
     */
    public String getName(){

        return this.NAME;

    }

    /**
     *
     * @return Numero favor token
     */
    public int getFTokens(){

        return this.FAVORTOKENS;

    }

    /**
     *
     * @param x Indica la righa
     * @param y Indica la colonna
     * @return Dado presente in quella box
     */
    public DieClient getDieFromBoardBox(int x, int y)
    {
        return this.boardBox[x][y].die;
    }

    /**
     *
     * @param x Indica la righa
     * @param y Indica la colonna
     * @return Valore del dado presente in quella box
     */
    public ValueClient getDieValue(int x, int y){

        return this.boardBox[x][y].die.getValueDie();

    }

    /**
     *
     * @param x Indica la righa
     * @param y Indica la colonna
     * @return Colore del dado presente in quella box
     */
    public ColourClient getDieColour(int x, int y){

        return this.boardBox[x][y].die.getColourDie();

    }

    /**
     *
     * @return Numero righe
     */
    public int rowSize(){

        return boardBox.length;
    }

    /**
     *
     * @return Numero colonne
     */
    public int columnSize(){

        return boardBox[0].length;

    }

}