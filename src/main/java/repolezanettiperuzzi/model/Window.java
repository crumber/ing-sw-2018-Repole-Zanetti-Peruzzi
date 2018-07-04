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
     * @param name Nome della Window
     * @param ft FavorToken che assegna in caso venga scelta
     * @param board Caselle della Window
     * @param fileName Nome del file in cui risiedono le informazioni sulla Window
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
     * @param d Dado da inserire
     * @param x Riga della casella
     * @param y Colonna della casella
     * @param restriction Restrizioni della casella da verificare
     */
    public void insertDie(Die d, int x, int y, String restriction){

        this.boardBox[x][y].setDie(d,restriction);

    }


    /**
     * Muove un dado da una casella ad un'altra
     * @param xIn Riga da cui muovere il dado
     * @param yIn Colonna da cui muovere il dado
     * @param xEnd Riga in cui muovere il dado
     * @param yEnd Colonna in cui muovere il dado
     * @param restriction Stringa che indica la restriziona
     */
    public void moveDie(int xIn,int yIn, int xEnd, int yEnd, String restriction) {

            this.boardBox[xEnd][yEnd].setDie(this.boardBox[xIn][yIn].removeDie(), restriction);

    }

    /**
     * Dice se la box contiene un dado
     * @param x Riga della casella
     * @param y Colonna della casella
     * @return True se c'è un dado nella box sennò false
     */
    public boolean thereIsDie(int x, int y){

        if(x>=0 && y>=0 && x<boardBox.length && y<boardBox[0].length && boardBox[x][y].die!=null){

             return true;

        }

        return false;
    }

    /**
     * Rimuove il dado nella box, se non presente restituisce null
     * @param x Riga della casella
     * @param y Colonna della casella
     * @return Rimuove il dado e lo restituisce ma se non presente ritorna null
     */
     public Die removeDie(int x, int y){

        if((boardBox[x][y].die!=null)){
            return boardBox[x][y].removeDie();
        }

        else return null;
    }

    /**
     * @return Nome della window
     */
    public String getName(){

        return this.NAME;

    }

    /**
     * @return Numero FV
     */
    public int getFTokens(){

        return this.FAVORTOKENS;

    }

    /**
     * @param x Riga della casella
     * @param y Colonna della casella
     * @return Restituisce dado della box
     */
    public Die getDieFromBoardBox(int x, int y)
    {
        return this.boardBox[x][y].die;
    }

    /**
     * @param x Riga della casella
     * @param y Colonna della casella
     * @return Restituisce valore dado nella box
     */
    public Value getDieValue(int x, int y){

        return this.boardBox[x][y].die.getValueDie();

    }

    /**
     * @param x Riga della casella
     * @param y Colonna della casella
     * @return Restituisce colore dado della box
     */
    public Colour getDieColour(int x, int y){

        return this.boardBox[x][y].die.getColourDie();

    }

    /**
     * @param x Riga della casella
     * @param y Colonna della casella
     * @return Vero se c'è un dado vicino alla posizione inserita sennò false
     */
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

    /**
     * @param d Dado da controllare
     * @param x Riga della casella
     * @param y Colonna della casella
     * @return Vero se c'è un dado vicino alla posizione inserita con lo stesso colore del dado passato sennò false
     */
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

    /**
     * @param d Dado da controllare
     * @param x Riga della casella
     * @param y Colonna della casella
     * @return Vero se c'è un dado vicino alla posizione inserita con lo stesso valore del dado passato sennò false
     */
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

    /**
     * @param d Dado da controllare
     * @param x Riga della casella
     * @param y Colonna della casella
     * @return Vero se c'è un dado vicino alla posizione inserita con lo stesso colore e/o valore del dado passato sennò false
     */
    public boolean controlAllBoundAdjacencies(Die d, int x, int y){

        if(controlColourBoundAdjacencies(d,x,y)){

            return true;

        }else if(controlValueBoundAdjacencies(d,x,y)){

            return true;

        }else{

            return false;

        }
    }

    /**
     * @return Vero se la window non contiene dadi senno ritorna false
     */
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

    /**
     * @return Numero di box vuote
     */
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

    /**
     * @param whichSecretColour Colore segreto
     * @return La somma dei valori dei dadi presenti nella window con il colore passato
     */
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

    /**
     * @param x Riga della casella
     * @param y Colonna della casella
     * @param d Dado da controllare
     * @return Vero se il dado passato rispetta i vincoli di colore e valore della box, falso altrimenti
     */
    public boolean controlAllBoundBox(int x, int y, Die d){

        return boardBox[x][y].controlBounds(d);

    }

    /**
     * @param x Riga della casella
     * @param y Colonna della casella
     * @param d Dado da controllare
     * @return Vero se il dado passato rispetta i vincoli di colore della box, falso altrimenti
     */
    public boolean controlColourBoundBox(int x, int y, Die d){

        return boardBox[x][y].controlColour(d);

    }

    /**
     * @param x Riga della casella
     * @param y Colonna della casella
     * @param d Dado da controllare
     * @return Vero se il dado passato rispetta i vincoli di valore della box, falso altrimenti
     */
    public boolean controlValueBoundBox(int x, int y, Die d){

        return boardBox[x][y].controlValue(d);

    }

    /**
     * @return Clone della Window
     */
    //deep clone method
    public Window copy(){
        return new Window(this);
    }

    /**
     * @return Numero righe della Window
     */
    public int numRow(){

        return boardBox.length;

    }

    /**
     * @return Numero colonne della Window
     */
    public int numColumn(){

        return boardBox[0].length;

    }

    /**
     * @return Stringa rappresentante la "schacchiera" della window, richiama la tostring dela box per rappresentare la singola box,
     * ogni colonna separata da - mentre ogni righa separata da uno spazio
     */
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