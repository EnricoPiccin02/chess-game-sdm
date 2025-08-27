package guitest.chessgame.board.clock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.sdm.units.chessgame.gamelogic.domain.ChessPieceColor;
import com.sdm.units.chessgame.gui.board.clock.ChessClock;
import com.sdm.units.chessgame.gui.board.clock.ChessClockPanel;

@DisplayName("ChessClockPanel")
class ChessClockPanelTest {

    private ChessClock mockClock;
    private ChessClockPanel panel;

    @BeforeEach
    void setUp() {
        mockClock = mock(ChessClock.class);
        panel = new ChessClockPanel(ChessPieceColor.WHITE, mockClock);
    }

    @Test
    @DisplayName("should expose the clock it was injected into")
    void shouldExposeInjectedClock() {
        assertSame(mockClock, panel.getClock());
    }

    @Test
    @DisplayName("should update the displayed time when updateTime is called")
    void shouldUpdateDisplayedTimeWhenUpdateTimeIsCalled() {
        panel.updateTime("5:00");

        assertEquals("5:00", panel.getTime());
    }

    @Test
    @DisplayName("should delegate reset to underlying clock")
    void shouldDelegateResetToClock() {
        panel.reset();

        verify(mockClock).reset();
    }
}