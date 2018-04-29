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

    public boolean insertDie(Die d, int x, int y, String restriction){

        if(isEmpty()){

            if((x==0)||(x==boardBox.length-1)||(y==0)||(y==boardBox[0].length-1)){

                return this.boardBox[x][y].setDie(d,restriction);

            }
            return false;
        }else if(controlAdjacences(x,y)){

            return this.boardBox[x][y].setDie(d, restriction);
        }

        return false;
    }

    public boolean moveDie(int xIn,int yIn, int xEnd, int yEnd, String restriction) {

        if((boardBox[xIn][yIn].die!=null)&&(controlAdjacences(xEnd,yEnd))) {
            return this.boardBox[xEnd][yEnd].setDie(this.boardBox[xIn][yIn].removeDie(), restriction);
        }
        return false;
    }

    public boolean changeDieValue(int x, int y, Value v){

        this.boardBox[x][y].die.setValue(v);

        return true;

    }


    public String getName(){

        return this.NAME;

    }

    public int getFTokens(){

        return this.FLAVORTOKENS;

    }

    public Value getDieValue(int x, int y){

        return this.boardBox[x][y].die.getValueDie();
    }

    public Colour getDieColour(int x, int y){

        return this.boardBox[x][y].die.getColourDie();

    }

    public boolean controlAdjacences(int x, int y){

        if(x-1>=0){

            if(boardBox[x-1][y].die!=null) {
                return true;
            }else if((y-1>=0)&&(boardBox[x-1][y-1].die!=null)){
                return true;
            }else if((y+1<boardBox[0].length)&&(boardBox[x-1][y+1].die!=null)){
                return true;
            }

        }

        if(x+1<boardBox.length){

            if(boardBox[x+1][y].die!=null){
                return true;
            }else if((y-1>=0)&&(boardBox[x+1][y-1].die!=null)){
                return true;
            }else if((y+1<boardBox[0].length)&&(boardBox[x+1][y+1].die!=null)){
                return true;
            }
        }

        if((y-1>=0)&&(boardBox[x][y-1].die!=null)){
            return true;
        }else if((y+1<boardBox[0].length)&&(boardBox[x][y+1].die!=null)){
            return true;
        }

        return false;
    }

    public boolean isEmpty(){

        for (int i=0; i<boardBox.length; i++){
            for(int j=0; j<boardBox[0].length; j++){
              if(boardBox[i][j].die!=null){
                  return false;
              }
            }
        }

        return true;
    }



}