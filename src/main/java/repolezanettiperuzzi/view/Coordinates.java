package repolezanettiperuzzi.view;

public class Coordinates {
        public int xPos;
        public int yPos;

        public Coordinates(int xPos, int yPos){
            this.xPos = xPos;
            this.yPos = yPos;
        }

        @Override
        public String toString(){
            return xPos+""+yPos;
        }
}
