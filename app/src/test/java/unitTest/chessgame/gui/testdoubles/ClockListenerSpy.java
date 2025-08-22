package unittest.chessgame.gui.testdoubles;

import com.sdm.units.chessgame.gui.board.clock.ChessClockListener;

public class ClockListenerSpy implements ChessClockListener {
    
    private boolean updatedCalled = false;
    private boolean expiredCalled = false;
    private long lastUpdated = -1;

    public boolean isUpdatedCalled() {
        return updatedCalled;
    }

    public boolean isExpiredCalled() {
        return expiredCalled;
    }

    public long getLastUpdated() {
        return lastUpdated;
    }

    @Override
    public void onTimeUpdated(long remainingMillis) {
        updatedCalled = true;
        lastUpdated = remainingMillis;
    }

    @Override
    public void onTimeExpired() {
        expiredCalled = true;
    }
}