package unittest.chessgame.gui.board.square;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.awt.Color;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.sdm.units.chessgame.gui.board.square.HighlightColor;

@DisplayName("HighlightColor")
class HighlightColorTest {

    private HighlightColor highlightColor;

    @BeforeEach
    void setUp() {
        highlightColor = null;
    }

    @Test
    @DisplayName("should use yellow for selectable squares")
    void shouldUseYellowForSelectableSquares() {
        highlightColor = HighlightColor.SELECTABLE;
        assertEquals(Color.YELLOW, highlightColor.getColor());
    }

    @Test
    @DisplayName("should use red for clicked squares")
    void shouldUseRedForClickedSquares() {
        highlightColor = HighlightColor.CLICKED;
        assertEquals(Color.RED, highlightColor.getColor());
    }

    @Test
    @DisplayName("should use blue for hovered squares")
    void shouldUseBlueForHoveredSquares() {
        highlightColor = HighlightColor.HOVER;
        assertEquals(Color.BLUE, highlightColor.getColor());
    }

    @Test
    @DisplayName("should have no color for none")
    void shouldHaveNoColorForNone() {
        highlightColor = HighlightColor.NONE;
        assertEquals(null, highlightColor.getColor());
    }
}