package guitest.chessgame.testdoubles;

import com.sdm.units.chessgame.gamelogic.domain.ChessPieceColor;
import com.sdm.units.chessgame.gui.board.clock.ChessClock;
import com.sdm.units.chessgame.gui.board.clock.ChessClockListener;
import com.sdm.units.chessgame.gui.board.clock.ChessClockPanel;

public class ChessClockPanelSpy extends ChessClockPanel {
    
    public boolean startClockCalled = false;
    public boolean stopClockCalled = false;
    public boolean resetCalled = false;

    public boolean isStartClockCalled() {
        return startClockCalled;
    }

    public boolean isStopClockCalled() {
        return stopClockCalled;
    }

    public boolean isResetCalled() {
        return resetCalled;
    }

    public ChessClockPanelSpy(ChessPieceColor color) {
        super(color, null);
    }

    @Override
    public void reset() {
        resetCalled = true;
    }

    @Override
    public ChessClock getClock() {
        return new ChessClock() {
            
            @Override
            public void start() {
                startClockCalled = true;
            }

            @Override public void stop() {
                stopClockCalled = true;
            }

            @Override
            public void reset() {
                
            }

            @Override
            public void setListener(ChessClockListener listener) {
                
            }
        };
    }
}