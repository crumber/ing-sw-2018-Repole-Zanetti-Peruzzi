package repolezanettiperuzzi.view;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;
import repolezanettiperuzzi.common.DynamicPath;
import repolezanettiperuzzi.common.modelwrapper.DieClient;
import repolezanettiperuzzi.common.modelwrapper.GameBoardClient;
import repolezanettiperuzzi.common.modelwrapper.PlayerClient;
import repolezanettiperuzzi.common.modelwrapper.*;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.*;

/**
 * Classe che rappresenta il controller del game
 * @author Andrea Zanetti
 * @author Giampiero Repole
 */
public class GameFXMLController extends FXMLController implements Initializable{

    private Stage stage;
    private GameView gV;
    private int timerCounter;
    private Timeline timerCountdown;
    private String textContent;
    private boolean mouseOut;
    private boolean alreadyUpdated;
    private Object clickLock;
    private ArrayList<Coordinates> lastWindowCells;
    private int lastDieDraft;
    private String lastDieRT;
    private boolean myTurn;
    private int numSelectableCells;
    private boolean incrToolCard;
    private String[] cardParameters;
    private int lastToolCard;
    private GridPane myWindow;

    @FXML
    // The reference of inputText will be injected by the FXML loader
    private Text playersText, currentTurn;

    @FXML
    // The reference of inputText will be injected by the FXML loader
    private Text timerText;

    // location and resources will be automatically injected by the FXML loader
    @FXML
    private URL location;

    @FXML
    private AnchorPane playerWindow;

    @FXML
    private GridPane draftPane, playersButtons;

    @FXML
    private Button insertDieButton;

    @FXML
    private Button endTurnButton;

    @FXML
    private ResourceBundle resources;

    @FXML
    private AnchorPane favorTokensPane;

    @FXML
    private ImageView tc1,tc2,tc3,pc1,pc2,pc3,secretColor;

    @FXML
    private VBox pcWindow, tcWindow,RTWindow;

    @FXML
    private Button ToolCards, PublicCards, tcClose, pcClose, currentPlayerButton, roundTrackButton, closeRoundTrack;

    @FXML
    private HBox RTGrids;

    @FXML
    private ImageView myTurnMarker;

    @FXML
    private Rectangle R1, R2, R3, R4, R5, R6, R7, R8, R9, R10;

    @FXML
    private Button tc1Button, tc2Button, tc3Button;

    @FXML
    private Button useCard;

    // Add a public no-args constructor
    public GameFXMLController()
    {
    }

    /**
     * Inizializza la game view
     * @param gV Game view
     */
    public void setGameView(GameView gV){
        this.gV = gV;
    }

    /**
     * Inizializza lo stage
     * @param stage Vista che visualizza tutta la grafica finestra
     */
    public void setStage(Stage stage){
        this.stage = stage;
    }

    /**
     * Inizializza il timer
     * @param timerDuration Intero che rappresenta il valore del timer
     */
    public void setTimer(int timerDuration){

        timerCounter = timerDuration-1;
        timerCountdown = new Timeline(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                timerText.setText(""+timerCounter);
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
        gV.notifyOnExit("game");
    }

    /**
     * Inizializza gli ultimi dadi del draft
     * @param lastDieDraft Ultimo dado/i sul draft
     */
    public void setLastDieDraft(int lastDieDraft){

        this.lastDieDraft = lastDieDraft;

    }

    /**
     * Inizializza
     * @param lastDieRT Stringa che rappresenta l'ultimo dado Round track
     */
    public void setLastDieRT(String lastDieRT){

        this.lastDieRT=lastDieRT;

    }

    /**
     *
     * @return Il turno personale del player
     */
    public boolean getMyTurn(){
        return this.myTurn;
    }

    /**
     * Aggiornamento view
     * @param board Game board
     */
    public void updateView(GameBoardClient board){
        synchronized (clickLock) {
            //this.setTimer(currentTime);
            ArrayList<Node> nodesToDeleteWindow = new ArrayList<>();
            ArrayList<Node> nodesToDeleteDraft = new ArrayList<>();
            ArrayList<Node> nodesToDeleteFT = new ArrayList<>();
            ArrayList<ArrayList<Node>> nodesToDeleteRT = new ArrayList<>();
            lastDieDraft = -1;
            lastDieRT = "";
            lastWindowCells = new ArrayList<>();
            numSelectableCells = 1;
            incrToolCard = false;
            //lastToolCard = 0;

            if (alreadyUpdated) {

                for(Node node: playersButtons.getChildren()){

                    if((node.getId()!=null) && !node.getId().contains("turnMarker") && board.getPlayerByName(node.getId()).getLiveStatus()){

                        setButtonEvents((Button) node, node.getId(), "#3cd83f", "#06a008", "#09ea0d");

                    }else if((node.getId()!=null) && !node.getId().contains("turnMarker") && !board.getPlayerByName(node.getId()).getLiveStatus()){

                        setButtonEvents((Button) node, node.getId(), "#e81e1e","#c10303","#ff0707");

                    }
                }

                ObservableList<Node> childrenWindow = playerWindow.getChildren();
                ObservableList<Node> draftChildren = draftPane.getChildren();
                ObservableList<Node> fvChildren = favorTokensPane.getChildren();
                ObservableList<Node> RTChildren = RTGrids.getChildren();

                for (Node node : childrenWindow) {
                    if ((node.getId() != null) && node.getId().contains("gridWindow")) {
                        nodesToDeleteWindow.add(node);
                    }
                }

                for (Node node : draftChildren) {
                    nodesToDeleteDraft.add(node);
                }

                for (Node node : fvChildren) {
                    nodesToDeleteFT.add(node);
                }

                for (Node node : RTChildren){

                    ArrayList<Node> gridChild = new ArrayList<>();

                    gridChild.addAll(((GridPane) node).getChildren());

                    nodesToDeleteRT.add(gridChild);

                }


            }else{

                tc1Button.setId("tc1Button");
                tc2Button.setId("tc2Button");
                tc3Button.setId("tc3Button");
                int i=0;

                for (PlayerClient player : board.getPlayers()) {

                    if(!player.getName().equals(gV.getUsername())) {

                        Button button = new Button();
                        button.setText(player.getName());
                        button.setPrefSize(116, 41);
                        setButtonEvents(button, player.getName(), "#3cd83f", "#06a008", "#09ea0d");
                        Integer I = new Integer(i);
                        getNodeByRowColumnIndex(i, 1, playersButtons).setId("turnMarker"+player.getName());
                        Platform.runLater(() -> playersButtons.add(button, 0, I));
                        i++;

                    }

                }
            }


            Platform.runLater(() -> {
                tc1Button.setText("Pay "+board.getToolCards().get(0).getFavorTokens()+" FT");
                tc2Button.setText("Pay "+board.getToolCards().get(1).getFavorTokens()+" FT");
                tc3Button.setText("Pay "+board.getToolCards().get(2).getFavorTokens()+" FT");
            });

            viewWindows(board);
            viewDraft(board);
            viewRoundTrack(board);
            viewCards(board);
            viewSecretColor(board);
            viewFavorTokens(board);
            setRoundRectangle(board);
            setCurrentTurn(board);
            //viewDraft();
            alreadyUpdated = true;

            for (Node n : nodesToDeleteWindow) {

                Platform.runLater(() -> playerWindow.getChildren().remove(n));

            }

            for (Node n : nodesToDeleteDraft) {

                Platform.runLater(() -> draftPane.getChildren().remove(n));

            }

            for (Node n : nodesToDeleteFT) {

                Platform.runLater(() -> favorTokensPane.getChildren().remove(n));

            }

            for (int i = 0; i<nodesToDeleteRT.size(); i++) {

                Integer I = new Integer(i);

                for(Node node: nodesToDeleteRT.get(i)) {

                    Platform.runLater(() -> ((GridPane)(RTGrids.getChildren()).get(I)).getChildren().remove(node));

                }
            }

        }
    }

    /**
     * Notifica il turno
     * @param actualPlayer Stringa che indica il player attuale
     * @param currentTime Vlore timer corrente
     */
    public void notifyTurn(String actualPlayer, int currentTime){
        cancelTimer();
        setTimer(currentTime);
        if(actualPlayer.equals(gV.getUsername())){
            myTurn = true;
            for (Node node : playersButtons.getChildren()) {
                if ((node.getId() != null) && node.getId().contains("turnMarker")) {
                    node.setVisible(false);
                }
            }
            myTurnMarker.setVisible(true);
        } else {
            myTurn = false;
            for (Node node : playersButtons.getChildren()) {
                if ((node.getId() != null) && node.getId().contains("turnMarker"+actualPlayer)) {
                    node.setVisible(true);
                } else if ((node.getId() != null) && node.getId().contains("turnMarker")) {
                    node.setVisible(false);
                }
            }
            myTurnMarker.setVisible(false);
        }
    }

    /**
     * Mostra draft
     * @param board Game Board
     */
    public void viewDraft(GameBoardClient board){
        int j = 0;
        for(int i = 0; i<board.getSizeDraft(); i++){
            DieClient die = board.getDieDraft(i);
            Image dieImage = new Image(new DynamicPath("assets/dice/"+die.toString()+".png").getPath());
            ImageView dieView = new ImageView(dieImage);
            dieView.setFitWidth(40);
            dieView.setFitHeight(40);
            Integer I = new Integer(i);
            Integer J = new Integer(j);
            Platform.runLater(() -> draftPane.add(dieView, (I%3), J));
            setDraftEvents(i, j, draftPane, dieView,40,40);
            if(i==2 || i==5 || i==8){
                j++;
            }

        }
    }

    /**
     * Mostra il roundtrack
     * @param board Game board
     */
    public void viewRoundTrack(GameBoardClient board){

        ObservableList<Node> childrenRoundTrack = RTGrids.getChildren();

        for(int i = 0; i<board.sizeRoundTrack(); i++){

            int k = 0;
            for(int j=0; j<board.sizeDiceRoundTrack(i); j++){

                DieClient die = board.getDieFromRoundTrack(i,j);
                Image dieImage = new Image(new DynamicPath("assets/dice/"+die.toString()+".png").getPath());
                ImageView dieView = new ImageView(dieImage);
                dieView.setFitWidth(30);
                dieView.setFitHeight(30);
                Integer I = new Integer(i);
                Integer J = new Integer(j);
                Integer K = new Integer(k);
                Platform.runLater(() -> ((GridPane)childrenRoundTrack.get(I)).add(dieView,(J%3),K));
                setRoundTrackEvents(J,K, (GridPane)childrenRoundTrack.get(i), dieView, 30,30);
                if(j==2 || j==5 || j==8){
                    k++;
                }
            }
        }

    }

    /**
     * Restituisce la cella della griglia
     * @param row Indica quale criga
     * @param column Indica quale colonna
     * @param gridPane Indica la griglia
     * @return Nodo della griglia (una cella)
     */
    public Node getNodeByRowColumnIndex (final int row, final int column, GridPane gridPane) {
        Node result = null;
        ObservableList<Node> childrens = gridPane.getChildren();

        for (Node node : childrens) {
            if((GridPane.getRowIndex(node) != null && GridPane.getColumnIndex(node) != null) && GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == column) {
                result = node;
                break;
            }
        }

        return result;
    }

    /**
     * Mostra l'errore
     * @param error Stringa che indica il codice d'errore
     */
    public void viewError(String error){
        showAlert("Illegal Action", error);
        lastDieDraft = -1;
        lastDieRT = "";
        lastWindowCells = new ArrayList<>();
        numSelectableCells = 1;
        incrToolCard = false;
        //lastToolCard = 0;
        synchronized (clickLock) {  //resetto selezioni nel draft, roundtrack e window
            ObservableList<Node> childrens = draftPane.getChildren();
            for (Node node : childrens) {               //resetto selezioni draft
                if ((node.getId() != null) && (node.getId().contains("dieDraft"))) {
                    Rectangle r = (Rectangle) node;
                    r.setVisible(false);
                    r.setStrokeWidth(0);
                    r.setOpacity(0.5);
                }
            }
            childrens = RTGrids.getChildren();
            for(Node grid : childrens) {                //resetto selezioni roundtrack
                ObservableList<Node> cells = ((GridPane)grid).getChildren();
                for (Node node : cells) {
                    if((node.getId() != null) && (node.getId().contains("dieRT"))) {
                        Rectangle r = (Rectangle) node;
                        r.setVisible(false);
                        r.setStrokeWidth(0);
                        r.setOpacity(0.5);
                    }
                }
            }
            childrens = myWindow.getChildren();
            for (Node node : childrens) {               //resetto selezioni window
                if ((node.getId() != null) && (node.getId().contains("Rect"))) {
                    Rectangle r = (Rectangle) node;
                    r.setStrokeWidth(0);
                    r.setOpacity(0.5);
                    r.setVisible(false);
                }
            }
        }
    }

    /**
     * Mostra che non è il tuo turno
     */
    public void notYourTurn(){
        String notYourTurn = "Not your turn";
        showAlert(notYourTurn, notYourTurn+"!");
    }

    /**
     * Metodo che mostra quando clicchi
     * @param grid Indica la griglia
     * @param i Intero
     * @param rect Rettangolo
     */
    public void onClickDieDraft(GridPane grid, int i, Rectangle rect){
        if(myTurn) {
            synchronized (clickLock) {
                ObservableList<Node> childrens = grid.getChildren();
                for (Node node : childrens) {
                    if ((node.getId() != null) && (node.getId().equals("dieDraft" + lastDieDraft))) {

                        Rectangle r = (Rectangle) node;
                        r.setVisible(false);
                        r.setStrokeWidth(0);
                        r.setOpacity(0.5);
                    }
                }
                setLastDieDraft(i);
                rect.setFill(Color.TRANSPARENT);
                rect.setOpacity(1);
                rect.setStroke(Color.BLACK);
                rect.setStrokeType(StrokeType.INSIDE);
                rect.setStrokeWidth(4.0);
            }
        }
    }

    /**
     * Mostra quando clicchi sul roundtrack
     * @param i Stringa
     * @param rect Rettangolo
     */
    public void onClickDieRT(String i, Rectangle rect){
        if(myTurn) {
            synchronized (clickLock) {
                ObservableList<Node> grids = RTGrids.getChildren();
                for (Node grid : grids) {
                    ObservableList<Node> cells = ((GridPane) grid).getChildren();
                    for (Node node : cells) {
                        if ((node.getId() != null) && (node.getId().equals("dieRT" + lastDieRT))) {

                            Rectangle r = (Rectangle) node;
                            r.setVisible(false);
                            r.setStrokeWidth(0);
                            r.setOpacity(0.5);
                        }
                    }
                }
                setLastDieRT(i);
                rect.setFill(Color.TRANSPARENT);
                rect.setOpacity(1);
                rect.setStroke(Color.BLACK);
                rect.setStrokeType(StrokeType.INSIDE);
                rect.setStrokeWidth(4.0);
            }
        }
    }

    /**
     * Inizializza l'evento del draft
     * @param i Intero
     * @param j Intero
     * @param pane Griglia
     * @param dieView Immagine della view: die view
     * @param width Larghezza
     * @param height Altezza
     */
    public void setDraftEvents(int i, int j, GridPane pane, ImageView dieView, double width, double height){

        Rectangle rect = new Rectangle(width, height);
        rect.setArcWidth(10);
        rect.setArcHeight(10);
        rect.setId("dieDraft"+i);
        rect.setFill(Color.LIGHTGRAY);
        rect.setOpacity(0.5);
        rect.setVisible(false);
        Platform.runLater(() -> pane.add(rect, i%3, j));
        dieView.setOnMouseEntered(e -> rect.setVisible(true));
        rect.setOnMouseExited(e -> {
            if(i!=lastDieDraft) {
                    rect.setVisible(false);
            }
        });
        rect.setOnMouseReleased(e -> {synchronized(clickLock){onClickDieDraft(pane, i, rect);}});
    }

    /**
     * Inizializza l'evento del roundtrack
     * @param i Intero
     * @param j Intero
     * @param pane Griglia
     * @param dieView Immagine della view: dieView
     * @param width Larghezza
     * @param height Altezza
     */
    public void setRoundTrackEvents(int i, int j, GridPane pane, ImageView dieView, double width, double height){

        Rectangle rect = new Rectangle(width, height);
        rect.setArcWidth(10);
        rect.setArcHeight(10);
        rect.setId("dieRT"+""+(Integer.parseInt(pane.getId().substring(2))-1)+""+i);
        rect.setFill(Color.LIGHTGRAY);
        rect.setOpacity(0.5);
        rect.setVisible(false);
        Platform.runLater(() -> pane.add(rect, i%3, j));
        dieView.setOnMouseEntered(e -> rect.setVisible(true));
        rect.setOnMouseExited(e -> {
            if(!rect.getId().replace("dieRT","").equals(lastDieRT)) {
                    rect.setVisible(false);
            }
        });
        rect.setOnMouseReleased(e -> {synchronized(clickLock){onClickDieRT(rect.getId().replace("dieRT",""), rect);}});

    }

    /**
     * Mostra le window
     * @param board Game board
     */
    public void viewWindows(GameBoardClient board)  {

        int i = 0;

        for(PlayerClient player : board.getPlayers() ) {

            WindowGenerator wGenerator = new WindowGenerator(player.getWindow(), clickLock, this);
            //Esempio con GridPane
            boolean clickableBox = gV.getUsername().equals(player.getName());
            myWindow = wGenerator.getWindowFXObject(clickableBox);
            VBox box = new VBox();
            box.setId("gridWindow"+board.getPlayer(i).getName());
            String windowName = player.getWindow().getName().replace(" ", "-"); //questo per gestire in caso la window arrivi da RMI in cui il nome e' senza trattini
            javafx.scene.image.ImageView windowLabel = new javafx.scene.image.ImageView(new Image(new DynamicPath("assets/Windows/"+windowName+".png").getPath()));
            windowLabel.setFitWidth(50*player.getWindow().getBoardBox()[0].length);
            windowLabel.setPreserveRatio(true);
            windowLabel.setSmooth(true);
            windowLabel.setCache(true);
            box.getChildren().addAll(myWindow,windowLabel);

            //se la window generata non è quella del client allora viene settata trasparente
            if(!gV.getUsername().equals(player.getName())) {

                box.setVisible(false);

            }

            Platform.runLater(() -> playerWindow.getChildren().add(box));
            i++;

            //box.setOnAction(e -> onBoxClick(e,windowName));

        }

    }

    /**
     * Inizializza la scena del gioco
     */
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

    /**
     * Mostra evento click della box
     * @param e Evento
     * @param windowName Nome della windo
     */
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

    /**
     * Metodo che rappresenta la pressione del mouse
     * @param e Mouse evento
     * @param b Bottone
     * @param backgroundColor Stringa che rappresenta il colore in background
     */
    public void mousePressed(MouseEvent e, Button b, String backgroundColor){
        b.setPrefHeight(35);
        b.setStyle(getPressedButtonStyle(backgroundColor));
        mouseOut = false;
    }

    /**
     * Mostra l'alert
     * @param title Titolo
     * @param text Testo dell'alert
     */
    public void showAlert(String title, String text){
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.NONE, text, ButtonType.OK);
            alert.initOwner(stage);
            alert.setX(stage.getX()+stage.getScene().getWidth()/2 - 125);
            alert.setY(stage.getY()+stage.getScene().getHeight()/2 - 60);
            alert.setTitle(title);
            alert.setResizable(true);
            alert.getDialogPane().setPrefSize(250, 120);
            alert.setResizable(false);

            alert.showAndWait();

        });
    }

    /**
     * Mostra l'alert della carta
     * @param idButton Id del bottone
     * @param title Titolo
     * @param text Tetso
     */
    public void showCardAlert (String idButton, String title, String text){

        if(myTurn) {

            Platform.runLater(() -> {

                Alert alert = new Alert(Alert.AlertType.NONE, text, ButtonType.YES, ButtonType.NO);
                alert.initOwner(stage);
                alert.setX(stage.getX() + stage.getScene().getWidth() / 2 - 125);
                alert.setY(stage.getY() + stage.getScene().getHeight() / 2 - 60);
                alert.setTitle(title);
                alert.setResizable(true);
                alert.getDialogPane().setPrefSize(250, 120);
                alert.setResizable(false);
                alert.showAndWait();

                if (alert.getResult() == ButtonType.YES) {

                    lastToolCard = Character.getNumericValue(idButton.charAt(2))-1;
                    this.chooseCard(lastToolCard);

                }

            });
        }else{

            showAlert("Not your turn", "This is not your turn!");

        }

    }

    /**
     * Controlla i parametri di inserimento di un dado
     */
    public void checkInsertDieParameters(){
        if(myTurn) {
            if (this.lastDieDraft >= 0 && lastWindowCells.size()>0) {
                try {
                    gV.sendInsertDie(lastDieDraft, lastWindowCells.get(0).xPos, lastWindowCells.get(0).yPos);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //showAlert("Inviato", "Invio "+lastDieDraft+" "+lastWindowCell.xPos+" "+lastWindowCell.yPos);
            } else if (lastWindowCells.isEmpty() && this.lastDieDraft < 0) {
                showAlert("Inserisci dati", "Scegli un dado e una cella!");
            } else if (lastWindowCells.isEmpty()) {
                showAlert("Scegli cella", "Scegli una cella!");
            } else if (this.lastDieDraft < 0) {
                showAlert("Scegli dado", "Scegli un dado!");
            }
        } else {
            showAlert("Not your turn", "This is not your turn!");
        }
    }

    /**
     * Scelta della carta
     * @param numCard Numero della carta
     */
    public void chooseCard(int numCard){

        try {

                gV.sendChooseCard(numCard);

        } catch (IOException e) {

                e.printStackTrace();

        }

    }

    /**
     * Invio parametri della carta
     */
    public void sendCardParameters(){


        if(incrToolCard && myTurn){

            Platform.runLater(() -> {

                String response = "";
                String parameters = "";

                for(int i = 0; i<cardParameters.length; i++){
                    parameters += cardParameters[i]+" ";
                }

                if(parameters.contains("dieDraft")){
                    if(lastDieDraft>=0){
                        response+=lastDieDraft+" ";
                    } else {
                        showAlert("Illegal card parameters", "You didn't choose a die in the draft!");
                        return;
                    }
                }

                Alert alert = new Alert(Alert.AlertType.NONE, "Scegli se incrementare o decrementare");
                alert.initOwner(stage);
                alert.setX(stage.getX() + stage.getScene().getWidth() / 2 - 125);
                alert.setY(stage.getY() + stage.getScene().getHeight() / 2 - 60);
                alert.setTitle("");
                alert.setResizable(true);
                alert.getDialogPane().setPrefSize(350, 200);
                alert.setResizable(false);

                ButtonType incrementa = new ButtonType("Incrementa", ButtonBar.ButtonData.YES);
                ButtonType decrementa = new ButtonType("Decrementa",ButtonBar.ButtonData.NO);

                alert.getButtonTypes().setAll(incrementa,decrementa);

                Optional<ButtonType> result = alert.showAndWait();


                if(result.isPresent()) {
                    if (result.get() == incrementa) {

                        response += "1 ";


                    } else if (result.get() == decrementa) {

                        response += "0 ";

                    }
                }

                response = response.substring(0, response.length()-1).replace(" ", "-");

                try {
                    gV.sendResponseToolCard(lastToolCard, response);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });


        } else if(myTurn && cardParameters!=null){

            String parameters = "";
            String response = "";

            for(int i = 0; i<cardParameters.length; i++){
                parameters += cardParameters[i]+" ";
            }

            if(parameters.contains("dieDraft")){
                if(lastDieDraft>=0){
                    response+=lastDieDraft+" ";
                } else {
                    showAlert("Illegal card parameters", "You didn't choose a die in the draft!");
                    return;
                }
            }

            if(parameters.contains("card11")){
                if(lastDieDraft>=0){
                    response+="preEffect "+lastDieDraft+" ";
                }else{
                    showAlert("Illegal card parameters", "You didn't choose a die in the draft!");
                    return;
                }
            }

            if(numSelectableCells==2){
                if(lastWindowCells.size()==2){
                    for(int i = 0; i<lastWindowCells.size(); i++) response+=lastWindowCells.get(i).xPos+" "+lastWindowCells.get(i).yPos+" ";
                } else {
                    showAlert("Illegal card parameters", "You didn't choose 2 dice and 2 end positions!");
                    return;
                }
            } else if(numSelectableCells==4){
                if(lastWindowCells.size()==4){
                    for(int i = 0; i<lastWindowCells.size(); i++) response+=lastWindowCells.get(i).xPos+" "+lastWindowCells.get(i).yPos+" ";
                } else {
                    showAlert("Illegal card parameters", "You didn't choose 4 dice and 2 end positions!");
                    return;
                }
            }else if(parameters.contains("endPos")){
                if(lastWindowCells.size()==1){
                    response+=lastWindowCells.get(0).xPos+" "+lastWindowCells.get(0).yPos+" ";
                } else {
                    showAlert("Illegal card parameters", "You didn't choose a position in the window!");
                    return;
                }
            }

            if(parameters.contains("dieRoundTrack")){
                if(!lastDieRT.equals("")){
                    response+=lastDieRT.charAt(0)+" "+lastDieRT.charAt(1)+" ";
                } else {
                    showAlert("Illegal card parameters", "You didn't choose a die in the round track!");
                    return;
                }
            }

            response = response.substring(0, response.length()-1).replace(" ", "-");
            try {
                gV.sendResponseToolCard(lastToolCard, response);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Effetto del mouse e chiamata del metodo
     * @param e Evento mouse
     * @param b Bottone
     * @param id Id
     * @param backgroundColor Stringa che rappresenta il colore del background
     * @param shadowColor Colore dell'ombra
     */
    public void mouseReleased(MouseEvent e, Button b, String id, String backgroundColor, String shadowColor){
        b.setPrefHeight(41);
        b.setStyle(getIdleButtonStyle(backgroundColor, shadowColor));
        if(!mouseOut) {
            switch(id){
                case "insertDieButton":
                    checkInsertDieParameters();
                    break;
                case "endTurnButton":
                    onClickEndTurn();
                    break;
                case "currentPlayerButton":
                    setVisibleWindow("gridWindow"+ gV.getUsername());
                    break;
                case "useCardButton":
                    sendCardParameters();
                    break;
                default :
                    setVisibleWindow("gridWindow"+b.getId());
                    break;

            }
        }
    }

    /**
     *
     * @param backgroundColor Stringa che rappresenta il colore del background
     * @param shadowColor Colore dell'ombra
     * @return Stringa che rappresenta lo stile corretto dei pulsanti
     */
    public String getIdleButtonStyle(String backgroundColor, String shadowColor){
        final String IDLE_BUTTON_STYLE = "-fx-padding: 8 15 15 15; -fx-background-insets: 0 0 7 0,0 0 5 0, 0 0 6 0, 0 0 7 0; -fx-background-radius: 8; -fx-background-color: linear-gradient(from 0% 93% to 0% 100%, "+shadowColor+" 0%, "+shadowColor+" 100%),        "+shadowColor+",        "+shadowColor+",        radial-gradient(center 50% 50%, radius 100%, "+backgroundColor+", "+backgroundColor+"); -fx-font-weight: bold; -fx-font-size: 1.1em; -fx-font-family: \"Arial\";";
        return IDLE_BUTTON_STYLE;
    }

    /**
     *
     * @param backgroundColor Stringa che rappresenta il colore del background
     * @param shadowColor Colore dell'ombra
     * @return Stringa che rappresenta lo stile corretto dei pulsanti
     */
    public String getHoverButtonStyle(String backgroundColor, String shadowColor){
        String HOVER_BUTTON_STYLE = "-fx-padding: 8 15 15 15; -fx-background-insets: 0 0 7 0,0 0 5 0, 0 0 6 0, 0 0 7 0; -fx-background-radius: 8; -fx-background-color: linear-gradient(from 0% 93% to 0% 100%, "+shadowColor+" 0%, "+shadowColor+" 100%),        "+shadowColor+",        "+shadowColor+",        radial-gradient(center 50% 50%, radius 100%, "+backgroundColor+", "+backgroundColor+"); -fx-font-weight: bold; -fx-font-size: 1.1em; -fx-font-family: \"Arial\";";
        return HOVER_BUTTON_STYLE;
    }

    /**
     *
     * @param backgroundColor Stringa che rappresenta il colore del background
     * @return Stringa che rappresenta lo stile corretto dei pulsanti
     */
    public String getPressedButtonStyle(String backgroundColor){
        final String PRESSED_BUTTON_STYLE = "-fx-padding: 6 6 6 6; -fx-background-radius: 8; -fx-background-color: "+backgroundColor+"; -fx-font-weight: bold; -fx-font-size: 1.1em; -fx-font-family: \"Arial\";";
        return PRESSED_BUTTON_STYLE;
    }

    /**
     * Inizializza i bottoni
     * @param b Bottone
     * @param id Id
     * @param backgroundColor Stringa che rappresenta il colore del background
     * @param shadowColor Colore dell'ombra
     * @param hoverColor Colore
     */
    public void setButtonEvents(Button b, String id, String backgroundColor, String shadowColor, String hoverColor){
        b.setId(id);
        b.setStyle(getIdleButtonStyle(backgroundColor, shadowColor));
        b.setOnMouseExited(e -> {
            mouseOut = true;
            b.setPrefHeight(41);
            b.setStyle(getIdleButtonStyle(backgroundColor, shadowColor));});
        b.setOnMousePressed(e -> mousePressed(e, b, backgroundColor));
        b.setOnMouseReleased(e -> mouseReleased(e, b, id, backgroundColor, shadowColor));
        b.setOnMouseEntered(e -> b.setStyle(getHoverButtonStyle(hoverColor, shadowColor)));
    }

    /**
     *
     * @return ArrayList delle coordinate delle ultime celle
     */
    public ArrayList<Coordinates> getLastWindowCells(){
        return this.lastWindowCells;
    }

    /**
     *
     * @return Intero che rappresenta il numero di celle selezionabili
     */
    public int getNumSelectableCells(){
        return numSelectableCells;
    }

    /**
     * Inizializza i vari bottoni e la situazione
     * @param location URL
     * @param resources Risorse
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        alreadyUpdated = false;
        clickLock = new Object();

        ToolCards.setOnMouseReleased(e -> tcWindow.setVisible(true));
        PublicCards.setOnMouseReleased(e -> pcWindow.setVisible(true));
        roundTrackButton.setOnMouseReleased(e -> RTWindow.setVisible(true));
        tcClose.setOnMouseReleased(e -> tcWindow.setVisible(false));
        pcClose.setOnMouseReleased(e -> pcWindow.setVisible(false));
        closeRoundTrack.setOnMouseReleased(e -> RTWindow.setVisible(false));

        tc1Button.setOnMouseReleased(e -> showCardAlert(tc1Button.getId(),"Choose card 1","Do you want to "+tc1Button.getText().toLowerCase().replace("ft","favor tokens")+"?"));
        tc2Button.setOnMouseReleased(e -> showCardAlert(tc2Button.getId(),"Choose card 2","Do you want to "+tc2Button.getText().toLowerCase().replace("ft","favor tokens")+"?"));
        tc3Button.setOnMouseReleased(e -> showCardAlert(tc3Button.getId(),"Choose card 3","Do you want to "+tc3Button.getText().toLowerCase().replace("ft","favor tokens")+"?"));


        numSelectableCells = 1;
        lastDieDraft = -1;
        lastDieRT = "";
        incrToolCard = false;
        lastWindowCells = new ArrayList<>();
        myTurn = false;
        lastToolCard = 0;

        currentPlayerButton.setText(gV.getUsername());
        setButtonEvents(insertDieButton, "insertDieButton","#1895d7", "#084c8a", "#00c0ff");
        setButtonEvents(useCard, "useCardButton","#1895d7", "#084c8a", "#00c0ff");
        setButtonEvents(endTurnButton, "endTurnButton","#1895d7", "#084c8a", "#00c0ff");
        setButtonEvents(currentPlayerButton, "currentPlayerButton","#1895d7","#084c8a", "#00c0ff");
    }

    /**
     * Mostra le carte
     * @param board Game board
     */
    public void viewCards(GameBoardClient board){

        int i = 0;

        for(ToolCardClient cardClient: board.getToolCards()){

            if(i==0){

                Platform.runLater(() -> tc1.setImage(new Image(new DynamicPath("assets/ToolCards/"+cardClient.getTitle()+".jpg").getPath())));


            }

            if(i==1){

                Platform.runLater(() ->tc2.setImage(new Image(new DynamicPath("assets/ToolCards/"+cardClient.getTitle()+".jpg").getPath())));


            }

            if(i==2){

                Platform.runLater(() ->tc3.setImage(new Image(new DynamicPath("assets/ToolCards/"+cardClient.getTitle()+".jpg").getPath())));

            }

            i++;

        }

        i=0;

        for(PublicCardClient cardClient: board.getPublicCards()){

            if(i==0){

                Platform.runLater(() -> pc1.setImage(new Image(new DynamicPath("assets/PublicCards/"+cardClient.getTitle()+".jpg").getPath())));


            }

            if(i==1){

                Platform.runLater(() ->pc2.setImage(new Image(new DynamicPath("assets/PublicCards/"+cardClient.getTitle()+".jpg").getPath())));


            }

            if(i==2){

                Platform.runLater(() ->pc3.setImage(new Image(new DynamicPath("assets/PublicCards/"+cardClient.getTitle()+".jpg").getPath())));

            }

            i++;
        }

    }

    /**
     * Mostra il colore segreto
     * @param board Game Board
     */
    public void viewSecretColor(GameBoardClient board){

        Platform.runLater(() -> secretColor.setImage(new Image(new DynamicPath("assets/SecretColors/"+board.getPlayerByName(gV.getUsername()).getSecretColour().toString()+".png").getPath())));

    }

    /**
     * Mostra i favor tokens
     * @param board Game board
     */
    public void viewFavorTokens(GameBoardClient board){

        for(PlayerClient player: board.getPlayers()) {

            HBox favorTokens = new HBox();
            ImageView fT;


            for (int i = 0; i < player.getFavorTokens(); i++) {

                fT = new ImageView(new Image(new DynamicPath("assets/FavorToken.png").getPath()));
                fT.setFitWidth(40);
                fT.setPreserveRatio(true);
                favorTokens.getChildren().add(fT);

            }

            favorTokens.setSpacing(5);
            favorTokens.setId(player.getName()+"FavorTokens");

            if (!player.getName().equals(gV.getUsername())){

                favorTokens.setVisible(false);

            }

            Platform.runLater(() -> favorTokensPane.getChildren().add(favorTokens));

        }


    }

    /**
     * Imposta la visible window a true o false
     * @param idWindow Id della window
     */
    public void setVisibleWindow(String idWindow){

        for (Node node: playerWindow.getChildren()) {

            if(node.getId()!=null && node.getId().equals(idWindow)){

                node.setVisible(true);

            }else{

                node.setVisible(false);

            }

        }

        for (Node node: favorTokensPane.getChildren()) {

            if(node.getId()!=null && node.getId().equals(idWindow.substring(10)+"FavorTokens")){

                node.setVisible(true);

            }else{

                node.setVisible(false);

            }

        }

    }

    /**
     * Imposta il rettangolo del round
     * @param board Game board
     */
    public void setRoundRectangle(GameBoardClient board) {

        switch(board.getRound()){

            case 1:

                R1.setVisible(true);
                R2.setVisible(false);
                R3.setVisible(false);
                R4.setVisible(false);
                R5.setVisible(false);
                R6.setVisible(false);
                R7.setVisible(false);
                R8.setVisible(false);
                R9.setVisible(false);
                R10.setVisible(false);
                break;

            case 2:

                R1.setVisible(false);
                R2.setVisible(true);
                R3.setVisible(false);
                R4.setVisible(false);
                R5.setVisible(false);
                R6.setVisible(false);
                R7.setVisible(false);
                R8.setVisible(false);
                R9.setVisible(false);
                R10.setVisible(false);
                break;

            case 3:

                R1.setVisible(false);
                R2.setVisible(false);
                R3.setVisible(true);
                R4.setVisible(false);
                R5.setVisible(false);
                R6.setVisible(false);
                R7.setVisible(false);
                R8.setVisible(false);
                R9.setVisible(false);
                R10.setVisible(false);
                break;

            case 4:

                R1.setVisible(false);
                R2.setVisible(false);
                R3.setVisible(false);
                R4.setVisible(true);
                R5.setVisible(false);
                R6.setVisible(false);
                R7.setVisible(false);
                R8.setVisible(false);
                R9.setVisible(false);
                R10.setVisible(false);
                break;

            case 5:

                R1.setVisible(false);
                R2.setVisible(false);
                R3.setVisible(false);
                R4.setVisible(false);
                R5.setVisible(true);
                R6.setVisible(false);
                R7.setVisible(false);
                R8.setVisible(false);
                R9.setVisible(false);
                R10.setVisible(false);
                break;

            case 6:

                R1.setVisible(false);
                R2.setVisible(false);
                R3.setVisible(false);
                R4.setVisible(false);
                R5.setVisible(false);
                R6.setVisible(true);
                R7.setVisible(false);
                R8.setVisible(false);
                R9.setVisible(false);
                R10.setVisible(false);
                break;

            case 7:

                R1.setVisible(false);
                R2.setVisible(false);
                R3.setVisible(false);
                R4.setVisible(false);
                R5.setVisible(false);
                R6.setVisible(false);
                R7.setVisible(true);
                R8.setVisible(false);
                R9.setVisible(false);
                R10.setVisible(false);
                break;

            case 8:

                R1.setVisible(false);
                R2.setVisible(false);
                R3.setVisible(false);
                R4.setVisible(false);
                R5.setVisible(false);
                R6.setVisible(false);
                R7.setVisible(false);
                R8.setVisible(true);
                R9.setVisible(false);
                R10.setVisible(false);
                break;

            case 9:

                R1.setVisible(false);
                R2.setVisible(false);
                R3.setVisible(false);
                R4.setVisible(false);
                R5.setVisible(false);
                R6.setVisible(false);
                R7.setVisible(false);
                R8.setVisible(false);
                R9.setVisible(true);
                R10.setVisible(false);
                break;

            case 10:

                R1.setVisible(false);
                R2.setVisible(false);
                R3.setVisible(false);
                R4.setVisible(false);
                R5.setVisible(false);
                R6.setVisible(false);
                R7.setVisible(false);
                R8.setVisible(false);
                R9.setVisible(false);
                R10.setVisible(true);
                break;

        }

    }

    /**
     * Imposta il turno corrente
     * @param board Game board
     */
    public void setCurrentTurn(GameBoardClient board){

        Platform.runLater(() -> currentTurn.setText(": "+(board.getTurn()+1)));

    }

    /**
     * Mostra i parametri delle carte
     * @param parameters Stringa di parametri
     */
    public void showCardParameters(String[] parameters){

        this.cardParameters = parameters;

        int j=1;

        StringBuilder showedParameters = new StringBuilder();

        for(int i=0; i<parameters.length; i++){

            switch(parameters[i]) {

                case "startPos":

                    showedParameters.append("Seleziona dado ").append(j).append(" dalla window.\n");
                    j++;
                    break;

                case "endPos":

                    showedParameters.append("Seleziona dove vuoi inserire il dado ").append(j-1).append(" sulla window.\n");
                    break;

                case "dieDraft":

                    showedParameters.append("Seleziona un dado dal draft.\n");
                    break;

                case "dieRoundTrack":

                    showedParameters.append("Selezionare un dado dalla Round Track.\n");
                    break;

                case "incrDecrDie":

                    showedParameters.append("Scegliere un dado dal draft, premere \"Use Card\" e inrementare o decrementare il dado.\n");
                    incrToolCard = true;
                    break;

                case "card11":

                    showedParameters.append("Seleziona un dado dal draft.\n");
                    break;

                case "dieValue":


                    Platform.runLater(() -> {
                        Alert alert = new Alert(Alert.AlertType.NONE, "Scegli il valore dell'ultimo dado del draft");
                        alert.initOwner(stage);
                        alert.setX(stage.getX() + stage.getScene().getWidth() / 2 - 125);
                        alert.setY(stage.getY() + stage.getScene().getHeight() / 2 - 60);
                        alert.setTitle("");
                        alert.setResizable(true);
                        alert.getDialogPane().setPrefSize(350, 200);
                        alert.setResizable(false);

                        ButtonType uno = new ButtonType("1");
                        ButtonType due = new ButtonType("2");
                        ButtonType tre = new ButtonType("3");
                        ButtonType quattro = new ButtonType("4");
                        ButtonType cinque = new ButtonType("5");
                        ButtonType sei = new ButtonType("6");

                        alert.getButtonTypes().setAll(uno,due,tre,quattro,cinque,sei);
                        Optional<ButtonType> result = alert.showAndWait();
                        if(result.isPresent()) {
                            if (result.get() == uno) {
                                try {
                                    gV.sendResponseToolCard(lastToolCard, "1");
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            } else if (result.get() == due) {
                                try {
                                    gV.sendResponseToolCard(lastToolCard, "2");
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            } else if (result.get() == tre) {
                                try {
                                    gV.sendResponseToolCard(lastToolCard, "3");
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            } else if (result.get() == quattro) {
                                try {
                                    gV.sendResponseToolCard(lastToolCard, "4");
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            } else if (result.get() == cinque) {
                                try {
                                    gV.sendResponseToolCard(lastToolCard, "5");
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            } else if (result.get() == sei) {
                                try {
                                    gV.sendResponseToolCard(lastToolCard, "6");
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }

                    });
                    return;


            }

        }
        if(j==2){
            numSelectableCells = 2;
        } else if(j==3){
            numSelectableCells = 4;
        }

        showedParameters.append("\nRispettare quest'ordine di selezione.");

        Platform.runLater(() -> {

            Alert alert = new Alert(Alert.AlertType.NONE, showedParameters.toString(), ButtonType.OK);
            alert.initOwner(stage);
            alert.setX(stage.getX() + stage.getScene().getWidth() / 2 - 125);
            alert.setY(stage.getY() + stage.getScene().getHeight() / 2 - 60);
            alert.setTitle("Scegli parametri carta");
            alert.setResizable(true);
            alert.getDialogPane().setPrefSize(350, 200);
            alert.setResizable(false);
            alert.showAndWait();

        });

    }

    /**
     * Click della fine del turno
     */
    public void onClickEndTurn() {

        if(myTurn){

            try {
                gV.sendEndTurn();
            }catch(IOException e){
                e.printStackTrace();
            }

        }else{

            showAlert("Not your turn", "This is not your turn!");

        }
    }

    public void showEndGame(String score){

        cancelTimer();
        Platform.runLater(() ->{

            Alert alert = new Alert(Alert.AlertType.NONE, score, ButtonType.OK);
            alert.initOwner(stage);
            alert.setX(stage.getX() + stage.getScene().getWidth() / 2 - 125);
            alert.setY(stage.getY() + stage.getScene().getHeight() / 2 - 60);
            alert.setTitle("Classifica");
            alert.setResizable(true);
            alert.getDialogPane().setPrefSize(350, 200);
            alert.setResizable(false);
            alert.showAndWait();

            if(alert.getResult()==ButtonType.OK){

                //Platform.exit();
                System.exit(0);
            }
        });
    }


    public void showWinBeforeEndGameAlert() {

        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.NONE, "You won! Seems like you're the only player left online!", ButtonType.OK);
            alert.setX(stage.getX()+stage.getScene().getWidth()/2 - 125);
            alert.setY(stage.getY()+stage.getScene().getHeight()/2 - 60);
            alert.setTitle("You won!");
            alert.setResizable(true);
            alert.getDialogPane().setPrefSize(250, 120);
            alert.setResizable(false);

            alert.showAndWait();

            if(alert.getResult()==ButtonType.OK){

                System.exit(0);
            }
        });

    }
}