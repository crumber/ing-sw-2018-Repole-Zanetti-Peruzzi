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

import java.io.File;
import java.io.IOException;
import java.net.URI;

public class GameViewGUI extends Application{

    Stage stage;
    public static GameView gameView;

    public void updateView() {
        //aggiorno View
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        this.stage = primaryStage;

        // Create the FXMLLoader
        FXMLLoader loader = new FXMLLoader(new URI(new DynamicPath("fxml/LoginFXML.fxml").getPath()).toURL());

        LoginFXMLController controller = new LoginFXMLController();
        controller.setGameView(gameView);
        gameView.setFXMLController(controller);
        loader.setController(controller);

        // Create the Pane and all Details
        Group root = (Group) loader.load();

        // Create the Scene
        Scene scene = new Scene(root, 600 , 600);

        primaryStage.getIcons().add(new Image(new File("assets/icon.png").toURI().toString()));

        MenuBar menuBar = new MenuBar();

        // --- Menu File
        Menu menuFile = new Menu("About"); //TODO metti info programma

        menuBar.getMenus().addAll(menuFile);

        final String os = System.getProperty("os.name");
        if (os != null && os.startsWith("Mac"))
            menuBar.useSystemMenuBarProperty().set(true);

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
            if(controller instanceof WaitingRoomFXMLController) {
                ((WaitingRoomFXMLController) controller).cancelTimer();
                ((WaitingRoomFXMLController) controller).notifyOnExit();
            } else if(controller instanceof ChooseWindowFXMLController){
                //((ChooseWindowFXMLController) controller).cancelTimer(); //TODO cancellare anche il timer
                ((ChooseWindowFXMLController) controller).notifyOnExit();
            }//TODO fare else if per gli altri controller con la notifyOnExit()
        }

    }
}
