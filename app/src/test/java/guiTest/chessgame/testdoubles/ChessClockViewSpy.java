package guitest.chessgame.testdoubles;

import javax.swing.JComponent;
import javax.swing.JPanel;

import com.sdm.units.chessgame.gui.board.clock.ChessClockView;

public class ChessClockViewSpy implements ChessClockView {

    private boolean startCalled = false;
    private boolean stopCalled = false;
    private boolean resetCalled = false;

    public boolean isStartCalled() {
        return startCalled;
    }

    public boolean isStopCalled() {
        return stopCalled;
    }

    public boolean isResetCalled() {
        return resetCalled;
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
    public void updateTime(String time) {}

    @Override
    public String getTime() {
        return "";
    }

    @Override
    public JComponent asComponent() {
        return new JPanel();
    }
}