package unittest.chessgame.gamecontrol.testdoubles;

import com.sdm.units.chessgame.gui.board.clock.ChessClock;
import com.sdm.units.chessgame.gui.board.clock.ChessClockListener;

public class ChessClockSpy implements ChessClock {

    private boolean startCalled = false;
    private boolean stopCalled = false;
    private boolean resetCalled = false;
    private boolean listenerSet = false;

    public boolean isStartCalled() {
        return startCalled;
    }

    public boolean isStopCalled() {
        return stopCalled;
    }

    public boolean isResetCalled() {
        return resetCalled;
    }

    public boolean isListenerSet() {
        return listenerSet;
    }

    @Override
    public void start() {
        startCalled = true;
    }

    @Override
    public void stop() {
        stopCalled = true;
    }

    @Override
    public void reset() {
        resetCalled = true;
    }

    @Override
    public void setListener(ChessClockListener listener) {
        listenerSet = true;
    }
}