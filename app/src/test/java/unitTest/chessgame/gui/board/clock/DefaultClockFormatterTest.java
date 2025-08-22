package unittest.chessgame.gui.board.clock;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.sdm.units.chessgame.gui.board.clock.DefaultClockFormatter;

@DisplayName("DefaultClockFormatter")
class DefaultClockFormatterTest {

    private DefaultClockFormatter formatter;

    @BeforeEach
    void setUp() {
        formatter = new DefaultClockFormatter();
    }

    @Test
    @DisplayName("should format minutes and seconds with leading zeros")
    void shouldFormatMinutesAndSeconds() {
        assertEquals("\t0:05", formatter.formatTime(5000));
        assertEquals("\t1:00", formatter.formatTime(60000));
        assertEquals("\t2:09", formatter.formatTime(129000));
    }
}