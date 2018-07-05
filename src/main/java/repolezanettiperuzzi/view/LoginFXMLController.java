package repolezanettiperuzzi.view;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import repolezanettiperuzzi.common.DynamicPath;

/**
 * Classe che rappresenta il Login FXML controller
 * @author Andrea Zanetti
 */
public class LoginFXMLController extends FXMLController{

    private Stage stage;
    private GameView gV;

    @FXML
    // The reference of inputText will be injected by the FXML loader
    private TextField textFieldName;

    // The reference of outputText will be injected by the FXML loader
    @FXML
    private PasswordField textFieldPwd;

    @FXML
    private ToggleGroup groupConn;

    @FXML
    private ToggleGroup groupUI;

    @FXML
    private Button sendButton;

    // location and resources will be automatically injected by the FXML loader
    @FXML
    private URL location;

    @FXML
    private ResourceBundle resources;

    @FXML
    private Rectangle progressBg;

    @FXML
    private Text progressText;

    /**
     * Costruttore
     */
    // Add a public no-args constructor
    public LoginFXMLController()
    {
    }

    /**
     * Inizializza
     */
    @FXML
    private void initialize()
    {
        //setGameView(new GameView());
        gV.setFXMLController(this);
        sendButton.setDefaultButton(true);
        progressBg.setVisible(false);
        progressText.setVisible(false);
    }

    /**
     * Notifica l'uscita
     * @throws IOException  Fallimento o interruzione delle operazioni I/O
     */
    public void notifyOnExit() throws IOException {
        gV.notifyOnExit("login");
    }

    /**
     *
     * @param event Evento
     */
    @FXML
    private void onSubmit(ActionEvent event) {
        this.stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        if ((!textFieldName.getText().equals("") && !textFieldPwd.getText().equals(""))) {

            RadioButton conn = (RadioButton) groupConn.getSelectedToggle();
            RadioButton userInt = (RadioButton) groupUI.getSelectedToggle();
            Platform.runLater(() -> {
                try {
                    gV.onLogin(textFieldName.getText(), textFieldPwd.getText(), conn.getText(), userInt.getText());
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }
            });
        } else {
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.NONE, "Username or password not inserted", ButtonType.OK);
                alert.setX(stage.getX()+stage.getScene().getWidth()/2 - 125);
                alert.setY(stage.getY()+stage.getScene().getHeight()/2 - 60);
                alert.setTitle("Invalid Login");
                alert.setResizable(true);
                alert.getDialogPane().setPrefSize(250, 120);
                alert.setResizable(false);
                textFieldName.clear();
                textFieldPwd.clear();

                alert.showAndWait();
            });
        }
    }

    /**
     * Mostra alert di nome gia usato
     */
    public void showPlayerAlreadyOnlineAlert(){
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.NONE, "The username you chose is already used by a Player online", ButtonType.OK);
            alert.setX(stage.getX()+stage.getScene().getWidth()/2 - 125);
            alert.setY(stage.getY()+stage.getScene().getHeight()/2 - 60);
            alert.setTitle("Player already online");
            alert.setResizable(true);
            alert.getDialogPane().setPrefSize(250, 120);
            alert.setResizable(false);
            textFieldName.clear();
            textFieldPwd.clear();

            alert.showAndWait();
        });
    }

    /**
     * Mostra l'errore nell'inserimento della password
     */
    public void showWrongPwdAlert(){
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.NONE, "Wrong password inserted, please try again", ButtonType.OK);
            alert.setX(stage.getX()+stage.getScene().getWidth()/2 - 125);
            alert.setY(stage.getY()+stage.getScene().getHeight()/2 - 60);
            alert.setTitle("Wrong password");
            alert.setResizable(true);
            alert.getDialogPane().setPrefSize(250, 120);
            alert.setResizable(false);
            textFieldPwd.clear();

            alert.showAndWait();
        });
    }

    /**
     * Mostra l'alert gioco gia iniziato
     */
    public void showGameAlreadyStartedAlert(){
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.NONE, "A game already started, we're sorry. Try again after the end of this game", ButtonType.OK);
            alert.setX(stage.getX()+stage.getScene().getWidth()/2 - 125);
            alert.setY(stage.getY()+stage.getScene().getHeight()/2 - 60);
            alert.setTitle("Game already started");
            alert.setResizable(true);
            alert.getDialogPane().setPrefSize(250, 120);
            alert.setResizable(false);
            textFieldPwd.clear();

            alert.showAndWait();
        });
    }

    /**
     * Mostra l'alert di raggionto numero massimo player
     */
    public void showAlready4PlayersAlert(){
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.NONE, "There are already 4 players online waiting the start of the game. Try again after the end of this game", ButtonType.OK);
            alert.setX(stage.getX()+stage.getScene().getWidth()/2 - 125);
            alert.setY(stage.getY()+stage.getScene().getHeight()/2 - 60);
            alert.setTitle("Already 4 Players online");
            alert.setResizable(true);
            alert.getDialogPane().setPrefSize(250, 120);
            alert.setResizable(false);
            textFieldPwd.clear();

            alert.showAndWait();
        });
    }

    /**
     * Imposta la waiting room
     */
    public void setWaitingRoomScene() {
        WaitingRoomFXMLController controller = new WaitingRoomFXMLController();
        controller.setGameView(gV);
        gV.setFXMLController(controller);
        controller.setStage(stage);
        FXMLLoader loader = null;
        try {
            loader = new FXMLLoader(new URI(new DynamicPath("fxml/WaitingRoomFXML.fxml").getPath()).toURL());
            loader.setController(controller);
            Group root = (Group) loader.load();
            FXMLLoader finalLoader = loader;
            Platform.runLater(() -> {
                stage.setScene(new Scene(root, 600, 600));
                stage.setUserData(finalLoader);
            });
            gV.waitingRoomLoaded();
        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Imposta la scelta delle windows
     */
    public void setChooseWindowScene(){

        ChooseWindowFXMLController controller = new ChooseWindowFXMLController();
        controller.setGameView(gV);
        gV.setFXMLController(controller);
        controller.setStage(stage);
        String currPath = System.getProperty("user.dir");
        FXMLLoader loader = null;
        try {
            loader = new FXMLLoader(new URI(new DynamicPath("fxml/ChooseWindowFXML.fxml").getPath()).toURL());
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

    /**
     * Imposta la scena di gioco
     */
    public void setGameScene(){

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
     * Mostra l'indicatore
     */
    public void showProgressIndicator(){
        Platform.runLater(() -> {
            progressBg.setVisible(true);
            progressText.setVisible(true);
            ProgressIndicator p1 = new ProgressIndicator();
            p1.setId("progress");
            p1.setPrefWidth(100);
            p1.setPrefHeight(100);
            p1.setLayoutX((stage.getScene().getWidth() / 2)-50);
            p1.setLayoutY((stage.getScene().getHeight() / 2)-70);
            p1.setProgress(ProgressIndicator.INDETERMINATE_PROGRESS);
            Group root = (Group) stage.getScene().getRoot();
            root.getChildren().add(p1);
        });
    }

    /**
     *  Rimuove l'indicatore
     */
    public void removeProgressIndicator(){
        Platform.runLater(() -> {
            Group root = (Group) stage.getScene().getRoot();
            root.getChildren().remove(root.lookup("#progress"));
            progressBg.setVisible(false);
            progressText.setVisible(false);
        });
    }

    /**
     * Imposta la game view
     * @param gV Game view
     */
    public void setGameView(GameView gV){
        this.gV = gV;
    }
}