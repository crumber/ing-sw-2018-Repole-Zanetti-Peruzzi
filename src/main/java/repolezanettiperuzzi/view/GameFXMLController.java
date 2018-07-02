package repolezanettiperuzzi.view;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;
import repolezanettiperuzzi.common.DynamicPath;
import repolezanettiperuzzi.common.modelwrapper.DieClient;
import repolezanettiperuzzi.common.modelwrapper.GameBoardClient;
import repolezanettiperuzzi.common.modelwrapper.PlayerClient;
import repolezanettiperuzzi.common.modelwrapper.WindowClient;
import repolezanettiperuzzi.model.Player;
import repolezanettiperuzzi.common.modelwrapper.*;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.*;

public class GameFXMLController extends FXMLController implements Initializable{

    private Stage stage;
    private GameView gV;
    private int timerCounter;
    private Timeline timerCountdown;
    private String textContent;
    private boolean mouseOut;
    private boolean alreadyUpdated;
    private Object clickLock;
    private WindowGenerator.Coordinates lastWindowCell;
    private int lastDieDraft;

    @FXML
    // The reference of inputText will be injected by the FXML loader
    private Text playersText;

    @FXML
    // The reference of inputText will be injected by the FXML loader
    private Text timerText;

    // location and resources will be automatically injected by the FXML loader
    @FXML
    private URL location;

    @FXML
    private AnchorPane playerWindow;

    @FXML
    private GridPane draftPane;

    @FXML
    private Button insertDieButton;

    @FXML
    private Button endTurnButton;

    @FXML
    private ResourceBundle resources;

    @FXML
    private AnchorPane favorTokensPane;

    @FXML
    private ImageView tc1,tc2,tc3,pc1,pc2,pc3,secretColor;

    @FXML
    private VBox pcWindow, tcWindow;

    @FXML
    private Button ToolCards, PublicCards, tcClose, pcClose;


    // Add a public no-args constructor
    public GameFXMLController()
    {
    }

    public void setGameView(GameView gV){
        this.gV = gV;
    }

    public void setStage(Stage stage){
        this.stage = stage;
    }

    public void setTimer(int timerDuration){

        timerCounter = timerDuration;
        timerCountdown = new Timeline(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                timerText.setText("Timer: "+timerCounter);
                timerCounter--;
            }
        }));
        timerCountdown.setCycleCount(timerDuration);
        timerCountdown.play();
    }

    public void cancelTimer(){
        if(this.timerCountdown != null){
            timerCountdown.stop();
            timerCountdown = null;
        }
    }

    public void notifyOnExit() throws IOException {
        gV.notifyOnExit("game");
    }

    public void setLastWindowCell(WindowGenerator.Coordinates coordinates){
        this.lastWindowCell = coordinates;
    }

    public void setLastDieDraft(int lastDieDraft){
        this.lastDieDraft = lastDieDraft;
    }

    public void updateView(GameBoardClient board, int currentTime){
        synchronized (clickLock) {
            //this.setTimer(currentTime);
            ArrayList<Node> nodesToDeleteWindow = new ArrayList<>();
            ArrayList<Node> nodesToDeleteDraft = new ArrayList<>();
            ArrayList<Node> nodesToDeleteFT = new ArrayList<>();

            if (alreadyUpdated) {
                ObservableList<Node> childrenWindow = playerWindow.getChildren();
                ObservableList<Node> draftChildren = draftPane.getChildren();
                ObservableList<Node> fvChildren = favorTokensPane.getChildren();

                for (Node node : childrenWindow) {
                    if ((node.getId() != null) && node.getId().contains("gridWindow")) {
                        nodesToDeleteWindow.add(node);
                    }
                }

                for (Node node :draftChildren) {
                    if (node.getId() != null) {
                        nodesToDeleteDraft.add(node);
                    }
                }

                for (Node node :fvChildren) {
                    if (node.getId() != null) {
                        nodesToDeleteFT.add(node);
                    }
                }


            }

            viewWindows(board);
            viewDraft(board);
            viewCards(board);
            viewSecretColor(board);
            //viewDraft();
            alreadyUpdated = true;

            for (Node n : nodesToDeleteWindow) {

                Platform.runLater(() -> playerWindow.getChildren().remove(n));

            }

            for (Node n : nodesToDeleteDraft) {

                Platform.runLater(() -> draftPane.getChildren().remove(n));

            }

            for (Node n : nodesToDeleteFT) {

                Platform.runLater(() -> favorTokensPane.getChildren().remove(n));

            }

        }
    }

    public void viewDraft(GameBoardClient board){
        int j = 0;
        for(int i = 0; i<board.getSizeDraft(); i++){
            DieClient die = board.getDieDraft(i);
            Image dieImage = new Image(new DynamicPath("assets/dice/"+die.toString()+".png").getPath());
            ImageView dieView = new ImageView(dieImage);
            dieView.setFitWidth(40);
            dieView.setFitHeight(40);
            Integer I = new Integer(i);
            Integer J = new Integer(j);
            Platform.runLater(() -> draftPane.add(dieView, (I%3), J));
            setDraftEvents(i, j, draftPane, dieView);
            if(i==2 || i==5 || i==8){
                j++;
            }

        }
    }

    public void onClickDieDraft(GridPane grid, int i, Rectangle rect){
        ObservableList<Node> childrens = grid.getChildren();
        for (Node node : childrens) {
            if ((node.getId() != null) && (node.getId().equals("dieDraft"+lastDieDraft))) {
                //System.out.println(node.getId());
                Rectangle r = (Rectangle) node;
                r.setVisible(false);
                r.setStrokeWidth(0);
                r.setOpacity(0.5);
            }
        }
        this.lastDieDraft = i;
        rect.setFill(Color.TRANSPARENT);
        rect.setOpacity(1);
        rect.setStroke(Color.PINK);
        rect.setStrokeType(StrokeType.INSIDE);
        rect.setStrokeWidth(4.0);
    }

    public void setDraftEvents(int i, int j, GridPane pane, ImageView dieView){

        Rectangle rect = new Rectangle(40 ,40);
        rect.setArcWidth(10);
        rect.setArcHeight(10);
        rect.setId("dieDraft"+i);
        rect.setFill(Color.LIGHTGRAY);
        rect.setOpacity(0.5);
        rect.setVisible(false);
        Platform.runLater(() -> pane.add(rect, i%3, j));
        dieView.setOnMouseEntered(e -> rect.setVisible(true));
        rect.setOnMouseExited(e -> rect.setVisible(false));
        rect.setOnMouseReleased(e -> onClickDieDraft(pane, i, rect));
    }

    public void viewWindows(GameBoardClient board)  {

        int i = 0;

        for(PlayerClient player : board.getPlayers() ) {

            WindowGenerator wGenerator = new WindowGenerator(player.getWindow(), clickLock, this);
            //Esempio con GridPane
            boolean clickableBox = gV.getUsername().equals(player.getName());
            GridPane pane = wGenerator.getWindowFXObject(clickableBox);
            VBox box = new VBox();
            box.setId("gridWindow"+i);
            String windowName = player.getWindow().getName().replace(" ", "-"); //questo per gestire in caso la window arrivi da RMI in cui il nome e' senza trattini
            javafx.scene.image.ImageView windowLabel = new javafx.scene.image.ImageView(new Image(new DynamicPath("assets/Windows/"+windowName+".png").getPath()));
            windowLabel.setFitWidth(50*player.getWindow().getBoardBox()[0].length);
            windowLabel.setPreserveRatio(true);
            windowLabel.setSmooth(true);
            windowLabel.setCache(true);
            box.getChildren().addAll(pane,windowLabel);

            //se la window generata non è quella del client allora viene settata trasparente
            if(!gV.getUsername().equals(player.getName())) {

                box.setVisible(false);

            }

            Platform.runLater(() -> playerWindow.getChildren().add(box));
            i++;

            //box.setOnAction(e -> onBoxClick(e,windowName));

        }

    }

    public void setGameScene(){
        this.cancelTimer();

        GameFXMLController controller = new GameFXMLController();
        controller.setGameView(gV);
        gV.setFXMLController(controller);
        controller.setStage(stage);
        String currPath = System.getProperty("user.dir");
        FXMLLoader loader = null;
        try {
            loader = new FXMLLoader(new URI(new DynamicPath("fxml/GameFXML.fxml").getPath()).toURL()); //TODO imposta nuovo controller
            loader.setController(controller);
            Group root = (Group) loader.load();
            FXMLLoader finalLoader = loader;
            Platform.runLater(() -> {
                stage.setScene(new Scene(root, 1280, 800));
                stage.setMaximized(true);
                stage.setFullScreen(true);
                Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
                System.out.println(primaryScreenBounds.getMinX()+" "+primaryScreenBounds.getMinY()+" "+primaryScreenBounds.getHeight()+" "+primaryScreenBounds.getWidth());
                stage.setX(primaryScreenBounds.getMinX());
                stage.setY(primaryScreenBounds.getMinY());
                stage.setWidth(primaryScreenBounds.getWidth());
                stage.setHeight(primaryScreenBounds.getHeight());
                stage.setUserData(finalLoader);
            });
            gV.waitingRoomLoaded();
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }

    }

    public void onBoxClick (ActionEvent e, String windowName){

        Button b = (Button) e.getSource();
        b.setStyle(".button:pressed {\n" +
                "    -fx-background-color: black;\n" +
                "    -fx-text-fill: transparent;\n" +
                "}");

        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.NONE,"Do you want to choose this window?",ButtonType.YES,ButtonType.NO);
            alert.setX(stage.getX()+stage.getScene().getWidth()/2 - 125);
            alert.setY(stage.getY()+stage.getScene().getHeight()/2 - 60);
            alert.setTitle("Confirm Window");
            alert.setResizable(true);
            alert.getDialogPane().setPrefSize(250, 120);
            alert.setResizable(false);
            alert.showAndWait();

            if(alert.getResult()==ButtonType.YES){


            }else{

                b.setStyle("-fx-background-color: transparent;"+"-fx-text-fill: transparent;");

            }
        });


    }

    public void mousePressed(MouseEvent e, Button b, String backgroundColor){
        b.setPrefHeight(35);
        b.setStyle(getPressedButtonStyle(backgroundColor));
        mouseOut = false;
    }

    public void mouseReleased(MouseEvent e, Button b, String id, String backgroundColor, String shadowColor){
        b.setPrefHeight(41);
        b.setStyle(getIdleButtonStyle(backgroundColor, shadowColor));
        if(!mouseOut) {
            switch(id){
                case "insertDieButton":
                    System.out.println("pressed insert die");
                    break;
                case "endTurnButton":
                    System.out.println("pressed end turn");
                    break;

            }
        }
    }

    public String getIdleButtonStyle(String backgroundColor, String shadowColor){
        final String IDLE_BUTTON_STYLE = "-fx-padding: 8 15 15 15; -fx-background-insets: 0 0 7 0,0 0 5 0, 0 0 6 0, 0 0 7 0; -fx-background-radius: 8; -fx-background-color: linear-gradient(from 0% 93% to 0% 100%, "+shadowColor+" 0%, "+shadowColor+" 100%),        "+shadowColor+",        "+shadowColor+",        radial-gradient(center 50% 50%, radius 100%, "+backgroundColor+", "+backgroundColor+"); -fx-font-weight: bold; -fx-font-size: 1.1em; -fx-font-family: \"Arial\";";
        return IDLE_BUTTON_STYLE;
    }

    public String getHoverButtonStyle(String backgroundColor, String shadowColor){
        String HOVER_BUTTON_STYLE = "-fx-padding: 8 15 15 15; -fx-background-insets: 0 0 7 0,0 0 5 0, 0 0 6 0, 0 0 7 0; -fx-background-radius: 8; -fx-background-color: linear-gradient(from 0% 93% to 0% 100%, "+shadowColor+" 0%, "+shadowColor+" 100%),        "+shadowColor+",        "+shadowColor+",        radial-gradient(center 50% 50%, radius 100%, "+backgroundColor+", "+backgroundColor+"); -fx-font-weight: bold; -fx-font-size: 1.1em; -fx-font-family: \"Arial\";";
        return HOVER_BUTTON_STYLE;
    }

    public String getPressedButtonStyle(String backgroundColor){
        final String PRESSED_BUTTON_STYLE = "-fx-padding: 6 6 6 6; -fx-background-radius: 8; -fx-background-color: "+backgroundColor+"; -fx-font-weight: bold; -fx-font-size: 1.1em; -fx-font-family: \"Arial\";";
        return PRESSED_BUTTON_STYLE;
    }

    public void setButtonEvents(Button b, String id, String backgroundColor, String shadowColor, String hoverColor){
        b.setId(id);
        b.setOnMouseExited(e -> {
            mouseOut = true;
            b.setPrefHeight(41);
            b.setStyle(getIdleButtonStyle(backgroundColor, shadowColor));});
        b.setOnMousePressed(e -> mousePressed(e, b, backgroundColor));
        b.setOnMouseReleased(e -> mouseReleased(e, b, id, backgroundColor, shadowColor));
        b.setOnMouseEntered(e -> b.setStyle(getHoverButtonStyle(hoverColor, shadowColor)));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        alreadyUpdated = false;
        clickLock = new Object();

        ToolCards.setOnMouseReleased((e) -> tcWindow.setVisible(true));
        PublicCards.setOnMouseReleased((e) -> pcWindow.setVisible(true));
        tcClose.setOnMouseReleased((e) -> tcWindow.setVisible(false));
        pcClose.setOnMouseReleased((e) -> pcWindow.setVisible(false));


        lastDieDraft = -1;
        setButtonEvents(insertDieButton, "insertDieButton","#1895d7", "#084c8a", "#00c0ff");
        setButtonEvents(endTurnButton, "endTurnButton","#1895d7", "#084c8a", "#00c0ff");
    }

    public void viewCards(GameBoardClient board){

        int i = 0;

        for(ToolCardClient cardClient: board.getToolCards()){

            if(i==0){

                Platform.runLater(() -> tc1.setImage(new Image(new DynamicPath("assets/ToolCards/"+cardClient.getTitle()+".jpg").getPath())));


            }

            if(i==1){

                Platform.runLater(() ->tc2.setImage(new Image(new DynamicPath("assets/ToolCards/"+cardClient.getTitle()+".jpg").getPath())));


            }

            if(i==2){

                Platform.runLater(() ->tc3.setImage(new Image(new DynamicPath("assets/ToolCards/"+cardClient.getTitle()+".jpg").getPath())));

            }

            i++;

        }

        i=0;

        for(PublicCardClient cardClient: board.getPublicCards()){

            if(i==0){

                Platform.runLater(() -> pc1.setImage(new Image(new DynamicPath("assets/PublicCards/"+cardClient.getTitle()+".jpg").getPath())));


            }

            if(i==1){

                Platform.runLater(() ->pc2.setImage(new Image(new DynamicPath("assets/PublicCards/"+cardClient.getTitle()+".jpg").getPath())));


            }

            if(i==2){

                Platform.runLater(() ->pc3.setImage(new Image(new DynamicPath("assets/PublicCards/"+cardClient.getTitle()+".jpg").getPath())));

            }

            i++;
        }

    }

    public void viewSecretColor(GameBoardClient board){

        Platform.runLater(() -> secretColor.setImage(new Image(new DynamicPath("assets/SecretColors/"+board.getPlayerByName(gV.getUsername()).getSecretColour().toString()+".png").getPath())));

    }

}