package guitest.chessgame.board.view;

import static org.assertj.core.api.Assertions.assertThat;

import javax.swing.JLabel;
import javax.swing.JPanel;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.sdm.units.chessgame.gui.board.view.FileNamesPanel;

@DisplayName("FileNamesPanel")
class FileNamesPanelTest {

    private FileNamesPanel panel;

    @BeforeEach
    void setUp() {
        panel = new FileNamesPanel();
    }

    @Test
    @DisplayName("should display eight file labels")
    void shouldDisplayEightFileLabels() {
        assertThat(panel.getComponents()).hasSize(8);
    }

    @Test
    @DisplayName("should display file labels in left to right order")
    void shouldDisplayFileLabelsInOrder() {
        assertThat(extractTexts(panel))
            .containsExactly("A","B","C","D","E","F","G","H");
    }

    @Test
    @DisplayName("should apply horizontal padding")
    void shouldApplyHorizontalPadding() {
        assertThat(panel.getBorder()).isNotNull();
    }

    private String[] extractTexts(JPanel panel) {
        return java.util.Arrays.stream(panel.getComponents())
            .map(c -> ((JLabel)c).getText())
            .toArray(String[]::new);
    }
}