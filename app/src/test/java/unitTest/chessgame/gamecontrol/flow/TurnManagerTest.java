package unittest.chessgame.gamecontrol.flow;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.sdm.units.chessgame.gamecontrol.flow.TurnManager;
import com.sdm.units.chessgame.gamelogic.domain.ChessPieceColor;

@DisplayName("TurnManager")
class TurnManagerTest {

    private TurnManager turns;

    @BeforeEach
    void setUp() {
        turns = new TurnManager();
    }

    @Test
    @DisplayName("should start with white to move")
    void shouldStartWithWhite() {
        assertEquals(ChessPieceColor.WHITE, turns.current());
    }

    @Test
    @DisplayName("should swap turns between players")
    void shouldSwapTurns() {
        turns.swap();
        assertEquals(ChessPieceColor.BLACK, turns.current());
    }

    @Test
    @DisplayName("should reset to white when restarting")
    void shouldResetToWhite() {
        turns.swap();
        turns.start();
        assertEquals(ChessPieceColor.WHITE, turns.current());
    }
}