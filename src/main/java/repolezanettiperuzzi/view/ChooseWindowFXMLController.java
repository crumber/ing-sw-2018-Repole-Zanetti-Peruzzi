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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Duration;
import repolezanettiperuzzi.common.DynamicPath;
import repolezanettiperuzzi.common.modelwrapper.WindowClient;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Classe che modellizza la scelta delle window
 * @author Andrea Zanetti
 * @author Giampiero Repole
 */
public class ChooseWindowFXMLController extends FXMLController{

    private Stage stage;
    private GameView gV;
    private int timerCounter;
    private Timeline timerCountdown;
    private String textContent;
    private boolean alreadyClicked = false;

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

    /**
     * Inizializza la game view
     * @param gV Game view
     */
    public void setGameView(GameView gV){
        this.gV = gV;
    }

    /**
     * Inizializza la stage
     * @param stage Vista che visualizza tutta la grafica finestra
     */
    public void setStage(Stage stage){
        this.stage = stage;
    }

    /**
     * Inizializza il timer
     * @param timerDuration Intero che indica la durata del timer
     */
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

    /**
     * Cancella il timer
     */
    public void cancelTimer(){
        if(this.timerCountdown != null){
            timerCountdown.stop();
            timerCountdown = null;
        }
    }

    /**
     * Notifica l'uscita
     * @throws IOException Fallimento o interruzione delle operazioni I/O
     */
    public void notifyOnExit() throws IOException {
        gV.notifyOnExit("chooseWindow");
    }

    /**
     * Mostra che hai vinto nella scelta delle window perchè gli altri si sono scollegati
     */
    public void showWinOnChooseWindowAlert(){
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.NONE, "You won! Seems like you're the only player left online!", ButtonType.OK);
            alert.setX(stage.getX()+stage.getScene().getWidth()/2 - 125);
            alert.setY(stage.getY()+stage.getScene().getHeight()/2 - 60);
            alert.setTitle("You won!");
            alert.setResizable(true);
            alert.getDialogPane().setPrefSize(250, 120);
            alert.setResizable(false);

            alert.showAndWait();
        });
    }

    /**
     * Mostra le window tra cui scegliere la propria window al cliente
     * @param windows ArrayList delle window del client
     * @param currentTime Intero che indica valore timer corrente
     */
    public void viewWindows(ArrayList<WindowClient> windows, int currentTime)  {

        this.setTimer(currentTime);
        int j = 0;
        int xPos = 0;
        int yPos = 0;
        for(int i = 0; i<windows.size(); i++) {

            WindowGenerator wGenerator = new WindowGenerator(windows.get(i));
            //Esempio con GridPane
            GridPane pane = wGenerator.getWindowFXObject(false);
            VBox box = new VBox();
            String windowName = windows.get(i).getName().replace(" ", "-"); //questo per gestire in caso la window arrivi da RMI in cui il nome e' senza trattini
            javafx.scene.image.ImageView windowLabel = new javafx.scene.image.ImageView(new Image(new DynamicPath("assets/Windows/"+windowName+".png").getPath()));
            windowLabel.setFitWidth(50*windows.get(i).getBoardBox()[0].length);
            windowLabel.setPreserveRatio(true);
            windowLabel.setSmooth(true);
            windowLabel.setCache(true);
            box.getChildren().addAll(pane,windowLabel);
            Button b = new Button();
            b.setId(i+"");
            b.setGraphic(box);
            xPos = 175+(300*((i%2))); //posiziono ogni gridpane creata alternando la pos X (prima *1 poi *2 poi *1 poi *2)
            b.setLayoutX(xPos);
            if(i%2==0){  //incrememnto il moltiplicatore della pos Y ogni 2 cicli
                yPos = 150+(300*j);
                j++;

            }
            b.setLayoutY(yPos);
            b.setStyle("-fx-background-color: transparent;"+"-fx-text-fill: transparent;");
            b.setOnAction(e -> onWindowClick(e,windowName));
            Platform.runLater(() -> {
                ((Group)stage.getScene().getRoot()).getChildren().add(b);
            });

        }

    }

    /**
     * Mostra la window del client
     * @param window Window scelta dal client
     * @param currentTime Intero che indica il valore del current time
     */
    public void viewOneWindow(WindowClient window, int currentTime)  {

        this.setTimer(currentTime);
        int xPos = 0;
        int yPos = 0;
        WindowGenerator wGenerator = new WindowGenerator(window);

        GridPane pane = wGenerator.getWindowFXObject(false);
        VBox box = new VBox();
        String windowName = window.getName().replace(" ", "-"); //questo per gestire in caso la window arrivi da RMI in cui il nome e' senza trattini
        javafx.scene.image.ImageView windowLabel = new javafx.scene.image.ImageView(new Image(new DynamicPath("assets/Windows/"+windowName+".png").getPath()));
        windowLabel.setFitWidth(50*window.getBoardBox()[0].length);
        windowLabel.setPreserveRatio(true);
        windowLabel.setSmooth(true);
        windowLabel.setCache(true);
        box.getChildren().addAll(pane,windowLabel);
        xPos = 200; //posiziono ogni gridpane creata alternando la pos X (prima *1 poi *2 poi *1 poi *2)
        box.setLayoutX(xPos);
        yPos = 200;
        box.setLayoutY(yPos);
        Platform.runLater(() -> ((Group)stage.getScene().getRoot()).getChildren().add(box));

    }

    /**
     * Invia la window scelta
     * @param windowName Nome della window
     * @throws IOException Fallimento o interruzione delle operazioni I/O
     */
    public void sendChosenWindow(String windowName) throws IOException {
        gV.sendChosenWindow(windowName);
    }

    /**
     * Inizializza la game scene
     */
    public void setGameScene(){
        this.cancelTimer();

        GameFXMLController controller = new GameFXMLController();
        controller.setGameView(gV);
        gV.setFXMLController(controller);
        controller.setStage(stage);
        FXMLLoader loader = null;
        try {
            loader = new FXMLLoader(new URI(new DynamicPath("fxml/GameFXML.fxml").getPath()).toURL()); //TODO imposta nuovo controller
            loader.setController(controller);
            AnchorPane root = (AnchorPane) loader.load();
            FXMLLoader finalLoader = loader;
            Platform.runLater(() -> {
                stage.setScene(new Scene(root, 1280, 800));
                //stage.setMaximized(true);
                stage.setFullScreen(true);
                //Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
                //stage.setX(primaryScreenBounds.getMinX());
                //stage.setY(primaryScreenBounds.getMinY());
                stage.setWidth(stage.getWidth());
                stage.setHeight(stage.getHeight()-22);
                stage.setUserData(finalLoader);
            });
            gV.gameLoaded();
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }

    }

    /**
     * Quando scegli una finestra
     * @param e Azione
     * @param windowName Nome stringa
     */
    public void onWindowClick (ActionEvent e, String windowName){

        if(!alreadyClicked) {

            Button b = (Button) e.getSource();
            b.setStyle(".button:pressed {\n" +
                    "    -fx-background-color: black;\n" +
                    "    -fx-text-fill: transparent;\n" +
                    "}");

            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.NONE, "Do you want to choose this window?", ButtonType.YES, ButtonType.NO);
                alert.setX(stage.getX() + stage.getScene().getWidth() / 2 - 125);
                alert.setY(stage.getY() + stage.getScene().getHeight() / 2 - 60);
                alert.setTitle("Confirm Window");
                alert.setResizable(true);
                alert.getDialogPane().setPrefSize(250, 120);
                alert.setResizable(false);
                alert.showAndWait();

                if (alert.getResult() == ButtonType.YES) {

                    try {
                        alreadyClicked = true;
                        this.sendChosenWindow(windowName);
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }

                } else {

                    b.setStyle("-fx-background-color: transparent;" + "-fx-text-fill: transparent;");
                }
            });

        }

    }
}