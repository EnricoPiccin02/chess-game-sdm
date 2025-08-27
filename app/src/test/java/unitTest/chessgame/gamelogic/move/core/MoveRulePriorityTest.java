package unittest.chessgame.gamelogic.move.core;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.sdm.units.chessgame.gamelogic.move.core.MoveRulePriority;

@DisplayName("MoveRulePriority")
class MoveRulePriorityTest {

    @Test
    @DisplayName("should return 2 for low priority")
    void shouldReturnTwoForLowPriority() {
        assertThat(MoveRulePriority.LOW_PRIORITY.getPriority()).isEqualTo(2);
    }

    @Test
    @DisplayName("should return 1 for medium priority")
    void shouldReturnOneForMediumPriority() {
        assertThat(MoveRulePriority.MEDIUM_PRIORITY.getPriority()).isEqualTo(1);
    }

    @Test
    @DisplayName("should return 0 for high priority")
    void shouldReturnZeroForHighPriority() {
        assertThat(MoveRulePriority.HIGH_PRIORITY.getPriority()).isEqualTo(0);
    }
}