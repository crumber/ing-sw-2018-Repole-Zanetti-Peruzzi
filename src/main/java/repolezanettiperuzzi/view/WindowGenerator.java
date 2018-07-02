package repolezanettiperuzzi.view;

import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import repolezanettiperuzzi.common.DynamicPath;
import repolezanettiperuzzi.common.modelwrapper.BoxClient;
import repolezanettiperuzzi.common.modelwrapper.WindowClient;

import javafx.scene.input.MouseEvent;

import java.awt.*;


public class WindowGenerator {

    private WindowClient window;
    private boolean mouseOut;
    private Coordinates coordinates;
    private Object clickLock;
    private GameFXMLController controller;

    public WindowGenerator(WindowClient window){
        this.window = window;
    }

    public WindowGenerator(WindowClient window, Object clickLock, GameFXMLController controller){
        this.window = window;
        this.coordinates = new Coordinates(-1, -1);
        this.clickLock = clickLock;
        this.controller = controller;
    }

    public GridPane getWindowFXObject(boolean clickableBox)  {
        //genero la finestra dinamicamente
        GridPane gridWindow = new GridPane();
        BoxClient[][] copyBoard = window.getBoardBox();

        Color red = Color.rgb(195, 53, 59);
        Color blue = Color.rgb(66, 169, 173);
        Color purple = Color.rgb(142, 67, 129);
        Color green = Color.rgb(36, 157, 107);
        Color yellow = Color.rgb(228, 209, 68);
        Canvas box;
        Button button;

        String buttonStyle = "-fx-background-color: transparent;"+"-fx-text-fill: transparent;";

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

                            if(clickableBox){

                                gridWindow.add(box,j,i);
                                setEventsOnBoxes(gridWindow, box, i, j);

                            }else {

                                gridWindow.add(box, j, i);

                            }

                            break;

                        case TWO:

                            box = new Canvas(50,50);
                            gc = box.getGraphicsContext2D();
                            gc.setFill(Color.BLACK);
                            gc.fillRect(0,0,50,50);
                            gc.drawImage(new Image(new DynamicPath("assets/boxes/BOX-02.png").getPath()),1,1,49,49);

                            if(clickableBox){

                                gridWindow.add(box,j,i);
                                setEventsOnBoxes(gridWindow, box, i, j);

                            }else {

                                gridWindow.add(box, j, i);

                            }

                            break;

                        case THREE:

                            box = new Canvas(50,50);
                            gc = box.getGraphicsContext2D();
                            gc.setFill(Color.BLACK);
                            gc.fillRect(0,0,50,50);
                            gc.drawImage(new Image(new DynamicPath("assets/boxes/BOX-03.png").getPath()),1,1,49,49);

                            if(clickableBox){

                                gridWindow.add(box,j,i);
                                setEventsOnBoxes(gridWindow, box, i, j);
                            }else {

                                gridWindow.add(box, j, i);

                            }


                            break;

                        case FOUR:

                            box = new Canvas(50,50);
                            gc = box.getGraphicsContext2D();
                            gc.setFill(Color.BLACK);
                            gc.fillRect(0,0,50,50);
                            gc.drawImage(new Image(new DynamicPath("assets/boxes/BOX-04.png").getPath()),1,1,49,49);

                            if(clickableBox){

                                gridWindow.add(box,j,i);
                                setEventsOnBoxes(gridWindow, box, i, j);

                            }else {

                                gridWindow.add(box, j, i);

                            }


                            break;

                        case FIVE:

                            box = new Canvas(50,50);
                            gc = box.getGraphicsContext2D();
                            gc.setFill(Color.BLACK);
                            gc.fillRect(0,0,50,50);
                            gc.drawImage(new Image(new DynamicPath("assets/boxes/BOX-05.png").getPath()),1,1,49,49);

                            if(clickableBox){

                                gridWindow.add(box,j,i);
                                setEventsOnBoxes(gridWindow, box, i, j);

                            }else {

                                gridWindow.add(box, j, i);

                            }


                            break;

                        case SIX:

                            box = new Canvas(50,50);
                            gc = box.getGraphicsContext2D();
                            gc.setFill(Color.BLACK);
                            gc.fillRect(0,0,50,50);
                            gc.drawImage(new Image(new DynamicPath("assets/boxes/BOX-06.png").getPath()),1,1,49,49);

                            if(clickableBox){

                                gridWindow.add(box,j,i);
                                setEventsOnBoxes(gridWindow, box, i, j);

                            }else {

                                gridWindow.add(box, j, i);

                            }


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

                            if(clickableBox){

                                gridWindow.add(box,j,i);
                                setEventsOnBoxes(gridWindow, box, i, j);

                            }else {

                                gridWindow.add(box, j, i);

                            }


                            break;
                        case BLUE:
                            box = new Canvas(50,50);
                            gc = box.getGraphicsContext2D();
                            gc.setFill(Color.BLACK);
                            gc.fillRect(0,0,50,50);
                            gc.setFill(blue);
                            gc.fillRect(1,1,49,49);

                            if(clickableBox){

                                gridWindow.add(box,j,i);
                                setEventsOnBoxes(gridWindow, box, i, j);

                            }else {

                                gridWindow.add(box, j, i);

                            }

                            break;
                        case GREEN:
                            box = new Canvas(50,50);
                            gc = box.getGraphicsContext2D();
                            gc.setFill(Color.BLACK);
                            gc.fillRect(0,0,50,50);
                            gc.setFill(green);
                            gc.fillRect(1,1,49,49);

                            if(clickableBox){

                                gridWindow.add(box,j,i);
                                setEventsOnBoxes(gridWindow, box, i, j);

                            }else {

                                gridWindow.add(box, j, i);

                            }


                            break;
                        case PURPLE:
                            box = new Canvas(50,50);
                            gc = box.getGraphicsContext2D();
                            gc.setFill(Color.BLACK);
                            gc.fillRect(0,0,50,50);
                            gc.setFill(purple);
                            gc.fillRect(1,1,49,49);

                            if(clickableBox){

                                gridWindow.add(box,j,i);
                                setEventsOnBoxes(gridWindow, box, i, j);

                            }else {

                                gridWindow.add(box, j, i);

                            }


                            break;
                        case YELLOW:
                            box = new Canvas(50,50);
                            gc = box.getGraphicsContext2D();
                            gc.setFill(Color.BLACK);
                            gc.fillRect(0,0,50,50);
                            gc.setFill(yellow);
                            gc.fillRect(1,1,49,49);

                            if(clickableBox){

                                gridWindow.add(box,j,i);
                                setEventsOnBoxes(gridWindow, box, i, j);

                            }else {

                                gridWindow.add(box, j, i);

                            }


                            break;

                    }
                }else{

                    box = new Canvas(50,50);
                    GraphicsContext gc = box.getGraphicsContext2D();
                    gc.setFill(Color.BLACK);
                    gc.fillRect(0,0,50,50);
                    gc.setFill(Color.WHITE);
                    gc.fillRect(1,1,49,49);

                    if(clickableBox){

                        gridWindow.add(box,j,i);
                        setEventsOnBoxes(gridWindow, box, i, j);

                    }else {

                        gridWindow.add(box, j, i);

                    }



                }
            }
        }

        gridWindow.setGridLinesVisible(true);

        return gridWindow;
    }

    public void setEventsOnBoxes(GridPane grid, Canvas box, int i, int j){
        Rectangle rect = new Rectangle(50 ,50);
        rect.setFill(Color.LIGHTGRAY);
        rect.setOpacity(0.5);
        rect.setVisible(false);
        rect.setId("Rect"+j+i);
        grid.add(rect, j, i);
        box.setOnMouseEntered(e -> {
            synchronized (clickLock) {
                mouseOut = false;
                rect.setVisible(true);
            }
        });
        rect.setOnMouseExited(e -> {
            synchronized (clickLock) {
                mouseOut = true;
                System.out.println("entro");
                if ((coordinates.xPos != i) || (coordinates.yPos != j)) {
                    System.out.println("ancora");
                    rect.setVisible(false);
                    rect.setOpacity(0.5);
                }
            }
        });
        rect.setOnMousePressed(e -> {synchronized (clickLock){rect.setOpacity(0.7);}});
        rect.setOnMouseReleased(e -> onClickBoxes(grid, box, rect, i, j));
    }

    public void onClickBoxes(GridPane grid, Canvas box, Rectangle rect, int i, int j){
        synchronized (clickLock) {
            if (!mouseOut) {
                ObservableList<Node> childrens = grid.getChildren();
                for (Node node : childrens) {
                    if ((node.getId() != null) && (node.getId().equals("Rect" + coordinates.yPos + coordinates.xPos))) {
                        //System.out.println(node.getId());
                        Rectangle r = (Rectangle) node;
                        r.setVisible(false);
                        r.setStrokeWidth(0);
                        r.setOpacity(0.5);
                    }
                }
                this.coordinates = new Coordinates(i, j);
                controller.setLastWindowCell(coordinates);
                rect.setFill(Color.TRANSPARENT);
                rect.setOpacity(1);
                rect.setStroke(Color.PINK);
                rect.setStrokeType(StrokeType.INSIDE);
                rect.setStrokeWidth(4.0);
            }
        }
    }

    class Coordinates{
        public int xPos;
        public int yPos;

        public Coordinates(int xPos, int yPos){
            this.xPos = xPos;
            this.yPos = yPos;
        }
    }
}
