package repolezanettiperuzzi.controller;

/**
 * Tracks transient flags used while the controller is in the turn state.
 */
public class TurnStateTracker {

    private boolean turnNotificationSent = false;
    private boolean toolCard8Active = false;

    /**
     * @return Vero se la notifica di inizio turno e' gia stata inviata
     */
    public boolean isTurnNotificationSent(){
        return turnNotificationSent;
    }

    /**
     * Segna la notifica di inizio turno come inviata.
     */
    public void markTurnNotificationSent(){
        turnNotificationSent=true;
    }

    /**
     * Azzera lo stato della notifica di inizio turno.
     */
    public void resetTurnNotification(){
        turnNotificationSent=false;
    }

    /**
     * @return Vero se la carta tool 8 ha modificato il flusso del turno
     */
    public boolean isToolCard8Active(){
        return toolCard8Active;
    }

    /**
     * Segna la carta tool 8 come attiva per il turno corrente.
     */
    public void activateToolCard8(){
        toolCard8Active=true;
    }

    /**
     * Azzera lo stato della carta tool 8.
     */
    public void resetToolCard8(){
        toolCard8Active=false;
    }
}
