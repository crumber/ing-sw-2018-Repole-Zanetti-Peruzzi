package repolezanettiperuzzi.view;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import repolezanettiperuzzi.common.DynamicPath;
import repolezanettiperuzzi.view.modelwrapper.WindowClient;

import javax.swing.text.html.ImageView;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ChooseWindowFXMLController extends FXMLController{

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
    public ChooseWindowFXMLController()
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
        gV.notifyOnExit("chooseWindow");
    }

    public void viewWindows(ArrayList<WindowClient> windows)  {

        int j = 0;
        int xPos = 0;
        int yPos = 0;
        for(int i = 0; i<windows.size(); i++) {
            //System.out.println("Generate window: "+i);
            WindowGenerator wGenerator = new WindowGenerator(windows.get(i));
            //Esempio con GridPane
            GridPane pane = wGenerator.getWindowFXObject();
            VBox box = new VBox();
            javafx.scene.image.ImageView windowLabel = new javafx.scene.image.ImageView(new Image(new DynamicPath("assets/Windows/"+windows.get(i).getName()+".png").getPath()));
            windowLabel.setFitWidth(250);
            windowLabel.setPreserveRatio(true);
            windowLabel.setSmooth(true);
            windowLabel.setCache(true);
            box.getChildren().addAll(pane,windowLabel);
            xPos = 100+(300*((i%2))); //posiziono ogni gridpane creata alternando la pos X (prima *1 poi *2 poi *1 poi *2)
            //System.out.println("xPos: "+xPos);
            box.setLayoutX(xPos);
            if(i%2==0){  //incrememnto il moltiplicatore della pos Y ogni 2 cicli
                yPos = 150+(300*j);
                j++;

            }
            //System.out.println("yPos: "+yPos);
            box.setLayoutY(yPos);
            Platform.runLater(() -> {
                ((Group)stage.getScene().getRoot()).getChildren().add(box);
            });

        }

    }

    public void setChooseWindowScene(){

        this.cancelTimer();
        Platform.runLater(() -> {
            timerText.setX(stage.getScene().getWidth()/2);
            timerText.setY(stage.getScene().getHeight()/2);
            timerText.setText("loading choose window scene");
        });

        ChooseWindowFXMLController controller = new ChooseWindowFXMLController();
        controller.setGameView(gV);
        gV.setFXMLController(controller);
        controller.setStage(stage);
        String currPath = System.getProperty("user.dir");
        FXMLLoader loader = null;
        try {
            loader = new FXMLLoader(new URI(new DynamicPath("fxml/ChooseWindowFXML.fxml").getPath()).toURL()); //TODO imposta nuovo controller
            loader.setController(controller);
            Group root = (Group) loader.load();
            FXMLLoader finalLoader = loader;
            Platform.runLater(() -> {
                stage.setScene(new Scene(root, 600, 600));
                stage.setUserData(finalLoader);
            });
            gV.waitingRoomLoaded();
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }

    }
}