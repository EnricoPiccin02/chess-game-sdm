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
    class Ticking {

        @Test
        @DisplayName("should reduce time after one tick")
        void shouldReduceTimeAfterOneTick() {
            clock.start();
            clock.tick();

            assertEquals(4000, listenerSpy.getLastUpdated());
        }

        @Test
        @DisplayName("should update time after one tick")
        void shouldUpdateTimeAfterOneTick() {
            clock.start();
            clock.tick();

            assertTrue(listenerSpy.isUpdatedCalled());
        }

        @Test
        @DisplayName("should stop when time runs out")
        void shouldStopWhenTimeRunsOut() {
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
    class Resetting {

        @Test
        @DisplayName("should restore full time after reset")
        void shouldRestoreFullTimeAfterReset() {
            clock.start();
            clock.tick();
            clock.reset();

            assertEquals(FIVE_SECONDS, listenerSpy.getLastUpdated());
        }
    }
}