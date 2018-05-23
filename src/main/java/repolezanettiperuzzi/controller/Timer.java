package repolezanettiperuzzi.controller;

public class Timer {
    private long timerDuration;
    private long startingTime;

    public Timer(long timerDuration){
        this.timerDuration = timerDuration;
    }

    public void start(){
        this.startingTime = System.currentTimeMillis();
    }

    public long getElapsedTime(){
        return System.currentTimeMillis()-startingTime;
    }

    public long getRemainingTime(){
        return timerDuration-getElapsedTime();
    }

    public boolean timeout(){
        return getRemainingTime()<=0;
    }
}

