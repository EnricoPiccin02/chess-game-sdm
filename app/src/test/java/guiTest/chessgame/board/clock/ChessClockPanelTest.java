package guitest.chessgame.board.clock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.sdm.units.chessgame.gamelogic.domain.ChessPieceColor;
import com.sdm.units.chessgame.gui.board.clock.ChessClockPanel;

import unittest.chessgame.gamecontrol.testdoubles.ChessClockSpy;

@DisplayName("ChessClockPanel")
class ChessClockPanelTest {

    private ChessClockSpy clockSpy;
    private ChessClockPanel panel;

    @BeforeEach
    void setUp() {
        clockSpy = new ChessClockSpy();
        panel = new ChessClockPanel(ChessPieceColor.WHITE, clockSpy);
    }

    @Test
    @DisplayName("should update the displayed time when updateTime is called")
    void shouldUpdateDisplayedTimeWhenUpdateTimeIsCalled() {
        panel.updateTime("5:00");

        assertEquals("5:00", panel.getTime());
    }

    @Test
    @DisplayName("should delegate start to underlying clock")
    void shouldDelegateStartToClock() {
        panel.start();

        assertTrue(clockSpy.isStartCalled());
    }

    @Test
    @DisplayName("should delegate stop to underlying clock")
    void shouldDelegateStopToClock() {
        panel.stop();

        assertTrue(clockSpy.isStopCalled());
    }

    @Test
    @DisplayName("should delegate reset to underlying clock")
    void shouldDelegateResetToClock() {
        panel.reset();

        assertTrue(clockSpy.isResetCalled());
    }
}