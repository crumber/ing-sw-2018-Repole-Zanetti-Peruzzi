package repolezanettiperuzzi.view;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

public class WaitingRoomFXMLController extends FXMLController{

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
    public WaitingRoomFXMLController()
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
        gV.notifyOnExit("waitingRoom");
    }

    public void refreshPlayers(int timerDuration, String[] players){
        if(timerDuration>0){
            if(this.timerCountdown!=null){
                this.timerCountdown.stop();
                this.timerCountdown = null;
            }
            setTimer(timerDuration);
        } else if (timerDuration==-1){
            if(this.timerCountdown!=null){
                this.timerCountdown.stop();
                this.timerCountdown = null;
            }
            Platform.runLater(() -> timerText.setText(""));
        }

        textContent = "";
        for(int i = 0; i<players.length; i++){
            textContent += players[i]+"\n";
        }
        Platform.runLater(() -> playersText.setText(textContent));
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
            URI checkJar = GameViewGUI.class.getResource("WaitingRoomFXMLController.class").toURI();
            if (checkJar.getScheme().equals("jar")) {
                String jarName = new File(GameViewGUI.class.getProtectionDomain().getCodeSource().getLocation().getPath()).getName();
                loader = new FXMLLoader(new URI("jar:file:"+currPath+"/"+jarName+"!/fxml/ChooseWindowFXML.fxml").toURL());
            } else {
                loader = new FXMLLoader(new File("fxml/ChooseWindowFXML.fxml").toURI().toURL());
            }
            loader.setController(controller);
            Group root = (Group) loader.load();
            FXMLLoader finalLoader = loader;
            Platform.runLater(() -> {
                stage.setScene(new Scene(root, 1024, 768));
                stage.setUserData(finalLoader);
            });
            gV.chooseWindowSceneLoaded();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

    }
}