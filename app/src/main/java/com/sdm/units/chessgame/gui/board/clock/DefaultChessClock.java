package com.sdm.units.chessgame.gui.board.clock;

import javax.swing.Timer;

public class DefaultChessClock implements ChessClock {
    
    private final long totalMillis;
    private long remainingMillis;
    private Timer timer;
    private ChessClockListener listener;

    public DefaultChessClock(long totalMillis) {
        this.totalMillis = this.remainingMillis = totalMillis;
    }

    @Override
    public void start() {
        if (timer != null) timer.stop();

        timer = new Timer(1000, e -> tick());
        timer.start();
    }

    public void tick() {
        remainingMillis -= 1000;
        if (listener != null) listener.onTimeUpdated(remainingMillis);

        if (remainingMillis <= 0) {
            stop();
            if (listener != null) listener.onTimeExpired();
        }
    }

    @Override
    public void stop() {
        if (timer != null) timer.stop();
    }

    @Override
    public void reset() {
        stop();
        remainingMillis = totalMillis;
        if (listener != null) listener.onTimeUpdated(remainingMillis);
    }

    @Override
    public void setListener(ChessClockListener listener) {
        this.listener = listener;
    }
}