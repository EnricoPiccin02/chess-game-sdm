package unittest.chessgame.gui.board.clock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.EnumMap;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.sdm.units.chessgame.gamelogic.domain.ChessPieceColor;
import com.sdm.units.chessgame.gui.board.clock.ChessClock;
import com.sdm.units.chessgame.gui.board.clock.DefaultChessClock;
import com.sdm.units.chessgame.gui.board.clock.PlayerClocksFactory;

import unittest.chessgame.gui.testdoubles.ClockListenerSpy;

@DisplayName("PlayerClocksFactory")
class PlayerClocksFactoryTest {

    @Test
    @DisplayName("should create a clock for each player")
    void shouldCreateClocksForEachPlayer() {
        PlayerClocksFactory factory = new PlayerClocksFactory(300000);
        EnumMap<ChessPieceColor, ChessClock> clocks = factory.createClocks();

        assertEquals(2, clocks.size());
        assertTrue(clocks.containsKey(ChessPieceColor.WHITE));
        assertTrue(clocks.containsKey(ChessPieceColor.BLACK));
    }

    @Test
    @DisplayName("each clock should start with the given initial time")
    void shouldInitializeClocksWithInitialTime() {
        long initialTime = 120000;
        PlayerClocksFactory factory = new PlayerClocksFactory(initialTime);

        EnumMap<ChessPieceColor, ChessClock> clocks = factory.createClocks();
        DefaultChessClock whiteClock = (DefaultChessClock) clocks.get(ChessPieceColor.WHITE);

        ClockListenerSpy listenerSpy = new ClockListenerSpy();
        whiteClock.setListener(listenerSpy);
        whiteClock.reset();

        assertEquals(initialTime, listenerSpy.getLastUpdated());
    }
}