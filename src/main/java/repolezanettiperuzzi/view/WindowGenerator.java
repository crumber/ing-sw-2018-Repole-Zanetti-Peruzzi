package repolezanettiperuzzi.view;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.canvas.Canvas;
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

        for (int i=0; i<copyBoard.length; i++){

            for(int j=0; j<copyBoard[0].length; j++){

                if(copyBoard[i][j].getBoundValue()!=null){

                    ImageView value;

                    switch(copyBoard[i][j].getBoundValue()){

                        case ONE:

                            value = new ImageView(new Image(new DynamicPath("assets/boxes/BOX-01.png").getPath()));
                            value.setFitWidth(50);
                            value.setPreserveRatio(true);
                            value.setSmooth(true);
                            value.setCache(true);
                            gridWindow.add(value,j,i);
                            break;

                        case TWO:

                            value = new ImageView(new Image(new DynamicPath("assets/boxes/BOX-02.png").getPath()));
                            value.setFitWidth(50);
                            value.setPreserveRatio(true);
                            value.setSmooth(true);
                            value.setCache(true);
                            gridWindow.add(value,j,i);
                            break;

                        case THREE:

                            value = new ImageView(new Image(new DynamicPath("assets/boxes/BOX-03.png").getPath()));
                            value.setFitWidth(50);
                            value.setPreserveRatio(true);
                            value.setSmooth(true);
                            value.setCache(true);
                            gridWindow.add(value,j,i);
                            break;

                        case FOUR:

                            value = new ImageView(new Image(new DynamicPath("assets/boxes/BOX-04.png").getPath()));
                            value.setFitWidth(50);
                            value.setPreserveRatio(true);
                            value.setSmooth(true);
                            value.setCache(true);
                            gridWindow.add(value,j,i);
                            break;

                        case FIVE:

                            value = new ImageView(new Image(new DynamicPath("assets/boxes/BOX-05.png").getPath()));
                            value.setFitWidth(50);
                            value.setPreserveRatio(true);
                            value.setSmooth(true);
                            value.setCache(true);
                            gridWindow.add(value,j,i);
                            break;

                        case SIX:

                            value = new ImageView(new Image(new DynamicPath("assets/boxes/BOX-06.png").getPath()));
                            value.setFitWidth(50);
                            value.setPreserveRatio(true);
                            value.setSmooth(true);
                            value.setCache(true);
                            gridWindow.add(value,j,i);
                            break;


                    }
                }else if(copyBoard[i][j].getBoundColour()!=null){

                    switch(copyBoard[i][j].getBoundColour()){

                        case RED:
                            Canvas box = new Canvas(50,50);
                            GraphicsContext gc = box.getGraphicsContext2D();
                            gc.setFill(red);
                            gc.fillRect(0,0,50,50);
                            gridWindow.add(box,j,i);
                            break;
                        case BLUE:
                            box = new Canvas(50,50);
                            gc = box.getGraphicsContext2D();
                            gc.setFill(blue);
                            gc.fillRect(0,0,50,50);
                            gridWindow.add(box,j,i);
                            break;
                        case GREEN:
                            box = new Canvas(50,50);
                            gc = box.getGraphicsContext2D();
                            gc.setFill(green);
                            gc.fillRect(0,0,50,50);
                            gridWindow.add(box,j,i);
                            break;
                        case PURPLE:
                            box = new Canvas(50,50);
                            gc = box.getGraphicsContext2D();
                            gc.setFill(purple);
                            gc.fillRect(0,0,50,50);
                            gridWindow.add(box,j,i);
                            break;
                        case YELLOW:
                            box = new Canvas(50,50);
                            gc = box.getGraphicsContext2D();
                            gc.setFill(yellow);
                            gc.fillRect(0,0,50,50);
                            gridWindow.add(box,j,i);
                            break;

                    }
                }else{

                    Canvas box = new Canvas(50,50);
                    GraphicsContext gc = box.getGraphicsContext2D();
                    gc.setFill(Color.WHITE);
                    gc.fillRect(0,0,50,50);
                    gridWindow.add(box,j,i);

                }
            }
        }

        return gridWindow;
    }
}
