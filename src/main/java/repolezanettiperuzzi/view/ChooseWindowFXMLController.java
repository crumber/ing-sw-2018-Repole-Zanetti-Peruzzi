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
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import repolezanettiperuzzi.view.modelwrapper.WindowClient;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
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

    public void viewWindows(ArrayList<WindowClient> windows){

        

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
        FXMLLoader loader = null;
        try {
            loader = new FXMLLoader(new File("fxml/WaitingRoomFXML.fxml").toURI().toURL());
            loader.setController(controller);
            Group root = (Group) loader.load();
            FXMLLoader finalLoader = loader;
            Platform.runLater(() -> {
                stage.setScene(new Scene(root, 600, 600));
                stage.setUserData(finalLoader);
            });
            gV.waitingRoomLoaded();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}