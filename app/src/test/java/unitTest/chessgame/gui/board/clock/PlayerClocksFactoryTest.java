package unittest.chessgame.gui.board.clock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.EnumMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.sdm.units.chessgame.gamelogic.domain.ChessPieceColor;
import com.sdm.units.chessgame.gui.board.clock.ChessClock;
import com.sdm.units.chessgame.gui.board.clock.PlayerClocksFactory;

@DisplayName("PlayerClocksFactory")
class PlayerClocksFactoryTest {

    private static final long FIVE_MINUTES = 300_000L;

    private PlayerClocksFactory factory;
    private EnumMap<ChessPieceColor, ChessClock> clocks;

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
    @DisplayName("should create two clocks in total")
    void shouldCreateTwoClocksInTotal() {
        assertEquals(2, clocks.size());
    }
}