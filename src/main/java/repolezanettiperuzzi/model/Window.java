package repolezanettiperuzzi.model;

public class Window {

    private final String NAME;
    private Box[][] boardBox;
    private final int FLAVORTOKENS;

    public Window(String name, int ft, Box[][] board) {

        NAME = name;
        FLAVORTOKENS = ft;

        boardBox=new Box[4][5];

        for(int i = 0; i< 4; i++) {

            for (int j = 0; j < 5; j++) {

                boardBox[i][j] = board[i][j];

            }
        }
    }

    public void insertDie(Die d, int x, int y){

        this.boardBox[x][y].setDie(d);

    }

    public void moveDie(int xIn,int yIn, int xEnd, int yEnd) {

        this.boardBox[xEnd][yEnd].setDie(this.boardBox[xIn][yIn].removeDie());

    }


    public String getName(){

        return this.NAME;

    }

    public int getFTokens(){

        return this.FLAVORTOKENS;

    }

}