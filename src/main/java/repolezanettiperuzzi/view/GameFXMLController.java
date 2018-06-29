package repolezanettiperuzzi.view;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
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

public class GameFXMLController extends FXMLController{

    private Stage stage;
    private GameView gV;
    private int timerCounter;
    private Timeline timerCountdown;
    private String textContent;

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
    private ResourceBundle resources;

    // Add a public no-args constructor
    public GameFXMLController()
    {
    }

    public void setWaitingRoomScene(){

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

    public void viewWindows(GameBoardClient board, int currentTime)  {

        this.setTimer(currentTime);
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
            Button b = new Button();
            b.setGraphic(box);
            xPos = 515; //posiziono ogni gridpane creata alternando la pos X (prima *1 poi *2 poi *1 poi *2)
            b.setLayoutX(xPos);
            yPos = 275;
            b.setLayoutY(yPos);
            b.setStyle("-fx-background-color: transparent;"+"-fx-text-fill: transparent;");
            b.setOnAction(e -> onBoxClick(e,windowName));
            Platform.runLater(() -> {
                ((Group)stage.getScene().getRoot()).getChildren().add(b);
            });

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
}