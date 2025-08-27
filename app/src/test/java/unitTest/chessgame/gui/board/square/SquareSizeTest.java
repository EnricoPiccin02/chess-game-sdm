package unittest.chessgame.gui.board.square;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.sdm.units.chessgame.gui.board.square.SquareSize;

@DisplayName("SquareSize")
class SquareSizeTest {

    private SquareSize squareSize;

    @BeforeEach
    void setUp() {
        squareSize = null;
    }

    @Test
    @DisplayName("should define standard width of square")
    void shouldDefineStandardWidthOfSquare() {
        squareSize = SquareSize.SQUARE_WIDTH;
        assertEquals(80, squareSize.getSize());
    }

    @Test
    @DisplayName("should define standard height of square")
    void shouldDefineStandardHeightOfSquare() {
        squareSize = SquareSize.SQUARE_HEIGHT;
        assertEquals(80, squareSize.getSize());
    }
}