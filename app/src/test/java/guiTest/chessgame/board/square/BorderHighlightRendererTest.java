package guitest.chessgame.board.square;

import static org.junit.jupiter.api.Assertions.assertTrue;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.sdm.units.chessgame.gui.board.square.BorderHighlightRenderer;
import com.sdm.units.chessgame.gui.board.square.HighlightColor;
import com.sdm.units.chessgame.gui.board.square.HighlightRenderer;

@DisplayName("BorderHighlightRenderer")
class BorderHighlightRendererTest {

    private HighlightRenderer renderer;
    private JComponent dummyComponent;

    @BeforeEach
    void setUp() {
        renderer = new BorderHighlightRenderer();
        dummyComponent = new JPanel();
    }

    @Test
    @DisplayName("shouldRemoveHighlightWhenTypeIsNone")
    void shouldRemoveHighlightWhenTypeIsNone() {
        renderer.render(dummyComponent, HighlightColor.NONE);
        assertTrue(dummyComponent.getBorder() instanceof EmptyBorder);
    }

    @Test
    @DisplayName("shouldApplyColoredHighlightWhenTypeIsSet")
    void shouldApplyColoredHighlightWhenTypeIsSet() {
        renderer.render(dummyComponent, HighlightColor.SELECTABLE);
        assertTrue(dummyComponent.getBorder() instanceof LineBorder);
    }
}