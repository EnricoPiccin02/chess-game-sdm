package unittest.chessgame.gui.board.clock;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.sdm.units.chessgame.gui.board.clock.DefaultClockFormatter;

@DisplayName("DefaultClockFormatter")
class DefaultClockFormatterTest {

    private static final long FIVE_SECONDS = 5000L;
    private static final long ONE_MINUTE = 60_000L;
    private static final long TWO_MINUTES_NINE_SECONDS = 129_000L;

    private DefaultClockFormatter formatter;

    @BeforeEach
    void setUp() {
        formatter = new DefaultClockFormatter();
    }

    @Test
    @DisplayName("should format seconds with leading zeros")
    void shouldFormatSeconds() {
        assertEquals("\t0:05", formatter.formatTime(FIVE_SECONDS));
    }

    @Test
    @DisplayName("should format minutes with trailing zeros")
    void shouldFormatMinutes() {
        assertEquals("\t1:00", formatter.formatTime(ONE_MINUTE));
    }

    @Test
    @DisplayName("should format minutes and seconds")
    void shouldFormatMinutesAndSeconds() {
        assertEquals("\t2:09", formatter.formatTime(TWO_MINUTES_NINE_SECONDS));
    }
}