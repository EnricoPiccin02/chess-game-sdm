package unittest.chessgame.gamelogic.move.core;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.sdm.units.chessgame.gamelogic.move.core.MoveRulePriority;

@DisplayName("MoveRulePriority")
class MoveRulePriorityTest {

    @Test
    @DisplayName("should associate appropriate priority for low priority")
    void shouldAssociateAppropriatePriorityForLowPriority() {
        assertThat(MoveRulePriority.LOW_PRIORITY.getPriority()).isEqualTo(2);
    }

    @Test
    @DisplayName("should associate appropriate priority for medium priority")
    void shouldAssociateAppropriatePriorityForMediumPriority() {
        assertThat(MoveRulePriority.MEDIUM_PRIORITY.getPriority()).isEqualTo(1);
    }

    @Test
    @DisplayName("should associate appropriate priority for high priority")
    void shouldAssociateAppropriatePriorityForHighPriority() {
        assertThat(MoveRulePriority.HIGH_PRIORITY.getPriority()).isEqualTo(0);
    }
}