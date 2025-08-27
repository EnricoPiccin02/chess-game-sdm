package guitest.chessgame.testdoubles;

import javax.swing.JComponent;
import javax.swing.JPanel;

import com.sdm.units.chessgame.gui.board.clock.ChessClock;
import com.sdm.units.chessgame.gui.board.clock.ChessClockListener;
import com.sdm.units.chessgame.gui.board.clock.ChessClockView;

public class ChessClockViewSpy implements ChessClockView {
    
    private boolean startClockCalled = false;
    private boolean stopClockCalled = false;
    private boolean resetCalled = false;

    public boolean isStartClockCalled() {
        return startClockCalled;
    }

    public boolean isStopClockCalled() {
        return stopClockCalled;
    }

    public boolean isResetCalled() {
        return resetCalled;
    }

    @Override
    public ChessClock getClock() {
        return new ChessClock() {
            
            @Override
            public void start() {
                startClockCalled = true;
            }

            @Override
            public void stop() {
                stopClockCalled = true;
            }

            @Override
            public void reset() {}

            @Override
            public void setListener(ChessClockListener listener) {}
        };
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