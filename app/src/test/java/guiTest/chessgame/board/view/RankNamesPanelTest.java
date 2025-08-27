package guitest.chessgame.board.view;

import static org.assertj.core.api.Assertions.assertThat;

import javax.swing.JLabel;
import javax.swing.JPanel;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.sdm.units.chessgame.gui.board.view.RankNamesPanel;

@DisplayName("RankNamesPanel")
class RankNamesPanelTest {

    private RankNamesPanel panel;

    @BeforeEach
    void setUp() {
        panel = new RankNamesPanel();
    }

    @Test
    @DisplayName("should display eight rank labels")
    void shouldDisplayEightRankLabels() {
        assertThat(panel.getComponents()).hasSize(8);
    }

    @Test
    @DisplayName("should display rank labels in top to bottom order")
    void shouldDisplayRankLabelsInOrder() {
        assertThat(extractTexts(panel))
            .containsExactly("8","7","6","5","4","3","2","1");
    }

    @Test
    @DisplayName("should not apply padding")
    void shouldNotApplyPadding() {
        assertThat(panel.getBorder()).isNull();
    }

    private String[] extractTexts(JPanel panel) {
        return java.util.Arrays.stream(panel.getComponents())
            .map(c -> ((JLabel)c).getText())
            .toArray(String[]::new);
    }
}