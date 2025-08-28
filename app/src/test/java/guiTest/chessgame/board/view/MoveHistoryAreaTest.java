package guitest.chessgame.board.view;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.sdm.units.chessgame.gui.board.view.MoveHistoryArea;

@DisplayName("MoveHistoryArea")
class MoveHistoryAreaTest {

    private MoveHistoryArea historyArea;

    @BeforeEach
    void setUp() {
        historyArea = new MoveHistoryArea();
    }

    @Test
    @DisplayName("should display appended moves in the history")
    void shouldDisplayAppendedMovesInTheHistory() {
        historyArea.append("e2 -> e4");

        assertThat(historyArea.getText()).contains("e2 -> e4");
    }

    @Test
    @DisplayName("should remove all previous moves from the history")
    void shouldRemoveAllPreviousMovesFromTheHistory() {
        historyArea.append("e2 -> e4");
        historyArea.clear();

        assertThat(historyArea.getText()).isEmpty();
    }
}