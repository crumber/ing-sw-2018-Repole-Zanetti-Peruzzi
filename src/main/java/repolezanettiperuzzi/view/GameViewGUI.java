package repolezanettiperuzzi.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import repolezanettiperuzzi.common.DynamicPath;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
/**
 * Classe che modella la GUI
 * @author Andrea Zanetti
 * @author Giampiero Repole
 */
public class GameViewGUI extends Application{

    Stage stage;
    public static GameView gameView;

    /**
     * Aggiorno view
     */
    public void updateView() {
        //aggiorno View
    }

    /**
     * Metodo che si lancia quando si chiama javafx
     * @param primaryStage Finestra della fase iniziale
     * @throws Exception Eccezione e
     */
    @Override
    public void start(Stage primaryStage) throws Exception {

        this.stage = primaryStage;

        // Create the FXMLLoader
        FXMLLoader loader = new FXMLLoader(new URI(new DynamicPath("fxml/LoginFXML.fxml").getPath()).toURL());

        LoginFXMLController controller = new LoginFXMLController();
        controller.setGameView(gameView);
        gameView.setFXMLController(controller);
        loader.setController(controller);
        stage.setUserData(loader);

        // Create the Pane and all Details
        Group root = (Group) loader.load();

        // Create the Scene
        Scene scene = new Scene(root, 600 , 600);

        primaryStage.getIcons().add(new javafx.scene.image.Image(new File("assets/icon.png").toURI().toString()));

        MenuBar menuBar = new MenuBar();

        // --- Menu File
        Menu menuFile = new Menu("About"); //TODO metti info programma

        menuBar.getMenus().addAll(menuFile);

        final String os = System.getProperty("os.name");
        /*if (os != null && os.startsWith("Mac")) {
            menuBar.useSystemMenuBarProperty().set(true);
            java.awt.Image image = new ImageIcon(new URL(new DynamicPath("assets/icon.png").getPath())).getImage();
            com.apple.eawt.Application.getApplication().setDockIconImage(image);
        }*/

        root.getChildren().addAll(menuBar);

        // Set the Scene to the Stage
        primaryStage.setScene(scene);
        // Set the Title to the Stage
        primaryStage.setTitle("Sagrada");
        // Not resizable
        primaryStage.setResizable(false);
        // Display the Stage
        primaryStage.show();

    }

    @Override
    public void stop() throws IOException {
        System.out.println("Closing GUI");
        if(stage.getUserData()!=null) {
            FXMLLoader loader = (FXMLLoader) stage.getUserData();
            FXMLController controller = loader.getController();
            if(controller instanceof LoginFXMLController) {
                ((LoginFXMLController) controller).notifyOnExit();
            }else if(controller instanceof WaitingRoomFXMLController) {
                ((WaitingRoomFXMLController) controller).cancelTimer();
                ((WaitingRoomFXMLController) controller).notifyOnExit();
            } else if(controller instanceof ChooseWindowFXMLController){
                ((ChooseWindowFXMLController) controller).cancelTimer(); //TODO cancellare anche il timer
                ((ChooseWindowFXMLController) controller).notifyOnExit();
            } else if(controller instanceof GameFXMLController){
                ((GameFXMLController) controller).cancelTimer();
                ((GameFXMLController) controller).notifyOnExit();
            }//TODO fare else if per gli altri controller con la notifyOnExit()
        }

    }
}
