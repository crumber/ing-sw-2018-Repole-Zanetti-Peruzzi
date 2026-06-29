package repolezanettiperuzzi.controller;

import repolezanettiperuzzi.application.actions.RoundTracker;
import repolezanettiperuzzi.application.actions.TurnTracker;

/**
 * Holds mutable state for one game session.
 */
public class GameSession {

    private final RoundTracker roundTracker;
    private final TurnTracker turnTracker;
    private final TurnStateTracker turnStateTracker;

    public GameSession(){
        this(new RoundTracker(), new TurnTracker(), new TurnStateTracker());
    }

    GameSession(RoundTracker roundTracker, TurnTracker turnTracker, TurnStateTracker turnStateTracker){
        this.roundTracker=roundTracker;
        this.turnTracker=turnTracker;
        this.turnStateTracker=turnStateTracker;
    }

    /**
     * @return Stato del round della sessione
     */
    public RoundTracker getRoundTracker(){
        return roundTracker;
    }

    /**
     * @return Stato del turno della sessione
     */
    public TurnTracker getTurnTracker(){
        return turnTracker;
    }

    /**
     * @return Stato temporaneo del controller durante il turno
     */
    public TurnStateTracker getTurnStateTracker(){
        return turnStateTracker;
    }
}
