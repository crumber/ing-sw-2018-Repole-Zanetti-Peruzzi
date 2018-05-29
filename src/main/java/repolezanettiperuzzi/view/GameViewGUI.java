package repolezanettiperuzzi.view;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import repolezanettiperuzzi.controller.HandlerSkeletonRMI;
import repolezanettiperuzzi.controller.HandlerStubRMI;

import java.io.File;
import java.io.FileInputStream;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class GameViewGUI extends Application{

    Stage stage;

    public void updateView() {
        //aggiorno View
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        this.stage = primaryStage;

        // Create the FXMLLoader
        FXMLLoader loader = new FXMLLoader(new File("src/main/java/repolezanettiperuzzi/view/LoginFXML.fxml").toURI().toURL());

        // Create the Pane and all Details
        Group root = (Group) loader.load();

        // Create the Scene
        Scene scene = new Scene(root, 600 , 600);

        primaryStage.getIcons().add(new Image(new File("assets/icon.png").toURI().toString()));

        // Set the Scene to the Stage
        primaryStage.setScene(scene);
        // Set the Title to the Stage
        primaryStage.setTitle("Sagrada");
        // Display the Stage
        primaryStage.show();

    }

    @Override
    public void stop(){
        System.out.println("Closing GUI");
        if(stage.getUserData()!=null) {
            FXMLLoader loader = (FXMLLoader) stage.getUserData();
            FXMLController controller = loader.getController();
            if(controller instanceof WaitingRoomFXMLController) {
                ((WaitingRoomFXMLController) controller).cancelTimer();
            }
        }

    }
}
