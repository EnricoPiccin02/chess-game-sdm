package unittest.chessgame.gui.board.clock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.EnumMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.sdm.units.chessgame.gamelogic.domain.ChessPieceColor;
import com.sdm.units.chessgame.gui.board.clock.ChessClock;
import com.sdm.units.chessgame.gui.board.clock.DefaultChessClock;
import com.sdm.units.chessgame.gui.board.clock.PlayerClocksFactory;

import unittest.chessgame.gui.testdoubles.ClockListenerSpy;

@DisplayName("PlayerClocksFactory")
class PlayerClocksFactoryTest {

    private static final long FIVE_MINUTES = 300_000L;
    private static final long TWO_MINUTES = 120_000L;

    private PlayerClocksFactory factory;
    private EnumMap<ChessPieceColor, ChessClock> clocks;

    @Nested
    @DisplayName("when creating clocks")
    class CreatingClocks {

        @BeforeEach
        void setUp() {
            factory = new PlayerClocksFactory(FIVE_MINUTES);
            clocks = factory.createClocks();

        }

        @Test
        @DisplayName("should create one clock for white player")
        void shouldCreateClockForWhitePlayer() {
            assertTrue(clocks.containsKey(ChessPieceColor.WHITE));
        }

        @Test
        @DisplayName("should create one clock for black player")
        void shouldCreateClockForBlackPlayer() {
            assertTrue(clocks.containsKey(ChessPieceColor.BLACK));
        }

        @Test
        @DisplayName("should assign two clocks in total")
        void shouldAssignTwoClocksInTotal() {
            assertEquals(2, clocks.size());
        }
    }

    @Nested
    @DisplayName("when initializing clocks")
    class InitializingClocks {

        private ClockListenerSpy listenerSpy;

        @BeforeEach
        void setUp() {
            listenerSpy = new ClockListenerSpy();
            factory = new PlayerClocksFactory(TWO_MINUTES);
            clocks = factory.createClocks();
        }

        @Test
        @DisplayName("should start white clock with initial time")
        void shouldStartWhiteClockWithInitialTime() {
            DefaultChessClock whiteClock = (DefaultChessClock) clocks.get(ChessPieceColor.WHITE);

            whiteClock.setListener(listenerSpy);
            whiteClock.reset();

            assertEquals(TWO_MINUTES, listenerSpy.getLastUpdated());
        }

        @Test
        @DisplayName("should start black clock with initial time")
        void shouldStartBlackClockWithInitialTime() {
            DefaultChessClock blackClock = (DefaultChessClock) clocks.get(ChessPieceColor.BLACK);

            blackClock.setListener(listenerSpy);
            blackClock.reset();

            assertEquals(TWO_MINUTES, listenerSpy.getLastUpdated());
        }
    }
}