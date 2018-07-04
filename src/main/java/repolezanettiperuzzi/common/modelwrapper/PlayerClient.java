package repolezanettiperuzzi.common.modelwrapper;

import java.io.Serializable;

/**
 * Classe che modellizza il giocatore nel client
 * @author Andrea Zanetti
 * @author Giampiero Repole
 * @author Alessandro Peruzzi
 */
public class PlayerClient implements Serializable {

    private String name;
    private ColourClient secretColour;
    private boolean insertDieInThisTurn;
    private boolean usedCardInThisTurn;
    private int favorTokens;
    private WindowClient window;
    private int turn;
    private int score;
    private boolean liveStatus;

    /**
     *
     * @param name Nome del player
     */
    public PlayerClient(String name){
        this.name = name;
        this.score = 0;
        this.liveStatus = true;
    }

    /**
     *
     * @return Score del player
     */
    public int getScore(){

        return score;

    }

    /**
     *
     * @return Vero se player ha già utilizzato una carta in questo turno sennò falso
     */
    public boolean getUseCardInThisTurn(){

        return usedCardInThisTurn;

    }

    /**
     *
     * @return La window del player
     */
    public WindowClient getWindow(){

        return this.window;

    }

    /**
     * Inizializza la window del player
     * @param window window da daren al player
     */
    public void setWindow(WindowClient window){

        this.window = window;
        this.setFavorTokens(window.getFTokens());

    }

    /**
     *
     * @return True se il giocatore è online sennò false
     */
    public boolean getLiveStatus(){
        return this.liveStatus;
    }

    /**
     * Inizializza lo stato del player
     * @param status True se online sennò false
     */
    public void setLiveStatus(boolean status){
        this.liveStatus = status;
    }

    /**
     *
     * @return Numero favor token
     */
    public int getFavorTokens() {

        return favorTokens;

    }

    /**
     * Inizializza i FV del player
     * @param flavorTokens Intero che rappresenta il numero di FV
     */
    public void setFavorTokens(int flavorTokens){

        this.favorTokens=flavorTokens;

    }

    /**
     *
     * @return Il nome del player
     */
    public String getName() {

        return name;

    }

    /**
     * Inizializza a true se il player ha gia inserito un dado nel turno sennò inizializza a false
     * @param trueOrFalse True se player ha gia inserito un dado in questo turno
     */
    public void setInsertDieInThisTurn(boolean trueOrFalse) {

         insertDieInThisTurn=trueOrFalse;

    }

    /**
     *
     * @return True se ha gia inserito un dado sennò false
     */
    public boolean getInsertDieInThisTurn(){

        return insertDieInThisTurn;

    }

    /**
     *
     * @return Il secret colour del player
     */
    public ColourClient getSecretColour() {

        return secretColour;

    }

    /**
     * Inizializza il colore segreto
     * @param colour Colore a cui viene inizializzato
     */
    public void setSecretColour(ColourClient colour){

        this.secretColour=colour;

    }

    /**
     *
     * @return Ritorna il valore del turno
     */
    public int getTurn() {

        return turn;

    }

    /**
     * Incrementa il turno
     */
    //vedi incrRound dentro GameBoard per il metovo per cui non ho creato una setTurn
    public void incrTurn(){
        this.turn++;
    }

}
