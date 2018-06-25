package repolezanettiperuzzi.model;

/**
 * Classe che modellizza le Windows
 * @author Giampiero Repole
 * @author Alessandro Peruzzi
 * @author Andrea Zanetti
 */
public class Window {

    private final String NAME;
    private Box[][] boardBox;
    private final int FAVORTOKENS;
    public final String fileName;

    /**
     * Costruttore della classe
     * @param name nome della Window
     * @param ft FavorToken che assegna in caso venga scelta
     * @param board caselle della Window
     * @param fileName nome del file in cui risiedono le informazioni sulla Window
     */
    public Window(String name, int ft, Box[][] board,String fileName) {

        this.NAME = name;
        this.FAVORTOKENS = ft;
        this.fileName = fileName;

        this.boardBox = new Box[board.length][board[0].length];

        for(int i = 0; i< board.length; i++) {

            System.arraycopy(board[i], 0, boardBox[i], 0, board[0].length);

        }
    }

    /**
     * Costruttore che crea una copia di una Window passatagli come parametro
     * @param w Window da copiare
     */
    public Window(Window w){
        this.NAME = w.NAME;
        this.boardBox = new Box[w.boardBox.length][w.boardBox[0].length];

        for(int i = 0; i<w.boardBox.length; i++){

            System.arraycopy(w.boardBox[i], 0, this.boardBox[i], 0, w.boardBox[0].length);

        }

        this.FAVORTOKENS = w.FAVORTOKENS;
        this.fileName=w.fileName;
    }

    /**
     * Inserisce un dado nella casella desiderata
     * @param d dado da inserire
     * @param x colonna della casella
     * @param y riga della casella
     * @param restriction restrizioni della casella da verificare
     */
    public void insertDie(Die d, int x, int y, String restriction){

        this.boardBox[x][y].setDie(d,restriction);

    }


    /**
     * Muove un dado da una casella ad un'altra
     * @param xIn
     * @param yIn
     * @param xEnd
     * @param yEnd
     * @param restriction
     */
    public void moveDie(int xIn,int yIn, int xEnd, int yEnd, String restriction) {

            this.boardBox[xEnd][yEnd].setDie(this.boardBox[xIn][yIn].removeDie(), restriction);

    }

    public boolean thereIsDie(int x, int y){

        if(x>=0 && y>=0 && x<boardBox.length && y<boardBox[0].length && boardBox[x][y].die!=null){

             return true;

        }

        return false;
    }

     public Die removeDie(int x, int y){

        if((boardBox[x][y].die!=null)){
            return boardBox[x][y].removeDie();
        }

        else return null;
    }

    public String getName(){

        return this.NAME;

    }

    public int getFTokens(){

        return this.FAVORTOKENS;

    }

    public Die getDieFromBoardBox(int x, int y)
    {
        return this.boardBox[x][y].die;
    }

    public Value getDieValue(int x, int y){

        return this.boardBox[x][y].die.getValueDie();

    }

    public Colour getDieColour(int x, int y){

        return this.boardBox[x][y].die.getColourDie();

    }

    public boolean controlAdjacencies(int x, int y){

        boolean thereIsDieNextTo;

        if(x-1>=0 && boardBox[x-1][y].die!=null) {

            thereIsDieNextTo=true;

        }else if(x-1>=0 && (y-1>=0)&&(boardBox[x-1][y-1].die!=null)){

            thereIsDieNextTo=true;

        }else if(x-1>=0 && (y+1<boardBox[0].length)&&(boardBox[x-1][y+1].die!=null)){

            thereIsDieNextTo=true;

        }else if(x+1<boardBox.length && boardBox[x+1][y].die!=null){

            thereIsDieNextTo=true;

        }else if(x+1<boardBox.length && (y-1>=0)&&(boardBox[x+1][y-1].die!=null)){

            thereIsDieNextTo=true;

        }else if(x+1<boardBox.length && (y+1<boardBox[0].length)&&(boardBox[x+1][y+1].die!=null)){

            thereIsDieNextTo=true;

        }else if((y-1>=0)&&(boardBox[x][y-1].die!=null)){

            thereIsDieNextTo=true;

        }else if((y+1<boardBox[0].length)&&(boardBox[x][y+1].die!=null)){

            thereIsDieNextTo=true;

        }else{

            thereIsDieNextTo=false;

        }

        return thereIsDieNextTo;
    }

    public boolean controlColourBoundAdjacencies(Die d, int x, int y){

        if (x - 1 >= 0 && boardBox[x - 1][y].die !=null && boardBox[x - 1][y].die.getColourDie().equals(d.getColourDie())) {

            return true;

        } else if (x + 1 < boardBox.length && boardBox[x + 1][y].die != null && boardBox[x + 1][y].die.getColourDie().equals(d.getColourDie())) {

            return true;

        } else if ((y - 1 >= 0) && boardBox[x][y - 1].die != null && boardBox[x][y - 1].die.getColourDie().equals(d.getColourDie()) ) {

            return true;

        } else if ((y + 1 < boardBox[0].length) && boardBox[x][y + 1].die != null && boardBox[x][y + 1].die.getColourDie().equals(d.getColourDie())) {

            return true;

        } else {

            return false;

        }
    }

    public boolean controlValueBoundAdjacencies(Die d, int x, int y){

        if (x - 1 >= 0 && boardBox[x - 1][y].die != null && boardBox[x - 1][y].die.getValueDie().equals(d.getValueDie())) {

            return true;

        } else if (x + 1 < boardBox.length && boardBox[x + 1][y].die != null && boardBox[x + 1][y].die.getValueDie().equals(d.getValueDie())) {

            return true;

        } else if ((y - 1 >= 0) && (boardBox[x][y - 1].die != null && boardBox[x][y - 1].die.getValueDie().equals(d.getValueDie()))) {

            return true;

        } else if ((y + 1 < boardBox[0].length) && boardBox[x][y + 1].die != null && boardBox[x][y + 1].die.getValueDie().equals(d.getValueDie())) {

            return true;

        } else {

            return false;

        }
    }

    public boolean controlAllBoundAdjacencies(Die d, int x, int y){

        if(controlColourBoundAdjacencies(d,x,y)){

            return true;

        }else if(controlValueBoundAdjacencies(d,x,y)){

            return true;

        }else{

            return false;

        }
    }

    public boolean isEmpty(){

        for (Box[] aBoardBox : boardBox) {
            for (int j = 0; j < boardBox[0].length; j++) {
                if (aBoardBox[j].die != null) {
                    return false;
                }
            }
        }

        return true;
    }

    public int numBoxEmpty(){

        int nEmpty=0;

        for (Box[] aBoardBox : boardBox) {

            for (int j = 0; j < boardBox[0].length; j++) {

                if (aBoardBox[j].die == null) {

                    nEmpty++;

                }
            }
        }

        return nEmpty;
    }


    public int calculateSecretScore(Colour whichSecretColour){

        int secretScore=0;

        for (Box[] aBoardBox : boardBox) {

            for (int j = 0; j < boardBox[0].length; j++) {

                if (aBoardBox[j].die!=null && aBoardBox[j].die.getColourDie()== whichSecretColour) {

                    secretScore+=aBoardBox[j].die.getValueDie().getNumber();

                }
            }
        }

        return secretScore;
    }

    public boolean controlAllBoundBox(int x, int y, Die d){

        return boardBox[x][y].controlBounds(d);

    }

    public boolean controlColourBoundBox(int x, int y, Die d){

        return boardBox[x][y].controlColour(d);

    }

    public boolean controlValueBoundBox(int x, int y, Die d){

        return boardBox[x][y].controlValue(d);

    }

    //deep clone method
    public Window copy(){
        return new Window(this);
    }

    public int numRow(){

        return boardBox.length;

    }

    public int numColumn(){

        return boardBox[0].length;

    }

    @Override
    public String toString(){
        String windowToString = "";
        for(int i = 0; i<boardBox.length; i++){
            for(int j = 0; j<boardBox[0].length; j++){
                windowToString += boardBox[i][j].toString();
                if(j<boardBox[0].length-1){ //solo se e' il penultimo elemento della riga
                    windowToString += "-";
                }
            }
            if(i<boardBox.length-1) {
                windowToString += " ";
            }
        }
        return windowToString;
    }
}