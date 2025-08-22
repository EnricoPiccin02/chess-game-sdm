package unittest.chessgame.gui.board.clock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.sdm.units.chessgame.gui.board.clock.DefaultChessClock;

import unittest.chessgame.gui.testdoubles.ClockListenerSpy;

@DisplayName("DefaultChessClock")
class DefaultChessClockTest {

    private static final long FIVE_SECONDS = 5000L;

    private DefaultChessClock clock;
    private ClockListenerSpy listenerSpy;

    @BeforeEach
    void setUp() {
        clock = new DefaultChessClock(FIVE_SECONDS);
        listenerSpy = new ClockListenerSpy();
        clock.setListener(listenerSpy);
    }

    @Nested
    @DisplayName("when ticking")
    class TickingTests {

        @Test
        @DisplayName("should decrease remaining time on each tick")
        void shouldDecreaseRemainingTimeOnEachTick() {
            clock.start();

            clock.tick();
            assertEquals(4000, listenerSpy.getLastUpdated());

            clock.tick();
            assertEquals(3000, listenerSpy.getLastUpdated());
        }

        @Test
        @DisplayName("should notify listener on each update")
        void shouldNotifyListenerOnEachUpdate() {
            clock.start();
            clock.tick();

            assertTrue(listenerSpy.isUpdatedCalled());
            assertEquals(4000, listenerSpy.getLastUpdated());
        }

        @Test
        @DisplayName("should stop and notify expiration when time reaches zero")
        void shouldNotifyOnExpiration() {
            clock.start();

            clock.tick();
            clock.tick();
            clock.tick();
            clock.tick();
            clock.tick();

            assertTrue(listenerSpy.isExpiredCalled());
        }
    }

    @Nested
    @DisplayName("when resetting")
    class ResetTests {

        @Test
        @DisplayName("should restore full time and notify listener")
        void shouldRestoreFullTime() {
            clock.start();
            clock.tick();
            clock.reset();

            assertEquals(FIVE_SECONDS, listenerSpy.getLastUpdated());
        }
    }
}