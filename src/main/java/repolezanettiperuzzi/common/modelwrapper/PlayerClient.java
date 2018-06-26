package repolezanettiperuzzi.common.modelwrapper;

import java.io.Serializable;

public class PlayerClient implements Serializable {

    private String name;
    private ColourClient secretColour;
    private boolean insertDieInThisTurn;
    private int favorTokens;
    private WindowClient window;
    private int turn;
    private int score;
    private String connection; //RMI o Socket
    private String UI; //CLI o GUI
    private String address;
    private int port;
    private String lastScene;
    private boolean liveStatus;

    public PlayerClient(String name, String connection, String UI, String address, int port){
        this.name = name;
        this.score = 0;
        this.connection = connection;
        this.UI = UI;
        this.turn = 0;
        this.address = address;
        this.port = port;
        this.lastScene = "login";
        this.liveStatus = true;
    }

    public void updateScore(int points){

        score+=points;

    }

    public int getScore(){

        return score;

    }

    public WindowClient getWindow(){

        return this.window;

    }

    public void setWindow(WindowClient window){

        this.window = window;
        this.setFavorTokens(window.getFTokens());

    }

    public boolean checkLastScene(String sceneName){
        return this.lastScene.equals(sceneName);
    }

    public String getLastScene(){
        return this.lastScene;
    }

    public void setLastScene(String sceneName){
        this.lastScene = sceneName;
    }

    public boolean getLiveStatus(){
        return this.liveStatus;
    }

    public void setLiveStatus(boolean status){
        this.liveStatus = status;
    }

    public void setConnection(String connection){
        this.connection = connection;
    }

    public void setUI(String UI){
        this.UI = UI;
    }

    public void setAddress(String address){
        this.address = address;
    }

    public void setPort(int port){
        this.port = port;
    }

    public int getFavorTokens() {

        return favorTokens;

    }

    public void setFavorTokens(int flavorTokens){

        this.favorTokens=flavorTokens;

    }

    public String getConnection(){
        return connection;
    }

    public String getAddress(){
        return address;
    }

    public int getPort(){
        return port;
    }



    public String getName() {

        return name;

    }

    public void setInsertDieInThisTurn(boolean trueOrFalse) {

         insertDieInThisTurn=trueOrFalse;

    }

    public boolean getInsertDieInThisTurn(){

        return insertDieInThisTurn;

    }

    public ColourClient getSecretColour() {

        return secretColour;

    }

    public void setSecretColour(ColourClient colour){

        this.secretColour=colour;

    }

    public int getTurn() {

        return turn;

    }

    public void reduceFavorTokens(int reduction) {

        favorTokens -= reduction;

    }

    //vedi incrRound dentro GameBoard per il metovo per cui non ho creato una setTurn
    public void incrTurn(){
        this.turn++;
    }

    //Il primo turno di un giocatore si ha quando turn = 1 . Qui nella reset lo imposto a 0 perchè questo
    //metodo viene chiamato solo dalla Azione BeginRound. Quindi all'inizio del Round tutti i turni vengono
    //impostati a 0 tramite questo metodo e quando sta per iniziare il primo turno vengono tutti incrementati di 1
    //tramite la incrTurn(). Così chi dovra saltare il turno successivo per via di una carta tool basterà incrementare
    //il suo turno di 1 portandolo quindi ad un eventuale turno 3 che non potra' mai giocare quel Round (per via di controlli
    // sui turni che verranno fatti).
    public void resetTurn(){
        this.turn = 0;
    }


}
