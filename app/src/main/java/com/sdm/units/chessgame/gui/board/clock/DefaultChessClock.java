package com.sdm.units.chessgame.gui.board.clock;

import javax.swing.Timer;

public class DefaultChessClock implements ChessClock {
    
    private long totalMillis;
    private long remainingMillis;
    private Timer timer;
    private ClockFormatter clockFormatter;

    public DefaultChessClock(ClockFormatter clockFormatter, long totalMillis) {
        this.clockFormatter = clockFormatter;
        this.totalMillis = this.remainingMillis = totalMillis;
    }

    @Override
    public void start() {
        if (timer != null) {
            timer.stop();
        } 

        timer = new Timer(1000, e -> {
            setTime(remainingMillis -= 1000);
            
            if (remainingMillis <= 0) {
                stop();
            }
        });

        timer.start();
    }

    @Override
    public void stop() {
        if (timer != null) {
            timer.stop();
        }
    }

    @Override
    public void reset() {
        stop();
        setTime(totalMillis);
    }

    private void setTime(long millis) {
        this.remainingMillis = millis;
        clockFormatter.updateDisplay(millis);
    }
}