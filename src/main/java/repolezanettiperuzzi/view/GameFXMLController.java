package repolezanettiperuzzi.view;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.HorizontalDirection;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;
import repolezanettiperuzzi.common.DynamicPath;
import repolezanettiperuzzi.common.modelwrapper.GameBoardClient;
import repolezanettiperuzzi.common.modelwrapper.PlayerClient;
import repolezanettiperuzzi.common.modelwrapper.WindowClient;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class GameFXMLController extends FXMLController implements Initializable{

    private Stage stage;
    private GameView gV;
    private int timerCounter;
    private Timeline timerCountdown;
    private String textContent;
    private boolean mouseOut;
    private static final String CLICKED_BUTTON_STYLE = "-fx-padding: 6 6 6 6; -fx-background-radius: 8; -fx-background-color: #1895d7; -fx-font-weight: bold; -fx-font-size: 1.1em; -fx-font-family: \"Arial\";";
    private static final String IDLE_BUTTON_STYLE = "-fx-padding: 8 15 15 15; -fx-background-insets: 0 0 7 0,0 0 5 0, 0 0 6 0, 0 0 7 0; -fx-background-radius: 8; -fx-background-color: linear-gradient(from 0% 93% to 0% 100%, #084c8a 0%, #084c8a 100%),        #084c8a,        #084c8a,        radial-gradient(center 50% 50%, radius 100%, #1895d7, #1895d7); -fx-font-weight: bold; -fx-font-size: 1.1em; -fx-font-family: \"Arial\";";
    private static final String HOVER_BUTTON_STYLE = "-fx-padding: 8 15 15 15; -fx-background-insets: 0 0 7 0,0 0 5 0, 0 0 6 0, 0 0 7 0; -fx-background-radius: 8; -fx-background-color: linear-gradient(from 0% 93% to 0% 100%, #084c8a 0%, #084c8a 100%),        #084c8a,        #084c8a,        radial-gradient(center 50% 50%, radius 100%, #00c0ff, #00c0ff); -fx-font-weight: bold; -fx-font-size: 1.1em; -fx-font-family: \"Arial\";";

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
    private Button insertDieButton;

    @FXML
    private Button endTurnButton;

    @FXML
    private ResourceBundle resources;

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

    public void updateView(GameBoardClient board, int currentTime){
        //this.setTimer(currentTime);
        viewWindows(board);
    }

    public void viewWindows(GameBoardClient board)  {

        int j = 0;
        int xPos ;
        int yPos = 0;

        for(PlayerClient player : board.getPlayers() ) {

            WindowGenerator wGenerator = new WindowGenerator(player.getWindow());
            //Esempio con GridPane
            GridPane pane = wGenerator.getWindowFXObject(true);
            VBox box = new VBox();
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
        setButtonEvents(insertDieButton, "insertDieButton","#1895d7", "#084c8a", "#00c0ff");
        setButtonEvents(endTurnButton, "endTurnButton","#1895d7", "#084c8a", "#00c0ff");
    }
}