package repolezanettiperuzzi.view;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import repolezanettiperuzzi.common.DynamicPath;
import repolezanettiperuzzi.view.modelwrapper.BoxClient;
import repolezanettiperuzzi.view.modelwrapper.WindowClient;

import java.net.URI;
import java.net.URISyntaxException;


public class WindowGenerator {

    private WindowClient window;

    public WindowGenerator(WindowClient window){
        this.window = window;
    }

    public GridPane getWindowFXObject()  {
        //genero la finestra dinamicamente
        GridPane gridWindow = new GridPane();
        BoxClient[][] copyBoard = window.getBoardBox();
        //System.out.println("board width: "+copyBoard[0].length);
        //System.out.println("board height: "+copyBoard.length);

        Color red = Color.rgb(195, 53, 59);
        Color blue = Color.rgb(66, 169, 173);
        Color purple = Color.rgb(142, 67, 129);
        Color green = Color.rgb(36, 157, 107);
        Color yellow = Color.rgb(228, 209, 68);
        Canvas box;

        for (int i=0; i<copyBoard.length; i++){

            for(int j=0; j<copyBoard[0].length; j++){

                if(copyBoard[i][j].getBoundValue()!=null){

                    switch(copyBoard[i][j].getBoundValue()){

                        case ONE:

                            box = new Canvas(50,50);
                            GraphicsContext gc = box.getGraphicsContext2D();
                            gc.setFill(Color.BLACK);
                            gc.fillRect(0,0,50,50);
                            gc.drawImage(new Image(new DynamicPath("assets/boxes/BOX-01.png").getPath()),1,1,49,49);
                            gridWindow.add(box,j,i);
                            break;

                        case TWO:

                            box = new Canvas(50,50);
                            gc = box.getGraphicsContext2D();
                            gc.setFill(Color.BLACK);
                            gc.fillRect(0,0,50,50);
                            gc.drawImage(new Image(new DynamicPath("assets/boxes/BOX-02.png").getPath()),1,1,49,49);
                            gridWindow.add(box,j,i);
                            break;

                        case THREE:

                            box = new Canvas(50,50);
                            gc = box.getGraphicsContext2D();
                            gc.setFill(Color.BLACK);
                            gc.fillRect(0,0,50,50);
                            gc.drawImage(new Image(new DynamicPath("assets/boxes/BOX-03.png").getPath()),1,1,49,49);
                            gridWindow.add(box,j,i);
                            break;

                        case FOUR:

                            box = new Canvas(50,50);
                            gc = box.getGraphicsContext2D();
                            gc.setFill(Color.BLACK);
                            gc.fillRect(0,0,50,50);
                            gc.drawImage(new Image(new DynamicPath("assets/boxes/BOX-04.png").getPath()),1,1,49,49);
                            gridWindow.add(box,j,i);
                            break;

                        case FIVE:

                            box = new Canvas(50,50);
                            gc = box.getGraphicsContext2D();
                            gc.setFill(Color.BLACK);
                            gc.fillRect(0,0,50,50);
                            gc.drawImage(new Image(new DynamicPath("assets/boxes/BOX-05.png").getPath()),1,1,49,49);
                            gridWindow.add(box,j,i);
                            break;

                        case SIX:

                            box = new Canvas(50,50);
                            gc = box.getGraphicsContext2D();
                            gc.setFill(Color.BLACK);
                            gc.fillRect(0,0,50,50);
                            gc.drawImage(new Image(new DynamicPath("assets/boxes/BOX-06.png").getPath()),1,1,49,49);
                            gridWindow.add(box,j,i);
                            break;


                    }
                }else if(copyBoard[i][j].getBoundColour()!=null){

                    switch(copyBoard[i][j].getBoundColour()){

                        case RED:
                            box = new Canvas(50,50);
                            GraphicsContext gc = box.getGraphicsContext2D();
                            gc.setFill(Color.BLACK);
                            gc.fillRect(0,0,50,50);
                            gc.setFill(red);
                            gc.fillRect(1,1,49,49);
                            gridWindow.add(box,j,i);
                            break;
                        case BLUE:
                            box = new Canvas(50,50);
                            gc = box.getGraphicsContext2D();
                            gc.setFill(Color.BLACK);
                            gc.fillRect(0,0,50,50);
                            gc.setFill(blue);
                            gc.fillRect(1,1,49,49);
                            gridWindow.add(box,j,i);
                            break;
                        case GREEN:
                            box = new Canvas(50,50);
                            gc = box.getGraphicsContext2D();
                            gc.setFill(Color.BLACK);
                            gc.fillRect(0,0,50,50);
                            gc.setFill(green);
                            gc.fillRect(1,1,49,49);
                            gridWindow.add(box,j,i);
                            break;
                        case PURPLE:
                            box = new Canvas(50,50);
                            gc = box.getGraphicsContext2D();
                            gc.setFill(Color.BLACK);
                            gc.fillRect(0,0,50,50);
                            gc.setFill(purple);
                            gc.fillRect(1,1,49,49);
                            gridWindow.add(box,j,i);
                            break;
                        case YELLOW:
                            box = new Canvas(50,50);
                            gc = box.getGraphicsContext2D();
                            gc.setFill(Color.BLACK);
                            gc.fillRect(0,0,50,50);
                            gc.setFill(yellow);
                            gc.fillRect(1,1,49,49);
                            gridWindow.add(box,j,i);
                            break;

                    }
                }else{

                    box = new Canvas(50,50);
                    GraphicsContext gc = box.getGraphicsContext2D();
                    gc.setFill(Color.BLACK);
                    gc.fillRect(0,0,50,50);
                    gc.setFill(Color.WHITE);
                    gc.fillRect(1,1,49,49);
                    gridWindow.add(box,j,i);

                }
            }
        }

        return gridWindow;
    }
}
