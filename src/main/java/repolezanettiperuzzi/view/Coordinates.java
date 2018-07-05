package repolezanettiperuzzi.view;

/**
 * Classe che rappresenta le coordinate
 * @author Andrea Zanetti
 */
public class Coordinates {
        public int xPos;
        public int yPos;

    /**
     * Costruttore
     * @param xPos Intero che indica la coordinata x
     * @param yPos Intero che indica la coordinata y
     */
    public Coordinates(int xPos, int yPos){
            this.xPos = xPos;
            this.yPos = yPos;
        }

    /**
     *
     * @return Stringa che ritorna coordinata x e y
     */
    @Override
        public String toString(){
            return xPos+""+yPos;
        }
}
