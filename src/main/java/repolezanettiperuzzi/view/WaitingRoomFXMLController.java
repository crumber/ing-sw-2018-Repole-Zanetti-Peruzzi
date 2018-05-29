package repolezanettiperuzzi.view;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

public class WaitingRoomFXMLController extends FXMLController{

    private Stage stage;
    private GameView gV;
    private static int timerCounter;
    private Timer timer;
    private static String textContent;

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
        this.timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                if(timerCounter > 0)
                {
                    Platform.runLater(() -> timerText.setText("Timer: "+timerCounter));
                    timerCounter--;
                }
                else
                    timer.cancel();
                    timer = null;
            }
        }, 0,1000);
    }

    public void cancelTimer(){
        if(this.timer != null){
            timer.cancel();
        }
    }

    public void refreshPlayers(int timerDuration, String[] players){
        if(timerDuration>0){
            if(this.timer!=null){
                this.timer.cancel();
            }
            setTimer(timerDuration);
        }

        textContent = "";
        for(int i = 0; i<players.length; i++){
            textContent += players[i]+"\n";
        }
        Platform.runLater(() -> playersText.setText(textContent));
    }
}